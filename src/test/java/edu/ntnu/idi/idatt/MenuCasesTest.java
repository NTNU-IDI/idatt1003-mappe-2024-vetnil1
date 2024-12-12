package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@code MenuCases} class.
 * These tests verify the behavior of methods for managing groceries and recipes through menu cases.
 */
public class MenuCasesTest {

  /**
   * Tests that the current date is successfully updated to a new valid date.
   */
  @Test
  public void testSetCurrentDatePositive() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    LocalDate initialDate = LocalDate.of(2023, 1, 1);
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, initialDate, null);

    LocalDate newDate = LocalDate.of(2023, 12, 25);

    // Act
    menuCases.setCurrentDate(newDate);

    // Assert
    assertEquals(newDate, menuCases.currentDate, "The current date should be updated to the new date.");
  }

  /**
   * Tests that setting the current date to {@code null} throws an exception.
   */
  @Test
  public void testSetCurrentDateNegativeNull() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    LocalDate initialDate = LocalDate.of(2023, 1, 1);
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, initialDate, null);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      menuCases.setCurrentDate(null);
    });

    assertEquals("Date cannot be null", exception.getMessage());
  }

  /**
   * Tests adding a grocery with valid parameters updates the food storage correctly.
   */
  @Test
  public void testAddGroceryPositive() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    LocalDate currentDate = LocalDate.now();
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, currentDate, null);

    // Act
    menuCases.addGrocery("Milk", 2.5, "liters", LocalDate.of(2024, 12, 31), 15.0);

    // Assert
    assertEquals(1, foodStorage.getGroceries().size(), "The grocery list should contain one item.");
    Grocery addedGrocery = foodStorage.getGroceries().get(0);

    assertEquals("Milk", addedGrocery.getName(), "The name of the grocery should be 'Milk'.");
    assertEquals(2.5, addedGrocery.getQuantity(), "The quantity of the grocery should be 2.5.");
    assertEquals("liters", addedGrocery.getUnit(), "The unit of the grocery should be 'liters'.");
    assertEquals(LocalDate.of(2024, 12, 31), addedGrocery.getExpirationDate(),
        "The expiration date should be 2024-12-31.");
    assertEquals(15.0, addedGrocery.getPricePerUnit(), "The price per unit should be 15.0.");
  }

  /**
   * Tests that adding a grocery with an empty name throws an exception.
   */
  @Test
  public void testAddGroceryWithEmptyName() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    LocalDate currentDate = LocalDate.now();
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, currentDate, null);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      menuCases.addGrocery("", 2.5, "liters", LocalDate.of(2024, 12, 31), 15.0);
    });

    assertEquals("Grocery name must not be null or empty.", exception.getMessage());
  }

  /**
   * Tests that adding a grocery with a negative quantity throws an exception.
   */
  @Test
  public void testAddGroceryWithNegativeQuantity() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    LocalDate currentDate = LocalDate.now();
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, currentDate, null);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      menuCases.addGrocery("Milk", -1.0, "liters", LocalDate.of(2024, 12, 31), 15.0);
    });

    assertEquals("Quantity must be greater than 0.", exception.getMessage());
  }

  /**
   * Tests that adding a grocery with a past expiration date throws an exception.
   */
  @Test
  public void testAddGroceryWithPastExpirationDate() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Cookbook cookbook = new Cookbook();
    LocalDate currentDate = LocalDate.now();
    MenuCases menuCases = new MenuCases(foodStorage, cookbook, currentDate, null);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      menuCases.addGrocery("Milk", 2.5, "liters", LocalDate.of(2022, 12, 31), 15.0);
    });

    assertEquals("Expiration date must not be null or in the past.", exception.getMessage());
  }

  /**
   * Tests that removing a grocery reduces its quantity when a partial amount is removed.
   */
  @Test
  public void testRemoveGroceriesPartialQuantity() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act
    foodStorage.removeGroceries("Milk", 1.0);

    // Assert
    assertEquals(1.0, foodStorage.getGroceries().get(0).getQuantity(),
        "The quantity of 'Milk' should be reduced to 1.0 liters.");
  }

  /**
   * Tests that attempting to remove a grocery completely removes it from the food storage.
   */
  @Test
  public void testRemoveGroceriesEntireQuantity() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act
    foodStorage.removeGroceries("Milk", 2.0);

    // Assert
    assertTrue(foodStorage.getGroceries().isEmpty(),
        "The grocery list should be empty after removing the entire quantity.");
  }

  /**
   * Tests that removing a grocery with a quantity greater than available does not alter the stored quantity.
   */
  @Test
  public void testRemoveGroceriesExceedsQuantity() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act
    foodStorage.removeGroceries("Milk", 3.0);

    // Assert
    assertEquals(2.0, foodStorage.getGroceries().get(0).getQuantity(),
        "The quantity of 'Milk' should remain unchanged when attempting to remove more than available.");
  }

  /**
   * Tests that attempting to remove a non-existent grocery does not alter the food storage.
   */
  @Test
  public void testRemoveNonExistentGrocery() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act
    foodStorage.removeGroceries("Eggs", 1.0);

    // Assert
    assertEquals(1, foodStorage.getGroceries().size(),
        "The grocery list should remain unchanged when attempting to remove a non-existent grocery.");
  }

  /**
   * Tests that attempting to remove a grocery with an invalid quantity throws an exception.
   */
  @Test
  public void testRemoveGroceriesInvalidQuantity() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodStorage.removeGroceries("Milk", -1.0); // Invalid quantity
    });

    assertEquals("Quantity to remove must be greater than 0.", exception.getMessage(),
        "The exception message should indicate that the quantity must be greater than 0.");
  }
}
