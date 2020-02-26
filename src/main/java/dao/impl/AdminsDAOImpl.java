package dao.impl;

import dao.connectionpool.ConnectionPool;
import dao.exceptions.DAOException;
import dao.interfaces.AdminsDAO;
import models.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * This class to work with AdminsDAO.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class AdminsDAOImpl extends BaseDAOImpl<Admin> implements AdminsDAO {

    private final static Logger logger = LogManager.getLogger(AdminsDAOImpl.class);

    /**
     * Constructor.
     *
     * @param source - ConnectionPool.
     */
    public AdminsDAOImpl(ConnectionPool source) {
        super(source, "admin");
    }

    /**
     * Method to get admin by specified id.
     *
     * @param adminId - admin id
     * @return - null if there was no such admin.
     * @throws DAOException;
     */
    @Override
    public Admin getById(Integer adminId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("admin_id"));
            statement.setInt(1, adminId);
            logger.info("Query to get admin by id successful, in method: getById, into the class: AdminsDAOImpl!");
            return super.getEntityByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getById, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getById, into the class: AdminsDAOImpl!");
        }
    }

    /**
     * Method to get list of admin which matches by specified name.
     *
     * @param adminName - admin name.
     * @return - null if no admin match to argument.
     * @throws DAOException;
     */
    @Override
    public List<Admin> getByName(String adminName) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("admin_name"));
            statement.setString(1, adminName);
            logger.info("query to get admin by name successful, in method: getByName, into the class: AdminsDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getByName, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByName, into the class: AdminsDAOImpl!");
        }
    }

    /**
     * Method to get list of admin which matches by specified login.
     *
     * @param adminLogin - admin login.
     * @return - null if no admin match to argument.
     * @throws DAOException;
     */
    @Override
    public Admin getByLogin(String adminLogin) throws DAOException {
        Connection connection = null;

        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("admin_login"));
            statement.setString(1, adminLogin);
            logger.info("query to get admin by name successful, in method: getByLogin, into the class: AdminsDAOImpl!");
            return super.getEntityByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getByLogin, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByLogin, into the class: AdminsDAOImpl!");
        }
    }

    /**
     * Method get admin that matches to specified login and pass.
     *
     * @param adminLogin - admin login.
     * @param adminPass  - admin pass.
     * @return - null if there was no such admin.
     * @throws DAOException;
     */
    public Admin getByLoginAndPass(String adminLogin, String adminPass) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        Admin admin;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByLoginAndPass());

            statement.setString(1, adminLogin);
            statement.setString(2, adminPass);

            ResultSet resultSet = statement.executeQuery();
            List<Admin> resSetCount = parseResultSet(resultSet);
            if (resSetCount.size() == 0) {
                admin = null;
            } else {
                admin = resSetCount.get(0);
            }
            logger.info("Query to get client by login and pass successful, in method: getByLoginAndPass, into the class: AdminsDAOImpl!");
        } catch (SQLException e) {
            logger.error("Failed to get connection, for method: getByLoginAndPass, into the class: AdminsDAOImpl! \n" + e);
            throw new DAOException(e);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByLoginAndPass, into the class: AdminsDAOImpl!");
        }
        return admin;
    }

    /**
     * Method delete of admin which matches by specified login.
     *
     * @param adminLogin - admin name;
     * @return "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteByLogin(String adminLogin) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDeleteByKey("admin_login"));
            deleteStatement.setString(1, adminLogin);
            logger.info("Query to delete admin by login successful, in method: deleteByLogin, into the class: AdminsDAOImpl!");
            return transaction(connection, deleteStatement, -1);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteByLogin, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteByLogin, into the class: AdminsDAOImpl!");
        }
    }


    @Override
    protected Admin parseEntity(ResultSet rs) throws DAOException {
        try {
            return new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
        } catch (SQLException cause) {
            logger.error("There are problems with parse entity, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected String getQueryForReadEntity(Admin entity) {
        return queryData.getString("READ_ENTITY").replace("key!", String.valueOf(entity.getAdminId()));
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement insertionStatement, Admin entity) throws DAOException {
        try {
            insertionStatement.setString(1, entity.getAdminName());
            insertionStatement.setString(2, entity.getAdminLogin());
            insertionStatement.setString(3, entity.getAdminPass());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForInsert, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement updateStatement, Admin newEntity, Admin oldEntity) throws DAOException {
        try {
            updateStatement.setString(1, newEntity.getAdminName());
            updateStatement.setString(2, newEntity.getAdminLogin());
            updateStatement.setString(3, newEntity.getAdminPass());
            updateStatement.setInt(4, oldEntity.getAdminId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForUpdate, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement deleteStatement, Admin entity) throws DAOException {
        try {
            deleteStatement.setInt(1, entity.getAdminId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForDelete, into the class: AdminsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    // Methods for creating entities through the administrator.
    @Override
    public void createClient(Client entity) throws DAOException {
        DAOFactoryImpl.getInstance().getClientDAO().create(entity);
    }

    @Override
    public void createOrder(Order entity) throws DAOException {
        DAOFactoryImpl.getInstance().getOrderDAO().create(entity);
    }

    @Override
    public void createOrderDetails(OrderDetails entity) throws DAOException {
        DAOFactoryImpl.getInstance().getOrderDetailsDAO().create(entity);
    }

    @Override
    public void createProduct(Product entity) throws DAOException {
        DAOFactoryImpl.getInstance().getProductDAO().create(entity);
    }

    // Methods for updating entities through the administrator.
    @Override
    public void updateClient(Client oldEntity, Client newEntity) throws DAOException {
        DAOFactoryImpl.getInstance().getClientDAO().update(oldEntity, newEntity);
    }

    @Override
    public void updateOrder(Order oldEntity, Order newEntity) throws DAOException {
        DAOFactoryImpl.getInstance().getOrderDAO().update(oldEntity, newEntity);
    }

    @Override
    public void updateOrderDetails(OrderDetails oldEntity, OrderDetails newEntity) throws DAOException {
        DAOFactoryImpl.getInstance().getOrderDetailsDAO().update(oldEntity, newEntity);
    }

    @Override
    public void updateProduct(Product oldEntity, Product newEntity) throws DAOException {
        DAOFactoryImpl.getInstance().getProductDAO().update(oldEntity, newEntity);
    }

    // Methods for deleting entities through the administrator.
    @Override
    public void deleteClient(Client entity) throws DAOException {
        DAOFactoryImpl.getInstance().getClientDAO().delete(entity);
    }

    @Override
    public void deleteOrder(Order entity) throws DAOException {
        DAOFactoryImpl.getInstance().getOrderDAO().delete(entity);
    }

    @Override
    public void deleteOrderDetails(OrderDetails entity) throws DAOException {
        DAOFactoryImpl.getInstance().getOrderDetailsDAO().delete(entity);
    }

    @Override
    public void deleteProduct(Product entity) throws DAOException {
        DAOFactoryImpl.getInstance().getProductDAO().delete(entity);
    }

    // Methods for get all entities through the administrator.
    @Override
    public List<Client> getAllClient() throws DAOException {
        return DAOFactoryImpl.getInstance().getClientDAO().getAll();
    }

    @Override
    public Client getClientByLoginAndPass(String clientLogin, String clientPass) throws DAOException {
        return DAOFactoryImpl.getInstance().getClientDAO().getByLoginAndPass(clientLogin, clientPass);
    }

    @Override
    public List<Order> getAllOrdersByOrderStatus(String orderStatus) throws DAOException {
        return DAOFactoryImpl.getInstance().getOrderDAO().getAllByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> getAllOrder() throws DAOException {
        return DAOFactoryImpl.getInstance().getOrderDAO().getAll();
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() throws DAOException {
        return DAOFactoryImpl.getInstance().getOrderDetailsDAO().getAll();
    }

    @Override
    public List<Product> getAllProduct() throws DAOException {
        return DAOFactoryImpl.getInstance().getProductDAO().getAll();
    }
}
