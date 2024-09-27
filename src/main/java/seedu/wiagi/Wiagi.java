package seedu.wiagi;

import seedu.type.Spending;
import seedu.type.Income;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Wiagi {

    private static final ArrayList<Income> incomes = new ArrayList<Income>();
    private static final ArrayList<Spending> spendings = new ArrayList<Spending>();
    private static int spendingsCount = 0;
    private static int incomesCount = 0;

    private static void addEntry(String userInput) {
        String[] userInputWords = userInput.split(" ");
        if (userInputWords.length < 2) {
            throw new IllegalArgumentException();
        }
        if (userInputWords[1].equals("spending")) {
            spendings.add(new Spending(userInputWords, userInput));
            spendingsCount++;
        } else if (userInputWords[1].equals("income")) {
            incomes.add(new Income(userInputWords, userInput));
            incomesCount++;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello from Wiagi\n");
        Scanner in = new Scanner(System.in);

        boolean isCompleted = false;
        String userInput = "";
        while (!isCompleted) {
            try {
                userInput = in.nextLine();
            } catch (NoSuchElementException e) {
                in.close();
                break;
            }
            String command = userInput.split(" ")[0].toLowerCase();

            switch (command) {
            case "bye":
                isCompleted = true;
                System.out.println("bye");
                in.close();
                break;
            case "add": // user input should be in the form add [add type] [amount] [description]...
                addEntry(userInput);
                break;
            case "delete":
                System.out.println("delete");
                break;
            case "list":
                System.out.println("list");
                break;
            default:
                System.out.println("Unknown command");
            }
        }


        /* Test code to print input in array list
        Income testIncome = new Income(100);
        incomes.add(testIncome);

        Spending testSpending = new Spending(100);
        spendings.add(testSpending);

        System.out.println(incomes.get(0).amount);
        System.out.println(spendings.get(0).amount);
        */
    }
}
