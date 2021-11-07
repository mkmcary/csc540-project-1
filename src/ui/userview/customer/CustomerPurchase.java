/**
 * 
 */
package ui.userview.customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Displays the Purchase Activity page for the customer to interact with.  This can be used
 * for the customer to enter information on purchases they have made to get points.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerPurchase {
	/**
	 * Constructs a new Purchase Activity page for the customer to interact with.  This can be used
	 * for the customer to enter information on purchases they have made to get points.
	 * 
	 * @param conn the Connection to the database.
	 * @param walletId the customer's wallet id.
	 * @param pid the program id that the customer is working with.
	 * @param ruleVersion the version of the reward earning rule the customer is interacting with.
	 * @param ruleCode the code for the reward earning rule the customer is interacting with.
	 * @param pointsToEarn the amount of points the user should earn if they complete the activity.
	 * @param tierThresholds the list of tierThresholds that need to be considered when updating the user's tier in a program
	 */
	public CustomerPurchase(Connection conn, int walletId, int pid, int ruleVersion, String ruleCode, int pointsToEarn, List<Integer> tierThresholds) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		boolean done = false;
		
		while (!done) {
			try {
				// Header
				System.out.println("------------------------------");
				System.out.println("Purchase Page:\n");
				
				int chosenCardId = -1;
				while(true) {
					System.out.println("Select A Gift Card to Use:");
					// Get gift cards for this user
					PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GiftCards WHERE wId = ? AND pId = ?");
					pstmt.setInt(1, walletId);
					pstmt.setInt(2, pid);
					ResultSet cards = pstmt.executeQuery();
					
					ArrayList<Integer> cardIds = new ArrayList<Integer>();
					
					int i = 1;
					while(cards.next()) {
						// Get the card id and value
						int cardId = cards.getInt("id");
						cardIds.add(cardId);
						double value = cards.getDouble("cardValue");
						
						// Print out option to user
						System.out.println(i + ") $" + value);
						
						// Move to next tuple
						cards.next();
						i++;
					}
					
					int noneOption = i;
					System.out.println(i + ") None");
					
					// Get user input
					System.out.print("Please enter your choice (1-" + noneOption + "): ");
					String unvalidated = scan.next();
					
					// Convert and validate the user input
					try {
						userInput = Integer.parseInt(unvalidated);
					} catch(Exception e) {
						// The user input was not a number
						userInput = 0;
					}
					
					// Invalid input?
					if (userInput < 1 || userInput > noneOption) {
						System.out.println("Please enter a valid choice (1-" + noneOption + ")");
						continue;
					}
					
					// Which gift card id did they choose?
					if (userInput != noneOption)
						chosenCardId = cardIds.get(userInput - 1);
					
					break;
				}
				
				int secondChoice = 0;
				while (secondChoice == 0) {
					System.out.println("Options:");
					System.out.println("1. Purchase");
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
				
				// Get info on the purchase
				System.out.print("Enter total of your purchase: ");
				String purchaseAmount = scan.next();
				
				// Insert the information on this purchase
				PreparedStatement purchase = conn.prepareStatement("INSERT INTO ActivityInstances (instanceDate, relevantInfo, pId, ruleVersion, ruleCode, wId) values (?, ?, ?, ?, ?, ?)");
				long millis = System.currentTimeMillis();
				purchase.setDate(1, new Date(millis));
				purchase.setString(2, purchaseAmount);
				purchase.setInt(3, pid);
				purchase.setInt(4, ruleVersion);
				purchase.setString(5, ruleCode);
				purchase.setInt(6, walletId);
				try {
					purchase.executeUpdate();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Could not complete purchase.");
					break;
				}
				
				// Update the user's points and tier
				PreparedStatement customerWallet = conn.prepareStatement("SELECT points, alltimepoints, tierNumber FROM WalletParticipation WHERE wId = ? AND pId = ?");
				customerWallet.setInt(1, walletId);
				customerWallet.setInt(2, pid);
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
				updateWallet.setInt(1, points);
				updateWallet.setInt(2, allTimePoints);
				updateWallet.setInt(3, tierNumber);
				updateWallet.setInt(4, walletId);
				updateWallet.setInt(5, pid);
				updateWallet.executeUpdate();
				
				// Delete a gift card if necessary
				if (chosenCardId != -1) {
					PreparedStatement deleteCard = conn.prepareStatement("DELETE FROM GiftCards WHERE id = ?");
					deleteCard.setInt(0, chosenCardId);
					deleteCard.executeUpdate();
				}
				
				System.out.println("Successfully completed a purchase!");
				break;
			} catch (SQLException e1) {
				// Could not connect to database - stop running
				System.out.println("Could not connect to the database");
				System.exit(1);
			}
		}
		//scan.close();
		
	}
}
