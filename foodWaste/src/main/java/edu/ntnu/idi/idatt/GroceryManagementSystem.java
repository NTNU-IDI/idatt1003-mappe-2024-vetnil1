package edu.ntnu.idi.idatt;
import java.time.LocalDate;
import java.util.Scanner;
public class GroceryManagementSystem {
  private FoodStorage foodStorage = new FoodStorage();

  public void showMenu() {
    System.out.println("1. Add grocery");
    System.out.println("2. Remove specific amount of grocery");
    System.out.println("3. Remove grocery");
    System.out.println("4. List of grocery");
    System.out.println("5. List expired groceries");
    System.out.println("6. Show total value of groceries");
    System.out.println("0. Exit \n");

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
          foodStorage.listGroceries();
          break;
        case 5:
          foodStorage.listExpiredGroceries();
          break;
        case 6:
          foodStorage.getTotalValue();
          break;
        case 0:
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
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

    System.out.println("Enter unit (e.g, grams, liters, units): ");
    String unit = scanner.nextLine();

    System.out.println("Enter expiration date (YYYY-MM-DD): ");
    LocalDate expirationDate = LocalDate.parse(scanner.nextLine());

    System.out.println("Enter price per unit (e.g, NOK/gram, NOK/liter, NOK/unit): ");
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
    if (grocery != null) {
      System.out.println("Grocery not found. ");
      return;
    }

    System.out.println("Enter quantity to remove (e.g, grams, liters: ");
    double amount = scanner.nextDouble();
    scanner.nextLine();

    foodStorage.removeGroceries(name, amount);
    System.out.println("Removed: " + amount + " " + grocery.getUnit() + " of " + name + " from food storage.");
  }

  // case 3: Remove a grocery completely
  private void removeGroceryCompletely(Scanner scanner) {
    System.out.println("Enter grocery name: ");
    String name = scanner.nextLine();

    Grocery grocery = foodStorage.getGrocery(name);
    if (grocery != null) {
      System.out.println("Grocery not found. ");
      return;
    }

    foodStorage.removeGroceryCompletely(name);
    System.out.println("Grocery " + name + " removed from food storage.");
  }
}
