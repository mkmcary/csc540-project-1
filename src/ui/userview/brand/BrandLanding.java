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
     * 
     * @param id user of the brand
     */
    @SuppressWarnings("static-access")
    public BrandLanding(int id) {
        this.id = id;
        brandLandingPage();
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

            int page = validPage(scanner, 7);

            // Directs to page
            switch (page) {
            case 1:
                LoyaltyPrograms lp = new LoyaltyPrograms(id);
                break;
            case 2:
                addRERules();
                break;
            case 3:
                addRERules();
                break;
            case 4:
                addRERules();
                break;
            case 5:
                addRERules();
                break;
            case 6:
                addRERules();
                break;
            default:
                logout = true;
            }
        }

        scanner.close();
    }

    // -------------------------------------------------------------------------------------------- oops

    public static void addRERules() {

    }

    public static void updateRERules() {

    }

    public static void addRRRules() {

    }

    public static void updateRRRules() {

    }

    public static void validateLoyaltyProgram() {

    }
}
