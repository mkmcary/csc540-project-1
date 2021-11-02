/**
 * 
 */
package ui.userview.brand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class ActivityTypes {

    /** JDBC url to connect to oracledb */
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the brand user */
    private int id;

    /**
     * Constructor for adding activity types
     * 
     * @param id user of the brand
     */
    public ActivityTypes(int id) {
        this.id = id;
        addActivityType();
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
            System.out.print("\nEnter an interger that corresponds to the menu above: ");

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
     * 
     * @param scanner read in input
     */
    public static void purchase(Scanner scanner) {

        String statement = "\"INSERT INTO Tiers " + "VALUES ('Colombian', 101, 7.99, 0, 0)";
    }

    /**
     * Leaving review activity type.
     * 
     * @param scanner read in input
     */
    public static void leaveReview(Scanner scanner) {
        // TODO
    }

    /**
     * Referring friend activity type.
     * 
     * @param scanner read in input
     */
    public static void referFriend(Scanner scanner) {
        // TODO
    }

    /**
     * Add tiers in SQL.
     * 
     * @return boolean success of SQL statement
     */
    private static boolean executeStatement(String statement) {
        boolean success = true;

        try {
            // Load the driver. This creates an instance of the driver
            // and calls the registerDriver method to make Oracle Thin
            // driver available to clients.
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "tkwu";
            String passwd = "abcd1234";

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                // Get a connection from the first driver in the
                // DriverManager list that recognizes the URL jdbcURL
                conn = DriverManager.getConnection(jdbcURL, user, passwd);

                // Create a statement object that will be sending your
                // SQL statements to the DBMS
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
