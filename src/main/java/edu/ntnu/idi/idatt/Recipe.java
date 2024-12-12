package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Recipe} class represents a recipe, which includes a name, description, procedure,
 * portion size, and a list of ingredients. It provides methods to manage and scale recipes.
 */
public class Recipe {

  /**
   * The name of the recipe.
   */
  private String name;

  /**
   * A description of the recipe.
   */
  private String description;

  /**
   * The procedure or steps to prepare the recipe.
   */
  private String procedure;

  /**
   * The portion size of the recipe, representing the default number of servings.
   */
  private int portionSize;

  /**
   * A list of ingredients required for the recipe.
   * Each ingredient is represented as a {@code Grocery}.
   */
  private List<Grocery> ingredients;

  /**
   * Constructs a {@code Recipe} with the specified name.
   * Initializes description and procedure as empty strings,
   * and sets the default portion size to 1.
   *
   * @param name the name of the recipe. Must not be {@code null} or blank.
   */
  public Recipe(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Recipe name must not be null or blank.");
    }
    this.name = name;
    this.description = "";
    this.procedure = "";
    this.portionSize = 1; // Default portion size
    this.ingredients = new ArrayList<>();
  }

  /**
   * Adds an ingredient to the recipe.
   *
   * @param ingredientName the name of the ingredient. Must not be {@code null} or blank.
   * @param quantity       the quantity of the ingredient. Must be greater than 0.
   * @param unit           the unit of measurement for the ingredient.
   *                       Must not be {@code null} or blank.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public void addIngredient(String ingredientName, double quantity, String unit) {
    if (ingredientName == null || ingredientName.isBlank()) {
      throw new IllegalArgumentException("Ingredient name must not be null or blank.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Ingredient quantity must be greater than zero.");
    }
    if (unit == null || unit.isBlank()) {
      throw new IllegalArgumentException("Ingredient unit must not be null or blank.");
    }
    Grocery ingredient = new Grocery(ingredientName, quantity, unit, null, 0);
    ingredients.add(ingredient);
  }

  /**
   * Retrieves the name of the recipe.
   *
   * @return the name of the recipe.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the portion size for the recipe.
   *
   * @param portionSize the new portion size. Must be greater than 0.
   */
  public void setPortionSize(int portionSize) {
    if (portionSize <= 0) {
      throw new IllegalArgumentException("Portion size must be greater than zero.");
    }
    this.portionSize = portionSize;
  }

  /**
   * Retrieves the portion size of the recipe.
   *
   * @return the portion size of the recipe.
   */
  public int getPortionSize() {
    return portionSize;
  }

  /**
   * Sets the description of the recipe.
   *
   * @param description a brief description of the recipe.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets the procedure for preparing the recipe.
   *
   * @param procedure the procedure or steps to prepare the recipe.
   */
  public void setProcedure(String procedure) {
    this.procedure = procedure;
  }

  /**
   * Retrieves the list of ingredients in the recipe.
   *
   * @return a {@code List} of {@code Grocery} objects representing the ingredients.
   */
  public List<Grocery> getIngredients() {
    return ingredients;
  }

  /**
   * Scales the ingredients in the recipe based on a new portion size.
   *
   * @param newPortionSize the new portion size. Must be greater than 0.
   * @return a scaled list of ingredients where
   * quantities are adjusted according to the new portion size.
   * @throws IllegalArgumentException if {@code newPortionSize} is less than or equal to 0.
   */
  public List<Grocery> scaleIngredients(int newPortionSize) {
    if (newPortionSize <= 0) {
      throw new IllegalArgumentException("New portion size must be greater than zero.");
    }

    List<Grocery> scaledIngredients = new ArrayList<>();
    for (Grocery ingredient : ingredients) {
      double scaledQuantity = (ingredient.getQuantity() * newPortionSize) / portionSize;
      Grocery scaledIngredient = new Grocery(ingredient.getName(),
          scaledQuantity, ingredient.getUnit(), null, 0);
      scaledIngredients.add(scaledIngredient);
    }
    return scaledIngredients;
  }

  /**
   * Formats the recipe into a readable string with its name,
   * description, ingredients, and procedure.
   *
   * @return a formatted string representation of the recipe.
   */
  public String getPrettyString() {
    StringBuilder sb = new StringBuilder("Recipe: " + name + "\n");
    sb.append("Description: ").append(description).append("\n");
    sb.append("Ingredients:\n");
    for (Grocery ingredient : ingredients) {
      sb.append("-"
          + " ").append(ingredient.getName()).append(":"
          + " ").append(ingredient.getQuantity()).append(" ")
          .append(ingredient.getUnit()).append("\n");
    }
    sb.append("Procedure:\n").append(procedure).append("\n");
    return sb.toString();
  }
}
