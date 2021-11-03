/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Tyrone Wu
 *
 */
public class TierSetup {

    /** JDBC url to connect to oracledb */
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the brand user */
    private static int id;

    /**
     * Constructor for setting up tiers for the program.
     * 
     * @param id user of the brand
     */
    @SuppressWarnings("static-access")
    public TierSetup(int id) {
        this.id = id;
        tierSetupMenu();
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
                try {
                    System.out.println("Please enter the following information:");
                    System.out.print("Number of tiers (max 3): ");
                    int tiers = scanner.nextInt();

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
                        System.out.println("Tiers have been successfully added. :))))");
                    } else {
                        System.out.println("Tiers were not added. :((((");
                    }
                } catch (Exception e) {
                    // Incorrect data type.
                    scanner.next();
                    System.out.println("\nInvalid input.");
                }
            } else {
                back = true;
            }
        }

        scanner.close();
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
            // Load the driver
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "tkwu";
            String passwd = "abcd1234";

            Connection conn = null;
            PreparedStatement pstmt = null;
            // ResultSet rs = null;

            try {
                conn = DriverManager.getConnection(jdbcURL, user, passwd);
                pstmt = conn.prepareStatement("INSERT INTO Brands VALUES(?,?,?,?,?)");
                
                for (int i = 0; i < names.length; i++) {
                    pstmt.clearParameters();
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, i + 1);
                    pstmt.setString(3, names[i]);
                    pstmt.setFloat(4, multipliers[i]);
                    pstmt.setInt(5, thresholds[i]);
                    pstmt.addBatch();
                }
                
                try {
                    pstmt.executeBatch();
                } catch (SQLException e) {
                    success = false;
                    System.out.println("Invalid input: " + e.getErrorCode() + "-" + e.getMessage());
                }
            } finally {
                // close(rs);
                close(pstmt);
                close(conn);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }

        return success;
    }

    private static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

    private static void close(PreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }

    /* 
    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
    */
}
