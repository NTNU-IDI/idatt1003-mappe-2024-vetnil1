package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodStorage {
  private List<Grocery> groceries = new ArrayList<>();

  public FoodStorage() {
    // Pre-set items
    groceries.add(new Grocery("Milk", 1.0, "liters", LocalDate.of(2024, 12, 20), 10.0));
    groceries.add(new Grocery("Eggs", 12.0, "amounts", LocalDate.of(2024, 12, 31), 3.0));
    groceries.add(new Grocery("Flour", 1.0, "kg", LocalDate.of(2025, 8, 22), 10.0));
    groceries.add(new Grocery("Sugar", 1.0, "kg", LocalDate.of(2025, 3, 13), 35.0));
  }

  public List<Grocery> getGroceries() {
    return groceries;
  }

  public void addGroceries(Grocery grocery) {
    groceries.add(grocery); // Simply add the new grocery to the list
  }

  public void removeGroceries(String name, double amount) {
    Iterator<Grocery> iterator = groceries.iterator();
    while (iterator.hasNext()) {
      Grocery grocery = iterator.next();
      if (grocery.getName().equalsIgnoreCase(name)) {
        double remaining = grocery.getQuantity() - amount;
        if (remaining > 0) {
          grocery.setQuantity(remaining);
          return;
        } else {
          iterator.remove();
          return;
        }
      }
    }
    System.out.println("Grocery not found.");
  }

  public void removeGroceryCompletely(String name) {
    groceries.removeIf(grocery -> grocery.getName().equalsIgnoreCase(name));
  }

  public void listGroceries(LocalDate currentDate) {
    boolean hasExpiredItems = false;

    for (Grocery grocery : groceries) {
      if (grocery.isExpired(currentDate)) {
        hasExpiredItems = true;
      } else {
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit() +
            ", Expiration Date: " + grocery.getExpirationDate() + ", Price per Unit: " + grocery.getPricePerUnit() + " NOK \n");
      }
    }

    if (hasExpiredItems) {
      System.out.println("Warning: There are expired food items in the food storage!");
    }
  }


  public void listExpiredGroceries(LocalDate currentDate) {
    double totalExpiredValue = 0.0;
    boolean hasExpiredItems = false;

    for (Grocery grocery : groceries) {
      if (grocery.isExpired(currentDate)) {
        hasExpiredItems = true;
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit() +
            ", Expired on: " + grocery.getExpirationDate());
        totalExpiredValue += grocery.getTotalValue();
      }
    }

    if (hasExpiredItems) {
      System.out.println("Total value of expired groceries: " + totalExpiredValue + " NOK");
    } else {
      System.out.println("No expired groceries in storage.");
    }
  }

  public double getTotalValue(LocalDate currentDate) {
    double totalValue = 0.0;

    for (Grocery grocery : groceries) {
      if (!grocery.isExpired(currentDate)) {
        totalValue += grocery.getTotalValue();
      }
    }
    return totalValue;
  }

  public Grocery getGrocery(String name) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(name)) {
        return grocery;
      }
    }
    return null;
  }
}
