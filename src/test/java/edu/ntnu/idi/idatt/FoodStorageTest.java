package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@code FoodStorage} class.
 * These tests verify the behavior of methods that manage groceries,
 * including adding, removing, retrieving, and calculating values.
 */
public class FoodStorageTest {

  /**
   * Tests that adding a grocery to the food storage updates the size of the grocery list
   * and ensures that the correct grocery name is stored.
   */
  @Test
  public void testAddGrocerySizeName() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);

    // Act
    foodStorage.addGrocery(grocery);

    // Assert
    assertEquals(1, foodStorage.getGroceries().size(), "The grocery list should contain one item.");
    assertEquals("Milk", foodStorage.getGroceries().get(0).getName(), "The added grocery should be 'Milk'.");
  }

  /**
   * Tests that removing a specific amount of a grocery reduces the quantity correctly.
   */
  @Test
  public void testRemoveGroceriesPartialPositive() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act
    foodStorage.removeGroceries("Milk", 1.0);

    // Assert
    assertEquals(1.0, foodStorage.getGroceries().get(0).getQuantity(),
        "The quantity of 'Milk' should be reduced to 1.0.");
  }

  /**
   * Tests that attempting to remove a negative amount of a grocery throws an exception.
   */
  @Test
  public void testRemoveGroceriesPartialNegative() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodStorage.removeGroceries("Milk", -1.0); // Invalid negative amount
    });

    // Assert Exception Message
    assertEquals("Quantity to remove must be greater than 0.", exception.getMessage(),
        "The exception message should indicate that the quantity must be greater than 0.");
  }

  /**
   * Tests that retrieving an existing grocery by name returns the correct grocery object.
   */
  @Test
  public void testGetGroceryExisting() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery grocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2024, 12, 31), 10.0);
    foodStorage.addGrocery(grocery);

    // Act
    Grocery result = foodStorage.getGrocery("Milk");

    // Assert
    assertNotNull(result, "The returned grocery should not be null.");
    assertEquals("Milk", result.getName(), "The name of the retrieved grocery should be 'Milk'.");
  }

  /**
   * Tests that retrieving a non-existent grocery by name returns {@code null}.
   */
  @Test
  public void testGetGroceryNonExistent() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();

    // Act
    Grocery result = foodStorage.getGrocery("Eggs");

    // Assert
    assertNull(result, "The returned grocery should be null for a non-existent grocery.");
  }

  /**
   * Tests that expired groceries are listed correctly and non-expired groceries are not included in the list.
   */
  @Test
  public void testListExpiredGroceries() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery expiredGrocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2022, 12, 31), 10.0);
    Grocery nonExpiredGrocery = new Grocery("Eggs", 12.0, "amounts", LocalDate.of(2024, 12, 31), 3.0);
    foodStorage.addGrocery(expiredGrocery);
    foodStorage.addGrocery(nonExpiredGrocery);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Act
    foodStorage.listExpiredGroceries(LocalDate.now());

    // Assert
    String output = outputStream.toString();
    assertTrue(output.contains("Milk"), "Expired grocery 'Milk' should be listed.");
    assertFalse(output.contains("Eggs"), "Non-expired grocery 'Eggs' should not be listed.");
  }

  /**
   * Tests that the total value of non-expired groceries is calculated correctly.
   */
  @Test
  public void testGetTotalValue() {
    // Arrange
    FoodStorage foodStorage = new FoodStorage();
    Grocery expiredGrocery = new Grocery("Milk", 2.0, "liters", LocalDate.of(2022, 12, 31), 10.0);
    Grocery nonExpiredGrocery = new Grocery("Eggs", 12.0, "amounts", LocalDate.of(2024, 12, 31), 3.0);
    foodStorage.addGrocery(expiredGrocery);
    foodStorage.addGrocery(nonExpiredGrocery);

    // Act
    double totalValue = foodStorage.getTotalValue(LocalDate.now());

    // Assert
    assertEquals(36.0, totalValue, "The total value of non-expired groceries should be 36.0 NOK.");
  }
}
