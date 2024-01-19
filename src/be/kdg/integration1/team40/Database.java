package be.kdg.integration1.team40;

import java.sql.*;
import java.util.Scanner;

public class Database extends SharedEnvironmentConsumer {
    private Connection connection;

    public Database() {
    }

    public void openConnection(String url, String username, String password) {
        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public void closeDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDatabase() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS INT_boards cascade");
            statement.executeUpdate("DROP TABLE IF EXISTS INT_boardsolved cascade");
            statement.executeUpdate("DROP TABLE IF EXISTS INT_playable_board cascade");
            statement.executeUpdate("DROP TABLE IF EXISTS INT_game cascade;");


            // Create INT_game table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS INT_game (" +
                    "game_id INTEGER CONSTRAINT pk_INT_game PRIMARY KEY," +
                    "player_name VARCHAR(255) CONSTRAINT nn_player_name NOT NULL," +
                    "date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");");


            // Create INT_boards table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS INT_boards(" +
                    "game_board CHAR (81) NOT NULL, " +
                    "solved_board CHAR(81) NOT NULL, " +
                    "level NUMERIC (1) CONSTRAINT pk_level PRIMARY KEY NOT NULL " +
                    "CONSTRAINT ch_level CHECK ( level between 1 and 7), " +
                    "difficulty NUMERIC (2,1) CONSTRAINT ch_difficulty CHECK (difficulty between 0 and 3), " +
                    "multiplier NUMERIC (3) CONSTRAINT ch_multiplier CHECK (multiplier between 1 and 1000))");

            // Create INT_allLoads table
            statement.executeUpdate("create TABLE IF NOT EXISTS INT_allLoads(" +
                    "game_id INTEGER" +
                    "   CONSTRAINT fk_game_id references int_game (game_id)," +
                    "level NUMERIC (1)" +
                    "   CONSTRAINT fk_level references int_boards(level)," +
                    "x NUMERIC (1)," +
                    "y NUMERIC (1)," +
                    "numberInCell NUMERIC (1)" +
                    ");");

            // Create INT_leaderboard table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS INT_leaderboard(" +
                    "player_name VARCHAR(50) CONSTRAINT pk_player_name PRIMARY KEY NOT NULL, " +
                    "date VARCHAR(50) DEFAULT CURRENT_DATE NOT NULL, " +
                    "score NUMERIC (8) NOT NULL)");

            // Create INT_saveAndLoad table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS INT_saveAndLoad(" +
                    "player_name VARCHAR(50) CONSTRAINT fk_player_name REFERENCES int_leaderboard(player_name), " +
                    "time_played numeric (3), " +
                    "time_remaining numeric (3), " +
                    "game_id INTEGER CONSTRAINT fk_game_id references int_game(game_id), " +
                    "level NUMERIC (1) CONSTRAINT fk_level references int_boards(level), " +
                    "CONSTRAINT pk_saveAndLoad PRIMARY KEY (player_name, level))");

            statement.executeUpdate("create TABLE IF NOT EXISTS INT_boardSolved(" +
                    "level NUMERIC (1)" +
                    "   CONSTRAINT fk_level references int_boards(level)," +
                    "x NUMERIC (1)," +
                    "y NUMERIC (1)," +
                    "numberInCell NUMERIC (1)," +
                    "   CONSTRAINT pk_boardSolved PRIMARY KEY (level, x, y)" +
                    ");");

            statement.executeUpdate("create TABLE IF NOT EXISTS INT_playable_board(" +
                    "level NUMERIC (1)" +
                    "CONSTRAINT fk_level references int_boards(level)," +
                    "x NUMERIC (1)," +
                    "y NUMERIC (1)," +
                    "numberInCell NUMERIC (1)" +
                    ");");


            // Insert data into INT_boards
            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('000260701680070090190004500820100040004602900050003028009300074040050036703018000', " +
                    "'435269781682571493197834562826195347374682915951743628519326874248957136763418259', 1, 1, 100)");

            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('100489006730000040000001295007120600500703008006095700914600000020000037800512004', " +
                    "'152489376739256841468371295387124659591763428246895713914637582625948137873512964', 2, 1.2, 200)");

            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('020608000580009700000040000370000500600000004008000013000020000009800036000306090', " +
                    "'123678945584239761967145328372461589691583274458792613836924157219857436745316892', 3, 1.4, 300)");

            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('000600400700003600000091080000000000050180003000306045040200060903000000020000100'," +
                    " '581672439792843651364591782438957216256184973179326845845219367913768524627435198', 4, 1.6, 400)");

            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('200300000804062003013800200000020390507000621032006000020009140601250809000001002'," +
                    " '276314958854962713913875264468127395597438621132596487325789146641253879789641532', 5, 1.8, 500)");

            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('000000200080007090602000500070060000000901000000020040005000603090400070006000000'," +
                    " '957613284483257196612849537178364952524971368369528741845792613291436875736185429', 6, 2, 600)");

            statement.executeUpdate("INSERT INTO INT_boards (game_board, solved_board, level, difficulty, multiplier) " +
                    "VALUES ('126437958895621473374985126457193862983246517612578394269314785548769231731852600'," +
                    " '126437958895621473374985126457193862983246517612578394269314785548769231731852649', 7, 2.2, 700)");


            statement.executeUpdate("DO $$ " +
                    "DECLARE " +
                    "    lv INTEGER = 1; " +
                    "    i INTEGER; " +
                    "    j INTEGER; " +
                    "    char_index INTEGER; " +
                    "    current_char TEXT; " +
                    "    input_string TEXT; " +
                    "BEGIN " +
                    "    FOR lv IN 1..7 LOOP " +
                    "        input_string := (SELECT solved_board from int_boards WHERE level = lv); " +
                    "        char_index := 1; " +
                    "        FOR i IN 1..9 LOOP " +
                    "            FOR j IN 1..9 LOOP " +
                    "                current_char := SUBSTRING(input_string FROM char_index FOR 1); " +
                    "                INSERT INTO INT_boardSolved (level, x, y, numberInCell) " +
                    "                VALUES (lv, i, j, CAST(current_char AS INTEGER)); " +
                    "                char_index := char_index + 1; " +
                    "            END LOOP; " +
                    "        END LOOP; " +
                    "    END LOOP; " +
                    "END $$;");


            statement.close();
        } catch (SQLException e) {
            System.out.println("Can't create database" + e.getMessage());
        }
    }


    public void saveIntoDatabase() {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO int_saveandload (player_name, time_played, time_remaining, game_id, level) VALUES" +
                    "('" + getEnv().getPlayer().getName().toUpperCase() + "'," + getEnv().getPlayer().getTimePlayed() + ", "
                    + Long.toString(getEnv().getBoard().timeUntilEnd()) + ",(SELECT max(game_id)FROM INT_game)," + getEnv().getBoard().getLevel() + ");";

            for (int x = 1; x <= 9; x++) {
                for (int y = 1; y <= 9; y++) {
                    String loads = "INSERT INTO int_allloads (game_id, level, x, y, numberincell) VALUES" +
                            "((SELECT max(game_id) FROM INT_game), " + getEnv().getBoard().getLevel() + ", " + x + "," + y + "," + getEnv().getBoard().getPlayableBoardArray(x - 1, y - 1) + ");";
                    statement.executeUpdate(loads);

                }
            }


            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Can't save into database .Error: " + e.getMessage());
        }
    }
    public String loadFromDatabase(int game_id) {
        try {
            Statement statement = connection.createStatement();

            // Fetch time_played and time_remaining for the player (assuming player_name is the column name)
            String playerName = getEnv().getPlayer().getName().toUpperCase(); // Convert player name to uppercase
            ResultSet timePlayedResult = statement.executeQuery("SELECT time_played, time_remaining FROM int_saveandload WHERE player_name = '" + playerName + "'");

            if (timePlayedResult.next()) {
                int timePlayed = timePlayedResult.getInt("time_played");
                int timeRemaining = timePlayedResult.getInt("time_remaining");

                // Assuming set methods for timePlayed and timeRemaining exist in the Player class
                getEnv().getPlayer().setTimePlayed(String.valueOf(timePlayed));
                getEnv().getPlayer().setTimeRemaining(String.valueOf(timeRemaining));
            }
            ResultSet level = statement.executeQuery("SELECT level FROM int_saveandload WHERE game_id = '" + game_id + "'");
            int retrievedLevel=-1;
            if(level.next()){
                retrievedLevel = level.getInt("level");
            }
            getEnv().getBoard().setLevel(retrievedLevel);
            // Fetching data for the game board
            for (int x = 1; x <= 9; x++) {
                for (int y = 1; y <= 9; y++) {
                    ResultSet result = statement.executeQuery("SELECT x, y, numberInCell FROM int_allloads WHERE game_id = " + game_id);
                    while (result.next()) {
                        int retrievedX = result.getInt("x") - 1;
                        int retrievedY = result.getInt("y") - 1;
                        int numberInCell = result.getInt("numberInCell");

                        // Assuming fillPlayableBoardFromLoad requires x, y, and numberInCell as arguments
                        getEnv().getBoard().fillPlayableBoardFromLoad(retrievedX, retrievedY, numberInCell);

                    }
                }
            }
            return getEnv().getBoard().getPlayableBoard();
    } catch(SQLException e){
        System.out.println("Can't load from database: " + e.getMessage());
        return "";
    }
}


        public void printLeaderBoard() {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM int_leaderboard");
            System.out.printf("%-30s %-30s %-30s", "PLAYER", "DATE AND TIME", "SCORE");
            System.out.println();
            while (result.next()) {
                System.out.printf("%-30s %-30s %-30s\n", result.getString("player_name"), result.getString("date"), result.getDouble("score"));
            }

            result.close();
            statement.close();


        } catch (SQLException e) {
            System.err.println("DB error: ");
            e.printStackTrace();
        }

    }

    public void insertNameDatabase(String name, String date, int score) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO int_leaderboard(player_name, date, score) VALUES ('" + name + "', '" + date + "', '" + score + "')");

            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS seq_game_id START WITH 1 INCREMENT BY 1;");

            statement.executeQuery("INSERT INTO INT_game(game_id, player_name, date_time) VALUES (NEXTVAL('seq_game_id'), " + "'" +  name + "'"+ ", CURRENT_TIMESTAMP);");

            statement.close();

        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                System.out.print("This name is already been used, choose another name: ");
                getEnv().getPlayer().setName(getEnv().getScanner().nextLine());
                insertNameDatabase(getEnv().getPlayer().getName().toUpperCase(), getEnv().getPlayer().getStartGameTime(), 0);

            }

        }
    }

    public void updateScore(double score, String playerName) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE int_leaderboard SET score = " + score+ " WHERE player_name = '"+playerName.toUpperCase()+"';");

            statement.close();

        } catch (SQLException e) {
            System.err.println("DB error: ");
            e.printStackTrace();
        }
    }


    public String getGameId() {
        String gameId = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT max(game_id) as max_id FROM INT_game;");

            if (result.next()) {
                gameId = String.valueOf(result.getInt("max_id"));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Can't get game id!" + e.getMessage());
        }
        return gameId;
    }




    //it will be used to define the amount of points after the end of the game (points = time left * multiplier)
    public double getMultiplier(int level) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT multiplier FROM int_boards WHERE level = " + level);
            while (result.next()) {
                return result.getDouble("multiplier");
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("DB error: ");
            e.printStackTrace();
        }
        return 0;
    }


    public String playableBoard(int level) {
        StringBuilder str = new StringBuilder();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DO $$" +
                    "    DECLARE" +
                    "        lv INTEGER = " + level + ";" +
                    "        i INTEGER;" +
                    "        j INTEGER;" +
                    "        char_index INTEGER;" +
                    "        current_char TEXT;" +
                    "        input_string TEXT;" +
                    "    BEGIN" +
                    "                input_string := (SELECT game_board from int_boards WHERE level = lv);" +
                    "                char_index := 1;" +
                    "                FOR i IN 1..9 LOOP" +
                    "                        FOR j IN 1..9 LOOP" +
                    "                                current_char := SUBSTRING(input_string FROM char_index FOR 1);" +
                    "                                INSERT INTO INT_playable_board (level, x, y, numberInCell) VALUES (lv, i, j, cast(current_char as INTEGER ));" +
                    "                                char_index := char_index + 1;" +
                    "                            END LOOP;" +
                    "                END LOOP;" +
                    "    END $$;");



            for (int x = 1; x <= 9; x++) {
                for (int y = 1; y <= 9; y++) {
                    ResultSet resultSet = statement.executeQuery("SELECT numberInCell FROM int_playable_board WHERE level = " + level + " AND x = " + x + " AND y = " + y);
                    // Check if the result set has any data and append it to the StringBuilder
                    if (resultSet.next()) {
                        str.append(resultSet.getString("numberInCell"));
                    }
                }
            }
            statement.close();
            return str.toString();

        } catch (SQLException e) {
            System.err.println("DB error: ");
            e.printStackTrace();
        }
        return null;
    }

    public String solvedBoard(int level) {
        StringBuilder str = new StringBuilder();
        try {
            Statement statement = connection.createStatement();
            for (int x = 1; x <= 9; x++) {
                for (int y = 1; y <= 9; y++) {
                    ResultSet resultSet = statement.executeQuery("SELECT numberInCell FROM INT_boardSolved WHERE level = " + level + " AND x = " + x + " AND y = " + y);
                    // Check if the result set has any data and append it to the StringBuilder
                    if (resultSet.next()) {
                        str.append(resultSet.getString("numberInCell"));
                    }
                }
            }
            statement.close();
            return str.toString();

        } catch (SQLException e) {
            System.err.println("DB error: ");
            e.printStackTrace();
        }
        return null;
    }


    public void highestPointsPlayer(){
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM int_leaderboard ORDER BY score desc FETCH FIRST 5 ROWS ONLY ");
            System.out.printf("%-30s %-30s %-30s", "PLAYER", "DATE AND TIME", "SCORE");
            System.out.println();;
            while (result.next()) {
                System.out.printf("%-30s %-30s %-30s", result.getString("player_name"), result.getString("date"), result.getDouble("score"));
                System.out.println();
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("DB error: ");
            e.printStackTrace();
        }
    }

}

