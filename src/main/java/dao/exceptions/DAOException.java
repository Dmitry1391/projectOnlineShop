package dao.exceptions;

/**
 * This class combines all possible DAO level exceptions with exception handling of the same type.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class DAOException extends Exception {

	public DAOException() {
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}