/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class BrandLanding {
	
	/** CLI separator */
	final static String SEPARATOR = "------------------------------";
	
	/** The id of the brand user */
	private static int id;

	/**
	 * Constructor for Brand Landing page
	 * @param id user of the brand
	 */
	@SuppressWarnings("static-access")
	public BrandLanding(int id) {
		this.id = id;
		brandLandingPage();
	}
	
	/**
	 * Loops until a valid input is read in.
	 * @param scanner scanner object that reads in input
	 * @param pages the max pages that menu can direct to
	 * @return valid page address
	 */
	private static int validPage(Scanner scanner, int pages) {
		int page = 0;
		boolean invalidInput = false;
		
		// Handles invalid input
		do {
			if (!scanner.hasNextInt()) {
				scanner.next();
				invalidInput = true;
				System.out.println("Input must be an integer from 1-" + pages + ".");
			} else {
				page = scanner.nextInt();
				if (page < 1 || page > pages) {
					invalidInput = true;
					System.out.println("Input must be an integer from 1-" + pages + ".");
				} else {
					invalidInput = false;
				}
			}
		} while (invalidInput);
		
		scanner.close();
		return page;
	}
	
	/**
	 * Homepage for brand users
	 */
	@SuppressWarnings("unused")
	public static void brandLandingPage() {
		Scanner scanner = new Scanner(System.in);
		boolean logout = false;
		
		while (!logout) {
			System.out.println(SEPARATOR);
			// Menu
			System.out.println("1. addLoyaltyProgram");
			System.out.println("2. addRERules");
			System.out.println("3. updateRERules");
			System.out.println("4. addRRRules");
			System.out.println("5. updateRRRules");
			System.out.println("6. validateLoyaltyProgram");
			System.out.println("7. Log out");
			System.out.println("Enter an interger that corresponds to the menu above:");
			
			int page = validPage(scanner, 7);
			
			// Directs to page
			switch (page) {
			case 1:
				LoyaltyPrograms lp = new LoyaltyPrograms(id);
				break;
			case 2:
				addRERules(scanner);
				break;
			case 3:
				addRERules(scanner);
				break;
			case 4:
				addRERules(scanner);
				break;
			case 5:
				addRERules(scanner);
				break;
			case 6:
				addRERules(scanner);
				break;
			default:
				logout = true;
			}
		}
	}
	
	// -------------------------------------------------------------------------------------------- oops
	
	public static void addRERules(Scanner scanner) {
		
	}
	
	public static void updateRERules(Scanner scanner) {
		
	}
	
	public static void addRRRules(Scanner scanner) {
		
	}

	
	public static void updateRRRules(Scanner scanner) {
		
	}
	
	public static void validateLoyaltyProgram(Scanner scanner) {
		
	}
}
