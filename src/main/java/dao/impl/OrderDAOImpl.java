package dao.impl;

import dao.interfaces.OrderDAO;
import dao.exceptions.DAOException;
import dao.connectionpool.ConnectionPool;
import models.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class to work with OrderDAO.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class OrderDAOImpl extends BaseDAOImpl<Order> implements OrderDAO {

    private final static Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    /**
     * Constructor.
     *
     * @param source - ConnectionPool.
     */
    public OrderDAOImpl(ConnectionPool source) {
        super(source, "order");
    }

    /**
     * Method to get order id which matches to specified order;
     *
     * @param order;
     * @return - 1 if there was no such order.
     * @throws DAOException;
     */
    @Override
    public int getOrderId(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getIdSQL());
            statement.setInt(1, order.getClientId());
            statement.setString(2, order.getOrderStatus());
            statement.setDouble(3, order.getTotalCost());

            Order result = super.getEntityByQuery(statement);

            if (result == null) {
                return -1;
            } else {
                logger.info("Query to get order Id successful, in method: getOrderId, into the class: OrderDAOImpl!");
                return result.getOrderId();
            }
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getOrderId, into the class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getOrderId, into the class: OrderDAOImpl!");
        }
    }

    /**
     * Method to get Order which matches to specified order ID;
     *
     * @param orderId - order id.
     * @return - null if there was no such order.
     * @throws DAOException;
     */
    @Override
    public Order getOrderByID(Integer orderId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("order_id"));
            statement.setInt(1, orderId);
            logger.info("Query to get order by orderId successful, in method: getOrderByID, into the class: OrderDAOImpl!");
            return super.getEntityByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getOrderByID, into the class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getOrderByID, into the class: OrderDAOImpl!");
        }
    }

    /**
     * Method to get list of Orders which matches to specified order status;
     *
     * @param orderStatus;
     * @return - null if no orders match to argument.
     * @throws DAOException;
     */
    @Override
    public List<Order> getAllByOrderStatus(String orderStatus) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("order_status"));
            statement.setString(1, orderStatus);
            logger.info("Query to get all order by orderStatus successful, in method: getAllByOrderStatus, into the class: OrderDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getAllByOrderStatus, into the class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getAllByOrderStatus, into the class: OrderDAOImpl!");
        }
    }

    /**
     * Method to get list of Orders which matches to specified client id;
     *
     * @param clientId;
     * @return - null if no orders match to argument.
     * @throws DAOException;
     */
    @Override
    public List<Order> getAllByClient(Integer clientId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("client_id"));
            statement.setInt(1, clientId);
            logger.info("Query to get all order by clientId successful, in method: getAllByClient, into the class: OrderDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getAllByClient, into the class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getAllByClient, into the class: OrderDAOImpl!");
        }
    }

    /**
     * Method delete all Orders which matches to specified order status;
     *
     * @param orderStatus;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteAllByOrderStatus(String orderStatus) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDeleteByKey("order_status"));
            deleteStatement.setString(1, orderStatus);
            logger.info("Query to delete all order by orderStatus successful, in method: deleteAllByOrderStatus, into the class: OrderDAOImpl!");
            return transaction(connection, deleteStatement, -1);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteAllByOrderStatus, into the class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteAllByOrderStatus, into the class: OrderDAOImpl!");
        }
    }

    /**
     * Method delete all Orders which matches to specified client id;
     *
     * @param clientId;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteAllByClient(Integer clientId) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDeleteByKey("client_id"));
            deleteStatement.setInt(1, clientId);
            logger.info("Query to delete all order by clientId successful, in method: deleteAllByClient, into the class: OrderDAOImpl!");
            return transaction(connection, deleteStatement, -1);

        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteAllByClient, into the class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteAllByClient, into the class: OrderDAOImpl!");
        }
    }

    @Override
    protected String getQueryForReadEntity(Order entity) {
        return queryData.getString("READ_ENTITY").replace("key!", String.valueOf(entity.getOrderId()));
    }

    protected String getIdSQL() {
        return queryData.getString("READ_ID");
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement insertionStatement, Order entity) throws DAOException {
        try {
            insertionStatement.setInt(1, entity.getClientId());
            insertionStatement.setString(2, entity.getOrderStatus());
            insertionStatement.setDouble(3, entity.getTotalCost());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForInsert, in class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement updateStatement, Order newEntity, Order oldEntity) throws DAOException {
        try {
            updateStatement.setInt(1, newEntity.getClientId());
            updateStatement.setString(2, newEntity.getOrderStatus());
            updateStatement.setDouble(3, newEntity.getTotalCost());
            updateStatement.setInt(4, oldEntity.getOrderId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForUpdate, in class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement deleteStatement, Order entity) throws DAOException {
        try {
            deleteStatement.setInt(1, entity.getOrderId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForDelete, in class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected Order parseEntity(ResultSet rs) throws DAOException {
        try {
            return new Order(rs.getInt(1), rs.getInt(2),
                    rs.getString(3), rs.getDouble(4));
        } catch (SQLException cause) {
            logger.error("There are problems with parse entity, in class: OrderDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }
}