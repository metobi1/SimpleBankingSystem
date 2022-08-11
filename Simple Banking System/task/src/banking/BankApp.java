package banking;

import java.util.Objects;
import java.util.Scanner;

public class BankApp {

    private static boolean exit = false;
     private static final Scanner scanner =
             new Scanner(System.in);

    public static void run(String fileName) {
        bankApp(fileName);
    }

    private static void bankApp(String fileName) {

        DataBase dataBase = new DataBase(fileName);
        createTable(dataBase);
        int id = 1;
        while (!exit) {

            displayBankingOptions();
            String option = getInput();
            System.out.println();

            if ("1".equals(option)) {
                Account account = new Account(0,
                        new CreditCard(400000));
                String insertStatement = String.format("INSERT INTO card VALUES " +
                        "(%d, %s, %s, %d)", id, account.getCreditCard()
                        .getCreditCardNumber(), account.getCreditCard()
                        .getCreditCardPin(), account.getBalance());
                insertOrUpdate(dataBase, insertStatement);

                displayCardDetails(account.getCreditCard().getCreditCardNumber(),
                        account.getCreditCard().getCreditCardPin());

            } else if ("2".equals(option)) {
                System.out.println("Enter your card number:");
                String cCardInput = getInput();
                System.out.println("Your card PIN:");
                String cCardPinInput = getInput();

                String selectStatement = String.format
                        ("SELECT pin FROM card WHERE number = %s", cCardInput);
                String pin = getData(dataBase, selectStatement, "string", "pin");

                System.out.println();
                if ("".equals(pin) || !cCardPinInput.equals(pin)) {
                    System.out.println("Wrong card number or PIN!");
                } else {
                    System.out.println("You have successfully logged in!");
                    navAccount(cCardInput, dataBase);
                }

            } else if ("0".equals(option)) {
                System.out.println("Bye!");
                break;
            }
            System.out.println();
        }
    }

    private static void createTable(DataBase dataBase) {
        String action = "create";
        dataBase.loadUpdateStatement(action);
    }

    private static void insertOrUpdate(DataBase dataBase, String values) {
        dataBase.loadUpdateStatement(values);
    }

    private static String getData(DataBase dataBase, String comm, String type, String col) {
        return dataBase.loadReturnStatement(comm, type, col);
    }

    private static void navAccount(String account, DataBase dataBase) {

        while (true) {
            System.out.println();
            displayNavAccountOptions();
            String option = getInput();
            System.out.println();

            if ("1".equals(option)) {
                String selectStatement = String.format
                        ("SELECT balance FROM card WHERE number = %s", account);
                String balance =
                        getData(dataBase, selectStatement, "int", "balance");
                System.out.printf("Balance: %s%n", balance);

            } else if ("2".equals(option)) {
                System.out.println("Enter income:");
                String inIncome = getInput();
                String updateStatement = String.format("UPDATE card SET " +
                        "balance = balance + %d WHERE number = %s",
                        Integer.parseInt(inIncome), account);
                insertOrUpdate(dataBase, updateStatement);
                System.out.println("Income was added!");

            } else if ("3".equals(option)) {
                System.out.println("Transfer");
                System.out.println("Enter card number:");
                String receivAcct = getInput();
                if (!isPassLuhn(receivAcct)) {
                    System.out.println("Probably you made a mistake in the card number. " +
                            "Please try again!");
                    continue;
                }
                if (Objects.equals(account, receivAcct)) {
                    System.out.println("You can't transfer money to the same account!");
                    continue;
                }
                String selectRecStatement = String.format
                        ("SELECT number FROM card WHERE number = %s", receivAcct);
                String verifyCard =
                        getData(dataBase, selectRecStatement, "string", "number");

                if (!"".equals(verifyCard)) {
                    System.out.println("Enter how much money you want to transfer:");
                    int transferSum = Integer.parseInt(getInput());

                    String selectAmtStatement = String.format
                            ("SELECT balance FROM card WHERE number = %s", account);
                    String balance =
                            getData(dataBase, selectAmtStatement, "int", "balance");

                    if (transferSum < Integer.parseInt(balance)) {
                        transferFunds(dataBase, transferSum, account, receivAcct);
                        System.out.println("Success!");
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Such a card does not exist.");
                }
            } else if ("4".equals(option)) {
                String deleteSQL = String.format
                        ("DELETE FROM card WHERE number = %s", account);
                insertOrUpdate(dataBase, deleteSQL);
                System.out.println("The account has been closed!");
                break;

            } else if ("5".equals(option)) {
                System.out.println("You have successfully logged out!");
                break;

            } else if ("0".equals(option)) {
                exit = true;
                System.out.println("Bye!");
                break;
            }
        }
    }

    private static void transferFunds(DataBase dataBase, int transferSum,
                                      String originAcct, String destAcct) {
        String sendStatement = String.format("UPDATE card SET " +
                        "balance = balance - %d WHERE number = %s",
                transferSum, originAcct);
        String receiveStatement = String.format("UPDATE card SET " +
                        "balance = balance + %d WHERE number = %s",
                transferSum, destAcct);
        transact(dataBase, sendStatement, receiveStatement);
    }

    private static void transact(DataBase dataBase, String send, String receive) {
        dataBase.transaction(send, receive);
    }

    private static boolean isPassLuhn(String cardNum) {
        String dpLasDigit = cardNum.substring(0, cardNum.length() - 1);
        int checkSum = CreditCard.getCheckSum(dpLasDigit);

        int lastStrIndex = cardNum.length() - 1;
        String lastStr = cardNum.substring(lastStrIndex);

        return checkSum == Integer.parseInt(lastStr);
    }

    private static void displayNavAccountOptions() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
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
