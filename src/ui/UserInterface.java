/**
 * 
 */
package ui;

import java.sql.*;

/**
 * @author Matthew Martin
 * @author Grey Files
 * @author Tyrone Wu
 * @author Niraj Lavani
 */
public class UserInterface {
	
	static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	
	public static void main(String[] args) {
		
        Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");

		    String user = args[0];
		    String passwd = args[1];
	            
            conn = DriverManager.getConnection(jdbcURL, user, passwd);
            
    	} catch (Throwable e) {
	        System.out.println("Error in database connection");
        }
		
		Home homepage = new Home(conn);
	}

	public static void newScreen() {
		System.out.println("------------------------------");
	}
	
	public static void invalidInput() {
		System.out.print("Invalid input, please try again: ");
	}
}
