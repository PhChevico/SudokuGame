package be.kdg.integration1.team40;
public class Screen {
        public void generateWelcomeSudokuAscii() {

            System.out.println("__          ________ _      _____ ____  __  __ ______   _______ ____     _____ _    _ _____   ____  _  ___    _ \n" +
                    "\\ \\        / /  ____| |    / ____/ __ \\|  \\/  |  ____| |__   __/ __ \\   / ____| |  | |  __ \\ / __ \\| |/ / |  | |\n" +
                    " \\ \\  /\\  / /| |__  | |   | |   | |  | | \\  / | |__       | | | |  | | | (___ | |  | | |  | | |  | | ' /| |  | |\n" +
                    "  \\ \\/  \\/ / |  __| | |   | |   | |  | | |\\/| |  __|      | | | |  | |  \\___ \\| |  | | |  | | |  | |  < | |  | |\n" +
                    "   \\  /\\  /  | |____| |___| |___| |__| | |  | | |____     | | | |__| |  ____) | |__| | |__| | |__| | . \\| |__| |\n" +
                    "    \\/  \\/   |______|______\\_____\\____/|_|  |_|______|    |_|  \\____/  |_____/ \\____/|_____/ \\____/|_|\\_\\\\____/ \n");
        }

    public void sudokuRules() {
        System.out.println("                                       ____  _   _ _     _____ ____");
        System.out.println("                                       |  _ \\| | | | |   | ____/ ___| ");
        System.out.println("                                       | |_) | | | | |   |  _| \\___ \\ ");
        System.out.println("                                       |  _ <| |_| | |___| |___ ___) |");
        System.out.println("                                       |_| \\_\\\\___/|_____|_____|____/ \n\n");
        System.out.println("""
                "Sudoku is an engaging game with straightforward rules. Imagine a 9x9 grid that needs to be filled with
                numbers from 1 to 9. The challenge? No number can repeat within a row, column, or the smaller 3x3 squares
                within the grid. These smaller squares add an extra layer of complexity!
                                
                Feeling stuck? You have the option to type 'Solve' at any point in the game, and the board will be solved
                for you. Keep in mind, though, that using this option won't earn you any points. It's a helpful tool when
                you're stuck and looking for a way forward."
                """);

    }

    public void sudokuMenu() {

        System.out.println("                                       _ __ ___   ___ _ __  _   _        ");
        System.out.println("                                       | '_ ` _ \\ / _ \\ '_ \\| | | | ");
        System.out.println("                                       | | | | | |  __/ | | | |_| | ");
        System.out.println("                                       |_| |_| |_|\\___|_| |_|\\__,_|");


        System.out.println("                          ********************************************************");
        System.out.println("                          *   Type LEVEL to start playing and choose your level  *");
        System.out.println("                          *      Type LEADERBOARD to see the leaderboard         *");
        System.out.println("                          *            Type RULES to see the rules               *");
        System.out.println("                          * Type LOAD if you want to keep playing an old session *");
        System.out.println("                          *            Type EXIT to exit the game                *");
        System.out.println("                          ********************************************************");
    }
}
