/**
 * 
 */
package ui.userview.brand;

import java.sql.Connection;
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
public class RewardTypes {
    
    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the brand user */
    private static int id;
    
    /** Connection to database */
    private static Connection conn;

    /**
     * Constructor for adding reward type to program.
     * 
     * @param id
     */
    @SuppressWarnings("static-access")
    public RewardTypes(int id, Connection conn) {
        this.id = id;
        this.conn = conn;
        rewardTypesMenu();
    }

    /**
     * Adding reward type to program.
     */
    public static void rewardTypesMenu() {
        ArrayList<String[]> rewards = getRewardTypes();
        Scanner scanner = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println(SEPARATOR);
            // Menu
            int i = 0;
            for (String[] r : rewards) {
                System.out.println((++i) + ". " + r[1]);
            }
            System.out.println((++i) + ". Go back");

            int page = validPage(scanner, i);

            if (page != i) {
                boolean success = insertReward(rewards.get(i - 2)[0]);
                if (success) {
                    System.out.println("Reward Type has been successfully added. :))))");
                } else {
                    System.out.println("Reward Type was not added. :((((");
                }
            } else {
                back = true;
            }
        }

        // scanner.close();
    }
    
    /**
     * Get rewards
     * @return array list of rewards
     */
    private static ArrayList<String[]> getRewardTypes() {
        ArrayList<String[]> rewards = new ArrayList<String[]>();
        
        try {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();

                try {
                    rs = stmt.executeQuery("SELECT rId, rName FROM Rewards");
                    
                    while (rs.next()) {
                        String[] reward = new String[] { rs.getString("rId"), rs.getString("rName") };
                        rewards.add(reward);
                    }
                } catch (SQLException e) {
                    System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " + e.getMessage());
                }
            } finally {
                close(rs);
                close(stmt);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }
        
        return rewards;
    }
    
    /**
     * Add reward type in SQL.
     * 
     * @return boolean success of SQL statement
     */
    private static boolean insertReward(String rId) {
        boolean success = true;

        try {
            PreparedStatement pstmt = null;

            try {
                pstmt = conn.prepareStatement("INSERT INTO ProgramRewards VALUES(?,?)");
                pstmt.clearParameters();
                pstmt.setInt(1, id);
                pstmt.setString(2, rId);

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
