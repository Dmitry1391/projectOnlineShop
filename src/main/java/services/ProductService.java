package services;

import dao.exceptions.DAOException;
import dao.impl.DAOFactoryImpl;
import dao.interfaces.ProductDAO;
import models.Order;
import models.Product;
import java.util.List;

/**
 * This class is designed to work with the ProductService, which is associated with the DAO layer.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
public class ProductService {

    private ProductDAO productDAO  = DAOFactoryImpl.getInstance().getProductDAO();

    /**
     * Basic method for get all products.
     *
     * @return - list all products from the database.
     * @throws DAOException - throw the exception above.
     */
    public List<Product> getAll() throws DAOException {
        return productDAO.getAll();
    }

    /**
     * Basic method for delete product.
     *
     * @param product;
     * @return - delete product in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean delete(Product product) throws DAOException {
        return productDAO.delete(product);
    }

    /**
     * Basic method for update product.
     *
     * @param oldProduct;
     * @param newProduct;
     * @return - updating product in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean update(Product oldProduct, Product newProduct) throws DAOException {
        return productDAO.update(oldProduct,newProduct);
    }

    /**
     * Basic method for create product.
     *
     * @param product - accepts product.
     * @return - creating product in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean create(Product product) throws DAOException {
        return productDAO.create(product);
    }

    /**
     * Method to get product id which matches to specified product.
     *
     * @param product;
     * @return - 1 if there was no such product.
     * @throws DAOException ;
     */
    public int getProductId(Product product) throws DAOException {
        return productDAO.getProductId(product);
    }

    /**
     * Method to get Product which matches to specified product ID.
     *
     * @param productId;
     * @return - null if there was no such product.
     * @throws DAOException;
     */
    public Product getProductByID(int productId) throws DAOException {
        return productDAO.getProductByID(productId);

    }

    /**
     * Method to get list of Products which matches to specified product name.
     *
     * @param productName;
     * @return - null if no products match to argument.
     * @throws DAOException;
     */
    public List<Product> getAllByName(String productName) throws DAOException {
        return productDAO.getAllByName(productName);

    }

    /**
     * Method to get list of Products which figures in specified order.
     *
     * @param order;
     * @return - null if no products match to argument.
     * @throws DAOException;
     */
    public List<Product> getProductsFromOrder(Order order) throws DAOException {
        return productDAO.getProductsFromOrder(order);

    }

    /**
     * Method delete product which matches to specified product id.
     *
     * @param productID;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteById(int productID) throws DAOException {
        return productDAO.deleteById(productID);
    }

    /**
     * Method deletes all Products which matches to specified product name.
     *
     * @param productName;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteAllByName(String productName) throws DAOException {
        return productDAO.deleteAllByName(productName);
    }

    /**
     * Method deletes all Products which matches to specified product price.
     *
     * @param productPrice;
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteAllByPrice(Double productPrice) throws DAOException {
        return productDAO.deleteAllByPrice(productPrice);
    }
}
