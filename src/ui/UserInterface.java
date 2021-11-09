/**
 * 
 */
package ui;

import java.sql.*;

import db.SafeDatabaseConnection;

/**
 * @author Matthew Martin
 * @author Grey Files
 * @author Tyrone Wu
 * @author Niraj Lavani
 */
public class UserInterface {
	
	public static void main(String[] args) {
		// Create a safe database connection
		SafeDatabaseConnection.initializeInstance(args[0], args[1]);
		SafeDatabaseConnection sdc = SafeDatabaseConnection.getInstance();
        Connection conn = sdc.getConnection();
		
        // Create the shutdown hook to close the connection upon exit.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        	public void run() {
        		System.out.println("Closing the db connection.");
        		SafeDatabaseConnection.closeConnection();
        	}
        }, "Shutdown-thread"));
        
        // Create the homepage.
		new Home(conn);
	}

	public static void newScreen() {
		System.out.println("------------------------------");
	}
	
	public static void invalidInput() {
		System.out.print("Invalid input, please try again: ");
	}
}
