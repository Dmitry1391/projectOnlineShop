package dao.impl;

import dao.connectionpool.ConnectionPool;
import dao.connectionpool.ConnectionPoolImpl;
import dao.interfaces.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * This class - is non-static singleton impl,
 * we dont need new instance of DAO object every time when we use MySQL methods,
 * and invoke of this methods doesn't modify state of objects in java heap.
 * Every method invoke - is separate transaction with own connection from connections pool.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class DAOFactoryImpl implements DAOFactory {

    private final static Logger logger = LogManager.getLogger(DAOFactoryImpl.class);

    private static DAOFactoryImpl factoryInstance;
    private ClientDAOImpl userInstance;
    private OrderDAOImpl orderInstance;
    private OrderDetailsDAOImpl detailsInstance;
    private ProductDAOImpl productInstance;
    private AdminsDAOImpl adminsInstance;

    /*
     * Connection pool
     */
    private ConnectionPool pool;

    /**
     * Private constructor. I use custom JDBC Connection Pool.
     */
    private DAOFactoryImpl() {
        try {
            this.pool = ConnectionPoolImpl.create();
        } catch (SQLException e) {
            logger.error("Exception on data source initialization", e);
        }
    }

    /**
     * Method to get singleton instance of factory.
     *
     * @return - factory instance.
     */
    public static DAOFactory getInstance() {
        if (factoryInstance == null) {
            synchronized (DAOFactoryImpl.class) {
                if (factoryInstance == null) {
                    factoryInstance = new DAOFactoryImpl();
                }
            }
        }
        return factoryInstance;
    }

    /**
     * Method to get singleton instance of Admin.
     *
     * @return - Admin instance.
     */
    @Override
    public AdminsDAO getAdminsDAO() {
        if (adminsInstance == null) {
            synchronized (this) {
                if (adminsInstance == null) {
                    adminsInstance = new AdminsDAOImpl(pool);
                }
            }
        }
        return adminsInstance;
    }

    /**
     * Method to get singleton instance of Client.
     *
     * @return - Client instance.
     */
    @Override
    public ClientDAO getClientDAO() {
        if (userInstance == null) {
            synchronized (this) {
                if (userInstance == null) {
                    userInstance = new ClientDAOImpl(pool);
                }
            }
        }
        return userInstance;
    }

    /**
     * Method to get singleton instance of Order.
     *
     * @return - Order instance.
     */
    @Override
    public OrderDAO getOrderDAO() {
        if (orderInstance == null) {
            synchronized (this) {
                if (orderInstance == null) {
                    orderInstance = new OrderDAOImpl(pool);
                }
            }
        }
        return orderInstance;
    }

    /**
     * Method to get singleton instance of OrderDetails.
     *
     * @return - OrderDetails instance.
     */
    @Override
    public OrderDetailsDAO getOrderDetailsDAO() {
        if (detailsInstance == null) {
            synchronized (this) {
                if (detailsInstance == null) {
                    detailsInstance = new OrderDetailsDAOImpl(pool);
                }
            }
        }
        return detailsInstance;
    }

    /**
     * Method to get singleton instance of product.
     *
     * @return - Product instance.
     */
    @Override
    public ProductDAO getProductDAO() {
        if (productInstance == null) {
            synchronized (this) {
                if (productInstance == null) {
                    productInstance = new ProductDAOImpl(pool);
                }
            }
        }
        return productInstance;
    }
}