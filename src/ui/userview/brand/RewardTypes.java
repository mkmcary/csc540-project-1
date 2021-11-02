/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class RewardTypes {

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the brand user */
    private int id;

    /**
     * Constructor for adding reward type to program.
     * 
     * @param id
     */
    public RewardTypes(int id) {
        this.id = id;
        addRewardType();
    }

    /**
     * Loops until a valid input is read in.
     * 
     * @param scanner scanner object that reads in input
     * @param pages   the max pages that menu can direct to
     * @return valid page address
     */
    private static int validPage(Scanner scanner, int pages) {
        int page = 0;
        boolean invalidInput = false;

        // Handles invalid input
        do {
            System.out.print("\nEnter an interger that corresponds to the menu above: ");
            if (scanner.hasNextInt()) {
                page = scanner.nextInt();
                if (page < 1 || page > pages) {
                    invalidInput = true;
                    System.out.println("Input must be an integer from 1-" + pages + ".");
                } else {
                    invalidInput = false;
                }
            } else {
                scanner.next();
                invalidInput = true;
                System.out.println("Input must be an integer from 1-" + pages + ".");
            }
        } while (invalidInput);

        return page;
    }

    /**
     * Adding reward type to program.
     */
    public static void addRewardType() {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println(SEPARATOR);

            System.out.println("1. Gift Card");
            System.out.println("2. Free Product");
            System.out.println("3. Go back");
            System.out.print("\nEnter an interger that corresponds to the menu above: ");

            int page = validPage(scanner, 3);

            switch (page) {
            case 1:
                giftCard(scanner);
                break;
            case 2:
                freeProduct(scanner);
                break;
            default:
                back = true;
            }
        }

        scanner.close();
    }

    /**
     * Add gift card reward type.
     * 
     * @param scanner
     */
    public static void giftCard(Scanner scanner) {
        // TODO
    }

    /**
     * Add free product reward type.
     * 
     * @param scanner
     */
    public static void freeProduct(Scanner scanner) {
        // TODO
    }
}
