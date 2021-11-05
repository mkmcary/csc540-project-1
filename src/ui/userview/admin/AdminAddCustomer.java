/**
 * 
 */
package ui.userview.admin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import ui.Login;
import ui.UserInterface;

/**
 * Displays the Add Customer page and allows for adding customers
 * @author Grey Files
 */
public class AdminAddCustomer {

	/**
	 * Displays the Add Customer page and allows for adding customers
	 * @param conn connection to the database
	 */
	public AdminAddCustomer(Connection conn) {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid brand input
		boolean validInput = false;
	    
		try {
			MessageDigest md = MessageDigest.getInstance("SHA3-256");
			
			//Class.forName("oracle.jdbc.OracleDriver");
			
            PreparedStatement pstmt = null;
            
            while (!validInput) {
            	
        		System.out.print("Enter Customer Name: ");
        		String name = scan.nextLine();
        		System.out.print("Enter Customer Phone Number: ");
        		String phoneNumber = scan.nextLine();
        		System.out.print("Enter Customer Address: ");
        		String address = scan.nextLine();
        		System.out.print("Enter Customer Username: ");
        		String username = scan.nextLine();
        		System.out.print("Enter Customer Password: ");
        		String password = scan.nextLine();
    			String hashedpw = new String(md.digest(password.getBytes()), StandardCharsets.UTF_8);
    			
    			System.out.println("\n1) Add Customer\n2) Go Back");
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
    			
    			// If user selected to add customer, otherwise exit to menu above
    			if (selection == 1) {
    				pstmt = conn.prepareStatement("INSERT INTO Customers VALUES(?,?,?,?,?)",
    						Statement.RETURN_GENERATED_KEYS);
                    
                    pstmt.clearParameters();
                    pstmt.setString(1, name);
                    pstmt.setString(2, phoneNumber);
                    pstmt.setString(3, address);
                    pstmt.setString(4, username);
                    pstmt.setString(5, hashedpw);
                    
                    int rows = pstmt.executeUpdate();
                    if (rows < 1) {
                    	throw new SQLException();
                    }
                    else {
                    	validInput = true;
                    	System.out.println("Customer information saved");
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
