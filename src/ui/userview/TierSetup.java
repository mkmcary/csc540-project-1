/**
 * 
 */
package ui.userview;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class TierSetup {
	
	/** CLI separator */
	final static String SEPARATOR = "------------------------------";
	
	/** The id of the brand user */
	private static int id;

	/**
	 * @param id 
	 * 
	 */
	@SuppressWarnings("static-access")
	public TierSetup(int id) {
		this.id = id;
		tierSetup();
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

	public static void tierSetup() {
		Scanner scanner = new Scanner(System.in);
		boolean back = false;
		
		while (!back) {
			System.out.println(SEPARATOR);
			
			System.out.println("1. Set up");
			System.out.println("2. Go back");
			System.out.println("Enter an interger that corresponds to the menu above:");
			
			int page = validPage(scanner, 2);
			
			if (page == 1) {
				System.out.println("Please enter the following information in order:");
				System.out.println("Please enter the following information in order:");
			} else {
				back = true;
			}
		}
	}
}
