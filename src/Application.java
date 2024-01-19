import java.util.Scanner;


public  class Application   {
    public static void main(String[] args) {
        SharedEnvironmentImpl sharedEnv = new SharedEnvironmentImpl();

        HandleInput handleInput = new HandleInput();
        handleInput.setEnv(sharedEnv);
        sharedEnv.setHandleInput(handleInput);


        Board board = new Board();
        board.setEnv(sharedEnv);
        sharedEnv.setBoard(board);

        Cell cell = new Cell();
        cell.setEnv(sharedEnv);
        sharedEnv.setCell(cell);

        Database database = new Database();
        database.setEnv(sharedEnv);
        sharedEnv.setDatabase(database);

        Player player = new Player();
        player.setEnv(sharedEnv);
        sharedEnv.setPlayer(player);

        GameSession gameSession = new GameSession();
        gameSession.setEnv(sharedEnv);
        sharedEnv.setGameSession(gameSession);

        sharedEnv.setScreen(new Screen());
        sharedEnv.setScanner(new Scanner(System.in));
        gameSession.start();
    }
}


