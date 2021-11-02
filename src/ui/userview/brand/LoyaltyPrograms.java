/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class LoyaltyPrograms {

	/** CLI separator */
	final static String SEPARATOR = "------------------------------";
	
	/** The id of the brand user */
	private static int id;
	
	/**
	 * Constructor for adding loyalty programs
	 * @param id user of the brand
	 */
	@SuppressWarnings("static-access")
	public LoyaltyPrograms(int id) {
		this.id = id;
		addLoyaltyProgram();
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
				invalidInput = true;
				System.out.println("Input must be an integer from 1-" + pages + ".");
				scanner.next();
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
	 * Brand user adding Loyalty Program
	 */
	@SuppressWarnings("unused")
	public static void addLoyaltyProgram() {
		Scanner scanner = new Scanner(System.in);
		boolean back = false;
		
		while (!back) {
			System.out.println(SEPARATOR);
			System.out.println("1. Regular");
			System.out.println("2. Tier");
			System.out.println("3. Go back");
			System.out.println("Enter an interger that corresponds to the menu above:");
			
			int page = validPage(scanner, 3);
			ProgramType pt = null;
			
			// Directs to page
			switch (page) {
			case 1:
				pt = new ProgramType(id, false);
				break;
			case 2:
				pt = new ProgramType(id, true);
				break;
			default:
				back = true;
			}
		}
	}
}
