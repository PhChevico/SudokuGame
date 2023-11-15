public class Test {
    static final int GRID_SIZE = 9;
    static int [] [] grid = new int[GRID_SIZE][GRID_SIZE];

    static final int box_size = 3;

    // verify the rows
    public static boolean isNumberInRow (int [][] grid, int number, int row){
        for (int i = 0; i < GRID_SIZE; i++) {
            if(grid [row] [i] == number){
                return true;
            }
        }
        return false;
    }

    // verify the columns
    public static boolean isNumberInColumn (int [][] grid, int number, int column){
        for (int i = 0; i < GRID_SIZE; i++) {
            if(grid [i] [column] == number){
                return true;
            }
        }
        return false;
    }

    //verify the box
    public static boolean isNumberInBox (int [][] grid, int number, int column, int row){
        int localBoxRow = row - row % box_size;
        int localBoxColumn = column - column % box_size;
        for (int i = localBoxRow; i < localBoxRow + box_size; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + box_size; j++) {
                if (grid [i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isValidSquare (int [][] grid, int number, int column, int row){
        return !isNumberInBox(grid, number, column, row)  &&
                !isNumberInRow(grid, number, row) &&
                !isNumberInColumn(grid, number, column);
    }



    public static boolean solveBoard(int [][] grid){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if(grid [row][column] == 0){
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE ; numberToTry++) {
                        if(isValidSquare(grid, numberToTry, column, row)){
                            grid [row][column] = numberToTry;

                            if (solveBoard(grid)) {
                                return true;
                            }else {
                                grid [row] [column] = 0;
                            }
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

//printing board
    public static void printBoard(){
        for(int i= 0; i < grid.length ; i ++) {
            if(i % 3 == 1) System.out.println();
            for (int j = 0; j < grid[i].length; j++) {
                if (j % 3 == 1) {System.out.println();}
                System.out.print(grid[i][j]);
            }
        }
    }
}
