/**
 * 
 */
package ui.userview.brand;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Tyrone Wu
 * @author Niraj Lavani
 * 
 */
public class BrandLanding {

    /** CLI separator */
    final static String SEPARATOR = "------------------------------";

    /** The id of the brand user */
    private static int id;
    
    /** Connection to database */
    private static Connection conn;

    /**
     * Constructor for Brand Landing page
     * 
     * @param id user of the brand
     * @throws SQLException 
     */
    @SuppressWarnings("static-access")
    public BrandLanding(int id, Connection conn) throws SQLException {
        this.id = id;
        this.conn = conn;
        brandLandingPage();
    }

    /**
     * Homepage for brand users
     * @throws SQLException 
     */
    public void brandLandingPage() throws SQLException {
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
            	//Loyalty program instantiation happens in LoyaltyPrograms.java
            	addLoyaltyProgram(conn);
                break;
            case 2:
                addRERules(scanner);
                break;
            case 3:
            	updateRERules(scanner);
                break;
            case 4:
            	addRRRules(scanner);
                break;
            case 5:
            	updateRRRules(scanner);
                break;
            case 6:
            	validateLoyaltyProgram();
                break;
            default:
                logout = true;
            }
        }

        scanner.close();
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Creates and adds a Loyalty Program to the Brand
     * @param s class-wide scanner for user input
     */
    private static void addLoyaltyProgram(Connection conn) {
    	@SuppressWarnings("unused") //Don't need to use LP in app since all storage happens in DB
		LoyaltyPrograms lp = new LoyaltyPrograms(id, conn);
    }
    
    /**
     * Adds a Brand's earning rules to the loyalty program
     * @param s Scanner reading user input
     * @param lp LoyaltyProgram of the brand
     */
    public static void addRERules(Scanner s) throws SQLException {
    	//Ask the Brand for Reward earning name
    	System.out.println("\nYou chose 'Add RE Rule'!");
    	
    	boolean anotherEntry;
    	do {
    		anotherEntry = false;

    		try {
    			
    			//Get Brand Rule Code
    			boolean brandRuleCodeRepeat;
            	String eaBrandRuleCode;
            	do {
            		brandRuleCodeRepeat = false;
                	System.out.println("what is the Brand Reward rule code?"
                			+ "\nThe elements in this list are reward rule codes that are taken:");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT ruleCode FROM RewardEarningRules";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("ruleCode");
                		System.out.println("Rule Code: " + tempCode);
                	}
                	
                	System.out.print("Enter your Brand Reward rule code (format: RE##): ");
                	eaBrandRuleCode = s.next();
                	if (!eaBrandRuleCode.substring(0,1).equals("RE") 
                			|| Integer.parseInt(eaBrandRuleCode.substring(0,1)) < 0) {
                		System.out.println("Brand Reward rule codes are in format 'RE##' "
                				+ "where the number is positive");
                		brandRuleCodeRepeat = true;
                	}
            	} while (brandRuleCodeRepeat);
            	
            	//Gets Activity Code
            	boolean anotherCode;
            	String eaCode;
            	do {
            		anotherCode = false;
                	System.out.print("What activity would you like to add? ");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT acId, acName FROM ActivityCategories";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("acId");
                		String tempName = rs.getString("acName");
                		System.out.println("Activity Name: " + tempName
                				+ " | Activity Code: " + tempCode);
                	}
                	
                	System.out.print("Enter the activity code for your selection: ");
                	eaCode = s.next();
                	if (!eaCode.substring(0,1).equals("A") || Integer.parseInt(eaCode.substring(0,1)) < 0) {
                		System.out.println("Activity codes are in format 'A##' "
                				+ "where the number is positive");
                		anotherCode = true;
                	}
            	} while (anotherCode);
            	
            	//Gets point value for the activity
            	boolean digit;
            	int eaPoints = -1;
            	do {
            	    digit = false;
            		System.out.print("How many points will '"+ eaCode +"' earn?: ");
                	if (s.hasNextInt()) {
                		eaPoints = s.nextInt();
                	} else {
                		System.out.print("Please enter a number: ");
                	    s.next();
                	    digit = true;
                	}
            	} while (digit);
            	
            	
            	//Get the loyalty ID from Brands table
            	int lpId = getLoyaltyId(id);

            	System.out.println("Press 1 to add another rule.");
                System.out.println("Press 2 to Go back to Brand Landing page");
                System.out.print("ans: ");
                
                //Adds information to RE table if user hits 1
                if (s.nextInt() == 1) {
                	addToTable(lpId, "RewardEarningRules", eaPoints, eaCode, eaBrandRuleCode);
                	System.out.println("Rule successfully added!");
                	anotherEntry = true;
                }
            	
        	} catch (SQLException e) {
        		System.out.println("An SQL error occured!");
        	}

    	} while (anotherEntry);
    	System.out.print("Going back...");
    }
    
    /**
     * Updates a Brand's earning rules to the loyalty program
     * @param s Scanner reading user input
     * @param lp LoyaltyProgram of the brand
     */
    public static void updateRERules(Scanner s) throws SQLException {
    	System.out.println("\nYou chose 'update RE Rule'!");
    	
    	boolean anotherEntry;
    	do {
    		anotherEntry = false;
        	
    		try {
    			
    			//Get Brand Rule Code
    			boolean brandRuleCodeRepeat;
            	String eaBrandRuleCode;
            	do {
            		brandRuleCodeRepeat = false;
                	System.out.println("what is the NEW Brand Reward rule code?"
                			+ "\nThe elements in this list are reward rule codes that currenty exist:");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT ruleCode FROM RewardEarningRules";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("ruleCode");
                		System.out.println("Rule Code: " + tempCode);
                	}
                	
                	System.out.print("Enter your Brand Reward rule code (format: RE##): ");
                	eaBrandRuleCode = s.next();
                	if (!eaBrandRuleCode.substring(0,1).equals("RE") 
                			|| Integer.parseInt(eaBrandRuleCode.substring(0,1)) < 0) {
                		System.out.println("Brand Reward rule codes are in format 'RE##' "
                				+ "where the number is positive");
                		brandRuleCodeRepeat = true;
                	}
            	} while (brandRuleCodeRepeat);
    			
    			//Gets Activity Code
            	boolean anotherCode;
            	String eaCode;
            	do {
            		anotherCode = false;
                	System.out.println("What activity would you like to update? ");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT ruleCode, acId FROM RewardEarningRules";
                	ResultSet rs = stmt.executeQuery(SQL);
           
                	while (rs.next()) {
                		String tempRuleCode = rs.getString("ruleCode");
                		String tempActCode = rs.getString("acId");
                		System.out.print("Rule Code: " + tempRuleCode + "| ");
                		System.out.println("Activity Code: " + tempActCode);
                	}
                	
                	System.out.print("Enter the activity code for your selection: ");
                	eaCode = s.next();
                	if (!eaCode.substring(0,1).equals("A") || Integer.parseInt(eaCode.substring(0,1)) < 0) {
                		System.out.println("Activity codes are in format 'A##' "
                				+ "where the number is positive");
                		anotherCode = true;
                	}
            	} while (anotherCode);
            	
            	//Gets point value for the activity
            	boolean digit;
            	int eaPoints = -1;
            	do {
            	    digit = false;
            		System.out.print("Points will the updated activity earn: ");
                	if (s.hasNextInt()) {
                		eaPoints = s.nextInt();
                	} else {
                		System.out.print("Please enter a number: ");
                	    s.next();
                	    digit = true;
                	}
            	} while (digit);
            	
            	//Get the loyalty ID from Brands table
            	int lpId = getLoyaltyId(id);
            	
            	System.out.println("Press 1 to add another rule.");
                System.out.println("Press 2 to Go back to Brand Landing page");
                System.out.print("ans: ");
        		
            	//adds new rule with updated info and incremented version if user hits 1
                if (s.nextInt() == 1) {
                	updateTable(lpId, "RewardEarningRules", eaPoints, eaCode, eaBrandRuleCode);
                	System.out.println("Rule successfully updated!");
                	anotherEntry = true;
                }
            	
    		} catch (SQLException e) {
    			System.out.println("An SQL error occured!");
    		}
            
    	} while (anotherEntry);
    	System.out.print("Going back...");
    }

    /**
     * Adds a Brand's rewarding rules to the loyalty program
     * @param s Scanner reading user input
     * @param loyaltyId the id of the loyalty program
     */
    public static void addRRRules(Scanner s) {
    	//Ask the Brand for Reward earning name
    	System.out.println("\nYou chose 'Add RR Rule'!");
    	
    	boolean anotherEntry;
    	do {
    		anotherEntry = false;

    		try {
    			
    			//Get Brand Rule Code
    			boolean brandRuleCodeRepeat;
            	String rrBrandRuleCode;
            	do {
            		brandRuleCodeRepeat = false;
                	System.out.println("what is the Brand Reward rule code?"
                			+ "\nThe elements in this list are reward rule codes that are taken:");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT ruleCode FROM RewardRedeemingRules";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("ruleCode");
                		System.out.println("Rule Code: " + tempCode);
                	}
                	
                	System.out.print("Enter your Brand Reward rule code (format: RR##): ");
                	rrBrandRuleCode = s.next();
                	if (!rrBrandRuleCode.substring(0,1).equals("RE") 
                			|| Integer.parseInt(rrBrandRuleCode.substring(0,1)) < 0) {
                		System.out.println("Brand Reward rule codes are in format 'RE##' "
                				+ "where the number is positive");
                		brandRuleCodeRepeat = true;
                	}
            	} while (brandRuleCodeRepeat);
            	
            	//Gets Reward code
            	boolean anotherCode;
            	String rrCode;
            	do {
            		anotherCode = false;
                	System.out.print("What reward would you like to add to the rule? ");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT rId, rName FROM Rewards";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("rId");
                		String tempName = rs.getString("rName");
                		System.out.println("Reward ID: " + tempName
                				+ " | Reward Name: " + tempCode);
                	}
                	
                	System.out.print("Enter the reward id code for your selection: ");
                	rrCode = s.next();
                	if (!rrCode.substring(0,1).equals("R") || Integer.parseInt(rrCode.substring(0,1)) < 0) {
                		System.out.println("reward codes are in format 'R##' "
                				+ "where the number is positive");
                		anotherCode = true;
                	}
            	} while (anotherCode);
            	
            	//Gets point value for the reward
            	boolean digit;
            	int rrPoints = -1;
            	do {
            	    digit = false;
            		System.out.print("How many points will '" + rrCode + "' need?: ");
                	if (s.hasNextInt()) {
                		rrPoints = s.nextInt();
                	} else {
                		System.out.print("Please enter a number: ");
                	    s.next();
                	    digit = true;
                	}
            	} while (digit);
            	
            	//Get the loyalty ID from Brands table
            	int lpId = getLoyaltyId(id);
            	
            	System.out.println("Rule successfully added!");

                System.out.println("Press 1 to add another rule.");
                System.out.println("Press 2 to Go back to Brand Landing page");
                System.out.print("ans: ");

                //Adds information to RE table if user hits 1
                if (s.nextInt() == 1) {
                	addToTable(lpId, "RewardRedeemingRules", rrPoints, rrCode, rrBrandRuleCode);
                	System.out.println("Rule successfully added!");
                	anotherEntry = true;
                }
                
        	} catch (SQLException e) {
        		System.out.println("An SQL error occured!");
        	}
        	
            
    	} while (anotherEntry);
    	System.out.print("Going back...");
    }

    /**
     * Updates a Brand's rewarding rules to the loyalty program
     * @param s Scanner reading user input
     * @param lp LoyaltyProgram of the brand
     */
    public static void updateRRRules(Scanner s) {
    	System.out.println("\nYou chose 'update RR Rule'!");
    	
    	boolean anotherEntry;
    	do {
    		anotherEntry = false;
        	
    		try {
    			
    			//Get Brand Rule Code
    			boolean brandRuleCodeRepeat;
            	String rrBrandRuleCode;
            	do {
            		brandRuleCodeRepeat = false;
                	System.out.println("what is the Brand Reward rule code?"
                			+ "\nThe elements in this list are reward rule codes that are taken:");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT ruleCode FROM RewardRedeemingRules";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("ruleCode");
                		System.out.println("Rule Code: " + tempCode);
                	}
                	
                	System.out.print("Enter your Brand Reward rule code (format: RR##): ");
                	rrBrandRuleCode = s.next();
                	if (!rrBrandRuleCode.substring(0,1).equals("RE") 
                			|| Integer.parseInt(rrBrandRuleCode.substring(0,1)) < 0) {
                		System.out.println("Brand Reward rule codes are in format 'RE##' "
                				+ "where the number is positive");
                		brandRuleCodeRepeat = true;
                	}
            	} while (brandRuleCodeRepeat);
    			
    			//Gets Activity Code
            	boolean anotherCode;
            	String rrCode;
            	do {
            		anotherCode = false;
                	System.out.println("What reward would you like to update? ");
                	
                	Statement stmt = conn.createStatement();
                	String SQL = "SELECT rId, rName FROM Rewards";
                	ResultSet rs = stmt.executeQuery(SQL);
                	
                	while (rs.next()) {
                		String tempCode = rs.getString("rId");
                		String tempName = rs.getString("rName");
                		System.out.println("Reward ID: " + tempName
                				+ " | Reward Name: " + tempCode);
                	}
                	
                	System.out.print("Enter the reward id code for your selection: ");
                	rrCode = s.next();
                	if (!rrCode.substring(0,1).equals("R") || Integer.parseInt(rrCode.substring(0,1)) < 0) {
                		System.out.println("reward codes are in format 'R##' "
                				+ "where the number is positive");
                		anotherCode = true;
                	}
            	} while (anotherCode);
            	
            	//Gets point value for the reward
            	boolean digit;
            	int rrPoints = -1;
            	do {
            	    digit = false;
            		System.out.print("How many points will '" + rrCode + "' need?: ");
                	if (s.hasNextInt()) {
                		rrPoints = s.nextInt();
                	} else {
                		System.out.print("Please enter a number: ");
                	    s.next();
                	    digit = true;
                	}
            	} while (digit);
            	
            	//Get the loyalty ID from Brands table
            	int lpId = getLoyaltyId(id);
            	
            	System.out.println("Press 1 to add another rule.");
                System.out.println("Press 2 to Go back to Brand Landing page");
                System.out.print("ans: ");
        		
                //adds new rule with updated info and incremented version if user hits 1
                if (s.nextInt() == 1) {
                	updateTable(lpId, "RewardRedeemingRules", rrPoints, rrCode, rrBrandRuleCode);
                	System.out.println("Rule successfully updated!");
                	anotherEntry = true;
                }
            	
    		} catch (SQLException e) {
    			System.out.println("An SQL error occured!");
    		}
    		
    	} while (anotherEntry);
    	System.out.print("Going back...");

    }

    /**
     * 
     */
    public static void validateLoyaltyProgram() {

    }
    
    /**
     * Adds entry to table
     * 
     * @param tableName  The name of the table
	 * @param points new point count to be updated
	 * @param code activity code of activity
	 * @param brandRuleCode the new RE/RR code from Brand
     * 
     * @throws SQLException 
     */
    private static void addToTable(int loyaltyId, String tableName, int points, String code, String brandRuleCode) throws SQLException {

    	PreparedStatement pstmt = null;
    	pstmt = conn.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		pstmt.clearParameters();
		pstmt.setInt(1, loyaltyId);
        pstmt.setInt(2, 1);
        pstmt.setString(3, brandRuleCode);
        pstmt.setInt(4, points);
        pstmt.setString(5, code);
        
        //Add row to Table
        int rows = pstmt.executeUpdate();
        if (rows < 1) {
        	throw new SQLException();
        }
    	
    }

	/**
	 * Increments version then adds entry with new version to table
	 * 
	 * @param loyaltyId loyalty id of the program
	 * @param tableName name of the table 
	 * @param points new point count to be updated
	 * @param code activity code of activity
	 * @param brandRuleCode the new RE/RR code from Brand
	 * 
	 * @throws SQLException 
	 */
	private static void updateTable(int loyaltyId, String tableName, int points, String code, String brandRuleCode) throws SQLException {
		
		
		//We need latest version and increment it by 1
		Statement stmt = conn.createStatement();
    	String SQL = "SELECT MAX(ruleVersion) "
    			+ "FROM " + tableName
    			+ "WHERE acId ='" + code + "')";
    	ResultSet rs = stmt.executeQuery(SQL);
    	int latestVersion = rs.getInt("ruleVersion");
    	latestVersion++;
		
		//Add entry into table
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		pstmt.clearParameters();
		pstmt.setInt(1, loyaltyId);
        pstmt.setInt(2, latestVersion);
        pstmt.setString(3, brandRuleCode);
        pstmt.setInt(4, points);
        pstmt.setString(5, code);
         
        //Add row to Table
        int rows = pstmt.executeUpdate();
        if (rows < 1) {
        	throw new SQLException();
        }
	}
	
	/**
	 * Gets the loyalty program Id from SQL
	 * @param id the brand's Id
	 * @return loyalty program id
	 */
	private static int getLoyaltyId(int id) {
		
		try {
			Statement stmt = conn.createStatement();
	    	String SQL = "SELECT pId FROM Brands WHERE id=" + id;
	    	ResultSet rs = stmt.executeQuery(SQL);
	    	
	    	if (rs.next()) {
	    		return rs.getInt("pId");
	    	}
		} catch (SQLException e) {
			System.out.println("An SQL related error occured when searching for loyalty ID!");
		}
		
		//return negative number to indicate error (brand doesn't have a loyalty program)
		return -1;
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
}
