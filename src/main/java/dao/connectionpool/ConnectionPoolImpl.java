package dao.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class custom JDBC Connection Pool.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class ConnectionPoolImpl implements ConnectionPool {

    private final static Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);

    private static Connection connection;

    private static ResourceBundle resBundle = ResourceBundle.getBundle("database");

    private final static String url = resBundle.getString("URL");
    private final static String user = resBundle.getString("loginDB");
    private final static String password = resBundle.getString("passwordDB");

    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<Connection>();
    private static final int INITIAL_POOL_SIZE = 10;
    private final int MAX_POOL_SIZE = 30;

    /**
     * Constructor.
     *
     * @param connectionPool;
     */
    private ConnectionPoolImpl(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Method for creating a Connection Pool object.
     *
     * @return - Connection Pool object.
     * @throws SQLException - throw the exception above.
     */
    public static ConnectionPoolImpl create() throws SQLException {

        List<Connection> pool = new ArrayList<Connection>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return new ConnectionPoolImpl(pool);
    }

    /**
     * Method of obtaining a connection from the Connection Pool.
     *
     * @return - connection
     * @throws SQLException - throw the exception above.
     */
    public Connection getConnection() throws SQLException {

        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                logger.error("Maximum pool size reached, no available connections! \n");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    /**
     * Method SQL driver creation and registration
     */
    public static void regDriver() {
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            logger.info("Driver created and successfully registered!");
        } catch (SQLException e) {
            logger.error("Error creating and registering a driver! \n" + e);
        }
    }

    /**
     * Method creating a connection the DB.
     *
     * @return - connection.
     * @throws SQLException - throw the exception above.
     */
    private static Connection createConnection() throws SQLException {
        regDriver();
        try {
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Connection successfully!");
        } catch (SQLException e) {
            logger.error("Failed to create connection! \n" + e);
        }
        return connection;
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void close() throws SQLException {

        for (Connection con : usedConnections) {
            this.releaseConnection(con);
        }

        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }
}
