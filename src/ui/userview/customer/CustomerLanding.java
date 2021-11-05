/**
 * 
 */
package ui.userview.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Displays the customer landing page with the following sub-options to take:
 * 1) Enroll in a Program
 * 2) Reward Activities
 * 3) View Wallet
 * 4) Redeem Points
 * 5) Log out
 * 
 * @author Matthew Martin
 *
 */
public class CustomerLanding {
	
	public static void main(String args[]) {
		//new CustomerLanding(new Connection());
	}
	
	/**
	 * Constructs a new CustomerLanding page, displaying the proper options to a customer user.
	 * When the user selects to exit this page, the constructor will end and fall back to the previous
	 * screen (Login)
	 */
	public CustomerLanding(int custId, Connection conn) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		
		// Get this user's wallet id
		int walletId = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT wId FROM CustomerWallets WHERE cId = ?");
			pstmt.setInt(0, custId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				walletId = rs.getInt("wId");
			}
		} catch(Exception e) {
			// Could not find this customers wallet - stop running
			System.out.println("Could not find customer wallet in the database");
			System.exit(1);
		}
		
		
		while(userInput != 5) {
			
			// Header
			System.out.println("------------------------------");
			System.out.println("Welcome to the Customer Landing Page!\n");
			System.out.println("Please choose an option below:");
			
			// Options for the user
			System.out.println("    1) Enroll in Loyalty Program");
			System.out.println("    2) Reward Activities");
			System.out.println("    3) View Wallet");
			System.out.println("    4) Redeem Points");
			System.out.println("    5) Log out");
			
			// Get user input
			System.out.print("Please enter your choice: ");
			String unvalidated = scan.next();
			
			// Convert and validate the user input
			try {
				userInput = Integer.parseInt(unvalidated);
			} catch(Exception e) {
				// The user input was not a number
				userInput = 0;
			}
			
			if (userInput < 1 || userInput > 5) {
				System.out.println("Please enter a valid choice (1, 2, 3, 4, or 5)");
				continue;
			}
			
			// Check each choice
			if (userInput == 1) {
				// Enroll in Loyalty Program
				new CustomerEnrollProgram(custId, walletId, conn);
			} else if (userInput == 2) {
				// Reward Activities
			} else if (userInput == 3) {
				// View Wallet
			} else if (userInput == 4) {
				// Redeem Points
			}
		}
	}
}
