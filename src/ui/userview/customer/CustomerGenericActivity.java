/**
 * 
 */
package ui.userview.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Displays the program enrollment page.  The user will be presented with a list of available loyalty
 * programs and will choose to enroll as appropriate.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerGenericActivity {
	
	// URL to connect to database
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	
	/**
	 * Constructs a new Program Enrollment Page, allowing the customer to view all available
	 * programs to enroll in as they choose to do so.  When the customer selects to exit, they will
	 * be brought out of this screen, they will be brought back to the customer landing page.
	 */
	public CustomerGenericActivity(Connection conn, int walletId, int pid, int ruleVersion, String ruleCode, int pointsToEarn, List<Integer> tierThresholds, String chosenActivity) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		boolean done = false;
		
		while (!done) {
			try {
				// Header
				System.out.println("------------------------------");
				System.out.println(chosenActivity + " Page:\n");
				
				int secondChoice = 0;
				while (secondChoice == 0) {
					System.out.println("Options:");
					System.out.println("1. " + chosenActivity);
					System.out.println("2. Go back");
					
					System.out.print("Your Choice: ");
					try {
						secondChoice = Integer.parseInt(scan.next());
					} catch (Exception e) {
						secondChoice = 0;
					}
					
					if (secondChoice != 1 && secondChoice != 2) {
						secondChoice = 0;
						System.out.println("Please choose a valid option (1 or 2).");
					}
				}
				
				// If go back, exit
				if (secondChoice == 2) {
					break;
				}
				
				// Get info on the activity
				System.out.print("Enter relevant information for this activity: ");
				String content = scan.next();
				
				// Insert the information on this review
				PreparedStatement purchase = conn.prepareStatement("INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (now(), ?, ?, ?, ?, ?)");
				purchase.setString(0, content);
				purchase.setInt(1, pid);
				purchase.setInt(2, ruleVersion);
				purchase.setString(3, ruleCode);
				purchase.setInt(4, walletId);
				try {
					purchase.executeUpdate();
				} catch (Exception e) {
					System.out.println("Could not complete activity.");
					break;
				}
				
				// Update the user's points and tier
				PreparedStatement customerWallet = conn.prepareStatement("SELECT points, alltimepoints, tierNumber FROM WalletParticipation WHERE wId = ? AND pId = ?");
				customerWallet.setInt(0, walletId);
				customerWallet.setInt(1, pid);
				ResultSet origPoints = customerWallet.executeQuery();
				int points = 0;
				int allTimePoints = 0;
				int tierNumber = 0;
				while(origPoints.next()) {
					points = origPoints.getInt("points");
					allTimePoints = origPoints.getInt("alltimepoints");
					tierNumber = origPoints.getInt("tierNumber");
				}
				
				points += pointsToEarn;
				allTimePoints += pointsToEarn;
				
				for(int j = 0; j < tierThresholds.size(); j++) {
					int threshold = tierThresholds.get(j);
					if (allTimePoints > threshold) {
						tierNumber = j;
					}
				}
				
				// Run the update query
				PreparedStatement updateWallet = conn.prepareStatement("UPDATE WalletParticipation SET points = ?, alltimepoints = ?, tierNumber = ? WHERE wId = ? AND pId = ?");
				updateWallet.setInt(0, points);
				updateWallet.setInt(1, allTimePoints);
				updateWallet.setInt(2, tierNumber);
				updateWallet.setInt(3, walletId);
				updateWallet.setInt(4, pid);
				updateWallet.executeUpdate();
				
				break;
			} catch (SQLException e1) {
				// Could not connect to database - stop running
				System.out.println("Could not connect to the database");
				System.exit(1);
			}
		}
		scan.close();
		
	}
}
