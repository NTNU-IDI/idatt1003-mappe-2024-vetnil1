package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@code Grocery} class.
 * These tests verify the behavior of getters, setters, and the creation of {@code Grocery} objects.
 */
public class GroceryTest {

  /**
   * Verifies that a {@code Grocery} object is correctly created with valid parameters
   * and that its getters return the expected values.
   */
  @Test
  @DisplayName("Test creating a grocery with valid parameters and return correct values")
  public void testPositiveGroceryGetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);

    assertEquals("Flour", grocery.getName());
    assertEquals(5, grocery.getQuantity());
    assertEquals("kg", grocery.getUnit());
    assertEquals(LocalDate.of(2024, 12, 20), grocery.getExpirationDate());
    assertEquals(10.0, grocery.getPricePerUnit());
  }

  /**
   * A deliberately failing test to verify negative outcomes for {@code Grocery} getters.
   * Demonstrates incorrect expectations.
   */
  @Test
  public void testNegativeGroceryGetName() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);

    assertNotEquals("Sugar", grocery.getName());
    assertNotEquals(4, grocery.getQuantity());
    assertNotEquals("liter", grocery.getUnit());
    assertNotEquals(LocalDate.of(2023, 12, 20), grocery.getExpirationDate());
    assertNotEquals(1.0, grocery.getPricePerUnit());
  }

  /**
   * Tests that the quantity of a grocery is greater than zero.
   * Fails if the quantity is invalid.
   */
  @Test
  public void testNegativeGroceryGetQuantity() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    if (grocery.getQuantity() <= 0) {
      fail("The grocery does not have enough quantity");
    }
  }

  /**
   * Tests that the unit of a grocery is as expected.
   * Fails if the unit does not match.
   */
  @Test
  public void testNegativeGroceryGetUnit() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    if (!grocery.getUnit().equals("kg")) {
      fail();
    }
  }

  /**
   * Tests that the expiration date of a grocery matches the expected value.
   * Fails if the expiration date is incorrect.
   */
  @Test
  public void testNegativeGroceryGetExpirationDate() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    if (!grocery.getExpirationDate().isEqual(LocalDate.of(2024, 12, 20))) {
      fail();
    }
  }

  /**
   * Verifies that setters for {@code Grocery} correctly update fields
   * with valid values.
   */
  @Test
  @DisplayName("Tests positive Grocery setters")
  public void testPositiveGrocerySetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    grocery.setQuantity(3);
    grocery.setUnit("liter");
    grocery.setExpirationDate(LocalDate.of(2024, 12, 10));
    grocery.setPricePerUnit(1.0);

    assertEquals(3, grocery.getQuantity());
    assertEquals("liter", grocery.getUnit());
    assertEquals(LocalDate.of(2024, 12, 10), grocery.getExpirationDate());
    assertEquals(1.0, grocery.getPricePerUnit());
  }

  /**
   * Verifies that attempting to set an invalid quantity throws an exception.
   */
  @Test
  public void testNegativeGroceryQuantitySetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    grocery.setQuantity(3);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setQuantity(-5.0); // Attempt to set a negative quantity
    });

    assertEquals("Quantity must be greater than zero", exception.getMessage());
  }

  /**
   * Verifies that attempting to set an invalid unit throws an exception.
   */
  @Test
  public void testNegativeGroceryUnitSetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    grocery.setUnit("liter");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setUnit(null);
    });

    assertEquals("Unit cannot be null", exception.getMessage());
  }

  /**
   * Verifies that attempting to set an invalid expiration date throws an exception.
   */
  @Test
  public void testNegativeGroceryExpirationDateSetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    grocery.setExpirationDate(LocalDate.of(2024, 10, 10));

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setExpirationDate(null);
    });

    assertEquals("Expiration date cannot be null", exception.getMessage());
  }

  /**
   * Verifies that attempting to set an invalid price per unit throws an exception.
   */
  @Test
  public void testNegativeGroceryPricePerUnitSetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024, 12, 20), 10.0);
    grocery.setPricePerUnit(1.0);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setPricePerUnit(-10.0);
    });

    assertEquals("Price per unit must be greater than zero", exception.getMessage());
  }
}
