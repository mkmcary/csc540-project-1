/**
 * 
 */
package ui.userview.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import ui.UserInterface;

/**
 * Displays the Show Customer Info page and allows for showing customer info
 * @author Grey Files
 */
public class AdminShowCustomerInfo {
	
	/**
	 * Displays the Show Customer Info page and allows for showing customer info
	 * @param conn connection to the database
	 */
	public AdminShowCustomerInfo(Connection conn) {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid brand input
		boolean validInput = false;
	    
		try {
			//Class.forName("oracle.jdbc.OracleDriver");

            PreparedStatement pstmt = null;
            
            while (!validInput) {
            	
        		System.out.print("Enter Customer User ID: ");
        		String username = scan.nextLine();
    			
    			System.out.println("\n1) Show Customer Info\n2) Go Back");
    			System.out.print("\nSelect an Option: ");
    			
    			boolean selected = false;
    			int selection = 0;
    			
    			//Validate user selection of menu
    			while (!selected) {
    				try {
    					selection = scan.nextInt();
    					if (selection < 1 || selection > 2) {
    						throw new InputMismatchException();
    					}
    					selected = true;
    					
    				} catch (InputMismatchException e) {
    					UserInterface.invalidInput();
    				}
    			}
    			
    			// If user selected to show customer info, otherwise exit to menu above
    			if (selection == 1) {
    				pstmt = conn.prepareStatement("SELECT * FROM Customers WHERE username = ?");
                    
                    pstmt.clearParameters();
                    pstmt.setString(1, username);
                    
                    ResultSet rs = pstmt.executeQuery();
                    
                    int i = 0;
                    while (rs.next()) {
                    	i++;
                    }
                    if (i != 1) {
                    	throw new SQLException();
                    }
                    else {
                    	validInput = true;
                    	rs.first();
                    	
                    	System.out.println("Name: " + rs.getString("cname"));
                    	System.out.println("Phone Number: " + rs.getString("phoneNumber"));
                    	System.out.println("Address: " + rs.getString("caddress"));
                    	System.out.println("User ID: " + rs.getString("username"));
                    }
    			}
    			else {
    				// Get out of loop and return to calling class even though no submission
    				validInput = true;
    			}
            }
            
            
		} catch (Throwable e) {
			System.out.println("Invalid Customer User ID. Try Again.");
		}
		
		scan.close();
	}

}
