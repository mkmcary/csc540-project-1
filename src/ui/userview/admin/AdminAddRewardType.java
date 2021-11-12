/**
 * 
 */
package ui.userview.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import ui.UserInterface;

/**
 * Displays the Add Reward Type page and allows for adding reward types
 * @author Grey Files
 */
public class AdminAddRewardType {
	
	/**
	 * Displays the Add Reward Type page and allows for adding reward types
	 * @param conn connection to the database
	 */
	public AdminAddRewardType(Connection conn) {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid reward input
		boolean validInput = false;
		
		while (!validInput) {
			
			try {
				//Class.forName("oracle.jdbc.OracleDriver");
	            PreparedStatement pstmt = null;
	            	
	    		System.out.print("Enter New Reward Name: ");
	    		String name = scan.nextLine();
	    		System.out.print("Enter New Reward Code: ");
	    		String code = scan.nextLine();
				
				System.out.println("\n1) Add Reward Type\n2) Go Back");
				System.out.print("\nSelect an Option: ");
				
				boolean selected = false;
				int selection = 0;
				
				//Validate user selection of menu
				while (!selected) {
					try {
						selection = Integer.parseInt(scan.nextLine());
						if (selection < 1 || selection > 2) {
							throw new InputMismatchException();
						}
						selected = true;
						
					} catch (Exception e) {
						UserInterface.invalidInput();
					}
				}
				
				// If user selected to add type, otherwise exit to menu above
				if (selection == 1) {
					pstmt = conn.prepareStatement("INSERT INTO Rewards(rId, rName) VALUES(?,?)");
	                
	                pstmt.clearParameters();
	                pstmt.setString(1, code);
	                pstmt.setString(2, name);
	                
	                int rows = pstmt.executeUpdate();
	                if (rows < 1) {
	                	throw new SQLException();
	                }
	                else {
	                	validInput = true;
	                	System.out.println("Reward Type Saved");
	                }
				}
				else {
					// Get out of loop and return to calling class even though no submission
					validInput = true;
				}
	            
	            
			} catch (Throwable e) {
				System.out.println("Invalid Reward Information. Try Again.");
			}
			
		}
	    
	}

}
