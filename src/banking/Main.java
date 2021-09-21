package banking;
import banking.controller.Controller;
import banking.controller.DataBaseController;

public class Main {
    public static void main(String args[]) {

        /*
        String fileArg = args[0];
        String fileName = args[1];
        if (fileArg.equals("-fileName")) {
            DataBaseController.createDBandTable(fileName);
            new Controller().run();
        } else {
            System.out.println("Please enter '-fileName' before databaseName.db");
        }
         */
        System.out.println(Controller.getInstance());
        System.out.println(Controller.getInstance());
        System.out.println(Controller.getInstance());
        System.out.println(Controller.getInstance());
    }

}

// go to src folder and then type -> javac banking/Main.java
// and then type this -> java -classpath ".;sqlite-jdbc-3.36.0.2.jar" banking.Main -fileName parameter1.db