package be.kdg.integration1.team40;

public class Application {
    public static void main(String[] args) {
        Test solving = new Test();
        Board board = new Board();

        board.dataBaseBoard();
        //board.printBoard(board.board);
        solving.solveBoard(board.board);
        board.printBoard(board.board);
    }
}