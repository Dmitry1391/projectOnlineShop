package dao.interfaces;

import dao.exceptions.DAOException;
import models.Order;
import models.Product;

import java.util.List;

/**
 * This interface defines operations that relate to the product entity.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface ProductDAO extends GenericDAO<Product> {

    /**
     * Method to get product id which matches to specified product.
     *
     * @param product;
     * @return - 1 if there was no such product.
     * @throws DAOException;
     */
    int getProductId(Product product) throws DAOException;

    /**
     * Method to get Product which matches to specified product ID.
     *
     * @param productId;
     * @return - null if there was no such product.
     * @throws DAOException;
     */
    Product getProductByID(int productId) throws DAOException;

    /**
     * Method to get list of Products which matches to specified product name.
     *
     * @param productName;
     * @return - null if no products match to argument.
     * @throws DAOException;
     */
    List<Product> getAllByName(String productName) throws DAOException;

    /**
     * Method to get list of Products which figures in specified order.
     *
     * @param order;
     * @return - null if no products match to argument.
     * @throws DAOException;
     */
    List<Product> getProductsFromOrder(Order order) throws DAOException;

    /**
     * Method delete product which matches to specified product id.
     *
     * @param productID;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteById(int productID) throws DAOException;

    /**
     * Method deletes all Products which matches to specified product name.
     *
     * @param productName;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteAllByName(String productName) throws DAOException;

    /**
     * Method deletes all Products which matches to specified product price.
     *
     * @param productPrice;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteAllByPrice(Double productPrice) throws DAOException;

}