package be.kdg.integration1.team40;

import java.util.Arrays;

public class Box {
    private final int boxHeight = 3;
    private final int boxWidth = 3;
    private  int [][]grid = new int[boxHeight][boxWidth];

    @Override
    public String toString() {
        return "Box{" +
                "boxHeight=" + boxHeight +
                ", boxWidth=" + boxWidth +
                ", grid=" + Arrays.toString(grid) +
                '}';
    }
}
