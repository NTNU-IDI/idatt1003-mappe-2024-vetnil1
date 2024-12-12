package edu.ntnu.idi.idatt;

import java.time.LocalDate;

/**
 * The {@code Grocery} class represents an item of food or similar products stored in a
 * food storage system.
 * It includes details such as name, quantity, unit, expiration date, and price per unit.
 */
public class Grocery {
  /**
   * The name of the grocery item.
   */
  private String name;

  /**
   * The quantity of the grocery item.
   */
  private double quantity;

  /**
   * The unit of measurement for the grocery item (e.g., liters, kg, pieces).
   */
  private String unit;

  /**
   * The expiration date of the grocery item.
   */
  private LocalDate expirationDate;

  /**
   * The price per unit of the grocery item.
   */
  private double pricePerUnit;

  /**
   * Constructs a new {@code Grocery} with the specified details.
   *
   * @param name           the name of the grocery item.
   * @param quantity       the quantity of the grocery item.
   * @param unit           the unit of measurement for the grocery item.
   * @param expirationDate the expiration date of the grocery item.
   * @param pricePerUnit   the price per unit of the grocery item.
   */
  public Grocery(String name, double quantity, String unit,
      LocalDate expirationDate, double pricePerUnit) {
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.expirationDate = expirationDate;
    this.pricePerUnit = pricePerUnit;
  }

  /**
   * Checks if the grocery item is expired based on the current date.
   *
   * @param currentDate the current date to compare with the grocery's expiration date.
   * @return {@code true} if the grocery is expired; {@code false} otherwise.
   */
  public boolean isExpired(LocalDate currentDate) {
    return currentDate.isAfter(expirationDate);
  }

  /**
   * Calculates the total value of the grocery item based on its quantity and price per unit.
   *
   * @return the total value of the grocery item.
   */
  public double getTotalValue() {
    return quantity * pricePerUnit;
  }

  /**
   * Retrieves the name of the grocery item.
   *
   * @return the name of the grocery item.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the quantity of the grocery item.
   *
   * @return the quantity of the grocery item.
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the grocery item.
   *
   * @param quantity the new quantity. Must be greater than 0.
   * @throws IllegalArgumentException if the quantity is less than or equal to 0.
   */
  public void setQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }
    this.quantity = quantity;
  }

  /**
   * Retrieves the unit of measurement for the grocery item.
   *
   * @return the unit of measurement.
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Sets the unit of measurement for the grocery item.
   *
   * @param unit the new unit of measurement. Must not be {@code null}.
   * @throws IllegalArgumentException if the unit is {@code null}.
   */
  public void setUnit(String unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null");
    }
    this.unit = unit;
  }

  /**
   * Retrieves the expiration date of the grocery item.
   *
   * @return the expiration date.
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the expiration date for the grocery item.
   *
   * @param expirationDate the new expiration date. Must not be {@code null}.
   * @throws IllegalArgumentException if the expiration date is {@code null}.
   */
  public void setExpirationDate(LocalDate expirationDate) {
    if (expirationDate == null) {
      throw new IllegalArgumentException("Expiration date cannot be null");
    }
    this.expirationDate = expirationDate;
  }

  /**
   * Retrieves the price per unit of the grocery item.
   *
   * @return the price per unit.
   */
  public double getPricePerUnit() {
    return pricePerUnit;
  }

  /**
   * Sets the price per unit for the grocery item.
   *
   * @param pricePerUnit the new price per unit. Must be greater than 0.
   * @throws IllegalArgumentException if the price per unit is less than or equal to 0.
   */
  public void setPricePerUnit(double pricePerUnit) {
    if (pricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be greater than zero");
    }
    this.pricePerUnit = pricePerUnit;
  }
}
