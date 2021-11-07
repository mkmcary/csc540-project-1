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
 * Displays the Add Activity Type page and allows for adding activity types
 * @author Grey Files
 */
public class AdminAddActivityType {
	
	/**
	 * Displays the Add Activity Type page and allows for adding activity types
	 * @param conn connection to the database
	 */
	public AdminAddActivityType(Connection conn) {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid activity input
		boolean validInput = false;
	    
		try {
			//Class.forName("oracle.jdbc.OracleDriver");
            PreparedStatement pstmt = null;
            
            while (!validInput) {
            	
        		System.out.print("Enter New Activity Name: ");
        		String name = scan.nextLine();
        		System.out.print("Enter New Activity Code: ");
        		String code = scan.nextLine();
    			
    			System.out.println("\n1) Add Activity Type\n2) Go Back");
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
    					
    				} catch (InputMismatchException e) {
    					UserInterface.invalidInput();
    				}
    			}
    			
    			// If user selected to add type, otherwise exit to menu above
    			if (selection == 1) {
    				pstmt = conn.prepareStatement("INSERT INTO ActivityCategories VALUES(?,?)");
                    
                    pstmt.clearParameters();
                    pstmt.setString(1, code);
                    pstmt.setString(2, name);
                    
                    int rows = pstmt.executeUpdate();
                    if (rows < 1) {
                    	throw new SQLException();
                    }
                    else {
                    	validInput = true;
                    	System.out.println("Activity Type Saved");
                    }
    			}
    			else {
    				// Get out of loop and return to calling class even though no submission
    				validInput = true;
    			}
            }
            
            
		} catch (Throwable e) {
			System.out.println("Invalid Customer Information. Try Again.");
		}
		
		scan.close();
	}

}
