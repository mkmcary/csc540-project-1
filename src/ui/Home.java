package ui;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Displays homepage and allows users to selection action
 * @author Grey Files
 */
public class Home {

	/**
	 * Displays homepage and allows users to selection action
	 * @param conn connection to the database
	 */
	public Home(Connection conn) {
		boolean running = true;
		Scanner scan = new Scanner(System.in);
		
		while(running) {
			
			UserInterface.newScreen();
			System.out.println("Welcome to the Customer Loyalty Marketplace");
			System.out.println("\n1) Login\n2) Sign Up\n3) showQueries\n4) Exit");
			System.out.print("\nSelect an Option: ");
			
			boolean selected = false;
			int selection = 0;
			
			while (!selected) {
				try {
					selection = scan.nextInt();
					if (selection < 1 || selection > 4) {
						throw new InputMismatchException();
					}
					selected = true;
					
				} catch (InputMismatchException e) {
					UserInterface.invalidInput();
				}
			}
			
			switch (selection) {
				case 1: Login loginpage = new Login(conn);
						break;
				case 2: SignUp signuppage = new SignUp(conn);
						break;
				case 3: ShowQueries showqueriespage = new ShowQueries(conn);
						break;
				case 4: running = false;
						break;
			}
		}
		
		scan.close();
	}
}
