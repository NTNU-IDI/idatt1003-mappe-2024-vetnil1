# Portfolio project IDATT1003

STUDENT NAME = "Vetle Nilsen"  
CANDIDATE ID = "10073"

### Project description

This project is a food storage and cookbook management system written in Java. It allows users to manage a food storage inventory and a cookbook through a text-based user interface (TUI). The system includes features like adding groceries, removing groceries, checking for expired items, creating and scaling recipes, and checking if a recipe can be prepared with the current inventory.

## Project structure

The project follows a package-based structure to organize its components. The main packages and their purposes are:

- **edu.ntnu.idi.idatt**: Contains all the main classes for the program.
    - **Main**: The entry point of the application.
    - **FoodStorage**: Manages the inventory of groceries, including adding, removing, and listing items.
    - **Cookbook**: Manages recipes, including adding and retrieving recipes.
    - **Recipe**: Represents an individual recipe with ingredients, portion size, description, and procedure.
    - **Grocery**: Represents an individual grocery item with attributes like name, quantity, unit, expiration date, and price.
    - **MenuCases**: Handles the logic for various menu options in the TUI.
    - **TextUserInterface**: Provides a text-based interface for interacting with the system.

- **JUnit Test Files**: The `src/test/java` directory contains all the test classes for the project:
    - **FoodStorageTest**: Tests for the `FoodStorage` class.
    - **GroceryTest**: Tests for the `Grocery` class.
    - **MenuCasesTest**: Tests for the `MenuCases` class.
    - **RecipeTest**: Tests for the `Recipe` class.

## Link to repository

[GitHub Repository Link](https://github.com/NTNU-IDI/idatt1003-mappe-2024-vetnil1)

## How to run the project

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/NTNU-IDI/idatt1003-mappe-2024-vetnil1
