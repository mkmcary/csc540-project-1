/**
 * 
 */
package ui.userview.brand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Tyrone Wu
 * @author Niraj Lavani
 *
 */
public class LoyaltyPrograms {

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the brand user */
    private static int bId;
    
    /** Connection to database */
    private static Connection conn;

    /**
     * Constructor for adding loyalty programs
     * 
     * @param id user of the brand
     */
    @SuppressWarnings("static-access")
    public LoyaltyPrograms(int id,  Connection conn) {
        this.bId = id;
        this.conn = conn;
        
        loyaltyProgramMenu();
    }

    /**
     * Brand user adding Loyalty Program
     */
    public static void loyaltyProgramMenu() {
        Scanner scanner = new Scanner(System.in);
        
        int programId = -1;
        String isTiered = null;
        try {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                
                rs = stmt.executeQuery("SELECT id, isTiered FROM LoyaltyPrograms WHERE bId =" + bId);
                if (rs.next()) {
                    programId = rs.getInt("id");
                    isTiered = rs.getString("isTiered");
                }
            } finally {
                close(rs);
                close(stmt);
            }
        } catch (Throwable oops) {
            oops.printStackTrace();
        }
        
        String pName = null;
        if (programId < 0) {
            pName = getProgramName(scanner);
        }
        
        boolean back = false;
        while (!back) {
            System.out.println(SEPARATOR);
            System.out.println("1. Regular");
            System.out.println("2. Tier");
            System.out.println("3. Go back");

            int page = validPage(scanner, 3);

            // Directs to page
            if (programId < 0) {
                switch (page) {
                case 1:
                    new ProgramType(bId, programId, pName, false, conn);
                    break;
                case 2:
                    new ProgramType(bId, programId, pName, true, conn);
                    break;
                default:
                    back = true;
                }
            } else {
                if (page == 1) {
                    if (isTiered.equals("N")) {
                        new ProgramType(bId, programId, pName, false, conn);
                    } else {
                        System.out.println("\nRegular Program cannot be created. Tiered Program is already in place.");
                    }
                } else if (page == 2) {
                    if (isTiered.equals("Y")) {
                        new ProgramType(bId, programId, pName, true, conn);
                    } else {
                        System.out.println("\nTiered Program cannot be created. Regular Program is already in place.");
                    }
                } else {
                    back = true;
                }
            }
        }

        // scanner.close();
    }
    
    /**
     * Get name of program from user.
     * @param scanner input
     * @return name of program
     */
    private static String getProgramName(Scanner scanner) {
        System.out.println(SEPARATOR);
        System.out.println("Enter a name for your loyalty program: ");
        String name = scanner.nextLine();
        return name;
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
    
    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
