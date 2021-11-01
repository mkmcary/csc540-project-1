/**
 * 
 */
package ui.userview;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class ProgramType {
	
	/** CLI separator */
	final static String SEPARATOR = "------------------------------";
	
	/** The id of the brand user */
	private static int id;
	
	/** Tiered or not */
	private static boolean tiered;

	@SuppressWarnings("static-access")
	public ProgramType(int id, boolean tiered) {
		this.id = id;
		this.tiered = tiered;
		addProgramType();
	}
	
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
		
		scanner.close();
		return page;
	}

	/**
	 * Page for adding program type.
	 * @param scanner input
	 * @param tiered whether program is 
	 */
	@SuppressWarnings("unused")
	public static void addProgramType() {
		Scanner scanner = new Scanner(System.in);
		boolean back = false;
		
		while (!back) {
			System.out.println(SEPARATOR);
			
			if (tiered) {
				System.out.println("1. Tier Set up");
				System.out.println("2. Activity Type");
				System.out.println("3. Reward Type");
				System.out.println("4. Go back");
				System.out.println("Enter an interger that corresponds to the menu above:");
				
				int page = validPage(scanner, 4);
				
				// Directs to page
				switch (page) {
				case 1:
					TierSetup ts = new TierSetup(id);
					break;
				case 2:
					//addActivityType(scanner);
					break;
				case 3:
					//addRewardType(scanner);
					break;
				default:
					back = true;
				}
			} else {
				System.out.println("1. Activity Type");
				System.out.println("2. Reward Type");
				System.out.println("3. Go back");
				System.out.println("Enter an interger that corresponds to the menu above:");
				
				int page = validPage(scanner, 3);
				
				// Directs to page
				switch (page) {
				case 1:
					//addActivityType(scanner);
					break;
				case 2:
					//addRewardType(scanner);
					break;
				default:
					back = true;
				}
			}
		}
	}
	
	public static void addActivityType(Scanner scanner) {
		
	}
	
	public static void addRewardType(Scanner scanner) {
		
	}
}
