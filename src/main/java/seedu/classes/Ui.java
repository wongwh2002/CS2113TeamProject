package seedu.classes;

public class Ui {
    public static void printSeparator() {
        printWithTab(Constants.SEPARATOR);
    }
    public static void printWithTab(String message) {
        System.out.println("\t" + message);
    }
    public static void printWithTabNSeparator(String message) {
        printSeparator();
        System.out.println("\t" + message);
        printSeparator();
    }
}
