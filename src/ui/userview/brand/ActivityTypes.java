/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class ActivityTypes {
	
	/** CLI separator */
	final static String SEPARATOR = "------------------------------";
	
	/** The id of the brand user */
	private int id;

	/**
	 * Constructor for adding activity types
	 * @param id user of the brand
	 */
	public ActivityTypes(int id) {
		this.id = id;
		addActivityType();
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
		
		return page;
	}
	
	/**
	 * Adding activity type.
	 */
	public static void addActivityType() {
		Scanner scanner = new Scanner(System.in);
		boolean back = false;
		
		while (!back) {
			System.out.println(SEPARATOR);
			
			System.out.println("1. Purchase");
			System.out.println("2. Leave a review");
			System.out.println("3. Refer a friend");
			System.out.println("4. Go back");
			System.out.println("Enter an interger that corresponds to the menu above:");
			
			int page = validPage(scanner, 4);
			
			switch (page) {
			case 1:
				purchase(scanner);
				break;
			case 2:
				leaveReview(scanner);
				break;
			case 3:
				referFriend(scanner);
				break;
			default:
				back = true;
			}
		}
		
		scanner.close();
	}
	
	/**
	 * Purchase activity type.
	 * @param scanner read in input
	 */
	public static void purchase(Scanner scanner) {
		// TODO
	}
	
	/**
	 * Leaving review activity type.
	 * @param scanner read in input
	 */
	public static void leaveReview(Scanner scanner) {
		// TODO
	}

	/**
	 * Referring friend activity type.
	 * @param scanner read in input
	 */
	public static void referFriend(Scanner scanner) {
		// TODO
	}
}
