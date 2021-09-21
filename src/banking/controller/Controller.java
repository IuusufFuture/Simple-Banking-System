package banking.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import banking.controller.enums.Command;
import banking.model.Card;

public class Controller {

//    private static Controller controller;
//    private Controller() {
//          this.storedData = new HashMap<>();
//          this.scan = new Scanner(System.in);
//    }
//    public static Controller getInstance() {
//        if (controller == null) {
//            controller = new Controller();
//        }
//        return controller;
//    }
    private Scanner scan;
    private HashMap<String, Card> storedData;
    private static Card card;
    private List<Command> commands;

    public Controller() {
        this.storedData = new HashMap<>();
        this.scan = new Scanner(System.in);
    }



    public void run() {
        while (true) {
            if(card == null) {
                printLogin();
                commands = getLoginCommands();
            } else {
                printLoggedIn();
                commands = getAccountCommands();
            }
            int userInput = scan.nextInt();
            Command command = commands.get(userInput);
            switch (command) {
                case CREATE:
                    createCard();
                    break;
                case LOGIN:
                    login();
                    break;
                case EXIT:
                    System.out.println("\nBye!");
                    System.exit(0);
                    break;
                case LOGOUT:
                    card = null;
                    System.out.println("\nYou have successfully logged out!\n");
                    break;
                case BALANCE:
                    System.out.println("\nBalance: " + card.getBalance() + "\n");
                    break;
                case ADD_INCOME:
                    addAmount();
                    break;
                case DO_TRANSFER:
                    transferAmount();
                    break;
                case CLOSE_ACCOUNT:
                    deleteAcc();
                    break;
            }
        }
    }
    private void login() {
        System.out.print("\nEnter your card number:\n>");
        String cardNumber = scan.next();
        System.out.print("\nEnter your PIN:\n>");
        card = storedData.get(cardNumber);
        String pin = scan.next();
        if (card == null || !pin.equals(card.getPin())) {
            System.out.println("\nWrong card number or PIN\n");
            card = null;
        } else if(pin.equals(card.getPin())) {
            System.out.println("\nYou have successfully logged in!\n");
        }
    }
    private void createCard() {
        Card card = new Card();
        DataBaseController dataBaseController = new DataBaseController();
        dataBaseController.insertData(card.getCardNumber(), card.getPin(), card.getBalance());
        storedData.put(card.getCardNumber(), card);
        System.out.println("" +
                "\nYour card has been created\n" +
                "Your card number:\n" +
                card.getCardNumber() + "\n" +
                "Your card PIN:\n" +
                card.getPin() + "\n");
        // go back and run it again
        run();
    }
    private void printLogin() {
        System.out.print("" +
                "1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit\n" +
                ">");
    }
    private void printLoggedIn() {
        System.out.print("" +
                "1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n" +
                ">");
    }
    private List<Command> getLoginCommands() {
        return List.of(Command.EXIT, Command.CREATE, Command.LOGIN);
    }
    private List<Command> getAccountCommands() {
        return List.of(Command.EXIT, Command.BALANCE, Command.ADD_INCOME, Command.DO_TRANSFER, Command.CLOSE_ACCOUNT, Command.LOGOUT);
    }
    private void addAmount() {
        DataBaseController dataBaseController = new DataBaseController();
        System.out.print("\nEnter income:\n" +
                ">");
        int amount = scan.nextInt();
        System.out.println("Income was added!\n");
        card.setBalance(card.getBalance() + amount);
        dataBaseController.addBalanceAmount(card.getCardNumber(), amount);
    }
    private void deleteAcc() {
        DataBaseController dataBaseController = new DataBaseController();
        System.out.println("The account has been closed!");
        storedData.remove(card.getCardNumber());
        dataBaseController.deleteAccount(card.getCardNumber());
    }
    private void transferAmount() {
        System.out.print("Enter card number: ");
        String input = scan.next();
        Card ad = storedData.get(input);
        System.out.println(validateCardByLuhnAlgorithem());
        if(ad == card){

        }
    }
    public static String validateCardByLuhnAlgorithem() {
        String validateCard = card.getCardNumber();

        // Convert generated card to int & add it to an array of int
        int[] creditCardInt = new int[validateCard.length()];

        for (int i = 0; i < creditCardInt.length; i++) {
            creditCardInt[i] = Integer.parseInt(validateCard.substring(i, i + 1));
        }

        // Starting from the right, double every other digit & if greater than 9 mod 10 + 1 to the remainder (or just subtract 9)
        for (int i = creditCardInt.length - 2; i >= 0 ; i = i - 2) {
            int tempValue = creditCardInt[i];
            tempValue = tempValue * 2;
            if(tempValue > 9) {
                tempValue = tempValue % 10 + 1; // or just subtract 9
            }
            creditCardInt[i] = tempValue;
        }

        // Add up all the digits
        int total = 0;
        for (int i = 0; i < creditCardInt.length; i++) {
            total += creditCardInt[i];
        }

        // If number is a multiple of 10, it's valid (basically needs to be dividable by 10)
        if (total % 10 == 0) {
            return "Ready to transfer"; // ready to transfer
        } else {
            return "Probably you made a mistake in the card number. Please try again!";
        }
    }
}

