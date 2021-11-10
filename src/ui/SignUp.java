/**
 * 
 */
package ui;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Displays the user type page asking what type of sign up to complete
 * @author Grey Files
 */
public class SignUp {
	
	/**
	 * Displays the user type page asking what type of sign up to complete
	 * @param conn connection to the database
	 */
	public SignUp(Connection conn) {
		boolean running = true;
		Scanner scan = new Scanner(System.in);
		
		while(running) {
			
			UserInterface.newScreen();
			System.out.println("Choose which type of account to create");
			System.out.println("\n1) Brand Sign-up\n2) Customer Sign-up\n3) Go Back");
			System.out.print("\nSelect an Option: ");
			
			boolean selected = false;
			int selection = 0;
			
			while (!selected) {
				try {
					selection = Integer.parseInt(scan.nextLine());
					if (selection < 1 || selection > 3) {
						throw new InputMismatchException();
					}
					selected = true;
					
				} catch (InputMismatchException e) {
					UserInterface.invalidInput();
				}
			}
			
			switch (selection) {
				case 1: BrandSignUp brandsignup = new BrandSignUp(conn);
						running = !brandsignup.submitted;
						break;
				case 2: CustomerSignUp customersignup = new CustomerSignUp(conn);
						running = !customersignup.submitted;
						break;
				case 3: running = false;
						break;
			}
		}
	}
}
