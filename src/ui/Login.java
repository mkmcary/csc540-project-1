/**
 * 
 */
package ui;

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Grey Files
 */
public class Login {
	
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

	public Login() {
		Scanner scan = new Scanner(System.in);
		UserInterface.newScreen();
		
		boolean signOn = false;
	    
		try {
			MessageDigest md = MessageDigest.getInstance("SHA3-256");
			String hashedpw = new String(md.digest(password.getBytes()), StandardCharsets.UTF_8);
			
			Class.forName("oracle.jdbc.OracleDriver");
			String user = "mgfiles";
			String passwd = "abcd1234";
			
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            conn = DriverManager.getConnection(jdbcURL, user, passwd);
            
            stmt = conn.createStatement();
            
            while (!signOn) {
            	//TODO: change to include 1) and 2) menu options
            	
        		System.out.print("Enter Your User ID: ");
        		String userID = scan.nextLine();
        		System.out.print("Enter Your Password: ");
        		String password = scan.nextLine();
                
                rs = stmt.executeQuery("SELECT id, username, pass FROM Brands");
                
                while(rs.next()) {
                	if (rs.getString("username") == userID && rs.getString("pass").equals(hashedpw)) {
                		signOn = true;
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
            
            
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		scan.close();
	}
}
