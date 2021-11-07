/**
 * 
 */
package ui.userview.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Displays the program enrollment page.  The user will be presented with a list of available loyalty
 * programs and will choose to enroll as appropriate.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerEnrollProgram {
	
	/**
	 * Constructs a new Program Enrollment Page, allowing the customer to view all available
	 * programs to enroll in as they choose to do so.  When the customer selects to exit, they will
	 * be brought out of this screen, they will be brought back to the customer landing page.
	 * 
	 * @param custId the customer's id value.
	 * @param walletId the customer's wallet id.
	 * @param conn the Connection to the database.
	 */
	public CustomerEnrollProgram(int custId, int walletId, Connection conn) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		boolean done = false;
		
		while (!done) {
			try {
				// Header
				System.out.println("------------------------------");
				System.out.println("Welcome to the Program Enrollment Page!\n");
				System.out.println("Please choose an option below to enroll or exit:");
				
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM LoyaltyPrograms");
				
				ResultSet programs = pstmt.executeQuery();
				ArrayList<Integer> ids = new ArrayList<Integer>();
				ArrayList<Boolean> tieredOrNot = new ArrayList<Boolean>();
				
				int i = 1;
				while(programs.next()) {
					// Get the Program id and name
					int id = programs.getInt("id");
					String code = programs.getString("pCode");
					ids.add(id);
					String name = programs.getString("pName");
					String tieredString = programs.getString("isTiered");
					boolean tiered = (tieredString.equals("Y")) ? true: false;
					tieredOrNot.add(tiered);
					
					// Print out option to user
					System.out.println(i + ") " + code + ": " + name);
					
					// Move to next tuple
					i++;
				}
				
				int exitOption = i;
				System.out.println(i + ") Exit");
				
				// Get user input
				System.out.print("Please enter your choice (1-" + exitOption + "): ");
				String unvalidated = scan.next();
				
				// Convert and validate the user input
				try {
					userInput = Integer.parseInt(unvalidated);
				} catch(Exception e) {
					// The user input was not a number
					userInput = 0;
				}
				
				if (userInput < 1 || userInput > exitOption) {
					System.out.println("Please enter a valid choice (1-" + exitOption + ")");
					continue;
				}
				
				// check if user said to exit
				if (userInput == exitOption) {
					done = true;
					break;
				}
				
				// Else, use their input
				int pChosen = ids.get(userInput - 1);
				boolean isTiered = tieredOrNot.get(userInput - 1);
				int choice = 0;
				while(choice == 0) {
					System.out.println("------------------------------");
					System.out.println("1. Enroll in Loyalty Program!");
					System.out.println("2. Go Back");
					System.out.println("Please enter your choice: ");
					String nextInput = scan.next();
					// Convert and validate the user input
					try {
						choice = Integer.parseInt(nextInput);
					} catch(Exception e) {
						// The user input was not a number
						choice = 0;
					}
					
					// If they said Enroll, enroll them
					if (choice == 1) {
						try {
							if (isTiered) {
								PreparedStatement insertion = conn.prepareStatement("INSERT INTO WalletParticipation (wId, pId, points, alltimepoints, tierNumber) values (?,?,0,0,0)");
								insertion.setInt(0, walletId);
								insertion.setInt(1, pChosen);
								insertion.executeUpdate();
								System.out.println("Successfully enrolled in loyalty program " + pChosen + ".");
							} else {
								PreparedStatement insertion = conn.prepareStatement("INSERT INTO WalletParticipation (wId, pId, points, alltimepoints) values (?,?,0,0)");
								insertion.setInt(0, walletId);
								insertion.setInt(1, pChosen);
								insertion.executeUpdate();
								System.out.println("Successfully enrolled in loyalty program " + pChosen + ".");
							}
						} catch (Exception e) {
							System.out.println("You are already enrolled in this loyalty program.");
						}
					}
					// Return to the user landing page
					done = true;
				}
				
			} catch (SQLException e1) {
				// Could not connect to database - stop running
				System.out.println("Could not connect to the database");
				System.exit(1);
			}
		}
		scan.close();
		
	}
}
