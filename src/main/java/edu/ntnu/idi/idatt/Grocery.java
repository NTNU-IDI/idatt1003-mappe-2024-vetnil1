package edu.ntnu.idi.idatt;
import java.time.LocalDate;

public class Grocery {
  private String name;
  private double quantity;
  private String unit;
  private LocalDate expirationDate;
  private double pricePerUnit;

  public Grocery(String name, double quantity, String unit, LocalDate expirationDate, double pricePerUnit) {
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.expirationDate = expirationDate;
    this.pricePerUnit = pricePerUnit;
  }

  public boolean isExpired(LocalDate currentDate) {
    return currentDate.isAfter(expirationDate);
  }

  public double getTotalValue() {
    return quantity * pricePerUnit;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  public void setPricePerUnit(double pricePerUnit) {
    this.pricePerUnit = pricePerUnit;
  }


}
