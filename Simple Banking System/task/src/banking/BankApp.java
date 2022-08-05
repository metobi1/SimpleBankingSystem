package banking;

import java.util.Scanner;

public class BankApp {

    private static boolean exit = false;
     private static final Scanner scanner =
             new Scanner(System.in);

    public static void run(String fileName) {
        bankApp(fileName);
    }

    private static void bankApp(String fileName) {

        DataBase dataBase = getDataBase(fileName);
        int id = 1;
        while (!exit) {

            displayBankingOptions();
            String option = getInput();
            System.out.println();

            if ("1".equals(option)) {
                Account account = new Account(0,
                        new CreditCard(400000));
                dataBase.insertValues(id, account.getCreditCard().getCreditCardNumber(),
                        account.getCreditCard().getCreditCardPin(), account.getBalance());
                //accounts.add(account);

                displayCardDetails(account.getCreditCard().getCreditCardNumber(),
                        account.getCreditCard().getCreditCardPin());
            } else if ("2".equals(option)) {

                System.out.println("Enter your card number:");
                String cCardInput = getInput();
                System.out.println("Your card PIN:");
                String cCardPinInput = getInput();

                String[] getAccount = dataBase.getAccount(cCardInput, cCardPinInput);

                System.out.println();
                if (getAccount == null) {
                    System.out.println("Wrong card number or PIN!");
                } else {
                    System.out.println("You have successfully logged in!");
                    System.out.println();
                    navAccount(getAccount[2]);
                }

            } else if ("0".equals(option)) {
                System.out.println("Bye!");
                break;
            }
            System.out.println();
        }
    }

    private static DataBase getDataBase(String fileName) {
        DataBase dataBase = new DataBase(fileName);
        dataBase.loadJdbcStatement();
        dataBase.createTable();
        return dataBase;
    }

    private static void navAccount(String balance) {

        while (true) {
            displayNavAccountOptions();
            String option = getInput();
            System.out.println();

            if ("1".equals(option)) {
                System.out.printf("Balance: %s%n", balance);

            } else if ("2".equals(option)) {
                System.out.println("You have successfully logged out!");
                break;

            } else if ("0".equals(option)) {
                exit = true;
                System.out.println("Bye!");
                break;
            }
        }
    }

    private static void displayNavAccountOptions() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }

    private static void displayBankingOptions() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    private static void displayCardDetails(String cardNumber, String pin) {
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pin);
    }

    private static String getInput() {
        return scanner.next();
    }
}
