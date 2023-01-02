import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

// The main class.
public class CoconutGroveSailingClub {
    public static ArrayList<Boat>fleet = new ArrayList<Boat>();
    private static final Scanner keyboard = new Scanner(System.in);

        public static void main(String[] args) throws IOException, ClassNotFoundException {
            System.out.println("Welcome to the Fleet Management System");
            System.out.println("--------------------------------------");
            if (args.length > 0) {
                initialFromCSVFile(args[0]);
            } else {
                initFromObjectFile();
            }
            menu();
            writeToObjectFile();
        }

// The file output stream that stores the information entered into the program and saves it to a db file.
// Hence, you don't have to use the csv file anymore.
    private static void writeToObjectFile() throws IOException {
        FileOutputStream fos = new FileOutputStream("FleetData.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(fleet);
        oos.close();
    }

// Inputs data entered and stores it into the db file.
    private static void initFromObjectFile() throws IOException, ClassNotFoundException {
            //String fileName = "FleetData.db";
            FileInputStream fileInputStream = new FileInputStream("FleetData.db");
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            fleet = (ArrayList<Boat>) ois.readObject();
            ois.close();
    }

// Here is where the buffered reader is used to read the csv file since it's reading a large text.
    public static void initialFromCSVFile(String fileName) throws IOException {
            File fleetData = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fleetData));
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] fields = line.split(",");
                String type = fields[0];
                Boat.BoatType boatType = Boat.BoatType.valueOf(type);
                String name = fields[1];
                String year = fields[2];
                short manufacturingYear = Short.parseShort(year);
                String make = fields[3];
                String length = fields[4];
                byte boatLength = Byte.parseByte(length);
                String price = fields[5];
                double purchasePrice = Double.parseDouble(price);
                Boat newBoat = new Boat(boatType, name, manufacturingYear, make, boatLength, purchasePrice, 0.0);
                fleet.add(newBoat);
            }
            bufferedReader.close();
        }

// The main menu method which is called multiple times.
// I used a switch case first but switch to the while loop with if else statements and it worked better.
        public static void menu() {
                char option;
                boolean exit = false;
                    while (exit == false) {
                        System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
                        option = keyboard.next().toUpperCase().charAt(0);
                        if (option == 'P') {
                            System.out.println();
                            printReport();
                            System.out.println();
                        } else if (option == 'A') {
                            System.out.print("Please enter the new boat CSV data          : ");
                            addBoat();
                            System.out.println();
                        } else if (option == 'R') {
                            removeBoat();
                        } else if (option == 'E') {
                            addExpense();
                        } else if (option == 'X') {
                            System.out.println();
                            System.out.print("Exiting the Fleet Management System");
                            System.out.println();
                            exit = true;
                        } else {
                            System.out.println("Invalid menu option, try again");
                        }
                    }
        }


// Array list for adding a boat.
// The parse short method is used and the string argument as a signed decimal short.
    private static ArrayList<Boat> addBoat() {
            String newBoatInfo = keyboard.next();
            String[] fields = newBoatInfo.split(",");
            String type = fields[0];
            Boat.BoatType boatType = Boat.BoatType.valueOf(type);
            String name = fields[1];
            String year = fields[2];
            short manufacturingYear = Short.parseShort(year);
            String make = fields[3];
            String length = fields[4];
            byte boatLength = Byte.parseByte(length);
            String price = fields[5];
            double purchasePrice = Double.parseDouble(price);
            Boat newBoat = new Boat(boatType, name, manufacturingYear, make, boatLength, purchasePrice, 0.0);
            fleet.add(newBoat);
            return fleet;
    }

// Remove boat that searches for the boat you want to remove.
// I use a for loop and an if function. If or if not the boat is found different system out print statements are displayed.
    public static void removeBoat() {
        System.out.print("Which boat do you want to remove?           : ");
        keyboard.nextLine();
        String boatToRemove = keyboard.nextLine();
        int isFound = 0;
        for (int i = 0; i < fleet.size(); i++) {
            if (fleet.get(i).getName().equalsIgnoreCase(boatToRemove)) {
                    fleet.remove(i);
                    isFound = 1;
                    System.out.println();
            }
        }
        if (isFound == 0) {
            System.out.println("Cannot find boat " + boatToRemove);
            System.out.println();
            }
    }

    // Add expense for the boat.
    // Also calculates the expense permitted for each boat. So it compares the purchase price to the expense.
    // If the expense is more than the purchase price then the computer outputs that the expense is not permitted.
    // Lastly it calculates and displays how much you allocated to spend on the boat if you have expenses.
    public static void addExpense() {
        System.out.print("Which boat do you want to spend on?         : ");
        keyboard.nextLine();
        String boatSpendOn = keyboard.nextLine();
        int isFound = 0;
        // Here is used the equalsIgnoreCase so any input with all the letter cases are ignored and the computer finds the input in the fleet.
        for (int i = 0; i < fleet.size(); i++) {
            if (fleet.get(i).getName().equalsIgnoreCase(boatSpendOn)) {
                isFound = 1;
                System.out.print("How much do you want to spend?              : ");
                double spendOn = keyboard.nextDouble();
                double moneyLeft = (fleet.get(i).getPurchasePrice() - fleet.get(i).getExpenses());
                if (spendOn > moneyLeft) {
                        System.out.println("Expense not permitted, only $" + String.format("%.2f", moneyLeft) + " left to spend.");
                        System.out.println();
                } else {
                    fleet.get(i).setExpenses(spendOn);
                    System.out.println("Expense authorized, $" + String.format("%.2f", fleet.get(i).getExpenses()) + " spent.");
                    System.out.println();
                }
            }
        }
        // If the boat is not found "boatSpendOn" is printed as what ever the user enters as the boat here.
        if (isFound == 0) {
            System.out.println("Cannot find boat " + boatSpendOn);
            System.out.println();
        }
    }

// Fleet report and this declares the fleet size and also gets the exact boat purchase price and expenses.
// The letter (i) is significant to each individual boat and its purchase price and expenses.
    public static void printReport() {
            System.out.println("Fleet report: ");
            for (int i = 0; i < fleet.size(); i++) {
                fleet.get(i).boatToString();
            }
            double totalPaid = 0;
            double totalExpense = 0;
        for (int i = 0; i < fleet.size(); i++) {
            totalPaid += fleet.get(i).getPurchasePrice();
            totalExpense += fleet.get(i).getExpenses();
        }
        System.out.printf("    %-49s", "Total");
        System.out.printf("%-20s",": Paid $ " + String.format("%.2f",totalPaid));
        System.out.printf("%-20s", ": Spent $     " + String.format("%.2f",totalExpense));
        System.out.println();
        }
}
