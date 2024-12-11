package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GroceryTest {
  @Test
  @DisplayName("Test creating a grocery with valid parameters,"
      + " and return correct values")
  public void testPositiveGroceryGetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);

    assertEquals("Flour", grocery.getName());
    assertEquals(5, grocery.getQuantity());
    assertEquals("kg", grocery.getUnit());
    assertEquals(LocalDate.of(2024,12,20), grocery.getExpirationDate());
    assertEquals(10.0, grocery.getPricePerUnit());
  }
  @Test
  public void testNegativeGroceryGetName() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);

    assertEquals("Sugar", grocery.getName());
    assertEquals(4, grocery.getQuantity());
    assertEquals("liter", grocery.getUnit());
    assertEquals(LocalDate.of(2023,12,20), grocery.getExpirationDate());
    assertEquals(1.0, grocery.getPricePerUnit());
  }

  @Test
  public void testNegativeGroceryGetQuantity() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    if (grocery.getQuantity() <= 0) {
      fail("The grocery does not have enough quantity");
    }

  }

  @Test
  public void testNegativeGroceryGetUnit() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    if (!grocery.getUnit().equals("kg")) {
      fail();
    }
  }

  @Test
  public void testNegativeGroceryGetExpirationDate() {
    Grocery grocery = new Grocery("Flour", 5, "kg", LocalDate.of(2024,12,20), 10.0);
    if(!grocery.getExpirationDate().isEqual(LocalDate.of(2024,12,20))) {
      fail();
    }
  }

  @Test
  @DisplayName("Tests positive Grocery setters")
  public void testPositiveGrocerySetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    grocery.setQuantity(3);
    grocery.setUnit("liter");
    grocery.setExpirationDate(LocalDate.of(2024,12,10));
    grocery.setPricePerUnit(1.0);
    assertEquals(3, grocery.getQuantity());
    assertEquals("liter", grocery.getUnit());
    assertEquals(LocalDate.of(2024,12,10), grocery.getExpirationDate());
    assertEquals(1.0, grocery.getPricePerUnit());
  }
  @Test
  public void testNegativeGroceryQuantitySetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    grocery.setQuantity(3);

    // Act and Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setQuantity(-5.0); // Attempt to set a negative quantity
    });

    // Verify that the exception message is correct
    assertEquals("Quantity must be greater than zero", exception.getMessage());

  }

  @Test
  public void testNegativeGroceryUnitSetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    grocery.setUnit("liter");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setUnit(null);
    });
    assertEquals("Unit cannot be null", exception.getMessage());
  }

  @Test
  public void testNegativeGroceryExpirationDateSetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    grocery.setExpirationDate(LocalDate.of(2024, 10, 10));
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setExpirationDate(null);
    });
    assertEquals("Expiration date cannot be null", exception.getMessage());
  }

  @Test
  public void testNegativeGroceryPricePerUnitSetters() {
    Grocery grocery = new Grocery("Flour", 5, "kg",
        LocalDate.of(2024, 12, 20), 10.0);
    grocery.setPricePerUnit(1.0);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      grocery.setPricePerUnit(-10.0);
    });
    assertEquals("Price per unit must be greater than zero", exception.getMessage());
  }

}
