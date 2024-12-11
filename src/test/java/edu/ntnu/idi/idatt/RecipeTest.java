package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class RecipeTest {
  @Test
  public void testAddIngredientPositive() {
    Recipe recipe = new Recipe("Test Recipe");

    recipe.addIngredient("Sugar", 1.5, "kg");

    List<Grocery> ingredients = recipe.getIngredients();

    assertEquals(1, ingredients.size(),
        "Ingredient list should contain 1 ingredient");
    Grocery addedIngredient = ingredients.getFirst();
    assertEquals("Sugar", addedIngredient.getName(),
        "Ingredient name should match");
    assertEquals(1.5, addedIngredient.getQuantity(),
        "Ingredient quantity should match");
    assertEquals("kg", addedIngredient.getUnit(),
        "Ingredient unit should match");

  }
  @Test
  public void testAddIngredientNullName() {
    Recipe recipe = new Recipe("Test Recipe");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient(null,1.0,"kg");

    });
    assertEquals("Recipe name should not be null", exception.getMessage());
  }

  @Test
  public void testAddIngredientNegativeQuantity() {
    Recipe recipe = new Recipe("Test Recipe");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("Sugar", -1.5, "kg");
    });
    assertEquals("Recipe quantity cannot be zero or less",
        exception.getMessage());

  }

  @Test
  public void testAddIngredientNullUnit() {
    Recipe recipe = new Recipe("Test Recipe");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("Sugar", 1.5, null);
    });
    assertEquals("Recipe unit cannot be null", exception.getMessage());
  }

}
