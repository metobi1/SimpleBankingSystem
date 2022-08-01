package banking;

public class Account {
    //The bank account

    private int balance;
    private final CreditCard creditCard;

    Account(int openingBalance, CreditCard creditCard) {

        this.balance = openingBalance;
        this.creditCard = creditCard;
    }

    public int getBalance() {
        return balance;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}
