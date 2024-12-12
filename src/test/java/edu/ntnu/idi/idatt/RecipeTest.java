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

    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        recipe.addIngredient(null,1.0,"kg"));
    assertEquals("Recipe name should not be null", exception.getMessage());
  }

  @Test
  public void testAddIngredientNegativeQuantity() {
    Recipe recipe = new Recipe("Test Recipe");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Sugar", -1.5, "kg"));
    assertEquals("Recipe quantity cannot be zero or less",
        exception.getMessage());

  }

  @Test
  public void testAddIngredientNullUnit() {
    Recipe recipe = new Recipe("Test Recipe");
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        recipe.addIngredient("Sugar", 1.5, null));
    assertEquals("Recipe unit cannot be null", exception.getMessage());
  }

  @Test
  public void testPositiveGetName() {
    Recipe recipe = new Recipe("Test Recipe");
    assertEquals(recipe.getName(), "Test Recipe");
  }

  @Test
  public void testPositiveGetPortionSize() {
    Recipe recipe = new Recipe("Test Recipe");
    recipe.setPortionSize(1);
    assertEquals(recipe.getPortionSize(), 1);
  }

  @Test
  public void testGetIngredientsPositive() {
    // Arrange
    Recipe recipe = new Recipe("Test Recipe");
    recipe.addIngredient("Sugar", 1.5, "kg");
    recipe.addIngredient("Flour", 2.0, "kg");
    recipe.addIngredient("Eggs", 3, "amount");

    // Act
    List<Grocery> ingredients = recipe.getIngredients();

    // Assert
    assertEquals(3, ingredients.size(), "The ingredient list should contain 3 items.");

    assertEquals("Sugar", ingredients.get(0).getName(), "First ingredient name should be 'Sugar'.");
    assertEquals(1.5, ingredients.get(0).getQuantity(), "First ingredient quantity should be 1.5.");
    assertEquals("kg", ingredients.get(0).getUnit(), "First ingredient unit should be 'kg'.");

    assertEquals("Flour", ingredients.get(1).getName(), "Second ingredient name should be 'Flour'.");
    assertEquals(2.0, ingredients.get(1).getQuantity(), "Second ingredient quantity should be 2.0.");
    assertEquals("kg", ingredients.get(1).getUnit(), "Second ingredient unit should be 'kg'.");

    assertEquals("Eggs", ingredients.get(2).getName(), "Third ingredient name should be 'Eggs'.");
    assertEquals(3, ingredients.get(2).getQuantity(), "Third ingredient quantity should be 3.");
    assertEquals("amount", ingredients.get(2).getUnit(), "Third ingredient unit should be 'amount'.");
  }

  @Test
  public void testScaleIngredientsPositive() {
    // Arrange
    Recipe recipe = new Recipe("Test Recipe");
    recipe.setPortionSize(4); // Original portion size
    recipe.addIngredient("Sugar", 2.0, "kg"); // 2 kg for 4 portions
    recipe.addIngredient("Flour", 1.0, "kg"); // 1 kg for 4 portions

    // Act
    List<Grocery> scaledIngredients = recipe.scaleIngredients(8); // Scale to 8 portions

    // Assert
    assertEquals(2, scaledIngredients.size(), "The ingredient list should contain 2 items.");

    assertEquals("Sugar", scaledIngredients.get(0).getName(), "First ingredient name should be 'Sugar'.");
    assertEquals(4.0, scaledIngredients.get(0).getQuantity(), "Scaled quantity of Sugar should be 4.0.");
    assertEquals("kg", scaledIngredients.get(0).getUnit(), "Unit of Sugar should remain 'kg'.");

    assertEquals("Flour", scaledIngredients.get(1).getName(), "Second ingredient name should be 'Flour'.");
    assertEquals(2.0, scaledIngredients.get(1).getQuantity(), "Scaled quantity of Flour should be 2.0.");
    assertEquals("kg", scaledIngredients.get(1).getUnit(), "Unit of Flour should remain 'kg'.");
  }

  @Test
  public void testScaleIngredientsInvalidNewPortionSize() {
    // Arrange
    Recipe recipe = new Recipe("Invalid New Portion Recipe");
    recipe.setPortionSize(4); // Valid original portion size
    recipe.addIngredient("Butter", 1.0, "kg");

    // Act and Assert for newPortionSize = 0
    Exception exceptionZero = assertThrows(IllegalArgumentException.class, () -> {
      recipe.scaleIngredients(0); // Invalid new portion size
    });
    assertEquals("New portion size must be greater than zero.", exceptionZero.getMessage());

    // Act and Assert for newPortionSize = -1
    Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> {
      recipe.scaleIngredients(-1); // Invalid new portion size
    });
    assertEquals("New portion size must be greater than zero.", exceptionNegative.getMessage());
  }

  @Test
  public void testGetPrettyString() {
    // Arrange
    Recipe recipe = new Recipe("Pancakes");
    recipe.setDescription("A classic breakfast dish that is light and fluffy.");
    recipe.setPortionSize(4); // Portion size for 4 servings
    recipe.addIngredient("Flour", 2.0, "cups");
    recipe.addIngredient("Milk", 1.5, "cups");
    recipe.addIngredient("Eggs", 2.0, "amount");
    recipe.setProcedure(
        "1. Mix dry ingredients.\n" +
            "2. Add wet ingredients and mix into a smooth batter.\n" +
            "3. Cook on a skillet until golden brown."
    );
    // Expected formatted string
    String expected = "Recipe: Pancakes\n" +
        "Description: A classic breakfast dish that is light and fluffy.\n" +
        "Ingredients:\n" +
        "- Flour: 2.0 cups\n" +
        "- Milk: 1.5 cups\n" +
        "- Eggs: 2.0 amount\n" +
        "Procedure:\n" +
        "1. Mix dry ingredients.\n" +
        "2. Add wet ingredients and mix into a smooth batter.\n" +
        "3. Cook on a skillet until golden brown.\n";

    // Act
    String actual = recipe.getPrettyString();

    // Assert
    assertEquals(expected, actual, "The formatted recipe string should match the expected output.");
  }

  

}
