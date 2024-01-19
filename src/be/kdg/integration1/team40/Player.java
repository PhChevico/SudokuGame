package be.kdg.integration1.team40;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Player extends SharedEnvironmentConsumer {

    private String name;
    private String timePlayed;
    private double points;
    private String timeRemaining;


    public String getStartGameTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    //link to board timer
    public String getTimePlayed() {
        long timePlayed = (getEnv().getBoard().getGAME_LENGTH()) - (getEnv().getBoard().timeUntilEnd());
        return Long.toString(timePlayed);
    }

    public void setTimePlayed(String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(Database database, int points) {
        this.points = getEnv().getBoard().timeUntilEnd() * database.getMultiplier(getEnv().getBoard().getLevel()); // (time left * multiplier)
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", points=" + points +
                '}';
    }

    public void setTimeRemaining(String loadedTimeRemaining) {
        this.timeRemaining = loadedTimeRemaining;
    }
}
