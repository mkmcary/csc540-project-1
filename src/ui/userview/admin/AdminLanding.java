/**
 * 
 */
package ui.userview.admin;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

import ui.UserInterface;

/**
 * Displays the Admin Landing page and allows for admins to choose their action
 * @author Grey Files
 */
public class AdminLanding {
	
	/**
	 * Displays the Admin Landing page and allows for admins to choose their action
	 * @param id id of the admin signing in
	 * @param conn connection to the database
	 */
	public AdminLanding(int id, Connection conn) {
		boolean running = true;
		Scanner scan = new Scanner(System.in);
		
		while(running) {
			
			UserInterface.newScreen();
			System.out.println("Welcome Administrator. Please Choose an Action");
			System.out.println("\n1) Add brand\n2) Add customer\n3) Show brand's info\n4) Show customer's info\n5) Add activity type\n6) Add reward type\n7) Log out");
			System.out.print("\nSelect an Option: ");
			
			boolean selected = false;
			int selection = 0;
			
			while (!selected) {
				try {
					selection = Integer.parseInt(scan.nextLine());
					if (selection < 1 || selection > 7) {
						throw new InputMismatchException();
					}
					selected = true;
					
				} catch (InputMismatchException e) {
					UserInterface.invalidInput();
				}
			}
			
			switch (selection) {
				case 1: new AdminAddBrand(conn);
						break;
				case 2: new AdminAddCustomer(conn);
						break;
				case 3: new AdminShowBrandInfo(conn);
						break;
				case 4: new AdminShowCustomerInfo(conn);
						break;
				case 5: new AdminAddActivityType(conn);
						break;
				case 6: new AdminAddRewardType(conn);
						break;
				case 7: running = false;
						break;
			}
		}
		
		scan.close();
	}

}
