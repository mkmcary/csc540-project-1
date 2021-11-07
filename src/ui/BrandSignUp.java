/**
 * 
 */
package ui;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Displays the Brand Sign Up Page and allows the User to make a new brand
 * @author Grey Files
 */
public class BrandSignUp {
	
	// Whether the brand sign up information has been submitted
	public boolean submitted = false;

	/**
	 * Displays the Brand Sign Up Page and allows the User to make a new brand
	 * @param conn connection to the database
	 */
	public BrandSignUp(Connection conn) {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid brand input
		boolean validInput = false;
	    
		try {
			//MessageDigest md = MessageDigest.getInstance("SHA3-256");
			
			//Class.forName("oracle.jdbc.OracleDriver");

            PreparedStatement pstmt = null;
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            
            while (!validInput) {
            	
        		System.out.print("Enter Your Name: ");
        		String name = scan.nextLine();
        		System.out.print("Enter Your Address: ");
        		String address = scan.nextLine();
        		System.out.print("Enter Your Username: ");
        		String username = scan.nextLine();
        		System.out.print("Enter Your Password: ");
        		String password = scan.nextLine();
    			//String hashedpw = new String(md.digest(password.getBytes()), StandardCharsets.UTF_8);
    			
    			System.out.println("\n1) Sign-up\n2) Go Back");
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
    			
    			// If user selected to sign up, otherwise exit to menu above
    			if (selection == 1) {
    				
    				// Make sure new user is unique
    				rs = stmt.executeQuery("SELECT id FROM Brands WHERE username = " + username);
    				int i = 0;
    				
    				while (rs.next()) {
    					i++;
    				}
    				
    				rs = stmt.executeQuery("SELECT id FROM Customers WHERE username = " + username);
    				
    				while (rs.next()) {
    					i++;
    				}
    				
    				rs = stmt.executeQuery("SELECT id FROM Admins WHERE username = " + username);
    				
    				while (rs.next()) {
    					i++;
    				}
    				
    				if (i > 0) {
    					throw new SQLException();
    				}
    				
    				pstmt = conn.prepareStatement("INSERT INTO Brands VALUES(?,?,?,?,?)",
    						Statement.RETURN_GENERATED_KEYS);
                    
                    pstmt.clearParameters();
                    pstmt.setString(1, name);
                    pstmt.setString(2, address);
                    pstmt.setString(3, username);
                    pstmt.setString(4, password);
                    long millis=System.currentTimeMillis();  
                    pstmt.setDate(5, new Date(millis));
                    
                    int rows = pstmt.executeUpdate();
                    if (rows < 1) {
                    	throw new SQLException();
                    }
                    else {
                    	validInput = true;
                    	this.submitted = true;
                    	System.out.println("Brand information saved");
                    }
    			}
    			else {
    				// Get out of loop and return to calling class even though no sign up
    				validInput = true;
    			}
            }
            
            
		} catch (Throwable e) {
			System.out.println("Invalid Brand Information. Try Again.");
		}
		
		new Login(conn);
	}
}
