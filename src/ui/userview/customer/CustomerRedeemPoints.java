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
 * Displays the customer points redemption page.  The customer can select from the rewards
 * available to them under all the programs they have enrolled in.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerRedeemPoints {
	
	/**
	 * Constructs a new customer points redemption page.  The customer can select from the rewards
	 * available to them under all the programs they have enrolled in.
	 */
	public CustomerRedeemPoints(int custId, int walletId, Connection conn) {
		// Create the scanner for user input
		Scanner scan = new Scanner(System.in);
		int userInput = 0;
		boolean done = false;
		
		while (!done) {
			try {
				// Header
				System.out.println("------------------------------");
				System.out.println("Welcome to the Rewards Page!\n");
				System.out.println("Please choose an program to redeem rewards for:");
				
				// All programs
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM LoyaltyPrograms");
				ResultSet programs = pstmt.executeQuery();
				
				// All of the programs we participate in
				pstmt = conn.prepareStatement("SELECT * FROM WalletParticipation WHERE wId = ?");
				pstmt.setInt(0, walletId);
				ResultSet participations = pstmt.executeQuery();
				
				ArrayList<Integer> ids = new ArrayList<Integer>();
				//ArrayList<String> codes = new ArrayList<String>();
				ArrayList<Integer> allPoints = new ArrayList<Integer>();
				
				int i = 1;
				while(programs.next()) {
					// Get the Program id
					int id = programs.getInt("id");
					
					// See if we participate
					boolean inProgram = false;
					participations.beforeFirst();
					while(participations.next()) {
						if(participations.getInt("pId") == id) {
							inProgram = true;
							break;
						}
					}
					
					// Only worry about this if we are in the program
					if (inProgram) {
						ids.add(id);
						
						// We need the program code, name, and points as well.
						String code = programs.getString("pCode");
						String name = programs.getString("pName");
						allPoints.add(programs.getInt("points"));
						
						// Print out option to user
						System.out.println(i + ") " + code + ": " + name);
						
						// Move to next tuple
						i++;
					}
				}
				
				int exitOption = i;
				System.out.println(i + ") Go Back");
				
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
				int userPoints = allPoints.get(userInput - 1);
				int choice = 0;
				while(choice == 0) {
					System.out.println("------------------------------");
					System.out.println("1. Rewards Selection");
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
					
					// If they said Rewards Selection, list rewards to choose from
					if (choice == 1) {
						while(true) {
							// All the rules for this program
							PreparedStatement programRuleQuery = conn.prepareStatement("SELECT * FROM RewardRedeemingRules WHERE pId = ?");
							programRuleQuery.setInt(0, pChosen);
							ResultSet programRules = programRuleQuery.executeQuery();
							
							// All the rewards
							PreparedStatement rewardQuery = conn.prepareStatement("SELECT * FROM Rewards");
							ResultSet allRewards = rewardQuery.executeQuery();
							
							// Print out the options to the user
							ArrayList<String> ruleCodes = new ArrayList<String>();
							ArrayList<Integer> ruleVersions = new ArrayList<Integer>();
							
							System.out.println("Select your reward: ");
							int j = 1;
							while(programRules.next()) {
								// Get basic info
								int pointsForRule = programRules.getInt("points");
								int quantity = programRules.getInt("quantity");
								String rewardId = programRules.getString("rId");
								
								String rewardName = "";
								allRewards.beforeFirst();
								while(allRewards.next()) {
									if(allRewards.getString("rId").equals(rewardId)) {
										rewardName = allRewards.getString("rName");
										break;
									}
								}
								
								// Special case for gift cards
								if(rewardName.equals("Gift Card")) {
									// Print out to the user
									System.out.println(j + ") $" + quantity + " Gift Card, " + pointsForRule + " Points");
								} else {
									// Print out to the user
									System.out.println(j + ") " + quantity + " " + rewardName +", " + pointsForRule + " Points");
								}
								
								// Get information on this rule in case the user selects it
								ruleCodes.add(programRules.getString("ruleCode"));
								ruleVersions.add(programRules.getInt("ruleVersion"));
								
								// next option
								j++;
							}
							
							// Go back option
							int goBackOption = j;
							System.out.println(j + ") Go Back");
							
							// Get user input
							String unvalidatedRewardIndexChoice = scan.next();
							int rewardIndexChoice = 0;
							try {
								rewardIndexChoice = Integer.parseInt(unvalidatedRewardIndexChoice);
							} catch (Exception e){
								rewardIndexChoice = 0;
							}
							
							// Validate options
							if (rewardIndexChoice < 1 || rewardIndexChoice > goBackOption)
								rewardIndexChoice = 0;
							
							// Retry if they gave bad input
							if (rewardIndexChoice == 0) {
								System.out.println("Please enter a valid choice (1-" + goBackOption + ")");
								continue;
							}
							
							// Go Back if they said to do so
							if (rewardIndexChoice == goBackOption) {
								break;
							}
							
							// Else, try to give them the reward requested
							String chosenRuleCode = ruleCodes.get(rewardIndexChoice - 1);
							int chosenRuleVersion = ruleVersions.get(rewardIndexChoice - 1);
							
							// Get info on this rule
							PreparedStatement chosenRuleRequest = conn.prepareStatement("SELECT * FROM RewardRedeemingRules WHERE pId = ? AND ruleVersion = ? AND ruleCode = ?");
							chosenRuleRequest.setInt(0, pChosen);
							chosenRuleRequest.setInt(1, chosenRuleVersion);
							chosenRuleRequest.setString(2, chosenRuleCode);
							ResultSet chosenRule = chosenRuleRequest.executeQuery();
							
							// Get the points, rId, and quantity
							chosenRule.next();
							int chosenRulePoints = chosenRule.getInt("points");
							String chosenRuleRID = chosenRule.getString("rId");
							int quantity = chosenRule.getInt("quantity");
							
							// Do we have enough points to do this?
							if (userPoints < chosenRulePoints) {
								// No, we dont have enough points
								System.out.println("You do not have enough points for this option.");
								break;
							}
							
							// Find out what reward this actually is
							String chosenRewardName = "";
							allRewards.beforeFirst();
							while(allRewards.next()) {
								if(allRewards.getString("rId").equals(chosenRuleRID)) {
									chosenRewardName = allRewards.getString("rName");
									break;
								}
							}
							
							// If its a gift card, give the user a gift card
							if(chosenRewardName.equals("Gift Card")) {
								PreparedStatement giftCardInsertion = conn.prepareStatement("INSERT INTO GiftCards (pId, wId, cardValue) values (?, ?, ?)");
								giftCardInsertion.setInt(0, pChosen);
								giftCardInsertion.setInt(1, walletId);
								giftCardInsertion.setFloat(2, quantity);
								
								giftCardInsertion.executeUpdate();
							}
							
							// Record the reward instance
							PreparedStatement rewardInstanceInsertion = conn.prepareStatement("INSERT INTO RewardInstances (instanceDate, pId, ruleVersion, ruleCode, wId) values (now(), ?, ?, ?, ?)");
							rewardInstanceInsertion.setInt(0, pChosen);
							rewardInstanceInsertion.setInt(1, chosenRuleVersion);
							rewardInstanceInsertion.setString(2, chosenRuleCode);
							rewardInstanceInsertion.setInt(3, walletId);
							rewardInstanceInsertion.executeUpdate();
							
							// Deduct the points from the user
							userPoints -= chosenRulePoints;
							PreparedStatement updateUserPoints = conn.prepareStatement("UPDATE TABLE WalletParticipation SET points = ? WHERE wId = ? AND pId = ?");
							updateUserPoints.setInt(0, userPoints);
							updateUserPoints.setInt(1, walletId);
							updateUserPoints.setInt(2, pChosen);
							updateUserPoints.executeUpdate();
							
							// Print out success to the user
							System.out.println("Congratulations! You have earned your chosen reward!");
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
