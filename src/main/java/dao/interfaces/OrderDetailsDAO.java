package dao.interfaces;


import dao.exceptions.DAOException;
import models.OrderDetails;

import java.util.List;

/**
 * This interface defines operations that relate to the orderDetails entity.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface OrderDetailsDAO extends GenericDAO<OrderDetails> {

	/**
	 * Method to get orderDetails match to specified order id.
	 * 
	 * @param orderId;
	 * @return - null if there was no such orderId.
	 * @throws DAOException;
	 */
	List<OrderDetails> getByOrderId(int orderId) throws DAOException;

	/**
	 * Method to get list orderDetails match to specified product id.
	 *
	 * @param productId;
	 * @return - null if there was no such orderId.
	 * @throws DAOException;
	 */
	List<OrderDetails> getByProductId(int productId) throws DAOException;

	/**
	 * Method deletes all records which match to specified order id.
	 * 
	 * @param orderId;
	 * @return - "true" in case of success, "false" otherwise.
	 * @throws DAOException;
	 */
	boolean deleteAllByOrderId(int orderId) throws DAOException;
}