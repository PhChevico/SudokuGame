import java.util.Scanner;

public interface ISharedEnvironment {
    public Board getBoard();

    public Cell getCell();

    public Database getDatabase();

    public Player getPlayer();

    public Screen getScreen();

    public Scanner getScanner();
    public HandleInput getHandleInput();

    public GameSession getGameSession();

}
