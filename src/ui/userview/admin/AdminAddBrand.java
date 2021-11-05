/**
 * 
 */
package ui.userview.admin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import ui.Login;
import ui.UserInterface;

/**
 * Displays the Add Brand page and allows for adding brands
 * @author Grey Files
 */
public class AdminAddBrand {
	
	/**
	 * Displays the Add Brand page and allows for adding brands
	 * @param conn connection to the database
	 */
	public AdminAddBrand(Connection conn) {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid brand input
		boolean validInput = false;
	    
		try {
			MessageDigest md = MessageDigest.getInstance("SHA3-256");
			
			//Class.forName("oracle.jdbc.OracleDriver");

            PreparedStatement pstmt = null;
            
            while (!validInput) {
            	
        		System.out.print("Enter Brand Name: ");
        		String name = scan.nextLine();
        		System.out.print("Enter Brand Address: ");
        		String address = scan.nextLine();
        		System.out.print("Enter Brand Username: ");
        		String username = scan.nextLine();
        		System.out.print("Enter Brand Password: ");
        		String password = scan.nextLine();
    			String hashedpw = new String(md.digest(password.getBytes()), StandardCharsets.UTF_8);
    			
    			System.out.println("\n1) Add Brand\n2) Go Back");
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
    			
    			// If user selected to add brand, otherwise exit to menu above
    			if (selection == 1) {
    				pstmt = conn.prepareStatement("INSERT INTO Brands VALUES(?,?,?,?,?,?)",
    						Statement.RETURN_GENERATED_KEYS);
                    
                    pstmt.clearParameters();
                    pstmt.setString(1, name);
                    pstmt.setString(2, address);
                    pstmt.setString(3, username);
                    pstmt.setString(4, hashedpw);
                    long millis=System.currentTimeMillis();  
                    pstmt.setDate(5, new Date(millis));
                    pstmt.setNull(6, 4);
                    
                    int rows = pstmt.executeUpdate();
                    if (rows < 1) {
                    	throw new SQLException();
                    }
                    else {
                    	validInput = true;
                    	System.out.println("Brand information saved");
                    }
    			}
    			else {
    				// Get out of loop and return to calling class even though no submission
    				validInput = true;
    			}
            }
            
            
		} catch (Throwable e) {
			System.out.println("Invalid Brand Information. Try Again.");
		}
		
		scan.close();
	}

}
