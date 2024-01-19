import java.util.Scanner;

public class SharedEnvironmentImpl implements ISharedEnvironment {

    private Database database_;
    private Player player_;
    private Cell cell_;
    private Board board_;
    private Screen screen_;
    private Scanner scanner_;
    private HandleInput handleInput_;
    private GameSession gameSession_;
    @Override
    public GameSession getGameSession() {
        return gameSession_;
    }


    public void setGameSession(GameSession gameSession_) {
        this.gameSession_ = gameSession_;
    }

    @Override
    public HandleInput getHandleInput() {
        return handleInput_;
    }



    public void setHandleInput(HandleInput handleInput) {
        this.handleInput_ = handleInput;
    }

    @Override
    public Database getDatabase() {
        return database_;
    }

    public void setDatabase(Database database) {
        this.database_ = database;
    }
    @Override
    public Player getPlayer() {
        return player_;
    }

    public void setPlayer(Player player) {
        this.player_ = player;
    }
    @Override
    public Cell getCell() {
        return cell_;
    }

    public void setCell(Cell cell) {
        this.cell_ = cell;
    }
    @Override
    public Board getBoard() {
        return board_;
    }

    public void setBoard(Board board) {
        this.board_ = board;
    }
    @Override
    public Screen getScreen() {
        return screen_;
    }

    public void setScreen(Screen screen) {
        this.screen_ = screen;
    }
    @Override
    public Scanner getScanner() {
        return scanner_;
    }

    public void setScanner(Scanner scanner) {
        this.scanner_ = scanner;
    }
}
