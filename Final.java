
import java.util.Scanner;

import utils.Facade;

public class Final {
    public static void main(String[] args) {
        Facade fc = new Facade();
        Scanner scan = new Scanner(System.in);

        // menu loop to call Facade
        menuLoop: while (true) {
            System.out.println("==== CS-KMITL Coin Exchange ====");
            System.out.println("Select menu: ");
            System.out.println("1: View Chain.");
            System.out.println("2: View All Chain.");
            System.out.println("3: Create Transaction.");
            System.out.println("4: Verify Transaction.");
            System.out.println("5: Report Invalid Transaction.");
            System.out.println("6: Scan Transaction by ID.");
            System.out.println("7: Find All Previous of transaction.");
            System.out.println("8: Find All Next of transaction.");
            System.out.println("0: Exit.");
            System.out.print("Please enter your choice: ");

            while (!scan.hasNextInt()) {
                System.out.print("Invalid input\nType the integer number: ");
                scan.next();
            }
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println();
            switch (choice) {
                case 0:
                    break menuLoop;
                case 1:
                    fc.viewChain();
                    break;
                case 2:
                    fc.viewAllChain();
                    break;
                case 3:
                    fc.createTransaction();
                    break;
                case 4:
                    fc.verifyTransaction();
                    break;
                case 5:
                    fc.reportTransaction();
                    break;
                case 6:
                    fc.findTransactionByID();
                    break;
                case 7:
                    fc.findAllParentnOf();
                    break;
                case 8:
                    fc.findAllChildrenOf();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
            // slow down output
            System.out.print("Enter any key to return to menu.");
            scan.nextLine();
        }
        scan.close();
    }
}
