package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;
/*
import ArrayList and List
*/

/*
create class recipe
*/

public class Recipe {
  private String name;
  private String description;
  private String procedure;
  private int portionSize;
  private List<Grocery> ingredients;
/*
Each ingredient is a Grocery-like object with quantity and unit
*/


/*
create constructor
*/
  public Recipe(String name) {
    this.name = name;
    this.description = "";
    this.procedure = "";
    this.portionSize = 1; //default portion size if not specified
    this.ingredients = new ArrayList<>();
  }
/*
Add an ingredient to the recipe
*/
  public void addIngredient(String ingredientName, double quantity, String unit) {
    if (ingredientName == null || ingredientName.isBlank()) {
      throw new IllegalArgumentException("Recipe name should not be null");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Recipe quantity cannot be zero or less");
    }
    if (unit == null || unit.isBlank()) {
      throw new IllegalArgumentException("Recipe unit cannot be null");
    }
    Grocery ingredient = new Grocery(ingredientName, quantity, unit, null, 0); // Expiration date and price are irrelevant here
    ingredients.add(ingredient);
  }


/*
getter for name which is in recipe
*/
  public String getName() {
    return name;
  }

  // Setters and Getters for portion size
/*
Setter and getter for portion size which is in recipie
*/
  public void setPortionSize(int portionSize) {
    this.portionSize = portionSize;
  }

  public int getPortionSize() {
    return portionSize;
  }

/*
setter for description of recipe
*/
  public void setDescription(String description) {
    this.description = description;
  }

/*
setter for procedure of recipe
*/
  public void setProcedure(String procedure) {
    this.procedure = procedure;
  }
/*
getter for ingredients
*/
  public List<Grocery> getIngredients() {
    return ingredients;
  }
/*
method to scale ingredients according to portion sizes
*/
  public List<Grocery> scaleIngredients(int newPortionSize) {
    if (newPortionSize <= 0) {
      throw new IllegalArgumentException("New portion size must be greater than zero.");
    }

    List<Grocery> scaledIngredients = new ArrayList<>();
    for (Grocery ingredient : ingredients) {
      double scaledQuantity = (ingredient.getQuantity() * newPortionSize) / portionSize;
      Grocery scaledIngredient = new Grocery(ingredient.getName(), scaledQuantity, ingredient.getUnit(), null, 0);
      scaledIngredients.add(scaledIngredient);
    }
    return scaledIngredients;
  }

/*
We were not supposed to use @Overide toString,
so had to create my own version to make the recipes look better than the standard list setup
*/
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
