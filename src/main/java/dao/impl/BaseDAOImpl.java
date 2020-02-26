package dao.impl;

import dao.exceptions.DAOException;
import dao.connectionpool.ConnectionPool;
import dao.interfaces.GenericDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * This core class describes MySQL DAO realization. Implements most of all main operations with DB.
 *
 * @param <E>
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
abstract class BaseDAOImpl<E> implements GenericDAO<E> {

    private final static Logger logger = LogManager.getLogger(BaseDAOImpl.class);
    private ConnectionPool connectionPool;
    protected ResourceBundle queryData;

    /**
     * Constructor.
     *
     * @param source     - ConnectionPool.
     * @param bundleName - ResourceBundle.
     */
    public BaseDAOImpl(ConnectionPool source, String bundleName) {
        this.connectionPool = source;
        this.queryData = ResourceBundle.getBundle(bundleName);
    }

    /**
     * Basic method for create entity.
     *
     * @param entity - accepts entity.
     * @return - creating entity in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean create(E entity) throws DAOException {

        Connection connection = null;
        PreparedStatement insertStatement = null;

        if (entity == null) {
            throw new IllegalArgumentException("Entity can't be null!");
        }

        try {
            connection = getConnection();
            insertStatement = connection.prepareStatement(getQueryForCreate());

            // Preparing data to execute by setting entity info to query.
            prepareStatementForInsert(insertStatement, entity);

            // Execute transaction.
            return transaction(connection, insertStatement, 1);
        } catch (Exception e) {
            logger.error("There are problems with creating " + entity.getClass().getSimpleName() + " in the database! \n" + e);
            throw new DAOException(e);
        } finally {
            closeConnection(connection, insertStatement,"There are problems with to close the connection, for method: create, into the class: BaseDAOImpl!");
        }
    }

    /**
     * Basic method for update entity.
     *
     * @param oldEntity;
     * @param newEntity;
     * @return - updating entity in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean update(E oldEntity, E newEntity) throws DAOException {

        Connection connection = null;
        PreparedStatement updateStatement = null;

        if (oldEntity == null || newEntity == null) {
            throw new IllegalArgumentException("Entity can't be null!");
        }

        // Check that oldEntity is actually exist in DB.
        if (!checkExistence(oldEntity)) {
            return false;
        }

        try {
            connection = getConnection();
            updateStatement = connection.prepareStatement(getQueryForUpdate());

            // Preparing data to execute by setting entity info to query.
            prepareStatementForUpdate(updateStatement, newEntity, oldEntity);

            // Execute transaction.
            return transaction(connection, updateStatement, 1);
        } catch (Exception cause) {
            logger.error("There are problems with updating entity in the database! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection, updateStatement,"There are problems with to close the connection, for method: update, into the class: BaseDAOImpl!");
        }
    }

    /**
     * Basic method for delete entity.
     *
     * @param entity;
     * @return - delete entity in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean delete(E entity) throws DAOException {

        Connection connection = null;
        PreparedStatement deleteStatement = null;

        if (entity == null) {
            throw new IllegalArgumentException("Entity can't be null!");
        }

        // Check that entity is actually exist in DB.
        if (!checkExistence(entity)) {
            return false;
        }

        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDelete());

            // Preparing data to execute by setting entity info to query.
            prepareStatementForDelete(deleteStatement, entity);

            // Execute transaction.
            return transaction(connection, deleteStatement, 1);
        } catch (Exception cause) {
            logger.error("There are problems with delete entity in the database! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection, deleteStatement,"There are problems with to close the connection, for method: delete, into the class: BaseDAOImpl!");
        }
    }

    /**
     * Basic method for get all entity.
     *
     * @return - list all entities from the database.
     * @throws DAOException - throw the exception above.
     */
    public List<E> getAll() throws DAOException {

        Connection connection = null;
        PreparedStatement getAllStatement = null;

        try {
            connection = getConnection();
            getAllStatement = connection.prepareStatement(getQueryForRead());
            ResultSet rs = getAllStatement.executeQuery();
            return parseResultSet(rs);
        } catch (Exception cause) {
            logger.error("There are problems with get list all entities from the database! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection, getAllStatement,"There are problems with to close the connection, for method: getAll, into the class: BaseDAOImpl!");
        }
    }

    /**
     * Method to get a connection from a connection pool.
     *
     * @return - returns connection from data source.
     * @throws DAOException - throw the exception above.
     */
    protected Connection getConnection() throws DAOException {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            logger.error("There are problems getting a connection pool! \n" + e);
            throw new DAOException(e);
        }
    }


    // Method for close connection.
    protected void closeConnection(Connection connection, PreparedStatement statement,String logMsg) {
        if (connection != null && statement != null) {
            try {
                statement.close();
                connectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                logger.error(logMsg + " \n" + e);
            }
        }
    }

