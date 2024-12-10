package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodStorage {
  private List<Grocery> groceries = new ArrayList<>();
  private List<Recipe> cookbook = new ArrayList<>();


  public FoodStorage() {
    // Pre-set items
    groceries.add(new Grocery("Milk", 1.0, "liters", LocalDate.of(2024, 12, 20), 10.0));
    groceries.add(new Grocery("Eggs", 12.0, "amounts", LocalDate.of(2024, 12, 31), 3.0));
    groceries.add(new Grocery("Flour", 1.0, "kg", LocalDate.of(2025, 8, 22), 10.0));
    groceries.add(new Grocery("Sugar", 1.0, "kg", LocalDate.of(2025, 3, 13), 35.0));
    groceries.add(new Grocery("Salt", 1.0, "kg", LocalDate.of(2026, 6, 14), 25.0));
    groceries.add(new Grocery("Pepper", 0.1, "kg", LocalDate.of(2025, 5, 15), 55.0));
    groceries.add(new Grocery("Butter", 0.5, "kg", LocalDate.of(2024, 12, 26), 50.0));

    // Pre-set recipes in the cookbook
    Recipe pancakes = new Recipe("Pancakes");
    pancakes.addIngredient("Butter", 0.025, "kg");
    pancakes.addIngredient("Milk", 0.1, "liter");
    pancakes.addIngredient("Eggs", 1.0, "amount");
    pancakes.addIngredient("Flour", 0.1, "kg");
    pancakes.addIngredient("Baking Soda", 0.005, "kg");
    pancakes.addIngredient("Salt", 0.001, "kg");
    pancakes.addIngredient("Sugar", 0.01, "kg \n");
    cookbook.add(pancakes);

    Recipe omelette = new Recipe("Omelette");
    omelette.addIngredient("Eggs", 2.0, "amount");
    omelette.addIngredient("Milk", 0.03, "liter");
    omelette.addIngredient("Salt", 0.002, "kg");
    omelette.addIngredient("Pepper", 0.002, "kg");
    omelette.addIngredient("Butter", 0.0015, "kg \n");
    cookbook.add(omelette);

    Recipe pyttIPanne = new Recipe("Pytt i Panne");
    pyttIPanne.addIngredient("Sausage", 1.0, "amount");
    pyttIPanne.addIngredient("Butter", 0.003, "kg");
    pyttIPanne.addIngredient("Potatoes", 0.2, "kg");
    pyttIPanne.addIngredient("Carrots", 0.5, "amount");
    pyttIPanne.addIngredient("Onions", 0.5, "amount");
    pyttIPanne.addIngredient("Salt", 0.002, "kg");
    pyttIPanne.addIngredient("Pepper", 0.002, "kg \n");
    cookbook.add(pyttIPanne);
  }

  public List<Grocery> getGroceries() {
    return groceries;
  }

  public void addGroceries(Grocery grocery) {
    groceries.add(grocery); // Simply add the new grocery to the list
  }

  public void removeGroceries(String name, double amount) {
    Iterator<Grocery> iterator = groceries.iterator();
    while (iterator.hasNext()) {
      Grocery grocery = iterator.next();
      if (grocery.getName().equalsIgnoreCase(name)) {
        double remaining = grocery.getQuantity() - amount;
        if (remaining > 0) {
          grocery.setQuantity(remaining);
          return;
        } else {
          iterator.remove();
          return;
        }
      }
    }
    System.out.println("Grocery not found.");
  }

  public void removeGroceryCompletely(String name) {
    groceries.removeIf(grocery -> grocery.getName().equalsIgnoreCase(name));
  }

  public void listGroceries(LocalDate currentDate) {
    boolean hasExpiredItems = false;

    for (Grocery grocery : groceries) {
      if (grocery.isExpired(currentDate)) {
        hasExpiredItems = true;
      } else {
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit() +
            ", Expiration Date: " + grocery.getExpirationDate() + ", Price per Unit: " + grocery.getPricePerUnit() + " NOK \n");
      }
    }

    if (hasExpiredItems) {
      System.out.println("Warning: There are expired food items in the food storage!");
    }
  }


  public void listExpiredGroceries(LocalDate currentDate) {
    double totalExpiredValue = 0.0;
    boolean hasExpiredItems = false;

    for (Grocery grocery : groceries) {
      if (grocery.isExpired(currentDate)) {
        hasExpiredItems = true;
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit() +
            ", Expired on: " + grocery.getExpirationDate());
        totalExpiredValue += grocery.getTotalValue();
      }
    }

    if (hasExpiredItems) {
      System.out.println("Total value of expired groceries: " + totalExpiredValue + " NOK");
    } else {
      System.out.println("No expired groceries in storage.");
    }
  }

  public double getTotalValue(LocalDate currentDate) {
    double totalValue = 0.0;

    for (Grocery grocery : groceries) {
      if (!grocery.isExpired(currentDate)) {
        totalValue += grocery.getTotalValue();
      }
    }
    return totalValue;
  }

  public Grocery getGrocery(String name) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(name)) {
        return grocery;
      }
    }
    return null;
  }
  public void searchGroceryByName(String name) {
    boolean found = false;

    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(name)) {
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit() +
            ", Expiration Date: " + grocery.getExpirationDate() + ", Price per Unit: " + grocery.getPricePerUnit() + " NOK \n");
        found = true;
      }
    }

    if (!found) {
      System.out.println("No grocery found with the name \"" + name + "\" in the storage. \n");
    }
  }

  public List<Recipe> getCookbook() {
    return cookbook;
  }

  // Add a new recipe to the cookbook
  public void addRecipe(Recipe recipe) {
    cookbook.add(recipe);
  }

  // Check if FoodStorage contains enough ingredients for a recipe
  public boolean canPrepareRecipe(Recipe recipe) {
    for (Grocery ingredient : recipe.getIngredients()) {
      double requiredQuantity = ingredient.getQuantity();
      double availableQuantity = 0;

      // Sum up quantities of matching items in FoodStorage
      for (Grocery storedItem : groceries) {
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

  // Suggest recipes based on available ingredients
  public List<Recipe> suggestRecipes() {
    List<Recipe> possibleRecipes = new ArrayList<>();
    for (Recipe recipe : cookbook) {
      if (canPrepareRecipe(recipe)) {
        possibleRecipes.add(recipe);
      }
    }
    return possibleRecipes;
  }

  // List all recipes in the cookbook
  public void listRecipes() {
    if (cookbook.isEmpty()) {
      System.out.println("No recipes available in the cookbook.");
    } else {
      for (Recipe recipe : cookbook) {
        System.out.println(recipe);
      }
    }
  }


}
