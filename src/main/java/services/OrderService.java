package services;

import dao.exceptions.DAOException;
import dao.impl.DAOFactoryImpl;
import dao.interfaces.OrderDAO;
import models.Order;

import java.util.List;

/**
 * This class is designed to work with the OrderService, which is associated with the DAO layer.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
public class OrderService {
    private OrderDAO orderDAO = DAOFactoryImpl.getInstance().getOrderDAO();

    /**
     * Basic method for get all orders.
     *
     * @return - list all orders from the database.
     * @throws DAOException - throw the exception above.
     */
    public List<Order> getAll() throws DAOException {
        return orderDAO.getAll();
    }

    /**
     * Basic method for delete product.
     *
     * @param order;
     * @return - delete order in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean delete(Order order) throws DAOException {
        return orderDAO.delete(order);
    }

    /**
     * Basic method for update order.
     *
     * @param oldOrder;
     * @param newOrder;
     * @return - updating order in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean update(Order oldOrder, Order newOrder) throws DAOException {
        return orderDAO.update(oldOrder,newOrder);
    }

    /**
     * Basic method for create order.
     *
     * @param order - accepts order.
     * @return - creating order in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean create(Order order) throws DAOException {
        return orderDAO.create(order);
    }


    /**
     * Method to get order id which matches to specified order;
     *
     * @param order;
     * @return - 1 if there was no such order.
     * @throws DAOException ;
     */
    public int getOrderId(Order order) throws DAOException {
        return orderDAO.getOrderId(order);
    }

    /**
     * Method to get Order which matches to specified order ID;
     *
     * @param orderId - order id.
     * @return - null if there was no such order.
     * @throws DAOException;
     */
    public Order getOrderByID(Integer orderId) throws DAOException {
        return orderDAO.getOrderByID(orderId);
    }

    /**
     * Method to get list of Orders which matches to specified order status;
     *
     * @param orderStatus;
     * @return - null if no orders match to argument.
     * @throws DAOException;
     */
    public List<Order> getAllByOrderStatus(String orderStatus) throws DAOException {
        return orderDAO.getAllByOrderStatus(orderStatus);
    }

    /**
     * Method to get list of Orders which matches to specified client id;
     *
     * @param clientId;
     * @return - null if no orders match to argument.
     * @throws DAOException;
     */
    public List<Order> getAllByClient(Integer clientId) throws DAOException {
        return orderDAO.getAllByClient(clientId);
    }

    /**
     * Method delete all Orders which matches to specified order status;
     *
     * @param orderStatus;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteAllByOrderStatus(String orderStatus) throws DAOException {
        return orderDAO.deleteAllByOrderStatus(orderStatus);
    }

    /**
     * Method delete all Orders which matches to specified client id;
     *
     * @param clientId;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteAllByClient(Integer clientId) throws DAOException {
        return orderDAO.deleteAllByClient(clientId);
    }
}
