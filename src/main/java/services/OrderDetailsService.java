package services;

import dao.exceptions.DAOException;
import dao.impl.DAOFactoryImpl;
import dao.interfaces.OrderDetailsDAO;
import models.OrderDetails;

import java.util.List;

/**
 * This class is designed to work with the OrderDetailsService, which is associated with the DAO layer.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
public class OrderDetailsService {
    private OrderDetailsDAO orderDetailsDAO = DAOFactoryImpl.getInstance().getOrderDetailsDAO();

    /**
     * Basic method for create orderDetails.
     *
     * @param orderDetails - accepts orderDetails.
     * @return - creating orderDetails in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean create(OrderDetails orderDetails) throws DAOException {
        return orderDetailsDAO.create(orderDetails);
    }

    /**
     * Method to get orderDetails match to specified order id.
     *
     * @param orderId;
     * @return - null if there was no such orderId.
     * @throws DAOException ;
     */
    public List<OrderDetails> getByOrderId(int orderId) throws DAOException {
        return orderDetailsDAO.getByOrderId(orderId);
    }

    /**
     * Method to get list orderDetails match to specified product id.
     *
     * @param productId;
     * @return - null if there was no such orderId.
     * @throws DAOException;
     */
    public List<OrderDetails> getByProductId(int productId) throws DAOException {
        return orderDetailsDAO.getByProductId(productId);
    }

    /**
     * Method deletes all records which match to specified order id.
     *
     * @param orderId;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteAllByOrderId(int orderId) throws DAOException {
        return orderDetailsDAO.deleteAllByOrderId(orderId);
    }
}
