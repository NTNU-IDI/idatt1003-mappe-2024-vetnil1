package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Cookbook} class represents a collection of recipes. It provides functionality
 * to manage and access a list of recipes.
 */
public class Cookbook {
  /**
   * The list of recipes stored in the cookbook.
   */
  private final List<Recipe> recipes;

  /**
   * Constructs an empty {@code Cookbook} with no recipes.
   */
  public Cookbook() {
    this.recipes = new ArrayList<>();
  }

  /**
   * Retrieves the list of recipes in the cookbook.
   *
   * @return a {@code List} containing all recipes in the cookbook.
   */
  public List<Recipe> getRecipes() {
    return recipes;
  }

  /**
   * Adds a new recipe to the cookbook.
   *
   * @param recipe the {@code Recipe} to add to the cookbook. Must not be {@code null}.
   */
  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }
}
