import java.io.Serializable;

// Serializable because its class implements the java.io.Serializable.
// Also so that an object can be represented as a sequence of bytes that include the object's data.
public class Boat implements Serializable {
    public BoatType getTypeOfBoat() {
        return typeOfBoat;
    }

    public String getName() {
        return boatName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getExpenses() {
        return expenses;
    }

// The expenses here are manipulated with the "spendOn" value for whatever is spent on a boat or boats.
    public void setExpenses(double spendOn) {
        expenses += spendOn;
    }

// I used the "%-..." to have perfect spacing and font for the fleet.
// These are all the boatToString so the fleet can be displayed.
    public void boatToString() {
        System.out.printf("    %-8s", typeOfBoat);
        System.out.printf("%-21s", boatName);
        System.out.printf("%-5s", manufacturingYear);
        System.out.printf("%-11s", make);
        System.out.printf("%-2s", length + "'");
        System.out.printf("%-20s", " : Paid $ " + purchasePrice);
        System.out.printf("%-20s", " : Spent $     " + String.format("%.2f",expenses));
        System.out.println();
    }

// Declares all the java data types.
    public enum BoatType{SAILING, POWER};
    BoatType typeOfBoat;
    private String boatName;
    private short manufacturingYear;
    private String make;
    private byte length;
    private double purchasePrice;
    private double expenses;

// I use the "this." so you can refer to any member of the current objects from within a method or constructor.
public Boat(BoatType typeOfBoat, String boatName, short manufacturingYear, String make, byte length, double purchasePrice, double expenses) {
    this.typeOfBoat = typeOfBoat;
    this.boatName = boatName;
    this.manufacturingYear = manufacturingYear;
    this.make = make;
    this.length = length;
    this.purchasePrice = purchasePrice;
    this.expenses = expenses;
    }
}
