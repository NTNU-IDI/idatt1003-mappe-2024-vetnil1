package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

/**
 * The {@code TextUserInterface} class represents the main user
 * interface for interacting with the program.
 * It provides a text-based menu system to manage groceries and recipes,
 * allowing users to perform various operations.
 */
public class TextUserInterface {
  private FoodStorage foodStorage;
  private Cookbook cookbook;
  private LocalDate currentDate;
  private Scanner scanner;

  /**
   * Initializes the application by setting up the {@code Scanner},
   * {@code FoodStorage}, and {@code Cookbook}.
   * The {@code Locale.US} is used to ensure consistent parsing of decimal values.
   */
  public void init() {
    this.scanner = new Scanner(System.in);
    this.currentDate = LocalDate.now();
    this.foodStorage = new FoodStorage();
    this.cookbook = new Cookbook();
    this.scanner.useLocale(Locale.US);
  }

  /**
   * Displays the main menu options to the user.
   */
  public void showMenu() {
    System.out.println();
    System.out.println("1. Add grocery");
    System.out.println("2. Remove specific amount of grocery");
    System.out.println("3. Remove grocery");
    System.out.println("4. List of non-expired groceries");
    System.out.println("5. List expired groceries and their value");
    System.out.println("6. Show total value of non-expired groceries");
    System.out.println("7. Change the current date");
    System.out.println("8. Search for a grocery by name");
    System.out.println("9. Create a new recipe");
    System.out.println("10. Check if a recipe can be prepared");
    System.out.println("11. Suggest recipes based on available ingredients");
    System.out.println("12. Pick a recipe and scale by portion size");
    System.out.println("13. Add demo data");
    System.out.println("14. Show recipe");
    System.out.println("15. Open the cookbook");
    System.out.println("0. Exit \n");
    System.out.println("Current date: " + currentDate + "\n");
  }

  /**
   * Starts the text-based user interface.
   * This method loops through the menu options, allowing the user to select actions.
   * It creates an instance of {@code MenuCases} to handle menu-specific logic.
   */
  public void start() {
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, currentDate, scanner);
    int choice = -1;
    do {
      showMenu();
      System.out.print("Enter your choice: ");
      try {
        choice = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid choice, please try again");
        continue;
      }

      switch (choice) {
        case 1:
          menuCases.addGroceries(scanner);
          break;
        case 2:
          menuCases.removeGroceries(scanner);
          break;
        case 3:
          menuCases.removeGroceryCompletely(scanner);
          break;
        case 4:
          menuCases.listGroceries(currentDate);
          break;
        case 5:
          menuCases.listExpiredGroceries(currentDate);
          break;
        case 6:
          menuCases.getTotalValue();
          break;
        case 7:
          currentDate = menuCases.changeCurrentDate(scanner);
          break;
        case 8:
          menuCases.searchGroceryByName();
          break;
        case 9:
          menuCases.createNewRecipe();
          break;
        case 10:
          menuCases.checkIfRecipeCanBePrepared();
          break;
        case 11:
          menuCases.suggestRecipes();
          break;
        case 12:
          menuCases.pickAndScaleRecipe();
          break;
        case 13:
          menuCases.addDummyData();
          break;
        case 14:
          menuCases.showRecipe();
          break;
        case 15:
          menuCases.showCookbook();
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
}
