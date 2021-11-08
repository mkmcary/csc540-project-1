package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Implements a singleton design for the connection object such that we can safely
 * close the connection every time the program exits.
 * @author Matthew Martin
 *
 */
public class SafeDatabaseConnection {
	/**
	 * The singleton instance of SafeDatabaseConnection.
	 */
	private static SafeDatabaseConnection instance;
	
	private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	
	
	/**
	 * The connection to the database
	 */
	private Connection conn;
	
	public static void initializeInstance(String user, String pass) {
		instance = new SafeDatabaseConnection(user, pass);
	}
	
	/**
	 * Get the singleton instance of SafeDatabaseConnection.
	 * @return the singleton instance.
	 */
	public static SafeDatabaseConnection getInstance() {
		if (instance == null) {
			throw new NoSuchElementException();
		}
		return instance;
	}
	
	/**
	 * Constructs a new SafeDatabaseConnection with the given credentials.
	 * @param user the username to use.
	 * @param pass the password to use.
	 */
	private SafeDatabaseConnection(String user, String pass) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
            this.conn = DriverManager.getConnection(jdbcURL, user, pass);
    	} catch (Throwable e) {
    		System.out.println(e.getMessage());
	        System.out.println("Error in database connection");
        }
	}
	
	/**
	 * Get the connection to the database.
	 * @return the connection to the database.
	 */
	public Connection getConnection() {
		return this.conn;
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public static void closeConnection() {
		try {
			SafeDatabaseConnection.getInstance().getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
