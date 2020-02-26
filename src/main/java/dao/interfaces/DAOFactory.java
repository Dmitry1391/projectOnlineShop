package dao.interfaces;

/**
 * This interface provides unified access to various objects of DAO classes.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface DAOFactory {

    /*
     * Method returns implementation of ClientDAO interface relies to factory
     */
    ClientDAO getClientDAO();

    /*
     * Method returns implementation of OrderDAO interface relies to factory.
     */
    OrderDAO getOrderDAO();

    /*
     * Method returns implementation of OrderDetailsDAO interface relies to factory.
     */
    OrderDetailsDAO getOrderDetailsDAO();

    /*
     * Method returns implementation of ProductDAO interface relies to factory.
     */
    ProductDAO getProductDAO();

    /*
     * Method returns implementation of AdminsDAO interface relies to factory.
     */
    AdminsDAO getAdminsDAO();
}