/**
 * 
 */
package ui.userview.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Displays the program enrollment page.  The user will be presented with a list of available loyalty
 * programs and will choose to enroll as appropriate.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerEnrollProgram {
	
	// URL to connect to database
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	
	/**
	 * Constructs a new Program Enrollment Page, allowing the customer to view all available
	 * programs to enroll in as they choose to do so.  When the customer selects to exit, they will
	 * be brought out of this screen, they will be brought back to the customer landing page.
	 */
	public CustomerEnrollProgram(Connection conn) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		
		try {
			// Header
			System.out.println("------------------------------");
			System.out.println("Welcome to the Program Enrollment Page!\n");
			System.out.println("Please choose an option below to enroll or exit:");
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM LoyaltyPrograms");
			
			ResultSet programs = pstmt.executeQuery();
			
			int i = 1;
			while(programs.next()) {
				// Get the Program id and name
				String code = programs.getString("Code");
				String name = programs.getString("Name");
				
				// Print out option to user
				System.out.println(i + ") " + code + ": " + name);
				
				// Move to next tuple
				programs.next();
				i++;
			}
			
			System.out.println(i + ") Exit");
		} catch (SQLException e1) {
			// Could not connect to database - stop running
			System.out.println("Could not connect to the database");
			System.exit(1);
		}
		
		scan.close();
	}
}
