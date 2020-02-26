package dao.impl;

import dao.interfaces.ProductDAO;
import dao.exceptions.DAOException;
import dao.connectionpool.ConnectionPool;
import models.Order;
import models.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class to work with ProductDAO.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class ProductDAOImpl extends BaseDAOImpl<Product> implements ProductDAO {

    private final static Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    /**
     * Constructor.
     *
     * @param source - ConnectionPool.
     */
    public ProductDAOImpl(ConnectionPool source) {
        super(source, "product");
    }

    /**
     * Method to get product id which matches to specified product.
     *
     * @param product;
     * @return - 1 if there was no such product.
     * @throws DAOException;
     */
    @Override
    public int getProductId(Product product) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForId());
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            Product result = super.getEntityByQuery(statement);

            if (result == null) {
                return -1;
            } else {
                logger.info("Query to get product id by product = '" + product + "' successful, in method: getProductId, into the class: ProductDAOImpl!");
                return result.getId();
            }
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getProductId, into the class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getProductId, into the class: ProductDAOImpl!");
        }
    }

    /**
     * Method to get Product which matches to specified product ID.
     *
     * @param productId;
     * @return - null if there was no such product.
     * @throws DAOException;
     */
    @Override
    public Product getProductByID(int productId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("product_id"));
            statement.setInt(1, productId);
            logger.info("Query to get product by productId = '" + productId + "' successful, in method: getProductByID, into the class: ProductDAOImpl!");
            return super.getEntityByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getProductByID, into the class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getProductByID, into the class: ProductDAOImpl!");
        }
    }

    /**
     * Method to get list of Products which matches to specified product name.
     *
     * @param productName;
     * @return - null if no products match to argument.
     * @throws DAOException;
     */
    @Override
    public List<Product> getAllByName(String productName) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForReadByKey("product_name"));
            statement.setString(1, productName);
            logger.info("Query to get all product by productName = '" + productName + "' successful, in method: getAllByName, into the class: ProductDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getAllByName, into the class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getAllByName, into the class: ProductDAOImpl!");
        }
    }

    /**
     * Method to get list of Products which figures in specified order.
     *
     * @param order;
     * @return - null if no products match to argument.
     * @throws DAOException;
     */
    @Override
    public List<Product> getProductsFromOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryForJoin());
            statement.setInt(1, order.getOrderId());
            logger.info("Query to get all product from order = '" + order + "' successful, in method: getProductsFromOrder, into the class: ProductDAOImpl!");
            return super.getListByQuery(statement);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: getProductsFromOrder, into the class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,statement,"There are problems with to close the connection, for method: getProductsFromOrder, into the class: ProductDAOImpl!");
        }
    }

    /**
     * Method delete product which matches to specified product id.
     *
     * @param productID;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteById(int productID) throws DAOException {
        return super.delete(new Product(productID, "", 0.0));
    }

    /**
     * Method deletes all Products which matches to specified product name.
     *
     * @param productName;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteAllByName(String productName) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDeleteByKey("product_name"));
            deleteStatement.setString(1, productName);
            logger.info("Query to delete all product by productName = '" + productName + "' successful, in method: deleteAllByName, into the class: ProductDAOImpl!");
            return transaction(connection, deleteStatement, -1);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteAllByName, into the class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteAllByName, into the class: ProductDAOImpl!");
        }
    }

    /**
     * Method deletes all Products which matches to specified product price.
     *
     * @param productPrice;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    @Override
    public boolean deleteAllByPrice(Double productPrice) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = getConnection();
            deleteStatement = connection.prepareStatement(getQueryForDeleteByKey("product_price"));
            deleteStatement.setDouble(1, productPrice);
            logger.info("Query to delete all product by productPrice = '" + productPrice + "' successful, in method: deleteAllByOrderId, into the class: ProductDAOImpl!");
            return transaction(connection, deleteStatement, -1);
        } catch (SQLException cause) {
            logger.error("Failed to get connection, for method: deleteAllByPrice, into the class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        } finally {
            closeConnection(connection,deleteStatement,"There are problems with to close the connection, for method: deleteAllByPrice, into the class: ProductDAOImpl!");
        }
    }

    @Override
    protected String getQueryForReadEntity(Product entity) {
        return queryData.getString("READ_ENTITY").replace("key!", String.valueOf(entity.getId()));
    }

    protected String getQueryForJoin() {
        return queryData.getString("READ_JOIN");
    }

    protected String getQueryForId() {
        return queryData.getString("READ_ID");
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement insertionStatement, Product entity) throws DAOException {
        try {
            insertionStatement.setString(1, entity.getName());
            insertionStatement.setDouble(2, entity.getPrice());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForInsert, in class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement updateStatement, Product newEntity, Product oldEntity) throws DAOException {
        try {
            updateStatement.setString(1, newEntity.getName());
            updateStatement.setDouble(2, newEntity.getPrice());
            updateStatement.setInt(3, oldEntity.getId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForUpdate, in class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement deleteStatement, Product entity) throws DAOException {
        try {
            deleteStatement.setInt(1, entity.getId());
        } catch (SQLException cause) {
            logger.error("There are problems with to query for prepareStatementForDelete, in class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }

    @Override
    protected Product parseEntity(ResultSet rs) throws DAOException {
        try {
            return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3));
        } catch (SQLException cause) {
            logger.error("There are problems with parse entity, in class: ProductDAOImpl! \n" + cause);
            throw new DAOException(cause);
        }
    }
}