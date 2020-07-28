package xyz.zeeraa.ezcore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.log.EZLogger;

/**
 * Represents a connection to a MySQL server
 * 
 * @author Zeeraa
 */
public class DBConnection {
	private Connection connection;

	private DBCredentials credentials;

	private int keepAliveTaskID;

	public DBConnection() {
		this.connection = null;
		this.keepAliveTaskID = -1;
	}

	/**
	 * Connects to the database using the com.mysql.jdbc.Driver as driver
	 * 
	 * @param host     host to connect to
	 * @param username user name of the MySQL user
	 * @param password password of the MySQL user
	 * @param database name of the database to use
	 * @return <code>true</code> on success or <code>false</code> on failure
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean connect(String host, String username, String password, String database) throws ClassNotFoundException, SQLException {
		return this.connect(host, username, password, database, "com.mysql.jdbc.Driver");
	}

	/**
	 * Connects to the database
	 * 
	 * @param host     host to connect to
	 * @param username user name of the MySQL user
	 * @param password password of the MySQL user
	 * @param database name of the database to use
	 * @param driver   driver to use
	 * @return <code>true</code> on success or <code>false</code> on failure
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean connect(String host, String username, String password, String database, String driver) throws ClassNotFoundException, SQLException {
		DBCredentials credentials = new DBCredentials(driver, host, username, password, database);

		return this.connect(credentials);
	}

	/**
	 * Connects to the database using the com.mysql.jdbc.Driver as driver
	 * 
	 * @param credentials The {@link DBCredentials} to use
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean connect(DBCredentials credentials) throws ClassNotFoundException, SQLException {
		if (connection != null) {
			if (!connection.isClosed()) {
				return false;
			}
		}

		this.credentials = credentials;

		EZLogger.info("DBC","Connecting to database " + credentials.getHost() + " using database driver: " + credentials.getDriver());

		Class.forName(credentials.getDriver());
		connection = DriverManager.getConnection(credentials.getHost(), credentials.getUsername(), credentials.getPassword());
		connection.setCatalog(credentials.getDatabase());
		return true;
	}

	/**
	 * Try to reconnect to the database with the last used credentials
	 * 
	 * @return <code>true</code> on success
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean reconnect() throws ClassNotFoundException, SQLException {
		EZLogger.info("DBC","Trying to reconnect to database " + credentials.getHost());
		try {
			if (this.isConnected()) {
				this.close();
			}
		} catch (Exception e) {
			EZLogger.warn("DBC","Failed to close the database while trying to reconnect");
			e.printStackTrace();
		}

		return this.connect(credentials);
	}

	/**
	 * Close the {@link Connection} to the database
	 * 
	 * @return <code>true</code> if the {@link Connection} was closed
	 * @throws SQLException
	 */
	public boolean close() throws SQLException {
		if (connection == null) {
			return false;
		}

		if (connection.isClosed()) {
			return false;
		}

		connection.close();
		return true;
	}

	/**
	 * Check if the connection is open
	 * 
	 * @return <code>true</code> if connected
	 * @throws SQLException
	 */
	public boolean isConnected() throws SQLException {
		if (connection != null) {
			return !connection.isClosed();
		}
		return false;
	}

	/**
	 * Try to run the query <code>SELECT 1;</code> and check for success
	 * 
	 * @return <code>true</code> on success, <code>false</code> on error
	 */
	public boolean testQuery() {
		try {
			String sql = "SELECT 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeQuery();
			ps.close();
		} catch (Exception ee) {
			ee.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Starts a task to execute the {@link DBConnection#testQuery()} every 30
	 * minutes to keep the connection alive
	 * 
	 * @return <code>true</code> if the task was started
	 */
	public boolean startKeepAliveTask() {
		if (keepAliveTaskID != -1) {
			return false;
		}

		keepAliveTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(EZCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				EZLogger.trace("DBC","Sending keep alive query to database " + credentials.getHost());
				testQuery();
			}
		}, 36000L, 36000L); // 30 minutes

		return true;
	}

	/**
	 * Stop the keep alive task
	 * 
	 * @return <code>true</code> if the task was ended
	 */
	public boolean endKeepAliveTask() {
		if (keepAliveTaskID == -1) {
			return false;
		}

		Bukkit.getScheduler().cancelTask(keepAliveTaskID);
		keepAliveTaskID = -1;

		return true;
	}

	/**
	 * Check if the keep alive task is running
	 * 
	 * @return <code>true</code> if the task is running
	 */
	public boolean isKeepAliveTaskRunning() {
		return keepAliveTaskID != -1;
	}

	/**
	 * Get instance of the connection
	 * 
	 * @return {@link Connection} instance
	 */
	public Connection getConnection() {
		return connection;
	}
}