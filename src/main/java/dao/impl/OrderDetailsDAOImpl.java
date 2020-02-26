package dao.impl;

import dao.interfaces.OrderDetailsDAO;
import dao.exceptions.DAOException;
import dao.connectionpool.ConnectionPool;
import models.OrderDetails;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class to work with OrderDetailsDAO.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class OrderDetailsDAOImpl extends BaseDAOImpl<OrderDetails> implements OrderDetailsDAO {

    private final static Logger logger = LogManager.getLogger(OrderDetailsDAOImpl.class);

    /**
     * Constructor.
     *
     * @param source - ConnectionPool.
     */
    public OrderDetailsDAOImpl(ConnectionPool source) {
        super(source, "order_details");
    }

    /**
     * Method to get orderDetails match to specified order id.
     *
     * @param orderId;
     * @return - null if there was no such orderId.
     * @throws DAOException;
     */
    @Override
    public List<OrderDetails> getByOrderId(int orderId) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("order_id"));
            statement.setInt(1, orderId);
            logger.info("Query to get all orderDetails by orderId successful, in method: getByOrderId, into the class: OrderDetailsDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getByOrderId, into the class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByOrderId, into the class: OrderDetailsDAOImpl!");
        }
    }

    /**
     * Method to get list orderDetails match to specified product id.
     *
     * @param productId;
     * @return - null if there was no such orderId.
     * @throws DAOException;
     */
    @Override
    public List<OrderDetails> getByProductId(int productId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("product_id"));
            statement.setInt(1, productId);
            logger.info("query to get all orderDetails by productId successful, in method: getByProductId, into the class: OrderDetailsDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getByProductId, into the class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getByProductId, into the class: OrderDetailsDAOImpl!");
        }
    }

    /**
     * Method deletes all records which match to specified order id.
     *
     * @param orderId;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteAllByOrderId(int orderId) throws DAOException {

        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDelete());
            deleteStatement.setInt(1, orderId);
            logger.info("Query to delete all orderDetails by orderId successful, in method: deleteAllByOrderId, into the class: OrderDetailsDAOImpl!");
            return transaction(connection, deleteStatement, -1);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteAllByOrderId, into the class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteAllByOrderId, into the class: OrderDetailsDAOImpl!");
        }
    }

    protected String getQueryForReadNonOrdered() {
        return queryData.getString("READ_NON_ORDERED");
    }

    @Override
    protected String getQueryForReadEntity(OrderDetails entity) {
        return queryData.getString("READ_ENTITY").replace("key!", String.valueOf(entity.getOrderId()));
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement insertionStatement, OrderDetails entity) throws DAOException {
        try {
            insertionStatement.setInt(1, entity.getOrderId());
            insertionStatement.setInt(2, entity.getProductId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForInsert, in class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement updateStatement, OrderDetails newEntity, OrderDetails oldEntity) throws DAOException {
        try {
            updateStatement.setInt(1, newEntity.getOrderId());
            updateStatement.setInt(2, newEntity.getProductId());
            updateStatement.setInt(3, oldEntity.getDetailsId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForUpdate, in class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement deleteStatement, OrderDetails entity) throws DAOException {
        try {
            deleteStatement.setInt(1, entity.getDetailsId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForDelete, in class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected OrderDetails parseEntity(ResultSet rs) throws DAOException {
        try {
            return new OrderDetails(rs.getInt(1), rs.getInt(2), rs.getInt(3));
        } catch (SQLException cause) {
            logger.error("There are problems with parse entity, in class: OrderDetailsDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }
}