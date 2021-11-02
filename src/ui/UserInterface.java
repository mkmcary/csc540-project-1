/**
 * 
 */
package ui;

/**
 * @author Matthew Martin
 * @author Grey Files
 * @author Tyrone Wu
 * @author Niraj Lavani
 */
public class UserInterface {
	
	public static void main(String[] args) {
		Home homepage = new Home();
	}

	public static void newScreen() {
		System.out.println("------------------------------");
	}
	
	public static void invalidInput() {
		System.out.print("Invalid input, please try again: ");
	}
}
