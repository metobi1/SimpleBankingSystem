package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class BankApp {

    private static final List<Account> accounts =
            new ArrayList<>();
    private static boolean exit = false;
     private static final Scanner scanner =
             new Scanner(System.in);

    public static void run() {
        //From main here
        bankApp();
    }

    private static void bankApp() {
        while (!exit) {
            displayBankingOptions();
            String option = getInput();
            System.out.println();

            if ("1".equals(option)) {
                Account account = new Account(0,
                        new CreditCard(400000, 9));
                accounts.add(account);

                displayCardDetails(account.getCreditCard().getCreditCardNumber(),
                        account.getCreditCard().getCreditCardPin());
            } else if ("2".equals(option)) {

                System.out.println("Enter your card number:");
                String cCardInput = getInput();
                System.out.println("Your card PIN:");
                String cCardPinInput = getInput();

                for (int i = 0; i < accounts.size(); i++) {
                    Account account = accounts.get(i);
                    String cCardStr = account.getCreditCard()
                            .getCreditCardNumber();
                    if (Objects.equals(cCardInput, cCardStr)) {
                        String cCardPin = account.getCreditCard()
                                .getCreditCardPin();
                        if (Objects.equals(cCardPinInput, cCardPin)) {
                            System.out.println();
                            System.out.println("You have successfully logged in!");
                            System.out.println();
                            navAccount(account);
                            System.out.println();
                        } else {
                            System.out.println();
                            System.out.println("Wrong card number or PIN!");
                        }
                    } else if (i == accounts.size() - 1) {
                        System.out.println();
                        System.out.println("Wrong card number or PIN!");
                    }
                }

            } else if ("0".equals(option)) {
                System.out.println("Bye!");
                break;
            }
            System.out.println();
        }
    }

    private static void navAccount(Account account) {

        while (true) {
            displayNavAccountOptions();
            String option = getInput();
            System.out.println();

            if ("1".equals(option)) {
                System.out.printf("Balance: %s%n", account
                        .getBalance());

            } else if ("2".equals(option)) {
                System.out.println("\nYou have successfully logged out!");
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
