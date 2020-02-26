package dao.interfaces;

import dao.exceptions.DAOException;
import models.Order;

import java.util.List;

/**
 * This interface defines operations that relate to the order entity.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface OrderDAO extends GenericDAO<Order> {

    /**
     * Method to get Order which matches to specified order ID;
     *
     * @param orderId - order id.
     * @return - null if there was no such order.
     * @throws DAOException;
     */
    Order getOrderByID(Integer orderId) throws DAOException;

    /**
     * Method to get order id which matches to specified order;
     *
     * @param order;
     * @return - 1 if there was no such order.
     * @throws DAOException;
     */
    int getOrderId(Order order) throws DAOException;

    /**
     * Method to get list of Orders which matches to specified order status;
     *
     * @param orderStatus;
     * @return - null if no orders match to argument.
     * @throws DAOException;
     */
    List<Order> getAllByOrderStatus(String orderStatus) throws DAOException;

    /**
     * Method to get list of Orders which matches to specified client id;
     *
     * @param clientId;
     * @return - null if no orders match to argument.
     * @throws DAOException;
     */
    List<Order> getAllByClient(Integer clientId) throws DAOException;

    /**
     * Method delete all Orders which matches to specified order status;
     *
     * @param orderStatus;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteAllByOrderStatus(String orderStatus) throws DAOException;

    /**
     * Method delete all Orders which matches to specified client id;
     *
     * @param clientId;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteAllByClient(Integer clientId) throws DAOException;
}