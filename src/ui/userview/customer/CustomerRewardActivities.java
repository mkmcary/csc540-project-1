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
 * Displays the Reward Activities page.  Customers can choose a program and an activity
 * that they would like to complete in order to earn points for that program.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerRewardActivities {

	/**
	 * Constructs a new Reward Activities page.  Customers can choose a program and an activity
	 * that they would like to complete in order to earn points for that program.
	 * 
	 * @param custId the id of the customer currently using the system.
	 * @param walletId the wallet id of the customer.
	 * @param conn the Connection to the database.
	 */
	public CustomerRewardActivities(int custId, int walletId, Connection conn) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		boolean done = false;
		
		while (!done) {
			try {
				// Header
				System.out.println("------------------------------");
				System.out.println("Welcome to the Reward Activities Page!\n");
				System.out.println("Please choose an program below or exit:");
				
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM LoyaltyPrograms");
				
				ResultSet programs = pstmt.executeQuery();
				ArrayList<String> codes = new ArrayList<String>();
				ArrayList<Boolean> tieredOrNot = new ArrayList<Boolean>();
				
				int i = 1;
				while(programs.next()) {
					// Get the Program id and name
					String code = programs.getString("pCode");
					codes.add(code);
					String name = programs.getString("pName");
					String tieredString = programs.getString("isTiered");
					boolean tiered = (tieredString.equals("Y")) ? true : false;
					tieredOrNot.add(tiered);
					
					// Print out option to user
					System.out.println(i + ") " + code + ": " + name);
					
					// Move to next tuple
					programs.next();
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
				String pChosen = codes.get(userInput - 1);
				boolean isTiered = tieredOrNot.get(userInput - 1);
				int choice = 0;
				
				// Find the appropriate earning types for this program
				ResultSet reRules = null;
				try {
					PreparedStatement findRules = conn.prepareStatement("SELECT * FROM RewardEarningRules WHERE pId = ?");
					findRules.setString(0, pChosen);
					reRules = findRules.executeQuery();
				} catch (Exception e) {
					System.out.println("Could not connect to db.");
					System.exit(1);
				}
				
				int goBackChoice = -1;
				while(choice != goBackChoice) {
					System.out.println("------------------------------");
					
					// List each choice
					ArrayList<String> activityChoices = new ArrayList<String>();
					int choiceNum = 1;
					reRules.beforeFirst();
					while (reRules.next()) {
						String acId = reRules.getString("acId");
						String acName = "NoName";
						try {
							// Get the activity info (name)
							PreparedStatement findActivity = conn.prepareStatement("SELECT * FROM ActivityCategories WHERE acId = ?");
							findActivity.setString(0, acId);
							ResultSet activities = findActivity.executeQuery();
							
							while(activities.next()) {
								acName = activities.getString("acName");
								activityChoices.add(acName);
							}
						} catch (Exception e) {
							System.out.println("Could not connect to db.");
							System.exit(1);
						}
						
						// Print the option
						System.out.println(choiceNum + ". " + acName);
						choiceNum++;
					}
					
					// List exit
					System.out.println(choiceNum + ". Go back");
					goBackChoice = choiceNum;
					
					// Ask for user input
					System.out.println("Please enter your choice: ");
					String nextInput = scan.next();
					
					// Convert and validate the user input
					try {
						choice = Integer.parseInt(nextInput);
					} catch(Exception e) {
						// The user input was not a number
						choice = 0;
					}
					
					// Did the user say to go back?
					if (choice != goBackChoice) {
						String chosenActivity = activityChoices.get(choice - 1);
						
						// Get the rule with this acId
						int pid = 0;
						int ruleVersion = 0;
						String ruleCode = "";
						int pointsToEarn = 0;
						reRules.beforeFirst();
						while(reRules.next()) {
							String localACID = reRules.getString("acId");
							if (localACID.equals(chosenActivity)) {
								pid = reRules.getInt("pId");
								ruleVersion = reRules.getInt("ruleVersion");
								ruleCode = reRules.getString("ruleCode");
								pointsToEarn = reRules.getInt("points");
							}
						}
						
						// Adjust points to earn if this is a tiered program
						ArrayList<Integer> tierThresholds = new ArrayList<Integer>();
						if(isTiered) {
							PreparedStatement tierCheck = conn.prepareStatement("SELECT * FROM Tiers WHERE pId = ?");
							tierCheck.setInt(0, pid);
							ResultSet programTiers = tierCheck.executeQuery();
							
							tierCheck = conn.prepareStatement("SELECT tierNumber FROM WalletParticipation WHERE pId = ? AND wId = ?");
							tierCheck.setInt(0, pid);
							tierCheck.setInt(1, walletId);
							ResultSet customerTier = tierCheck.executeQuery();
							int tierNum = 0;
							while (customerTier.next()) {
								tierNum = customerTier.getInt("tierNumber");
							}
							
							while(programTiers.next()) {
								tierThresholds.add(programTiers.getInt("threshold"));
								if(programTiers.getInt("tnum") == tierNum) {
									float multiplier = programTiers.getFloat("multiplier");
									pointsToEarn *= multiplier;
								}
							}
						}
						
						
						// Check for known cases
						if (chosenActivity.equalsIgnoreCase("Purchase")) {
							// Purchase page
							new CustomerPurchase(conn, walletId, pid, ruleVersion, ruleCode, pointsToEarn, tierThresholds);
						} else if (chosenActivity.equalsIgnoreCase("Leave a review")) {
							// Review Page
							new CustomerReview(conn, walletId, pid, ruleVersion, ruleCode, pointsToEarn, tierThresholds);
						} else if (chosenActivity.equalsIgnoreCase("Refer a friend")) {
							// Refer page
							new CustomerRefer(conn, walletId, pid, ruleVersion, ruleCode, pointsToEarn, tierThresholds);
						} else {
							// Generic page
							new CustomerGenericActivity(conn, walletId, pid, ruleVersion, ruleCode, pointsToEarn, tierThresholds, chosenActivity);
						}
						
						// Return to the user landing page
						done = true;
					}
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
