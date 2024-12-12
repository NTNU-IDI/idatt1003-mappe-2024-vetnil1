package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code FoodStorage} class manages a collection of groceries.
 * It provides functionality to add, remove, search, and list groceries,
 * as well as calculate the total value of the groceries.
 */
public class FoodStorage {
  /**
   * The list of groceries stored in the food storage system.
   */
  private final List<Grocery> groceries;

  /**
   * Constructs an empty {@code FoodStorage}.
   */
  public FoodStorage() {
    this.groceries = new ArrayList<>();
  }

  /**
   * Retrieves the list of groceries currently in the food storage.
   *
   * @return a {@code List} of {@code Grocery} objects.
   */
  public List<Grocery> getGroceries() {
    return groceries;
  }

  /**
   * Adds a new grocery to the food storage.
   *
   * @param grocery the {@code Grocery} to add. Must not be {@code null}.
   */
  public void addGrocery(Grocery grocery) {
    groceries.add(grocery);
  }

  /**
   * Removes a specific quantity of a grocery from the food storage.
   * If the remaining quantity is zero, the grocery is removed completely.
   *
   * @param name   the name of the grocery to remove.
   * @param amount the quantity to remove. Must be greater than 0.
   * @throws IllegalArgumentException if {@code amount} is less than or equal to 0.
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

  /**
   * Removes a grocery completely from the food storage by its name.
   *
   * @param name the name of the grocery to remove.
   */
  public void removeGroceryCompletely(String name) {
    groceries.removeIf(grocery -> grocery.getName().equalsIgnoreCase(name));
  }

  /**
   * Lists all non-expired groceries in the food storage.
   * If any expired groceries are present, a warning is displayed.
   *
   * @param currentDate the current date to check for expiration.
   */
  public void listGroceries(LocalDate currentDate) {
    boolean hasExpiredItems = false;

    for (Grocery grocery : groceries) {
      if (grocery.isExpired(currentDate)) {
        hasExpiredItems = true;
      } else {
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " "
            + grocery.getUnit() + ", Expiration Date: " + grocery.getExpirationDate()
            + ", Price per Unit: " + grocery.getPricePerUnit() + " NOK \n");
      }
    }

    if (hasExpiredItems) {
      System.out.println("Warning: There are expired food items in the food storage!");
    }
  }

  /**
   * Lists all expired groceries in the food storage along with their total value.
   *
   * @param currentDate the current date to check for expiration.
   */
  public void listExpiredGroceries(LocalDate currentDate) {
    double totalExpiredValue = 0.0;
    boolean hasExpiredItems = false;

    for (Grocery grocery : groceries) {
      if (grocery.isExpired(currentDate)) {
        hasExpiredItems = true;
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " "
            + grocery.getUnit() + ", Expired on: " + grocery.getExpirationDate());
        totalExpiredValue += grocery.getTotalValue();
      }
    }

    if (hasExpiredItems) {
      System.out.println("Total value of expired groceries: " + totalExpiredValue + " NOK");
    } else {
      System.out.println("No expired groceries in storage.");
    }
  }

  /**
   * Calculates the total value of all non-expired groceries in the food storage.
   *
   * @param currentDate the current date to check for expiration.
   * @return the total value of non-expired groceries.
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

  /**
   * Retrieves a grocery from the food storage by its name.
   *
   * @param name the name of the grocery to search for.
   * @return the {@code Grocery} object if found; {@code null} otherwise.
   */
  public Grocery getGrocery(String name) {
    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(name)) {
        return grocery;
      }
    }
    return null;
  }

  /**
   * Searches for a grocery by its name and displays its details.
   * If the grocery is not found, a message is displayed.
   *
   * @param name the name of the grocery to search for.
   */
  public void searchGroceryByName(String name) {
    boolean found = false;

    for (Grocery grocery : groceries) {
      if (grocery.getName().equalsIgnoreCase(name)) {
        System.out.println(grocery.getName() + ": " + grocery.getQuantity() + " "
            + grocery.getUnit() + ", Expiration Date: " + grocery.getExpirationDate()
            + ", Price per Unit: " + grocery.getPricePerUnit() + " NOK \n");
        found = true;
      }
    }

    if (!found) {
      System.out.println("No grocery found with the name \"" + name + "\" in the storage. \n");
    }
  }
}
