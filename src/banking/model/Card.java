package banking.model;

import java.util.Random;

public class Card {
    private int balance;
    private String cardNumber;
    private String pin;

    public Card() {
        this.cardNumber = GenerateCardNumber.getCardNumber();
        this.pin = GeneratePin.getPin();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    private static class GenerateCardNumber {
        private static Random random = new Random();
        private static String getCardNumber() {
            return mii() + bin() + accIdentifier() + checksum();
        }
        private static String mii() {
            return "4";
        }
        private static String bin() {
            return "00000";
        }
        private static int accIdentifier() {
            return random.nextInt(999_999_999 - 100_000_000 + 1) + 100_000_000;
        }
        private static int checksum() {
            return random.nextInt(10);
        }
    }
    private static class GeneratePin {
        private static Random random = new Random();
        private static String getPin() {
            return Integer.toString(random.nextInt(9999 - 1000 + 1) + 1000);
        }
    }
}
