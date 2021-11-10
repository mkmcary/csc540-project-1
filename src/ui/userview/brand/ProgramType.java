/**
 * 
 */
package ui.userview.brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Tyrone Wu
 *
 */
public class ProgramType {

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";
    
    /** The id of the brand */
    private static int bId;

    /** The id of the program */
    private static int programId;
   
    /** The name of the program */
    private static String pName;

    /** Tiered or not */
    private static boolean tiered;
    
    /** Connection to database */
    private static Connection conn;

    /**
     * Constructor for adding program type
     * 
     * @param id     user of the brand
     * @param tiered whether program is tiered or not
     */
    @SuppressWarnings("static-access")
    public ProgramType(int bId, int programId, String pName, boolean tiered, Connection conn) {
        this.bId = bId;
        this.programId = programId;
        this.pName = pName;
        this.tiered = tiered;
        this.conn = conn;

        System.out.println(SEPARATOR);
        if (this.programId < 0) {
            this.programId  = addProgram();
        }
        programTypeMenu();
    }

    /**
     * Page for adding program type.
     * 
     * @param tiered  whether program is tiered or not
     */
    public static void programTypeMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;
        
        while (!back) {
            if (tiered) {
                System.out.println("1. Tier Set up");
                System.out.println("2. Activity Type");
                System.out.println("3. Reward Type");
                System.out.println("4. Go back");

                int page = validPage(scanner, 4);

                // Directs to page
                switch (page) {
                case 1:
                    new TierSetup(programId, conn);
                    break;
                case 2:
                    new ActivityTypes(programId, conn);
                    break;
                case 3:
                    new RewardTypes(programId, conn);
                    break;
                default:
                    back = true;
                }
            } else {
                System.out.println("1. Activity Type");
                System.out.println("2. Reward Type");
                System.out.println("3. Go back");

                int page = validPage(scanner, 3);

                // Directs to page
                switch (page) {
                case 1:
                    new ActivityTypes(programId, conn);
                    break;
                case 2:
                    new RewardTypes(programId, conn);
                    break;
                default:
                    back = true;
                }
            }
            
            if (!back) {
                System.out.println("\n" + SEPARATOR);
            }
        }
        
        // scanner.close();
    }
    
    /**
     * Creates a new loyalty program for the user.
     * @param scanner input
     * @return id of program
     */
    private static int addProgram() {
        int programId = -1;
        
        try {
            PreparedStatement pstmt = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                pstmt = conn.prepareStatement("INSERT INTO LoyaltyPrograms (pName, isTiered, bId) VALUES(?,?,?)");
                pstmt.clearParameters();
                pstmt.setString(1, pName);
                if (tiered) {
                    pstmt.setString(2, "Y");
                } else {
                    pstmt.setString(2, "N");
                }
                pstmt.setInt(3, bId);

                stmt = conn.createStatement();
                try {
                    int rows = pstmt.executeUpdate();
                    if (rows < 1) {
                        throw new SQLException();
                    } else {
                        if (tiered) {
                            System.out.println("Tiered Program: " + pName + " has been added.\n");
                        } else {
                            System.out.println("Regular Program: " + pName + " has been added.\n");
                        }
                    }
                    
                    rs = stmt.executeQuery("SELECT id FROM LoyaltyPrograms WHERE bId =" + bId);
                    if (rs.next()) {
                        programId = rs.getInt("id");
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } finally {
                close(rs);
                close(pstmt);
                close(stmt);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }

        return programId;
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
