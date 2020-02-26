package dao.impl;

import dao.interfaces.ClientDAO;
import dao.exceptions.DAOException;
import dao.connectionpool.ConnectionPool;
import models.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class to work with ClientDAO.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class ClientDAOImpl extends BaseDAOImpl<Client> implements ClientDAO {
    private final static Logger logger = LogManager.getLogger(ClientDAOImpl.class);

    /**
     * Constructor.
     *
     * @param source - ConnectionPool.
     */
    public ClientDAOImpl(ConnectionPool source) {
        super(source, "client");
    }

    /**
     * Method get client that matches to specified id.
     *
     * @param id - client id.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    @Override
    public Client getById(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("client_id"));
            statement.setInt(1, id);
            logger.info("Query to get client by id successful, in method: getById, into the class: ClientDAOImpl!");
            return super.getEntityByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getById, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getById, into the class: ClientDAOImpl!");
        }
    }

    /**
     * Method get client that matches to specified login.
     *
     * @param clientLogin - client login.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    @Override
    public Client getByLogin(String clientLogin) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("client_login"));
            statement.setString(1, clientLogin);
            logger.info("Query to get client by login successful, in method: getByLogin, into the class: ClientDAOImpl!");
            return super.getEntityByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getByLogin, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByLogin, into the class: ClientDAOImpl!");
        }
    }

    /**
     * Method get client that matches to specified login and pass.
     *
     * @param clientLogin - client login.
     * @param clientPass - client pass.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    public Client getByLoginAndPass(String clientLogin, String clientPass) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        Client client;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByLoginAndPass());

            statement.setString(1, clientLogin);
            statement.setString(2, clientPass);

            ResultSet resultSet = statement.executeQuery();
            List<Client> resSetCount = parseResultSet(resultSet);
            if (resSetCount.size() == 0) {
                client = null;
            } else {
                client = resSetCount.get(0);
            }
            logger.info("Query to get client by login and pass successful, in method: getByLoginAndPass, into the class: ClientDAOImpl!");
        } catch (SQLException e) {
            logger.error("Failed to get connection, for method: getByLoginAndPass, into the class: ClientDAOImpl! \n" + e);
            throw new DAOException(e);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByLoginAndPass, into the class: ClientDAOImpl!");
        }
        return client;
    }

    /**
     * Method to get list of client which matches by specified name.
     *
     * @param name - client name.
     * @return - null if no users match to argument.
     * @throws DAOException;
     */
    @Override
    public List<Client> getByName(String name) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("client_name"));
            statement.setString(1, name);
            logger.info("Query to get client by name successful, in method: getByName, into the class: ClientDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getByName, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByName, into the class: ClientDAOImpl!");
        }
    }

    /**
     * Method delete of client which matches by specified name.
     *
     * @param name - client name.
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteByName(String name) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDeleteByKey("client_name"));
            deleteStatement.setString(1, name);
            logger.info("Query to delete client by name successful, in method: deleteByName, into the class: ClientDAOImpl!");
            return transaction(connection, deleteStatement, -1);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteByName, into the class: ClientDAOImpl!! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteByName, into the class: ClientDAOImpl!");
        }
    }

    @Override
    protected String getQueryForReadEntity(Client entity) {
        return queryData.getString("READ_ENTITY").replace("key!", String.valueOf(entity.getClientId()));
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement insertionStatement, Client entity) throws DAOException {
        try {
            insertionStatement.setString(1, entity.getName());
            insertionStatement.setString(2, entity.getClientLogin());
            insertionStatement.setString(3, entity.getPassword());
            insertionStatement.setBoolean(4, entity.getBlackList());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForInsert, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement updateStatement, Client newEntity, Client oldEntity) throws DAOException {
        try {
            updateStatement.setString(1, newEntity.getName());
            updateStatement.setString(2, newEntity.getClientLogin());
            updateStatement.setString(3, newEntity.getPassword());
            updateStatement.setBoolean(4, newEntity.getBlackList());
            updateStatement.setInt(5, oldEntity.getClientId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForUpdate, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement deleteStatement, Client entity) throws DAOException {
        try {
            deleteStatement.setInt(1, entity.getClientId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForDelete, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected Client parseEntity(ResultSet rs) throws DAOException {
        try {
            return new Client(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getBoolean(5));
        } catch (SQLException cause) {
            logger.error("There are problems with parse entity, into the class: ClientDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }
}