/**
 * 
 */
package ui.userview.brand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    private static int id;

    /**
     * Constructor for adding activity types
     * 
     * @param id user of the brand
     */
    @SuppressWarnings("static-access")
    public ActivityTypes(int id) {
        this.id = id;
        activityTypesMenu();
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
    public static void activityTypesMenu() {
        ArrayList<String[]> activities = getActivityTypes();
        Scanner scanner = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println(SEPARATOR);
            // Menu
            int i = 0;
            for (String[] a : activities) {
                System.out.println((++i) + ". " + a[1]);
            }
            System.out.println((++i) + ". Go back");

            int page = validPage(scanner, i);
            
            if (page != i) {
                boolean success = insertActivity(activities.get(i - 1)[0]);
                if (success) {
                    System.out.println("Activity Type has been successfully added. :))))");
                } else {
                    System.out.println("Activity Type was not added. :((((");
                }
            } else {
                back = true;
            }
        }

        scanner.close();
    }

    /**
     * Get activities
     * @return array list of activities
     */
    private static ArrayList<String[]> getActivityTypes() {
        ArrayList<String[]> activities = new ArrayList<String[]>();
        
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "tkwu";
            String passwd = "abcd1234";

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                conn = DriverManager.getConnection(jdbcURL, user, passwd);
                stmt = conn.createStatement();

                try {
                    rs = stmt.executeQuery("SELECT acId, acName FROM ActivityCategories");
                    
                    while (rs.next()) {
                        String[] activity = new String[] { rs.getString("acId"), rs.getString("acName") };
                        activities.add(activity);
                    }
                } catch (SQLException e) {
                    System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());
                }
            } finally {
                close(rs);
                close(stmt);
                close(conn);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }
        
        return activities;
    }

    /**
     * Add activity type in SQL.
     * 
     * @return boolean success of SQL statement
     */
    private static boolean insertActivity(String acId) {
        boolean success = true;

        try {
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "tkwu";
            String passwd = "abcd1234";

            Connection conn = null;
            PreparedStatement pstmt = null;
            // ResultSet rs = null;

            try {
                conn = DriverManager.getConnection(jdbcURL, user, passwd);
                pstmt = conn.prepareStatement("INSERT INTO ProgramActivities VALUES(?,?)");
                pstmt.clearParameters();
                pstmt.setInt(1, id);
                pstmt.setString(2, acId);

                try {
                    int rows = pstmt.executeUpdate();
                    if (rows < 1) {
                        throw new SQLException();
                    }
                } catch (SQLException e) {
                    success = false;
                    System.out.println("Invalid Input: " + e.getErrorCode() + "-" + e.getMessage());
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

    private static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
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

    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
