/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;
import java.sql.*;

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
        tierSetup();
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
    public static void tierSetup() {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println(SEPARATOR);

            System.out.println("1. Set up");
            System.out.println("2. Go back");
            System.out.print("\nEnter an interger that corresponds to the menu above: ");

            int page = validPage(scanner, 2);

            if (page == 1) {
                System.out.println("Please enter the following information:");
                System.out.print("Number of tiers (max 3): ");
                if (scanner.hasNextInt()) {
                    int tiers = scanner.nextInt();

                    String[] tierNames = new String[tiers];
                    System.out.println("Name of the tiers (in increasing order of precedence): ");
                    for (int i = 0; i < tierNames.length; i++) {
                        System.out.println("Tier " + (i + 1) + " name: ");

                    }
                } else {
                    scanner.next();
                    System.out.println("Invalid input.");
                }
            } else {
                back = true;
            }
        }

        scanner.close();
    }

    /**
     * Executes the SQL statement give in the parameter.
     * 
     * @return boolean true if success of SQL statement, otherwise false
     */
    private static boolean executeStatement(String statement) {
        boolean success = true;

        try {
            // Load the driver. This creates an instance of the driver and calls the
            // registerDriver method to make Oracle Thin driver available to clients.
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "tkwu";
            String passwd = "abcd1234";

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                // Get a connection from the first driver in the DriverManager list that
                // recognizes the URL jdbcURL
                conn = DriverManager.getConnection(jdbcURL, user, passwd);

                // Create a statement object that will be sending your SQL statements to the
                // DBMS
                stmt = conn.createStatement();

                // Insert Tiers into Tiers table
                try {
                    stmt.executeUpdate(statement);
                } catch (java.sql.SQLException e) {
                    success = false;
                    System.out.println(
                            "Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());
                }
            } finally {
                close(rs);
                close(stmt);
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

    private static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }

    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
