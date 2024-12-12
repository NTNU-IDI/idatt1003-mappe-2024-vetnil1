package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
import LocalDate, ArrayList, Iterator and list
 */
/*
create FoodStorage class
 */

public class FoodStorage {
  private final List<Grocery> groceries;

  /*
  Foodstorage constructor
  */
  public FoodStorage() {
    this.groceries = new ArrayList<>();
  }

  public List<Grocery> getGroceries() {
    return groceries;
  }

  public void addGrocery(Grocery grocery) {
    groceries.add(grocery); // Simply add the new grocery to the list
  }
  /*
  removeGroceries method removes a chosen amount of a chosen grocery in the food storage,
  with the requirements of it not being a negative amount, non-existent grocery or
  an amount which exceeds the amount of stored grocery in the food storage
   */
  public void removeGroceries(String name, double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Quantity to remove must be greater than 0.");
    }

    Iterator<Grocery> iterator = groceries.iterator();
    while (iterator.hasNext()) {
      Grocery grocery = iterator.next();
      if (grocery.getName().equalsIgnoreCase(name)) {
        if (amount > grocery.getQuantity()) {
          System.out.println("Requested removal quantity exceeds available quantity."
              + " No changes made.");
          return;
        }

        double remaining = grocery.getQuantity() - amount;
        if (remaining > 0) {
          grocery.setQuantity(remaining);
          return;
        }

        iterator.remove(); // Remove the grocery completely if remaining is 0
        return;
      }
    }

    System.out.println("Grocery not found.");
  }

  /*
  method which removes a grocery completely from the food storage by name
   */
  public void removeGroceryCompletely(String name) {
    groceries.removeIf(grocery -> grocery.getName().equalsIgnoreCase(name));
  }
  /*
  method which lists groceries in food storage
  it checks if the grocery has expired, and only prints non-expired food.
  If there is any grocery which are expired it will alert the user that there are expired food
  in the food storage
   */
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

  /*
  prints out a list of groceries which are expired.
  it will also calculate the total value of the expired groceries.
  this helps the user see the monetary loss of the expired groceries.
   */
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

  /*
  method which calculates the total value of groceries
   */
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

  /*
  method which searches and prints out information on a specific grocery in the food storage
   */
  public void searchGroceryByName(String name) {
    boolean found = false;

    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(name)) {
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " " + grocery.getUnit() +
            ", Expiration Date: " + grocery.getExpirationDate() + ", Price per Unit: " + grocery.getPricePerUnit() + " NOK \n");
        found = true;
      }
    }

    if (!found) {
      System.out.println("No grocery found with the name \"" + name + "\" in the storage. \n");
    }
  }
}
