package edu.ntnu.idi.idatt;
import java.util.HashMap;
import java.util.Map;

public class FoodStorage {
  private Map<String, Grocery> groceries = new HashMap<>();

  public void addGroceries(Grocery grocery) {
    groceries.put(grocery.getName(), grocery);
  }

  public void removeGroceries(String name, double amount) {
    Grocery grocery = groceries.get(name);
    if (grocery != null) {
      double remaining = grocery.getQuantity() - amount;
      if (remaining > 0) {
        grocery.setQuantity(remaining);
      } else {
        groceries.remove(name);
      }
    }
  }

  public void removeGroceryCompletely(String name) {
    groceries.remove(name);
  }

  public void listGroceries() {
    for (Grocery grocery : groceries.values()) {
      System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit());
    }
  }

  public void listExpiredGroceries() {
    double totalExpiredValue = 0;
    for (Grocery grocery : groceries.values()) {
      if (grocery.isExpired()) {
        System.out.println(grocery.getName() + " is expired.");
        totalExpiredValue += grocery.getTotalValue();
      } else {
        System.out.println("No groceries are expired.");
      }
    }
    System.out.println("Total value of expired groceries: " + totalExpiredValue + "NOK");
  }

  public double getTotalValue() {
    double totalValue = 0.0;
    for (Grocery grocery : groceries.values()) {
      totalValue += grocery.getTotalValue();
    }
    return totalValue;
  }
  public Grocery getGrocery(String name) {
    return groceries.get(name);
  }
}
