package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import chain.Chain;
import data.STATUS;
import data.Transaction;

public class Facade {
    private final Singleton sg;

    public Facade() {
        // create a sigleton to make sure every chains has only one
        sg = Singleton.getInstance();
    }

    public void viewChain() {
        System.out.println("=== View Chain ===");

        // Show all chain status
        int count = 1;
        List<STATUS> statusList = Arrays.asList(STATUS.values());
        for (STATUS status : statusList) {
            System.out.println(count + ": " + status);
            count++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Chain status: ");
        while (!sc.hasNextInt()) { // check integer input
            System.out.print("Invalid input\nType the integer number: ");
            sc.next();
        }
        int selected = sc.nextInt();
        sc.nextLine();
        if (selected < 0 || selected > statusList.size()) {
            System.out.println("Invalid input");
            return;
        }
        // Show selected chain
        System.out.println();
        System.out.println(sg.chainManager.getChain(statusList.get(selected - 1)).toString());
    }

    public void viewAllChain() {
        System.out.println("=== View All Chain ===");
        List<STATUS> statusList = Arrays.asList(STATUS.values());
        for (STATUS status : statusList) {
            System.out.println(sg.chainManager.getChain(status).toString());
        }
    }

    public void createTransaction() {
        System.out.println("=== Create Transaction ===");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter sender ID: ");
        String sender = sc.next();
        sc.nextLine();
        System.out.print("Enter reciver ID: ");
        String reciver = sc.next();
        sc.nextLine();
        System.out.print("Amount: ");
        while (!sc.hasNextDouble()) { // check demical number input
            System.out.print("Invalid input\nType the demical number(up to " + sg.cstoken.decimals + "): ");
            sc.next();
        }
        double amount = sc.nextDouble();
        while (amount <= 0) {
            System.out.print("Amount cannot be less than 0 : ");
            amount = sc.nextDouble();
        }
        sc.nextLine();
        System.out.print("Rate: ");
        while (!sc.hasNextDouble()) { // check demical number input
            System.out.print("Invalid input\nType Thai bath number: ");
            sc.next();
        }
        double rate = sc.nextDouble();
        sc.nextLine();
        // create transaction from valid input
        Transaction trx = new Transaction(sg.cstoken, sender, reciver, amount, rate);
        sg.chainManager.addTransaction(trx);
        // show output transaction id
        System.out.println();
        System.out.println("Create Complete:\n" + trx.getId());

    }

    public void verifyTransaction() {
        System.out.println("=== Verify Transaction ===");
        // Show all awaiting transactions
        Chain await = sg.chainManager.getChain(STATUS.Awaiting);
        System.out.println(await.toString());

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Transaction ID to vertify: ");
        String trxID = sc.next();
        sc.nextLine();

        // find transaction in awaiting chain
        Transaction trx = await.findById(trxID);
        if (trx == null) {
            System.out.println("Transaction not found.");
            return;
        }
        trx.setStatus(STATUS.Complete);
    }

    public void reportTransaction() {
        System.out.println("=== Report Invalid Transaction ===");
        // Show all complete and awaiting transactions
        Chain complete = sg.chainManager.getChain(STATUS.Complete);
        System.out.println(complete.toString());
        Chain awaiting = sg.chainManager.getChain(STATUS.Awaiting);
        System.out.println(awaiting.toString());

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Transaction ID to report: ");
        String trxID = sc.next();
        sc.nextLine();
        // find transactio both complete and awaiting
        Transaction trx = complete.findById(trxID) != null ? complete.findById(trxID) : awaiting.findById(trxID);
        if (trx == null) {
            System.out.println("Transaction not found.");
            return;
        }
        trx.setStatus(STATUS.Incomplete);
    }

    public void findTransactionByID() {
        System.out.println("=== Scan Transaction ===");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Transaction ID to show info: ");
        String trxID = sc.next();
        sc.nextLine();

        // find transaction in every chains
        Transaction trx = sg.chainManager.findTransaction(trxID);
        if (trx == null) {
            System.out.println("Transaction not found.");
            return;
        }
        // show info
        System.out.println("-- Transaction info --");
        System.out.println(trx.toString());
    }

    public void findAllParentnOf() {
        System.out.println("=== Find All Previous of transaction ===");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Transaction ID to find all previous transaction: ");
        String trxID = sc.next();
        sc.nextLine();

        // find all parent transaction in every chains
        Chain parent = sg.chainManager.findAllParentnOf(trxID);
        if (parent == null) {
            System.out.println("Transaction not found.");
            return;
        }
        System.out.println(parent.toString());
    }

    public void findAllChildrenOf() {
        System.out.println("=== Find All Next of transaction ===");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Transaction ID to find all next transaction: ");
        String trxID = sc.next();
        sc.nextLine();

        // find all children transaction in every chains
        Chain children = sg.chainManager.findAllChildrenOf(trxID);
        if (children == null) {
            System.out.println("Transaction not found.");
            return;
        }
        System.out.println(children.toString());
    }
}
