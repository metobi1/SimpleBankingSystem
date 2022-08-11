package banking;

import java.util.Random;

public class CreditCard {

    private final int bankIdentificationNumber;
    //private final int checkSum;

    private final String creditCardNumber;
    private final String creditCardPin;
    private final int PIN_LENGTH = 4;
    private final Random random = new Random();

    public CreditCard(int bankIdentificationNumber) {
        this.bankIdentificationNumber = bankIdentificationNumber;
        this.creditCardNumber = accountIdentifier();
        this.creditCardPin = generateRandomStrInt(PIN_LENGTH);
    }

    private String accountIdentifier() {
        int accIdentifierLen = 9; //accountIdentifierLength();
        String randomStrInt = generateRandomStrInt(accIdentifierLen);
        int checkSum = getCheckSum(bankIdentificationNumber + randomStrInt);

        return String.format( bankIdentificationNumber +
                randomStrInt  + checkSum);
    }

    static int getCheckSum(String strInt) {

        StringBuilder stringBuilder = new StringBuilder(strInt);
        int strIntSum = 0;
        for (int i = 0; i < strInt.length(); i++) {
            if (i % 2 == 0) {
                int getInt = Integer.parseInt(stringBuilder.substring(i, i + 1));
                getInt = getInt * 2;
                getInt = getInt > 9 ? getInt - 9 : getInt;
                stringBuilder.replace(i, i + 1, String.valueOf(getInt));
            }
            strIntSum += Integer.parseInt(stringBuilder.substring(i, i + 1));
        }
        return multipleOfTen(strIntSum) - strIntSum;
    }

    private static int multipleOfTen(int num) {

        for (int i = 0; i < 10; i++) {
            int multOfTen = num + i;
            if (multOfTen % 10 == 0) {
                return multOfTen;
            }
        }
        return 0;
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
