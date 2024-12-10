package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;

public class Cookbook {
  // Instance variable
  private final List<Recipe> recipes;

  // Constructor
  public Cookbook() {
    this.recipes = new ArrayList<Recipe>();
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }

  // Add a new recipe to the cookbook
  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }

  // List all recipes in the cookbook
  public void listRecipes() {
    if (recipes.isEmpty()) {
      System.out.println("No recipes available in the cookbook.");
    } else {
      for (Recipe recipe : recipes) {
        System.out.println(recipe);
      }
    }
  }

}
