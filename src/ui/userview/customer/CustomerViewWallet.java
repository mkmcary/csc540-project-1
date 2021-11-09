package ui.userview.customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Displays a Customer wallet view page.  The customer can see all of their points for each program that they
 * are enrolled in.
 * 
 * @author Matthew Martin
 *
 */
public class CustomerViewWallet {
	
	/**
	 * Constructs a new Customer wallet view page.  The customer can see all of their points for each program that they
	 * are enrolled in.
	 * 
	 * @param custId the customer's id value.
	 * @param walletId the customer's wallet id.
	 * @param conn the Connection to the database.
	 */
	public CustomerViewWallet(int custId, int walletId, Connection conn) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM WalletParticipation WHERE wId = ?");
			pstmt.setInt(1, walletId);
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("------------------------------");
			System.out.println("Your Wallet:");
			System.out.println("Loyalty Programs:");
			
			// Go through our wallet programs/points
			while(rs.next()) {
				// Get the information on this participation
				int pid = rs.getInt("pId");
				int points = rs.getInt("points");
				int pointsAllTime = rs.getInt("alltimepoints");
				int tier = rs.getInt("tierNumber");
				
				// Get the program name and isTiered
				PreparedStatement programQuery = conn.prepareStatement("SELECT * FROM LoyaltyPrograms WHERE id = ?");
				programQuery.setInt(1, pid);
				ResultSet programInfo = programQuery.executeQuery();
				
				// program name
				String programName = "";
				boolean isTiered = false;
				while(programInfo.next()) {
					programName = programInfo.getString("pName");
					String tieredString = programInfo.getString("isTiered");
					isTiered = (tieredString.equals("Y")) ? true : false;
				}
				
				// tier name
				if (isTiered) {
					PreparedStatement tierQuery = conn.prepareStatement("SELECT * FROM Tiers WHERE pId = ? AND tnum = ?");
					tierQuery.setInt(1, pid);
					tierQuery.setInt(2, tier);
					ResultSet tierInfo = tierQuery.executeQuery();
					
					String tierName = "";
					while(tierInfo.next()) {
						tierName = tierInfo.getString("tname");
					}
					
					// Print to user
					System.out.println("- Program: " + programName + "\n     Points: " + points + "\n     All Time Points Earned: " + pointsAllTime + "\n     Tier: " + tierName);
				} else {
					// Print to user
					System.out.println("- Program: " + programName + "\n     Points: " + points + "\n     All Time Points Earned: " + pointsAllTime);
				}
			}
			
			PreparedStatement giftCardsQuery = conn.prepareStatement("SELECT * FROM GiftCards GC, LoyaltyPrograms LP WHERE GC.wId = ? AND LP.id = GC.pId");
			giftCardsQuery.setInt(1, walletId);
			ResultSet giftCards = giftCardsQuery.executeQuery();
			
			// Go through our gift cards
			System.out.println("\nGift Cards:");
			while(giftCards.next()) {
				// Get the info on the card - program, value, exp date
				String pName = giftCards.getString("pName");
				int value = giftCards.getInt("cardValue");
				Date expDate = giftCards.getDate("expiryDate");
				
				// Print out to the user
				System.out.println("- $" + value + " for " + pName + ", Expires " + expDate.toString());
			}
			
		} catch (Exception e) {
			System.out.println("Could not connect to database.");
			System.exit(1);
		}
		
		System.out.println("Type \'exit\' and press enter to go back.");
		Scanner scan = new Scanner(System.in);
		scan.next();
		//scan.close();
	}
}
