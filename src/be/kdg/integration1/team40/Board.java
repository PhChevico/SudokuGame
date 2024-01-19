package be.kdg.integration1.team40;

public class Board extends SharedEnvironmentConsumer {

    //size of the board
    private final int GRID_SIZE = 9;

    private final int SECOND_IN_MILISECONDS = 1000;
    private final long GAME_LENGTH = 900;

    private boolean gameRunning = true;

    private int levelNumber;

    public boolean getGameRunning() {
        return gameRunning;
    }

    //board to be solved && it's the board used and edited by the final user (also comes from DB)
    // CACHED
    public String textOfPlayableBoard;
    public String solvedTextBoard;

    // Time related
    private long timeLeft;
    private long startTime;
    private long elapsedTime;
    private long totalTime;

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int[][] playableBoard = new int[GRID_SIZE][GRID_SIZE];
    public int[][] solvedBoard = new int[GRID_SIZE][GRID_SIZE];


    public void setLevel(int level) {
        levelNumber = level;
    }

    public int getLevel() {
        return levelNumber;
    }

    public void fillFieldFromDb(int level) {
        textOfPlayableBoard = getEnv().getDatabase().playableBoard(getLevel());
        solvedTextBoard = getEnv().getDatabase().solvedBoard(getLevel());
    }


    public void startTimer() {
        startTime = System.currentTimeMillis();

    }

    public void stopTimer() {
        elapsedTime = (System.currentTimeMillis() - startTime) / SECOND_IN_MILISECONDS;
        totalTime += elapsedTime;
    }


    public long timeUntilEnd() {
        return timeLeft = GAME_LENGTH - totalTime;
    }

    public long getGAME_LENGTH() {
        return GAME_LENGTH;
    }

    public void isThereTimeLeft() {
        if (timeUntilEnd() <= 0) {
            System.out.println("Your time has expired,you have 1 last move left");
            gameRunning = false;
        }
    }

    public int getPlayableBoardArray(int x, int y) {
        return playableBoard[x][y];
    }


    public String getPlayableBoard() {
        String result = "";
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                result += Long.toString(playableBoard[i][j]);

            }
        }
        return result;
    }

    public void boardToSolve() {
        int count = 0;
        //checking if the inserted board has the same size of the amount of squares we have in the sudoku board
        if (textOfPlayableBoard.length() != Math.pow(GRID_SIZE, 2)) {
            System.out.println("Invalid input: textBoard length does not match the grid size.");
        } else {
            //filling the play board with the String from database
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int column = 0; column < GRID_SIZE; column++, count++) {
                    playableBoard[row][column] = Integer.parseInt(String.valueOf(textOfPlayableBoard.charAt(count)));

                }
            }
        }
        printBoard(playableBoard);
        System.out.println();
        System.out.println("Type EXIT at any moment to go back to main menu!");
    }

    public void initBoardSolved() {
        int count = 0;
        //checking if the inserted board has the same size of the amount of squares we have in the sudoku board
        if (solvedTextBoard.length() != Math.pow(GRID_SIZE, 2)) {
            System.out.println("Invalid input: textBoard length does not match the grid size.");
        } else {
            //filling the solved board with the String from database
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int column = 0; column < GRID_SIZE; column++, count++) {
                    solvedBoard[row][column] = Integer.parseInt(String.valueOf(solvedTextBoard.charAt(count)));
                }
            }
        }
    }


    public void printBoard(int[][] board) {
        int count = 0;
        int rowCounter = 0;
        System.out.println("-----------------------------------------------------");
        System.out.println("|   |\t1\t2\t3\t|\t4\t5\t6\t|\t7\t8\t9\t|");
        System.out.print("|---|-----------------------------------------------|");
        for (int row = 0; row < GRID_SIZE; row++) {
            System.out.println();
            if (row % 3 == 0 && row != 0) {
                System.out.println("|---|-----------------------------------------------|");
            }
            System.out.print("| " + (row + 1) + " |\t");
            for (int column = 0; column < GRID_SIZE; column++) {
                System.out.print(board[row][column] + "\t");
                count++;
                if (count == 3) {
                    System.out.print("|\t");
                    count = 0;
                }
            }
        }
        System.out.println("\n|---|-----------------------------------------------|");
    }


    public void changeCell(int row, int column, int insertNumber) {
        if (insertNumber == solvedBoard[row][column]) {
            playableBoard[row][column] = insertNumber;
            System.out.println("\nCorrect Insert. Keep going!");
        } else {
            System.out.println("\nIncorrect Insert! Try again.");
        }
        printBoard(playableBoard);
    }


    public void fillPlayableBoardFromLoad(int row, int column, int insertNumber) {
        playableBoard[row][column] = insertNumber;
    }

    public boolean ifBoardSolvedChecker() {
        if (playableBoard.length != solvedBoard.length || playableBoard[0].length != solvedBoard[0].length) {
            return false; // Arrays have different dimensions
        }
        for (int i = 0; i < playableBoard.length; i++) {
            for (int j = 0; j < playableBoard[0].length; j++) {
                if (playableBoard[i][j] != solvedBoard[i][j]) {
                    return false; // Elements at position (i, j) are not equal
                }
            }
        }
        return true; // Arrays are equal
    }
}
