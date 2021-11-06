/**
 * 
 */
package ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Shows queries given by instructor
 * @author Grey Files
 */
public class ShowQueries {

	/**
	 * Shows queries given by instructor
	 * @param conn connection to the database
	 */
	public ShowQueries(Connection conn) {
		Scanner scan = new Scanner(System.in);
		
		//Used to loop back if invalid brand input
		boolean loop = true;
		
		while(loop) {
			UserInterface.newScreen();
			
			System.out.println("Select the desired query from the list below");
			System.out.println("1) List all customers that are not part of Brand02’s program");
			System.out.println("2) List customers that have joined a loyalty program but have not participated in any activity "
					+ "in that program (list the customerid and the loyalty program id)");
			System.out.println("3) List the rewards that are part of Brand01 loyalty program");
			System.out.println("4) List all the loyalty programs that include “refer a friend” as an activity in at least one of "
					+ "their reward rules");
			System.out.println("5) For Brand01, list for each activity type in their loyalty program, the number instances that "
					+ "have occurred");
			System.out.println("6) List customers of Brand01 that have redeemed at least twice");
			System.out.println("7) All brands where total number of points redeemed overall is less than 500 points");
			System.out.println("8) For Customer C0003, and Brand02, number of activities they have done in the period of "
					+ "08/1/2021 and 9/30/2021");
			System.out.println("9) Go Back");
			
			System.out.print("\nSelect an Option: ");
			
			boolean selected = false;
			int selection = 0;
			
			//Validate user selection of menu
			while (!selected) {
				try {
					selection = scan.nextInt();
					if (selection < 1 || selection > 8) {
						throw new InputMismatchException();
					}
					selected = true;
					
				} catch (InputMismatchException e) {
					UserInterface.invalidInput();
				}
			}
			
			switch(selection) {
				case 1: query1(conn);
						break;
				case 2: query2(conn);
					break;
				case 3: query3(conn);
					break;
				case 4: query4(conn);
					break;
				case 5: query5(conn);
					break;
				case 6: query6(conn);
					break;
				case 7: query7(conn);
					break;
				case 8: query8(conn);
					break;
				case 9: loop = false;
					break;
			}
			
		}
	}
	
	private void query1(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT C1.cname FROM Customers C1 WHERE NOT EXISTS "
					+ "(SELECT WP1.wId FROM WalletParticipation WP1 WHERE (WP1.wId = "
					+ "(SELECT CW1.wId FROM CustomerWallets CW1 WHERE (CW1.cId = C1.id)) AND "
					+ "WP1.pId = (SELECT B1.pId FROM Brands B1 WHERE (B1.id = 2)))");
			
			while (rs.next()) {
				System.out.println(rs.getString("cname"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void query2(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT C1.id, WP.pId FROM Customers C1, WalletParticipation WP1, CustomerWallets CW1 WHERE "
					+ "(C1.id = CW1.cId AND CW1.wId = WP1.wId AND WP1.points = 0 AND WP1.alltimepoints = 0)");
			
			while (rs.next()) {
				System.out.println("Customer id: " + rs.getInt("id"));
				System.out.println("Program id: " + rs.getInt("pId"));
				System.out.println("---");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void query3(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("Select R1.rName FROM Rewards R1, RewardRedeemingRules RR1, Brands B1 WHERE "
					+ "(B1.id = 1 AND B1.pId = RR1.pId AND RR1.rId = R1.rId)");
			
			while (rs.next()) {
				System.out.println(rs.getString("cname"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void query4(Connection conn) {
		
	}
	
	private void query5(Connection conn) {
		
	}
	
	private void query6(Connection conn) {
		
	}
	
	private void query7(Connection conn) {
		
	}
	
	private void query8(Connection conn) {
		
	}
}
