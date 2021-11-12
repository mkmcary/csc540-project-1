/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Tyrone Wu
 *
 */
public class TierSetup {

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the program */
    private static int id;
    
    /** Connection to database */
    private static Connection conn;

    /**
     * Constructor for setting up tiers for the program.
     * 
     * @param id user of the brand
     */
    @SuppressWarnings("static-access")
    public TierSetup(int id, Connection conn) {
        this.id = id;
        this.conn = conn;
        tierSetupMenu();
    }

    /**
     * Setup the tiers of the program.
     */
    public static void tierSetupMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println(SEPARATOR);
            System.out.println("1. Set up");
            System.out.println("2. Go back");

            int page = validPage(scanner, 2);

            if (page == 1) {
                boolean scan = true;
                try {
                    System.out.println("Please enter the following information:");
                    System.out.print("Number of tiers (max 3): ");
                    int tiers = scanner.nextInt();
                    if (tiers < 1 || tiers > 3) {
                        scan = false;
                        throw new IllegalArgumentException();
                    }

                    String[] tierNames = new String[tiers];
                    System.out.println("\nName of the tiers (in increasing order of precedence): ");
                    scanner.nextLine();
                    for (int i = 0; i < tiers; i++) {
                        System.out.println("Tier " + (i + 1) + " name: ");
                        tierNames[i] = scanner.nextLine();
                    }

                    int[] thresholds = new int[tiers];
                    System.out.println("\nEnter points required for each tier (lower bound): ");
                    for (int i = 0; i < tiers; i++) {
                        System.out.print("Tier " + (i + 1) + " threshold: ");
                        thresholds[i] = scanner.nextInt();
                    }

                    float[] multipliers = new float[tiers];
                    System.out.println("\nEnter multiplier for each tier: ");
                    for (int i = 0; i < tiers; i++) {
                        System.out.print("Tier " + (i + 1) + " multiplier: ");
                        multipliers[i] = scanner.nextFloat();
                    }

                    boolean success = insertTiers(tierNames, thresholds, multipliers);
                    if (success) {
                        System.out.println("\nTiers have been successfully added to the program.");
                    } else {
                        System.out.println("Tiers were not added to the program.");
                    }
                } catch (Exception e) {
                    if (scan) {
                        scanner.next();
                    }
                    System.out.println("\nInvalid input.");
                }
            } else {
                back = true;
            }
        }

        // scanner.close();
    }

    /**
     * Executes the SQL statements give in the parameter.
     * 
     * @param names       tier names
     * @param thresholds  threshold of tiers
     * @param multipliers multiplier of tiers
     * @return true if success of SQL statement, otherwise false
     */
    private static boolean insertTiers(String[] names, int[] thresholds, float[] multipliers) {
        boolean success = true;

        try {
            PreparedStatement pstmt = null;

            try {
                pstmt = conn.prepareStatement("INSERT INTO Tiers VALUES(?,?,?,?,?)");
                
                try {
                    for (int i = 0; i < names.length; i++) {
                        for (int j = 0; j < i; j++) {
                            if (thresholds[i] <= thresholds[j] || multipliers[i] <= multipliers[j]) {
                                close(pstmt);
                                throw new SQLException("Invalid Ordering of Tiers.\n");
                            } else if (names[i].equals(names[j])) {
                                close(pstmt);
                                throw new SQLException("Tier Names must be unique.\n");
                            }
                        }
                        
                        pstmt.clearParameters();
                        pstmt.setInt(1, id);
                        pstmt.setInt(2, i);
                        pstmt.setString(3, names[i]);
                        pstmt.setFloat(4, multipliers[i]);
                        pstmt.setInt(5, thresholds[i]);
                        pstmt.addBatch();
                    }
                    
                    pstmt.executeBatch();
                } catch (SQLException e) {
                    success = false;
                    System.out.println("\nError: " + e.getMessage());
                }
            } finally {
                close(pstmt);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }

        return success;
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
            System.out.print("\nEnter an integer that corresponds to the menu above: ");
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

    private static void close(PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
