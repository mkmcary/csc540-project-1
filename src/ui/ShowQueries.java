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
					selection = Integer.parseInt(scan.nextLine());
					if (selection < 1 || selection > 9) {
						throw new InputMismatchException();
					}
					selected = true;
					
				} catch (Exception e) {
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
					+ "WP1.pId = (SELECT LP1.id FROM LoyaltyPrograms LP1 WHERE (LP1.bId = 2))))");
			
			while (rs.next()) {
				System.out.println(rs.getString("cname"));
			}
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void query2(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT C1.id, WP1.pId FROM Customers C1, WalletParticipation WP1, CustomerWallets CW1 WHERE "
					+ "(C1.id = CW1.cId AND CW1.wId = WP1.wId AND WP1.points = 0 AND WP1.alltimepoints = 0)");
			
			while (rs.next()) {
				System.out.println("Customer id: " + rs.getInt("id"));
				System.out.println("Program id: " + rs.getInt("pId"));
				System.out.println("---");
			}
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	private void query3(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("Select R1.rName FROM Rewards R1, RewardRedeemingRules RR1 WHERE "
					+ "(RR1.pId = 1 AND RR1.rId = R1.rId)");
			
			while (rs.next()) {
				System.out.println(rs.getString("rName"));
			}
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void query4(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT LP1.pName FROM LoyaltyPrograms LP1, RewardEarningRules RE1, ActivityCategories AC1 WHERE "
					+ "(LP1.id = RE1.pId AND AC1.acId = RE1.acId AND AC1.acName = 'Refer a Friend')");
			
			while (rs.next()) {
				System.out.println(rs.getString("pName"));
			}
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void query5(Connection conn) {
		//For Brand01, list for each activity type in their loyalty program, 
		//the number instances that have occurred
		
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT AC.acName AS name, COUNT(*) AS count "
					+ "FROM LoyaltyPrograms LP, ActivityInstances AI, RewardEarningRules RE, ActivityCategories AC "
					+ "WHERE LP.bId = 1 AND AI.pId = LP.id AND RE.pId = AI.pId AND RE.ruleVersion = AI.ruleVersion AND RE.ruleCode = AI.ruleCode "
					+ "AND RE.acId = AC.acId "
					+ "GROUP BY AC.acName");
			
			while (rs.next()) {
				System.out.println(rs.getString("name") + " " + rs.getInt("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void query6(Connection conn) {
		// List customers of Brand01 that have redeemed at least twice
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT C.cname AS name "
					+ "FROM Customers C, CustomerWallets CW, Brands B, LoyaltyPrograms LP, RewardInstances RI "
					+ "WHERE B.id = 1 AND LP.bId = B.id AND RI.pId = LP.id AND CW.cId = C.id AND RI.wId = CW.wId "
					+ "GROUP BY C.cname "
					+ "HAVING COUNT(*) > 1");
			
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void query7(Connection conn) {
		// All brands where total number of points redeemed overall is less than 500 points
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT B1.bname, LP1.pCode " + 
					"FROM BRANDS B1, LoyaltyPrograms LP1, RewardInstances RI1, RewardRedeemingRules RRR1 " + 
					"WHERE (B1.id = LP1.bId AND RI1.pId = LP1.id AND RI1.pId = RRR1.pId AND RI1.ruleVersion = RRR1.ruleVersion AND RI1.ruleCode = RRR1.ruleCode) " + 
					"GROUP BY (B1.bname, LP1.pCode) " + 
					"HAVING (SUM(RRR1.points) < 500) " + 
					"UNION " + 
					"SELECT B2.bname, LP2.pCode " + 
					"FROM Brands B2, LoyaltyPrograms LP2 " + 
					"WHERE (B2.id = LP2.bId AND NOT EXISTS (SELECT * FROM RewardInstances RI2 WHERE RI2.pId = LP2.id))");
			
			while (rs.next()) {
				System.out.println("Brand: " + rs.getString("bname") + ", Code: " + rs.getString("pCode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void query8(Connection conn) {
		// For Customer C0003, and Brand02, number of activities they have done in the period of
		// 08/1/2021 and 9/30/2021
		try {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count "
					+ "FROM Customers C, CustomerWallets CW, Brands B, LoyaltyPrograms LP, ActivityInstances AI "
					+ "WHERE C.id = 3 AND CW.cId = 3 AND B.id = 2 AND LP.bId = B.id AND AI.wId = CW.wId "
					+ "AND AI.pId = LP.id AND AI.instanceDate >= to_date(\'01-08-2021\', \'dd-mm-yyyy\') AND AI.instanceDate <= to_date(\'30-09-2021\', \'dd-mm-yyyy\')");
		
			while (rs.next()) {
				System.out.println(rs.getInt("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
