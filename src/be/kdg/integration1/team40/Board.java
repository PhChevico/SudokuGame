package be.kdg.integration1.team40;

public class Board {

    private final int grid_size=9;
    private final String textBoard =
            ("3 8 5 0 0 0 0 0 0 " +
                    "   9 2 1 0 0 0 0 0 0 " +
                    "   6 4 7 0 0 0 0 0 0 " +
                    "   0 0 0 1 2 3 0 0 0 " +
                    "   0 0 0 7 8 4 0 0 0 " +
                    "   0 0 0 6 9 5 0 0 0 " +
                    "   0 0 0 0 0 0 8 7 3 " +
                    "   0 0 0 0 0 0 9 6 2 " +
                    "   0 0 0 0 0 0 1 4 5 ").replace(" ", "");

    protected int[][] board = new int[grid_size][grid_size];
    public void dataBaseBoard() {
        int count = 0;
        //checking if the inserted board has the same size of the amount of squares we have in the sudoku board
        if (textBoard.length() != Math.pow(grid_size, 2)) {
            System.out.println("Invalid input: textBoard length does not match the grid size.");
        } else {
            //filling the play board with the String from database
            for (int row = 0; row < grid_size; row++) {
                for (int column = 0; column < grid_size; column++, count ++) {
                    board [row][column] = Integer.parseInt(String.valueOf(textBoard.charAt(count)));

                }
            }
        }
    }

    public void printBoard(int [][] board){
        int count =0;
        for (int row = 0; row < grid_size; row++) {
            System.out.println();
            if(row % 3 == 0 && row != 0){
                System.out.println("----------------------------------------------");
            }
            for (int column = 0; column < grid_size; column++) {
                System.out.print(board [row][column] + "\t");
                count++;
                if (count == 3){
                    System.out.print("|\t");
                    count = 0;
                }
            }
        }
    }
}
