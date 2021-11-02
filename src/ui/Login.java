/**
 * 
 */
package ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.nio.charset.StandardCharsets;

/**
 * Displays the login page and allows for login functionality
 * @author Grey Files
 */
public class Login {
	
	// URL to connect to database
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    /**
     * Displays the login page and allows for login functionality
     */
	public Login() {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		//Used to loop back if invalid sign on
		boolean signOn = false;
	    
		try {
			MessageDigest md = MessageDigest.getInstance("SHA3-256");
			
			//Set up database connection
			Class.forName("oracle.jdbc.OracleDriver");
			String user = "mgfiles";
			String passwd = "abcd1234";
			
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            conn = DriverManager.getConnection(jdbcURL, user, passwd);
            
            stmt = conn.createStatement();
            
            while (!signOn) {
            	
        		System.out.print("Enter Your User ID: ");
        		String userID = scan.nextLine();
        		System.out.print("Enter Your Password: ");
        		String password = scan.nextLine();
    			String hashedpw = new String(md.digest(password.getBytes()), StandardCharsets.UTF_8);
    			
    			System.out.println("\n1) Sign-in\n2) Go Back");
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
    			
    			// If user selected to sign in, otherwise exit to menu above
    			if (selection == 1) {
                    rs = stmt.executeQuery("SELECT id, username, pass FROM Brands");
                    
                    while(rs.next()) {
                    	if (rs.getString("username") == userID && rs.getString("pass").equals(hashedpw)) {
                    		signOn = true;
                    		//TODO: pass in (id, connection)
                    		BrandLanding = new BrandLanding(rs.getInt("id"));
                    	}
                    }
                    
                    rs = stmt.executeQuery("SELECT id, username, pass FROM Customers");
                    
                    while(rs.next()) {
                    	if (rs.getString("username") == userID && rs.getString("pass").equals(hashedpw)) {
                    		signOn = true;
                    		CustomerLanding = new CustomerLanding(rs.getInt("id"));
                    	}
                    }
                    
                    //TODO: add Admin users to database schema and query to log in as one
                    
                    if (!signOn) {
                    	System.out.println("Login Incorrect. Please try again");
                    }
    			}
    			else {
    				// Get out of loop and return to calling class even though no sign on
    				signOn = true;
    			}
            }
            
            
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		scan.close();
	}
}
