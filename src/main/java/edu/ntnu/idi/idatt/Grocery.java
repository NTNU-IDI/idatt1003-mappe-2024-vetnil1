package edu.ntnu.idi.idatt;
import java.time.LocalDate;
/*
imports LocalDate to be able to extract the current date when the program starts
*/
/*
create Grocery class
*/

public class Grocery {
  private String name;
  private double quantity;
  private String unit;
  private LocalDate expirationDate;
  private double pricePerUnit;

/*
Grocery constructor
*/
  public Grocery(String name, double quantity, String unit, LocalDate expirationDate, double pricePerUnit) {
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.expirationDate = expirationDate;
    this.pricePerUnit = pricePerUnit;
  }
/*
boolean method to check if the current applied date is after expiration date of a grocery
*/
  public boolean isExpired(LocalDate currentDate) {
    return currentDate.isAfter(expirationDate);
  }
/*
method to calculate total value of a stored grocery,
by multiplying the current quantity of a grocery and the price per unit of this grocery
for example:
you have 2 liters of milk in your fridge, and the price per liter is 10kr/liter
this means the total value of the milk in your fridge is 20kr
*/
  public double getTotalValue() {
    return quantity * pricePerUnit;
  }

/*
a couple different getters and setters
there are some throws in a couple of the setters
this is to guide the user of the program to understand
what went wrong if they write something the program doesn't like
 */
  public String getName() {
    return name;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    if(quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    if(unit == null) {
      throw new IllegalArgumentException("Unit cannot be null");
    }
    this.unit = unit;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    if(expirationDate == null) {
      throw new IllegalArgumentException("Expiration date cannot be null");
    }
    this.expirationDate = expirationDate;
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  public void setPricePerUnit(double pricePerUnit) {
    if(pricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be greater than zero");
    }
    this.pricePerUnit = pricePerUnit;
  }


}
