public class HandleInput extends SharedEnvironmentConsumer {
    BoardSolver boardSolver = new BoardSolver();
    private String input;
    public int game_id;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public static String adjusticInput(String input) {
        return String.valueOf(Integer.parseInt(input) - 1);
    }

    private void exitToMenu() {
        System.out.println("Loading the menu...");
        getEnv().getScreen().sudokuMenu();
        getEnv().getHandleInput().handleMenu();
    }

    private void saveAndContinue() {
        getEnv().getDatabase().saveIntoDatabase();
        System.out.println("Game is saved. Your id for loading game = " + getEnv().getDatabase().getGameId() + ".\nType EXIT to go back to the menu.");
    }

    private void solveAndReturnToMenu() {
        boardSolver.solveBoard(getEnv().getBoard().playableBoard);
        getEnv().getBoard().printBoard(getEnv().getBoard().playableBoard);
        exitToMenu();
    }

    public int handleLevel() {
        while (true) {
            input = getEnv().getScanner().next();

            if (input.equalsIgnoreCase("exit")) {
                exitToMenu();
            } else {
                try {
                    int inputValue = Integer.parseInt(input);

                    if (inputValue >= 1 && inputValue <= 7) {
                        return inputValue;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 7 (inclusive).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 7 (inclusive) or 'exit'.");
                }
            }
        }
    }

    public void handleMenu() {
        while (true) {
            input = getEnv().getScanner().next();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("\nThanks for playing\nClosing the app...");
                System.exit(0);
            } else if (input.equalsIgnoreCase("level")) {
                System.out.printf("Hi %s, to start the game, please choose a level (1-7): ", getEnv().getPlayer().getName());
                getEnv().getBoard().setLevel(handleLevel());
                getEnv().getGameSession().play();
            } else if (input.equalsIgnoreCase("load")) {
                System.out.println("Great! Type id of your previous game");
                setGame_id(getEnv().getScanner().nextInt());
                getEnv().getBoard().textOfPlayableBoard = getEnv().getDatabase().loadFromDatabase(getGame_id());
                getEnv().getBoard().solvedTextBoard = getEnv().getDatabase().solvedBoard(getEnv().getBoard().getLevel());
                getEnv().getGameSession().playLoadedBoard(getEnv().getBoard().playableBoard);
            } else if (input.equalsIgnoreCase("leaderboard")) {
                System.out.println("\nLeaderboard...\n");
                getEnv().getDatabase().highestPointsPlayer();
                getEnv().getScreen().sudokuMenu();
                handleMenu();
            } else if (input.equalsIgnoreCase("rules")) {
                getEnv().getScreen().sudokuRules();
                getEnv().getScreen().sudokuMenu();
                handleMenu();
            } else {
                System.out.println("Please type in a valid option!");
                getEnv().getScreen().sudokuMenu();
                handleMenu();
            }
        }
    }

    public void handleInput() {
        while (getEnv().getBoard().getGameRunning()) {
            getEnv().getBoard().isThereTimeLeft();
            getEnv().getBoard().startTimer();

            System.out.print("\nWhat row do you want to modify: ");
            getEnv().getCell().setRow(adjusticInput(handleInputCell()));

            System.out.print("What column do you want to modify: ");
            getEnv().getCell().setColumn(adjusticInput(handleInputCell()));

            System.out.print("What number do you want to insert: ");
            getEnv().getCell().setValue(handleInputCell());


            getEnv().getBoard().changeCell(getEnv().getCell().getRow(), getEnv().getCell().getColumn(), getEnv().getCell().getValue());

            getEnv().getBoard().stopTimer();

            System.out.println("Time taken for the move: " + (getEnv().getBoard().getElapsedTime()) + " seconds");
            System.out.println("Total accumulated time: " + (getEnv().getBoard().getTotalTime()) + " seconds");
            System.out.println("Time left: " + (getEnv().getBoard().timeUntilEnd()) + " seconds");

            if (getEnv().getBoard().ifBoardSolvedChecker()){
                getEnv().getDatabase().updateScore((getEnv().getBoard().timeUntilEnd() * getEnv().getDatabase().getMultiplier(getEnv().getBoard().getLevel())), getEnv().getPlayer().getName());
                System.out.println("\n\n\nYou finished the game, excellent! Check your points in Leaderboard!\n\n\n");
            exitToMenu();}


        }
    }

    public String handleInputCell() {
        while (true) {
            input = getEnv().getScanner().next();

            if (input.equalsIgnoreCase("exit")) {
                exitToMenu();
            } else if (input.equalsIgnoreCase("save")) {
                saveAndContinue();
            } else if (input.equalsIgnoreCase("solve")) {
                solveAndReturnToMenu();
            } else {
                try {
                    int inputValue = Integer.parseInt(input);

                    if (inputValue >= 1 && inputValue <= 9) {
                        return String.valueOf(inputValue);
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 9 (inclusive).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 9 (inclusive) or 'exit'.");
                }
            }
        }
    }
}
