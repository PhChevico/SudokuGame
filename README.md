# Sudoku Terminal Game

This is a terminal-based Sudoku game built in Java, designed for first-semester computer science students. It allows users to play Sudoku, save their game progress, and resume it later. The game uses a PostgreSQL database to store the current state of the game.

## Features

- Play Sudoku on the terminal
- Save and load game progress using a PostgreSQL database
- Validate Sudoku rules (rows, columns, 3x3 subgrids)
- Simple user interface for input and navigation

## Requirements

To run this game, you need:

- Java 11 or higher
- PostgreSQL installed and running
- Required Java libraries

### Libraries

The needed library is present under /lib

## Database Setup

1. **Run the SQL script to set up the database**:

   - Create the database and tables by running the provided SQL script `java-db_s1.sql` in PostgreSQL:

     ```bash
     psql -U your_username -d postgres -f java-db_s1.sql
     ```

   This will create the necessary database, tables, and default configuration for saving and loading the game state.


## How to Play

1. Clone the repository or download the game files.

   ```bash
   git clone https://github.com/PhChevico/SudokuGame.git
   cd SudokuGame
   ```

2. Compile and run the game:

   ```bash
   javac -cp .:lib/postgresql-42.7.0.jar SudokuGame.java
   java -cp .:lib/postgresql-42.7.0.jar SudokuGame
   ```


## License

This project is open-source and available under the [MIT License](LICENSE).
