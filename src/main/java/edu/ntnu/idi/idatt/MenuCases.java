package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The {@code MenuCases} class contains methods corresponding to various menu options
 * in the application. It enables users to manage groceries in a {@code FoodStorage}
 * and recipes in a {@code Cookbook}.
 */
public class MenuCases {
  private final FoodStorage foodStorage;
  private final Cookbook cookbook;
  LocalDate currentDate;
  Scanner scanner;

  /**
   * Constructs a new {@code MenuCases} instance.
   *
   * @param foodStorage the {@code FoodStorage} instance to manage groceries.
   * @param cookbook    the {@code Cookbook} instance to manage recipes.
   * @param currentDate the current date used in the application.
   * @param scanner     the {@code Scanner} for reading user input.
   */
  public MenuCases(FoodStorage foodStorage, Cookbook cookbook, LocalDate currentDate,
      Scanner scanner) {
    this.foodStorage = foodStorage;
    this.cookbook = cookbook;
    this.currentDate = currentDate;
    this.scanner = scanner;
  }


  /**
   * Updates the current date used by the application.
   *
   * @param newDate the new date to set. Must not be {@code null}.
   * @throws IllegalArgumentException if {@code newDate} is {@code null}.
   */

  public void setCurrentDate(LocalDate newDate) {
    if (newDate == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    this.currentDate = newDate;
  }

  /**
   * Allows the user to add a grocery item to the food storage.
   *
   * @param scanner the {@code Scanner} for reading user input.
   */
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

    System.out.println("Enter price per unit (e.g, NOK/kg, NOK/liter, "
        + "NOK/amount (like NOK/egg)): ");
    double pricePerUnit = scanner.nextDouble();
    scanner.nextLine();

    Grocery grocery = new Grocery(name, quantity, unit, expirationDate, pricePerUnit);
    foodStorage.addGrocery(grocery);

    System.out.println("Grocery added: " + name);
  }
  /**
   * Allows the user to remove a specific quantity of a grocery from the food storage.
   *
   * @param scanner the {@code Scanner} for reading user input.
   * @throws IllegalArgumentException if the quantity is invalid or exceeds the available amount.
   */

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
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
    if (amount > grocery.getQuantity()) {
      throw new IllegalArgumentException("Amount must be less than grocery quantity");
    }
    System.out.println("Removed: " + amount + " "
        + grocery.getUnit() + " of " + name + " from food storage. \n");
  }

  /**
   * Removes a grocery completely from the food storage by its name.
   *
   * @param scanner the {@code Scanner} for reading user input.
   * @throws IllegalArgumentException if the grocery name is {@code null} or blank.
   */
  public void removeGroceryCompletely(Scanner scanner) {
    System.out.println("Enter grocery name: ");
    String name = scanner.nextLine();


    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Grocery cannot be null or blank");
    }

    foodStorage.removeGroceryCompletely(name);
    System.out.println("Grocery " + name + " removed from food storage.\n");
  }

  /**
   * Allows the user to manually change the application's current date.
   *
   * @param scanner the {@code Scanner} for reading user input.
   * @return the updated current date.
   */
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
  /**
   * Enables the user to create a new recipe and add it to the cookbook.
   */

  public void createNewRecipe() {
    System.out.println("Enter the name of the recipe:");
    String recipeName = scanner.nextLine();
    Recipe recipe = new Recipe(recipeName);

    System.out.println("Enter a short description of the recipe:");
    String description = scanner.nextLine();
    recipe.setDescription(description);

    System.out.println("Enter the procedure for the recipe:");
    String procedure = scanner.nextLine();
    recipe.setProcedure(procedure);

    System.out.println("Enter the portion size for the recipe (e.g., 4 servings):");
    int portionSize = scanner.nextInt();
    scanner.nextLine(); // Clear newline character
    recipe.setPortionSize(portionSize);

    boolean addMoreIngredients;
    do {
      System.out.println("Enter ingredient name:");
      String ingredientName = scanner.nextLine();
      if (ingredientName == null || ingredientName.isBlank()) {
        throw new IllegalArgumentException("Recipe name should not be null");
      }
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
  /**
  *case 10 method enables the user to check if a
  *recipe from the cookbook can be prepared from
  *the different groceries in the food storage.
   */

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
  /**
  *helps the user by suggesting
  *different recipes the user can make with the
  *groceries already in the food storage.
   */

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
  /**
  *enables the user to pick a recipe from the cookbook and scale the ingredients
  *to the wanted portion size.
   */

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
    int newPortionSize = scanner.nextInt();
    scanner.nextLine(); // Clear newline

    if (newPortionSize <= 0) {
      System.out.println("Invalid portion size. Please enter a positive integer. \n");
      return;
    }

    // Step 4: Scale the recipe and display
    List<Grocery> scaledIngredients = chosenRecipe.scaleIngredients(newPortionSize);
    System.out.println("Scaled recipe for " + newPortionSize + " portions of "
        + chosenRecipe.getName() + ":");
    for (Grocery ingredient : scaledIngredients) {
      System.out.println("- " + ingredient.getName() + ": " + ingredient.getQuantity() + " "
          + ingredient.getUnit());
    }
  }
  /**
   * Lists all expired groceries in the food storage.
   *
   * @param currentDate the current date to check for expiration.
   */

  public void listExpiredGroceries(LocalDate currentDate) {
    System.out.println("Listing all expired groceries:");
    foodStorage.listExpiredGroceries(currentDate);
  }
  /**
   * Lists all non-expired groceries in the food storage.
   *
   * @param currentDate the current date to check for expiration.
   */

  public void listGroceries(LocalDate currentDate) {
    System.out.println("Listing all groceries in storage:");
    foodStorage.listGroceries(currentDate);
  }
  /**
  *case 8 method enables the user to search for a specific
  *grocery in the food storage which then prints out all info
  *about this grocery.
   */

  public void searchGroceryByName() {
    System.out.println("Enter the name of the grocery to search for:");
    String searchName = scanner.nextLine();
    foodStorage.searchGroceryByName(searchName);
  }
  /**
  *prints out the total value of all the non-expired
  *groceries in the food storage.
   */

  public void getTotalValue() {
    double totalValue = foodStorage.getTotalValue(currentDate);
    System.out.println("Total value of groceries in storage (excluding expired food): "
        + totalValue + " NOK \n");
  }
  /**
  *case 13 method adds a couple pre-set groceries and recipes to
  *the food storage and cookbook, which might save the user some
  *time from having to manually add every data if they want to
  *test the program. The recipes and groceries are listed bellow.
   */

  public void addDummyData() {
    System.out.println();
    System.out.println("Demo data added. "
        + "Now you have a couple ingredients and recipes to work with. \n");
    // Pre-set items
    foodStorage.addGrocery(new Grocery("Milk", 1.0, "liters",
        LocalDate.of(2024, 12, 20), 10.0));
    foodStorage.addGrocery(new Grocery("Eggs", 12.0, "amounts",
        LocalDate.of(2024, 12, 31), 3.0));
    foodStorage.addGrocery(new Grocery("Flour", 1.0, "kg",
        LocalDate.of(2025, 8, 22), 10.0));
    foodStorage.addGrocery(new Grocery("Sugar", 1.0, "kg",
        LocalDate.of(2025, 3, 13), 35.0));
    foodStorage.addGrocery(new Grocery("Salt", 1.0, "kg",
        LocalDate.of(2026, 6, 14), 25.0));
    foodStorage.addGrocery(new Grocery("Pepper", 0.1, "kg",
        LocalDate.of(2025, 5, 15), 55.0));
    foodStorage.addGrocery(new Grocery("Butter", 0.5, "kg",
        LocalDate.of(2024, 12, 26), 50.0));



    Recipe pancakes = new Recipe("Pancakes");
    pancakes.setDescription("A classic breakfast dish that is light, fluffy, and delicious.");
    pancakes.setProcedure(
        "1. Mix the dry ingredients in a bowl.\n"
            + "2. In a separate bowl, whisk the wet ingredients together.\n"
            + "3. Gradually combine the wet and dry ingredients to form a smooth batter.\n"
            + "4. Heat a skillet and cook the pancakes until golden on both sides.\n"
            + "5. Serve with syrup, butter, or your favorite toppings."
    );
    pancakes.addIngredient("Butter", 0.025, "kg");
    pancakes.addIngredient("Milk", 0.1, "liter");
    pancakes.addIngredient("Eggs", 1.0, "amount");
    pancakes.addIngredient("Flour", 0.1, "kg");
    pancakes.addIngredient("Baking Soda", 0.005, "kg");
    pancakes.addIngredient("Salt", 0.001, "kg");
    pancakes.addIngredient("Sugar", 0.01, "kg");
    cookbook.addRecipe(pancakes);

    Recipe omelette = new Recipe("Omelette");
    omelette.setDescription("A quick and nutritious egg dish perfect for breakfast"
        + " or a light meal.");
    omelette.setProcedure(
        "1. Crack the eggs into a bowl and beat them until smooth.\n"
            + "2. Add a splash of milk, salt, and pepper to taste.\n"
            + "3. Heat butter in a pan and pour in the egg mixture.\n"
            + "4. Cook until the omelette is set, then fold and serve."
    );
    omelette.addIngredient("Eggs", 2.0, "amount");
    omelette.addIngredient("Milk", 0.03, "liter");
    omelette.addIngredient("Salt", 0.002, "kg");
    omelette.addIngredient("Pepper", 0.002, "kg");
    omelette.addIngredient("Butter", 0.0015, "kg");
    cookbook.addRecipe(omelette);

    Recipe pyttIPanne = new Recipe("Pytt i Panne");
    pyttIPanne.setDescription("A traditional Scandinavian dish made with diced potatoes and meat.");
    pyttIPanne.setProcedure(
        "1. Dice the potatoes, sausage, and vegetables.\n"
            + "2. Heat butter in a pan and fry the potatoes until golden.\n"
            + "3. Add the sausage and vegetables, seasoning with salt and pepper.\n"
            + "4. Cook until everything is heated through and slightly crispy.\n"
            + "5. Serve hot with a fried egg on top, if desired."
    );
    pyttIPanne.addIngredient("Sausage", 1.0, "amount");
    pyttIPanne.addIngredient("Butter", 0.003, "kg");
    pyttIPanne.addIngredient("Potatoes", 0.2, "kg");
    pyttIPanne.addIngredient("Carrots", 0.5, "amount");
    pyttIPanne.addIngredient("Onions", 0.5, "amount");
    pyttIPanne.addIngredient("Salt", 0.002, "kg");
    pyttIPanne.addIngredient("Pepper", 0.002, "kg");
    cookbook.addRecipe(pyttIPanne);
  }
  /**
  *prints out all info on chosen recipe.
   */

  public void showRecipe() {
    System.out.println("Choose recipe to show:");
    String recipeName = scanner.nextLine();

    for (Recipe recipe : cookbook.getRecipes()) {
      if (recipe.getName().equalsIgnoreCase(recipeName)) {
        System.out.println("Portion size: " + recipe.getPortionSize()
            + "\n" + recipe.getPrettyString());
        return;
      }
    }
    System.out.println("Recipe Name: " + recipeName + " doesn't exist");
  }

  /**
  *checks if chosen recipe can be prepared with
  *the current groceries in the food storage.
   */

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
  /**
   * Displays the names of all recipes in the cookbook.
   */

  public void showCookbook() {
    System.out.println("Recipes in the cookbook:");
    if (cookbook.getRecipes().isEmpty()) {
      System.out.println("The cookbook is empty.\n");
    } else {
      for (Recipe recipe : cookbook.getRecipes()) {
        System.out.println("- " + recipe.getName());
      }
      System.out.println(); // Blank line for better formatting
    }
  }

  /**
  *a method which enables me to test addGrocery without having to use
  *scanner.
   */

  public void addGrocery(String name, double quantity, String unit,
      LocalDate expirationDate, double pricePerUnit) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Grocery name must not be null or empty.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0.");
    }
    if (unit == null || unit.isBlank()) {
      throw new IllegalArgumentException("Unit must not be null or empty.");
    }
    if (expirationDate == null || expirationDate.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Expiration date must not be null or in the past.");
    }
    if (pricePerUnit < 0) {
      throw new IllegalArgumentException("Price per unit must be 0 or greater.");
    }

    Grocery grocery = new Grocery(name, quantity, unit, expirationDate, pricePerUnit);
    foodStorage.addGrocery(grocery);
  }



}
