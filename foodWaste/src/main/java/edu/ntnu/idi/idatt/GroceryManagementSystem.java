package edu.ntnu.idi.idatt;
import java.time.LocalDate;
import java.util.Scanner;
public class GroceryManagementSystem {
  private FoodStorage foodStorage = new FoodStorage();
  private LocalDate currentDate = LocalDate.now(); // Default to the real current date

  // Getter for currentDate
  public LocalDate getCurrentDate() {
    return currentDate;
  }

  // Setter for currentDate
  public void setCurrentDate(LocalDate newDate) {
    this.currentDate = newDate;
  }

  public void showMenu() {
    System.out.println("1. Add grocery");
    System.out.println("2. Remove specific amount of grocery");
    System.out.println("3. Remove grocery");
    System.out.println("4. List of non-expired groceries");
    System.out.println("5. List expired groceries and their value");
    System.out.println("6. Show total value of groceries");
    System.out.println("7. Change the current date");
    System.out.println("0. Exit \n");

    System.out.println("Current date: " + currentDate + "\n");

  }

  public void handleUserInput() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      showMenu();
      System.out.print("Enter your choice: ");
      choice = scanner.nextInt();
      scanner.nextLine(); //Clear new line
      switch (choice) {
        case 1:
          addGroceries(scanner);
          break;
        case 2:
          removeGroceries(scanner);
          break;
        case 3:
          removeGroceryCompletely(scanner);
          break;
        case 4:
          System.out.println("Listing all groceries in storage:");
          foodStorage.listGroceries(currentDate);
          break;
        case 5:
          System.out.println("Listing all expired groceries:");
          foodStorage.listExpiredGroceries(currentDate);
          break;
        case 6:
          double totalValue = foodStorage.getTotalValue(currentDate);  // Get the total value from FoodStorage
          System.out.println("Total value of groceries in storage (excluding expired food): " + totalValue + " NOK \n");
          break;
        case 7:
          changeCurrentDate(scanner);
          break;
        case 0:
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid choice. Please try again.\n");
      }
    } while (choice != 0);
    scanner.close();
  }

  // Case 1: Add groceries
  private void addGroceries(Scanner scanner) {
    System.out.println("Enter grocery name: ");
    String name = scanner.nextLine();

    System.out.println("Enter quantity: ");
    double quantity = scanner.nextDouble();
    scanner.nextLine();

    System.out.println("Enter unit (e.g, liter, kg, amount): ");
    String unit = scanner.nextLine();

    System.out.println("Enter expiration date (YYYY-MM-DD): ");
    LocalDate expirationDate = LocalDate.parse(scanner.nextLine());

    System.out.println("Enter price per unit (e.g, NOK/kg, NOK/liter, NOK/amount (like NOK/egg)): ");
    double pricePerUnit = scanner.nextDouble();
    scanner.nextLine();

    Grocery grocery = new Grocery(name, quantity, unit, expirationDate, pricePerUnit);
    foodStorage.addGroceries(grocery);

    System.out.println("Grocery added: " + name);
  }

  private void removeGroceries(Scanner scanner) {
    System.out.println("Enter grocery name: ");
    String name = scanner.nextLine();

    Grocery grocery = foodStorage.getGrocery(name);
    if (grocery == null) {
      System.out.println("Grocery not found. ");
      return;
    }

    System.out.println("Enter quantity to remove (e.g, grams, liters: ");
    double amount = scanner.nextDouble();
    scanner.nextLine();

    foodStorage.removeGroceries(name, amount);
    System.out.println("Removed: " + amount + " " + grocery.getUnit() + " of " + name + " from food storage. \n");
  }

  // case 3: Remove a grocery completely
  private void removeGroceryCompletely(Scanner scanner) {
    System.out.println("Enter grocery name: ");
    String name = scanner.nextLine();

    Grocery grocery = foodStorage.getGrocery(name);
    if (grocery == null) {
      System.out.println("Grocery not found. \n");
      return;
    }

    foodStorage.removeGroceryCompletely(name);
    System.out.println("Grocery " + name + " removed from food storage.\n");
  }



  // case 7: change current date
  private void changeCurrentDate(Scanner scanner) {
    System.out.print("Enter new current date (yyyy-mm-dd): ");
    String newDateStr = scanner.nextLine();
    try {
      LocalDate newDate = LocalDate.parse(newDateStr);
      setCurrentDate(newDate);
      System.out.println("Current date updated to: " + currentDate + "\n");
    } catch (Exception e) {
      System.out.println("Invalid date format. Please try again. \n");
    }
  }

}

