package dao.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * This interface custom JDBC Connection Pool.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface ConnectionPool {

    Connection getConnection() throws SQLException;

    boolean releaseConnection(Connection connection);

    List<Connection> getConnectionPool();

    int getSize();

    String getUrl();

    String getUser();

    String getPassword();

    void close() throws SQLException;

}
