package dao.interfaces;

import dao.exceptions.DAOException;
import models.*;

import java.util.List;

/**
 * This interface defines operations that relate to the admin entity.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface AdminsDAO extends GenericDAO<Admin> {

    /**
     * Method to get admin by specified id.
     *
     * @param adminId - admin id
     * @return - null if there was no such admin.
     * @throws DAOException;
     */
    Admin getById(Integer adminId) throws DAOException;

    /**
     * Method to get list of admin which matches by specified name.
     *
     * @param adminName - admin name.
     * @return - null if no admin match to argument.
     * @throws DAOException;
     */
    List<Admin> getByName(String adminName) throws DAOException;

    /**
     * Method to get list of admin which matches by specified login.
     *
     * @param adminLogin - admin login.
     * @return - null if no admin match to argument.
     * @throws DAOException;
     */
    Admin getByLogin(String adminLogin) throws DAOException;

    /**
     * Method get admin that matches to specified login and pass.
     *
     * @param adminLogin - admin login.
     * @param adminPass - admin pass.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    Admin getByLoginAndPass(String adminLogin, String adminPass) throws DAOException;

    /**
     * Method delete of admin which matches by specified name.
     *
     * @param adminLogin - admin name;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteByLogin(String adminLogin) throws DAOException;


    // Methods for creating entities through the administrator.
    void createClient(Client entity) throws DAOException;

    void createOrder(Order entity) throws DAOException;

    void createOrderDetails(OrderDetails entity) throws DAOException;

    void createProduct(Product entity) throws DAOException;

    // Methods for updating entities through the administrator.
    void updateClient(Client oldEntity, Client newEntity) throws DAOException;

    void updateOrder(Order oldEntity, Order newEntity) throws DAOException;

    void updateOrderDetails(OrderDetails oldEntity, OrderDetails newEntity) throws DAOException;

    void updateProduct(Product oldEntity, Product newEntity) throws DAOException;

    // Methods for deleting entities through the administrator.
    void deleteClient(Client entity) throws DAOException;

    void deleteOrder(Order entity) throws DAOException;

    void deleteOrderDetails(OrderDetails entity) throws DAOException;

    void deleteProduct(Product entity) throws DAOException;

    // Methods for get all entities through the administrator.
    List<Client> getAllClient() throws DAOException;

    Client getClientByLoginAndPass(String clientLogin, String clientPass) throws DAOException;

    List<Order> getAllOrder() throws DAOException;

    List<Order> getAllOrdersByOrderStatus(String orderStatus) throws DAOException;

    List<OrderDetails> getAllOrderDetails() throws DAOException;

    List<Product> getAllProduct() throws DAOException;
}