    /**
     * Method provides simple transaction interface.
     * Operation has 1 attempt to success, otherwise considered as failure.
     * If records that transaction modified does'nt match to expected value - transaction considered as failure,
     * if value of modified rows unnecessary(affects 1 and more rows) - set expectedModificationCount param as - 1.
     *
     * @param statement                 - PreparedStatement to execute.
     * @param expectedModificationCount - expected amount of rows that transaction will modify.
     * @return - "true" - in case of success transaction, "false" - otherwise.
     * @throws DAOException - throw the exception above.
     */
    protected boolean transaction(Connection connection, PreparedStatement statement, int expectedModificationCount) throws DAOException {
        int affectedRows;
        try {

            // Executing operation.
            connection.setAutoCommit(false);
            affectedRows = statement.executeUpdate();

            /*
             * If affected rows is necessary value, and its value doesn't match to expected - throw exception.
             */
            if (expectedModificationCount != -1 && affectedRows != expectedModificationCount) {
                throw new SQLException();
            } else if (affectedRows < 1) {
                throw new SQLException();
            }

            // In case of successful operation - commit.
            connection.commit();

            // At last - close statement and exit loop.
            statement.close();
            return true;
        } catch (SQLException e) {
            logger.error("There are problems from operation transaction! \n" + e);
            // If operation failed - try to rollback.
            try {
                connection.rollback();
            } catch (SQLException cause) {
                logger.error("There are problems from operation rollback! \n" + cause);
                throw new DAOException(cause);
            }
        }
        return false;
    }

    /**
     * Method check existence entity.
     *
     * @param entity;
     * @return - "true" if entity exist in DB, and "false" otherwise.
     * @throws DAOException - throw the exception above.
     */
    protected boolean checkExistence(E entity) throws DAOException {
        String existenceSQL = getQueryForReadEntity(entity);
        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(existenceSQL);
            return rs.next();
        } catch (SQLException e) {
            logger.error("There are problems from operation check existence entity! \n" + e);
            throw new DAOException(e);
        }
    }

    /**
     * Method returns specified entity parsed from result set.
     *
     * @param rs - ResultSet.
     * @return - Entity specified by subclass
     * @throws DAOException - throw the exception above.
     */
    protected abstract E parseEntity(ResultSet rs) throws DAOException;

    /**
     * Method returns specified entity parsed from result set.
     *
     * @param rs - ResultSet.
     * @return - list specified by subclass entity
     * @throws DAOException - throw the exception above.
     */
    protected List<E> parseResultSet(ResultSet rs) throws DAOException {
        List<E> result = new ArrayList<E>();
        try {
            while (rs.next()) {
                result.add(parseEntity(rs));
            }
        } catch (SQLException e) {
            logger.error("There are problems from operation parsed from result set! \n" + e);
            throw new DAOException(e);
        }
        return result;
    }

    /**
     * Method returns list of objects witch match to specified query.
     *
     * @param statement - query.
     * @return - null if no records match to query.
     * @throws DAOException - throw the exception above.
     */
    protected List<E> getListByQuery(PreparedStatement statement) throws DAOException {
        try {
            ResultSet rs = statement.executeQuery();
            return parseResultSet(rs);
        } catch (SQLException cause) {
            logger.error("There are problems with getting list by query! \n" + cause);
            throw new DAOException(cause);
        }
    }

    /**
     * Method returns first object witch match to specified query.
     *
     * @param statement - query.
     * @return - null if no records match to query.
     * @throws DAOException - throw the exception above.
     */
    protected E getEntityByQuery(PreparedStatement statement) throws DAOException {
        E result = null;
        try {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = parseEntity(rs);
            }
        } catch (SQLException e) {
            logger.error("There are problems with getting entity by query! \n" + e);
            throw new DAOException(e);
        }
        return result;
    }

    /**
     * Method to get query create.
     *
     * @return - prepared SQL Insert string.
     */
    protected String getQueryForCreate() {
        return queryData.getString("CREATE");
    }

    /**
     * Method to get query read.
     *
     * @return - prepared SQL Select string.
     */
    protected String getQueryForRead() {
        return queryData.getString("READ");
    }

    /**
     * Method to get query read by key.
     *
     * @return - prepared SQL Select string with special 'key' value in some place of string.
     */
    protected String getQueryForReadByKey(String key) {
        return queryData.getString("READ_BY_KEY").replace("key!", key);
    }

    /**
     * Method to get query read client by login and pass.
     *
     * @return - prepared SQL Select string with special 'key' value in some place of string.
     */
    protected String getQueryForReadByLoginAndPass() {
        return queryData.getString("READ_BY_LOGIN_PASS");
    }

    /**
     * Method to get query update.
     *
     * @return - prepared SQL Update string.
     */
    protected String getQueryForUpdate() {
        return queryData.getString("UPDATE");
    }

    /**
     * Method to get query delete.
     *
     * @return - prepared SQL Delete string.
     */
    protected String getQueryForDelete() {
        return queryData.getString("DELETE");
    }

    /**
     * Method to get query delete by key.
     *
     * @return - prepared SQL Delete string with special 'key' value in some place of string.
     */
    protected String getQueryForDeleteByKey(String key) {
        return queryData.getString("DELETE_BY_KEY").replace("key!", key);
    }

    /**
     * Method to get query for read entity.
     *
     * @return - prepared SQL select string with prepared parameter.
     */
    protected abstract String getQueryForReadEntity(E entity);

    /**
     * Method prepares statement for insert, sets specified entity values to '?' signs.
     *
     * @param insertStatement;
     * @param entity;
     * @throws DAOException - throw the exception above.
     */
    protected abstract void prepareStatementForInsert(PreparedStatement insertStatement, E entity) throws DAOException;

    /**
     * Method prepares statement for update.
     *
     * @param updateStatement;
     * @param newEntity;
     * @param oldEntity;
     * @throws DAOException - throw the exception above.
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement updateStatement, E newEntity, E oldEntity) throws DAOException;

    /**
     * Method prepares statement for delete.
     *
     * @param deleteStatement;
     * @param entity;
     * @throws DAOException - throw the exception above.
     */
    protected abstract void prepareStatementForDelete(PreparedStatement deleteStatement, E entity) throws DAOException;
}