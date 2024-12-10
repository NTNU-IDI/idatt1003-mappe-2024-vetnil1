package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCases {
  private final FoodStorage foodStorage;
  private final Cookbook cookbook;
  LocalDate currentDate;
  Scanner scanner;

  public MenuCases(FoodStorage foodStorage, Cookbook cookbook, LocalDate currentDate,
      Scanner scanner) {
    this.foodStorage = foodStorage;
    this.cookbook = cookbook;
    this.currentDate = currentDate;
    this.scanner = scanner;
  }



  // Setter for currentDate
  public void setCurrentDate(LocalDate newDate) {
    this.currentDate = newDate;
  }

  // Case 1: Add groceries
  public void addGroceries(Scanner scanner) {
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
    foodStorage.addGrocery(grocery);

    System.out.println("Grocery added: " + name);
  }

  public void removeGroceries(Scanner scanner) {
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
  public void removeGroceryCompletely(Scanner scanner) {
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
  public LocalDate changeCurrentDate(Scanner scanner) {
    System.out.print("Enter new current date (yyyy-mm-dd): ");
    String newDateStr = scanner.nextLine();
    try {
      LocalDate newDate = LocalDate.parse(newDateStr);
      setCurrentDate(newDate);
      System.out.println("Current date updated to: " + currentDate + "\n");
      return newDate;
    } catch (Exception e) {
      System.out.println("Invalid date format. Please try again. \n");
      System.out.println("Would you like to try again? (yes / no) ");
      if (scanner.nextLine().equals("yes")) {
      return changeCurrentDate(scanner);
      }
    }
    return currentDate;
  }

  public void createNewRecipe() {
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

    cookbook.addRecipe(recipe);
    System.out.println("Recipe added:\n" + recipe.getPrettyString());
  }

  public void checkIfRecipeCanBePrepared() {
    System.out.println("Enter the name of the recipe to check:");
    String recipeName = scanner.nextLine();

    Recipe recipeToCheck = null;
    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().equalsIgnoreCase(recipeName)) {
        recipeToCheck = recipe;
        break;
      }
    }
    if (recipeToCheck == null) {
      System.out.println("Recipe not found in the cookbook.");
      return;
    }
    if (canPrepareRecipe(recipeToCheck)) {
      System.out.println("You can prepare this recipe.");
    } else {
      System.out.println("You can't prepare this recipe.");
    }
  }

  public void suggestRecipes() {
    List<Recipe> suggestedRecipes = new ArrayList<>();
    for (Recipe recipe : cookbook.getRecipes()) {
      if (canPrepareRecipe(recipe)) {
        suggestedRecipes.add(recipe);
      }
    }

    if (suggestedRecipes.isEmpty()) {
      System.out.println("No recipes can be prepared with the available ingredients.");
    } else {
      System.out.println("You can prepare the following recipes:");
      for (Recipe recipe : suggestedRecipes) {
        System.out.println(recipe.getName());
      }
    }
  }

  public void pickAndScaleRecipe() {
    // Step 1: List all recipes
    System.out.println("Available recipes:");
    if (cookbook.getRecipes().isEmpty()) {
      System.out.println("No recipes available in the cookbook. \n");
      return;
    }

    for (Recipe recipe : cookbook.getRecipes()) {
      System.out.println("- " + recipe.getName());
    }

    // Step 2: User picks a recipe
    System.out.println("Enter the name of the recipe you want to scale:");
    String recipeName = scanner.nextLine();

    Recipe chosenRecipe = null;
    for (Recipe recipe : cookbook.getRecipes()) {
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

  public void listExpiredGroceries(LocalDate currentDate) {
    System.out.println("Listing all expired groceries:");
    foodStorage.listExpiredGroceries(currentDate);
  }

  public void listGroceries(LocalDate currentDate) {
    System.out.println("Listing all groceries in storage:");
    foodStorage.listGroceries(currentDate);
  }

  public void searchGroceryByName() {
    System.out.println("Enter the name of the grocery to search for:");
    String searchName = scanner.nextLine();
    foodStorage.searchGroceryByName(searchName);
  }

  public void getTotalValue() {
    double totalValue = foodStorage.getTotalValue(currentDate);  // Get the total value from FoodStorage
    System.out.println("Total value of groceries in storage (excluding expired food): " + totalValue + " NOK \n");
  }

  public void addDummyData() {
    // Pre-set items
    foodStorage.addGrocery(new Grocery("Milk", 1.0, "liters", LocalDate.of(2024, 12, 20), 10.0));
    foodStorage.addGrocery(new Grocery("Eggs", 12.0, "amounts", LocalDate.of(2024, 12, 31), 3.0));
    foodStorage.addGrocery(new Grocery("Flour", 1.0, "kg", LocalDate.of(2025, 8, 22), 10.0));
    foodStorage.addGrocery(new Grocery("Sugar", 1.0, "kg", LocalDate.of(2025, 3, 13), 35.0));
    foodStorage.addGrocery(new Grocery("Salt", 1.0, "kg", LocalDate.of(2026, 6, 14), 25.0));
    foodStorage.addGrocery(new Grocery("Pepper", 0.1, "kg", LocalDate.of(2025, 5, 15), 55.0));
    foodStorage.addGrocery(new Grocery("Butter", 0.5, "kg", LocalDate.of(2024, 12, 26), 50.0));

    // Pre-set recipes in the cookbook
    Recipe pancakes = new Recipe("Pancakes");
    pancakes.addIngredient("Butter", 0.025, "kg");
    pancakes.addIngredient("Milk", 0.1, "liter");
    pancakes.addIngredient("Eggs", 1.0, "amount");
    pancakes.addIngredient("Flour", 0.1, "kg");
    pancakes.addIngredient("Baking Soda", 0.005, "kg");
    pancakes.addIngredient("Salt", 0.001, "kg");
    pancakes.addIngredient("Sugar", 0.01, "kg \n");
    cookbook.addRecipe(pancakes);

    Recipe omelette = new Recipe("Omelette");
    omelette.addIngredient("Eggs", 2.0, "amount");
    omelette.addIngredient("Milk", 0.03, "liter");
    omelette.addIngredient("Salt", 0.002, "kg");
    omelette.addIngredient("Pepper", 0.002, "kg");
    omelette.addIngredient("Butter", 0.0015, "kg \n");
    cookbook.addRecipe(omelette);

    Recipe pyttIPanne = new Recipe("Pytt i Panne");
    pyttIPanne.addIngredient("Sausage", 1.0, "amount");
    pyttIPanne.addIngredient("Butter", 0.003, "kg");
    pyttIPanne.addIngredient("Potatoes", 0.2, "kg");
    pyttIPanne.addIngredient("Carrots", 0.5, "amount");
    pyttIPanne.addIngredient("Onions", 0.5, "amount");
    pyttIPanne.addIngredient("Salt", 0.002, "kg");
    pyttIPanne.addIngredient("Pepper", 0.002, "kg \n");
    cookbook.addRecipe(pyttIPanne);

  }
  public void showRecipe() {
    System.out.println("Choose recipe to show:");
    String recipeName = scanner.nextLine();

    for(Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().equalsIgnoreCase(recipeName)) {
        System.out.println(recipe.getPrettyString());
        return;
      }
    }
    System.out.println("Recipe Name: " + recipeName + " doesn't exist");
  }

  // Check if FoodStorage contains enough ingredients for a recipe
  public boolean canPrepareRecipe(Recipe recipe) {
    for (Grocery ingredient : recipe.getIngredients()) {
      double requiredQuantity = ingredient.getQuantity();
      double availableQuantity = 0;

      // Sum up quantities of matching items in FoodStorage
      for (Grocery storedItem : foodStorage.getGroceries()) {
        if (storedItem.getName().equalsIgnoreCase(ingredient.getName())) {
          availableQuantity += storedItem.getQuantity();
        }
      }

      if (availableQuantity < requiredQuantity) {
        return false; // Not enough of this ingredient
      }
    }
    return true; // All ingredients are available in sufficient quantity
  }

}
