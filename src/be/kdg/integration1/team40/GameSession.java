package be.kdg.integration1.team40;

public class GameSession extends SharedEnvironmentConsumer {

    public void start() {
        getEnv().getDatabase().openConnection("jdbc:postgresql://localhost:5432/ascii40", "postgres", "Student_1234");

        getEnv().getScreen().generateWelcomeSudokuAscii();

        System.out.print("Press enter to start Sudoku game!");
        getEnv().getScanner().nextLine();

        getEnv().getDatabase().createDatabase();

        System.out.print("Enter your name: ");
        getEnv().getPlayer().setName(getEnv().getScanner().nextLine());
        getEnv().getDatabase().insertNameDatabase(getEnv().getPlayer().getName().toUpperCase(), getEnv().getPlayer().getStartGameTime(), 0);

        getEnv().getScreen().sudokuMenu();
        getEnv().getHandleInput().handleMenu();
    }

    public void playLoadedBoard(int[][] playableBoard) {
        System.out.println();

        getEnv().getBoard().boardToSolve();
        getEnv().getBoard().initBoardSolved();
        getEnv().getHandleInput().handleInput();
        getEnv().getDatabase().printLeaderBoard();
        getEnv().getDatabase().closeDatabase();
    }
    public void play(){

            getEnv().getBoard().fillFieldFromDb(getEnv().getBoard().getLevel());
            System.out.println();
            getEnv().getBoard().boardToSolve();
            getEnv().getBoard().initBoardSolved();
            getEnv().getHandleInput().handleInput();

            // Display message goodbye
            getEnv().getDatabase().printLeaderBoard();
            getEnv().getDatabase().closeDatabase();
        }


    };




