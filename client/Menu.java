package client;

public class Menu {
    // Method to display the menu
    public static void showMenu() {
        System.out.println("[*] CHOOSE ONE OF AVAILABLE OPTIONS:");
        System.out.println("1. View account balance");
        System.out.println("2. Withdraw money");
        System.out.println("3. Add money");
        System.out.println("4. Transfer money");
        System.out.println();
    }

    // Method to validate the user's choice
    public static boolean isValidChoice(String choice) {
        return choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4");
    }
}
