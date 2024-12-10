package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
  private String name;
  private List<Grocery> ingredients; // Each ingredient is a Grocery-like object with quantity and unit

  // Constructor
  public Recipe(String name) {
    this.name = name;
    this.ingredients = new ArrayList<>();
  }

  // Add an ingredient to the recipe
  public void addIngredient(String ingredientName, double quantity, String unit) {
    Grocery ingredient = new Grocery(ingredientName, quantity, unit, null, 0); // Expiration date and price are irrelevant here
    ingredients.add(ingredient);
  }

  public String getName() {
    return name;
  }

  public List<Grocery> getIngredients() {
    return ingredients;
  }

  public List<Grocery> scaleIngredients(int desiredPortionSize) {
    List<Grocery> scaledIngredients = new ArrayList<>();
    for (Grocery ingredient : ingredients) {
      double scaledQuantity = ingredient.getQuantity() * desiredPortionSize;
      Grocery scaledIngredient = new Grocery(ingredient.getName(), scaledQuantity, ingredient.getUnit(), null, 0);
      scaledIngredients.add(scaledIngredient);
    }
    return scaledIngredients;
  }


  public String getPrettyString() {
    StringBuilder sb = new StringBuilder("Recipe: " + name + "\nIngredients:\n");
    for (Grocery ingredient : ingredients) {
      sb.append("- ").append(ingredient.getName()).append(": ").append(ingredient.getQuantity()).append(" ").append(ingredient.getUnit()).append("\n");
    }
    return sb.toString();
  }
}
