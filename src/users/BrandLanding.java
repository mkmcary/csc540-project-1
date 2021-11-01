/**
 * 
 */
package users;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class BrandLanding {
	
	/** CLI separator */
	final static String SEPARATOR = "------------------------------";
	
	/** The id of the brand user */
	private int id;

	/**
	 * Constructor for Brand Landing page
	 */
	public BrandLanding(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	private static int validPage(Scanner scanner, int pages) {
		int page = 0;
		boolean invalidInput = false;
		
		// Handles invalid input
		do {
			if (!scanner.hasNextInt()) {
				invalidInput = true;
				System.out.println("Input must be an integer from 1-" + pages + ".");
				scanner.next();
			} else {
				page = scanner.nextInt();
				if (page < 1 || page > pages) {
					invalidInput = true;
					System.out.println("Input must be an integer from 1-" + pages + ".");
				}
			}
		} while (invalidInput);
		
		return page;
	}
	
	/**
	 * Default page for brand users
	 */
	public static void brandLandingPage() {
		Scanner scanner = new Scanner(System.in);
		boolean logout = false;
		
		while (!logout) {
			System.out.println(SEPARATOR);
			
			System.out.println("1. addLoyaltyProgram");
			System.out.println("2. addRERules");
			System.out.println("3. updateRERules");
			System.out.println("4. addRRRules");
			System.out.println("5. updateRRRules");
			System.out.println("6. validateLoyaltyProgram");
			System.out.println("7. Log out");
			
			int page = validPage(scanner, 7);
			
			// Directs to page
			switch (page) {
			case 1:
				addLoyaltyProgram(scanner);
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
		
		scanner.close();
	}
	
	/**
	 * Brand user adding Loyalty Program
	 */
	public static void addLoyaltyProgram(Scanner scanner) {
		boolean back = false;
		
		while (!back) {
			System.out.println(SEPARATOR);
			System.out.println("1. Regular");
			System.out.println("2. Tier");
			System.out.println("3. Go back");
			
			int page = validPage(scanner, 3);
			
			// Directs to page
			switch (page) {
			case 1:
				addProgramType(scanner, false);
				break;
			case 2:
				addProgramType(scanner, true);
				break;
			default:
				back = true;
			}
		}
	}
	
	/**
	 * Page for adding program type.
	 * @param scanner input
	 * @param tiered whether program is 
	 */
	public static void addProgramType(Scanner scanner, boolean tiered) {
		boolean back = false;
		
		while (!back) {
			System.out.println(SEPARATOR);
			
			if (tiered) {
				System.out.println("1. Tier Set up");
				System.out.println("2. Activity Type");
				System.out.println("3. Reward Type");
				System.out.println("4. Go back");
				
				int page = validPage(scanner, 4);
				
				// Directs to page
				switch (page) {
				case 1:
					tierSetup(scanner);
					break;
				case 2:
					addActivityType(scanner);
					break;
				case 3:
					addRewardType(scanner);
					break;
				default:
					back = true;
				}
			} else {
				System.out.println("1. Activity Type");
				System.out.println("2. Reward Type");
				System.out.println("3. Go back");
				
				int page = validPage(scanner, 3);
				
				// Directs to page
				switch (page) {
				case 1:
					addActivityType(scanner);
					break;
				case 2:
					addRewardType(scanner);
					break;
				default:
					back = true;
				}
			}
		}
	}
	
	public static void tierSetup(Scanner scanner) {
		
	}
	
	public static void addActivityType(Scanner scanner) {
		
	}
	
	public static void addRewardType(Scanner scanner) {
		
	}
	
	// --------------------------------------------------------------------------------------------
	
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
