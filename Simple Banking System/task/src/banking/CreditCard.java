package banking;

import java.util.Random;

public class CreditCard {

    private final int bankIdentificationNumber;
    private final int checkSum;

    private final String creditCardNumber;
    private final String creditCardPin;
    private final int PIN_LENGTH = 4;
    private final Random random = new Random();

    public CreditCard(int bankIdentificationNumber, int checkSum) {
        this.bankIdentificationNumber = bankIdentificationNumber;
        this.checkSum = checkSum;
        this.creditCardNumber = accountIdentifier();
        this.creditCardPin = generateRandomStrInt(PIN_LENGTH);
    }

    private String accountIdentifier() {
        int accIdentifierLen = 9; //accountIdentifierLength();
        return String.format( bankIdentificationNumber +
                generateRandomStrInt(accIdentifierLen) + checkSum);
    }

    /*private int accountIdentifierLength() {
        int upper = 12;
        int lower = 9;
        int intervalLen = upper - lower + 1;
        return random.nextInt(intervalLen) + lower;
    }*/

    private String generateRandomStrInt(int strLength) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCreditCardPin() {
        return creditCardPin;
    }
}
