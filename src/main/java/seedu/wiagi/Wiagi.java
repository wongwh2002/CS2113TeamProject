package seedu.wiagi;

import type.Income;
import type.Spending;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Wiagi {

    private static final ArrayList<Income> incomes = new ArrayList<Income>();
    private static final ArrayList<Spending> spendings = new ArrayList<Spending>();

    public static void main(String[] args) {
        System.out.println("Hello from Wiagi\n");
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());

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
                in.close();
                break;
            case "add":
                System.out.println("add");
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
