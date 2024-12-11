package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
  private String name;
  private String description;
  private String procedure;
  private int portionSize;
  private List<Grocery> ingredients; // Each ingredient is a Grocery-like object with quantity and unit

  // Constructor
  public Recipe(String name) {
    this.name = name;
    this.description = "";
    this.procedure = "";
    this.portionSize = 1; //default portion size if not specified
    this.ingredients = new ArrayList<>();
  }

  // Add an ingredient to the recipe
  public void addIngredient(String ingredientName, double quantity, String unit) {
    Grocery ingredient = new Grocery(ingredientName, quantity, unit, null, 0); // Expiration date and price are irrelevant here
    ingredients.add(ingredient);
  }

  public void addDescription(String description) {

  }

  public String getName() {
    return name;
  }

  // Setters and Getters for portion size
  public void setPortionSize(int portionSize) {
    this.portionSize = portionSize;
  }

  public int getPortionSize() {
    return portionSize;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // Add the procedure to the recipe
  public void setProcedure(String procedure) {
    this.procedure = procedure;
  }

  public String getProcedure() {
    return procedure;
  }

  public List<Grocery> getIngredients() {
    return ingredients;
  }

  public List<Grocery> scaleIngredients(int newPortionSize) {
    List<Grocery> scaledIngredients = new ArrayList<>();
    for (Grocery ingredient : ingredients) {
      double scaledQuantity = (ingredient.getQuantity() * newPortionSize) / portionSize;
      Grocery scaledIngredient = new Grocery(ingredient.getName(), scaledQuantity, ingredient.getUnit(), null, 0);
      scaledIngredients.add(scaledIngredient);
    }
    return scaledIngredients;
  }


  // Pretty string for displaying the recipe
  public String getPrettyString() {
    StringBuilder sb = new StringBuilder("Recipe: " + name + "\n");
    sb.append("Description: ").append(description).append("\n");
    sb.append("Ingredients:\n");
    for (Grocery ingredient : ingredients) {
      sb.append("- ").append(ingredient.getName()).append(": ").append(ingredient.getQuantity()).append(" ").append(ingredient.getUnit()).append("\n");
    }
    sb.append("Procedure:\n").append(procedure).append("\n");
    return sb.toString();
  }
}
