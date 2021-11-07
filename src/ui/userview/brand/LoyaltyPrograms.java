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
    @SuppressWarnings("unused")
    public static void loyaltyProgramMenu() {
        Scanner scanner = new Scanner(System.in);
        
        String pName = null;
        int programId = getProgramId();
        if (programId < 0) {
            pName = getProgramName(scanner);
        }
        boolean back = false;
        
        while (!back) {
            System.out.println(SEPARATOR);
            System.out.println("1. Regular");
            System.out.println("2. Tier");
            System.out.println("3. Go back");
            System.out.print("\nEnter an interger that corresponds to the menu above: ");

            int page = validPage(scanner, 3);
            ProgramType pt = null;

            // Directs to page
            switch (page) {
            case 1:
                pt = new ProgramType(bId, programId, pName, false, conn);
                break;
            case 2:
                pt = new ProgramType(bId, programId, pName, true, conn);
                break;
            default:
                back = true;
            }
        }

        scanner.close();
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
     * Gets the loyalty program id, if it exists. Otherwise, return -1.
     * @return loyalty program id. if exists. Otherwise, -1
     */
    private static int getProgramId() {
        int programId = -1;

        try {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                
                rs = stmt.executeQuery("SELECT id FROM LoyaltyPrograms WHERE bId =" + bId);
                if (rs.next()) {
                    programId = rs.getInt("id");
                }
            } finally {
                close(rs);
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
