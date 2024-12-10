package edu.ntnu.idi.idatt;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
public class GroceryManagementSystem {
  private FoodStorage foodStorage = new FoodStorage();
  private LocalDate currentDate = LocalDate.now(); // Default to the real current date
  private Scanner scanner;

  // Constructor to initialize the Scanner with Locale.US
  public GroceryManagementSystem() {
    this.scanner = new Scanner(System.in);
    this.scanner.useLocale(Locale.US); // Force Scanner to use US locale for decimal parsing
  }

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
    System.out.println("6. Show total value of non-expired groceries");
    System.out.println("7. Change the current date");
    System.out.println("8. Search for a grocery by name");
    System.out.println("9. Create a new recipe");
    System.out.println("10. Check if a recipe can be prepared");
    System.out.println("11. Suggest recipes based on available ingredients");
    System.out.println("12. Pick a recipe and scale by portion size");
    System.out.println("0. Exit \n");

    System.out.println("Current date: " + currentDate + "\n");

  }

  public void handleUserInput() {
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
        case 8:
          System.out.println("Enter the name of the grocery to search for:");
          String searchName = scanner.nextLine();
          foodStorage.searchGroceryByName(searchName);
          break;
        case 9:
          createNewRecipe();
          break;
        case 10:
          checkIfRecipeCanBePrepared();
          break;
        case 11:
          suggestRecipes();
          break;
        case 12:
          pickAndScaleRecipe();
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

    System.out.println("Enter quantity to remove (e.g, grams, liters): ");
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
  private void createNewRecipe() {
    System.out.println("Enter the name of the recipe:");
    String recipeName = scanner.nextLine();
    Recipe recipe = new Recipe(recipeName);

    boolean addMoreIngredients;
    do {
      System.out.println("Enter ingredient name:");
      String ingredientName = scanner.nextLine();

      System.out.println("Enter quantity:");
      double quantity = scanner.nextDouble();
      scanner.nextLine();

      System.out.println("Enter unit (e.g., liters, kg, amounts):");
      String unit = scanner.nextLine();

      recipe.addIngredient(ingredientName, quantity, unit);

      System.out.println("Add another ingredient? (yes/no):");
      addMoreIngredients = scanner.nextLine().equalsIgnoreCase("yes");
    } while (addMoreIngredients);

    foodStorage.addRecipe(recipe);
    System.out.println("Recipe added:\n" + recipe);
  }

  private void checkIfRecipeCanBePrepared() {
    System.out.println("Enter the name of the recipe to check:");
    String recipeName = scanner.nextLine();

    Recipe recipeToCheck = null;
    for (Recipe recipe : foodStorage.getCookbook()) {
      if (recipe.getName().equalsIgnoreCase(recipeName)) {
        recipeToCheck = recipe;
        break;
      }
    }

    if (recipeToCheck == null) {
      System.out.println("Recipe not found in the cookbook.");
    } else if (foodStorage.canPrepareRecipe(recipeToCheck)) {
      System.out.println("You can prepare this recipe.");
    } else {
      System.out.println("You cannot prepare this recipe. Not enough ingredients.");
    }
  }

  private void suggestRecipes() {
    List<Recipe> suggestedRecipes = foodStorage.suggestRecipes();
    if (suggestedRecipes.isEmpty()) {
      System.out.println("No recipes can be prepared with the available ingredients.");
    } else {
      System.out.println("You can prepare the following recipes:");
      for (Recipe recipe : suggestedRecipes) {
        System.out.println(recipe.getName());
      }
    }
  }

  private void pickAndScaleRecipe() {
    // Step 1: List all recipes
    System.out.println("Available recipes:");
    if (foodStorage.getCookbook().isEmpty()) {
      System.out.println("No recipes available in the cookbook. \n");
      return;
    }

    for (Recipe recipe : foodStorage.getCookbook()) {
      System.out.println("- " + recipe.getName());
    }

    // Step 2: User picks a recipe
    System.out.println("Enter the name of the recipe you want to scale:");
    String recipeName = scanner.nextLine();

    Recipe chosenRecipe = null;
    for (Recipe recipe : foodStorage.getCookbook()) {
      if (recipe.getName().equalsIgnoreCase(recipeName)) {
        chosenRecipe = recipe;
        break;
      }
    }

    if (chosenRecipe == null) {
      System.out.println("Recipe not found in the cookbook. \n");
      return;
    }

    // Step 3: User picks a portion size
    System.out.println("Enter the desired portion size:");
    int portionSize = scanner.nextInt();
    scanner.nextLine(); // Clear newline

    if (portionSize <= 0) {
      System.out.println("Invalid portion size. Please enter a positive integer. \n");
      return;
    }

    // Step 4: Scale the recipe and display
    List<Grocery> scaledIngredients = chosenRecipe.scaleIngredients(portionSize);
    System.out.println("Scaled recipe for " + portionSize + " portions of " + chosenRecipe.getName() + ":");
    for (Grocery ingredient : scaledIngredients) {
      System.out.println("- " + ingredient.getName() + ": " + ingredient.getQuantity() + " " + ingredient.getUnit());
    }
  }


}

