package com.wuli.flabbergast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by liyawu on 19/8/17.
 */

public class GridGenerator {

    private static final String[][] DICE = {
            {"R", "I", "F", "O", "B", "X"},
            {"I", "F", "E", "H", "E", "Y"},
            {"D", "E", "N", "O", "W", "S"},
            {"U", "T", "O", "K", "N", "D"},
            {"H", "M", "S", "R", "A", "O"},
            {"L", "U", "P", "E", "T", "S"},
            {"A", "C", "I", "T", "O", "A"},
            {"Y", "L", "G", "K", "U", "E"},
            {"Qu", "B", "M", "J", "O", "A"},
            {"E", "H", "I", "S", "P", "N"},
            {"V", "E", "T", "I", "G", "N"},
            {"B", "A", "L", "I", "Y", "T"},
            {"E", "Z", "A", "V", "N", "D"},
            {"R", "A", "L", "E", "S", "C"},
            {"U", "W", "I", "L", "R", "G"},
            {"P", "A", "C", "E", "M", "D"},

    };

    public static ArrayList<String> generateGrid() {
        List<String[]> listOfDice = Arrays.asList(DICE);
        Collections.shuffle(listOfDice);

        ArrayList<String> grid = new ArrayList<>();
        for (String[] die : listOfDice) {
            Random randomizer = new Random();
            String random = die[randomizer.nextInt(6)];
            grid.add(random);
        }
        return grid;

    }
}
