package model;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.exceptions.UnrealGameException;

import static org.junit.jupiter.api.Assertions.fail;

// Represents a library of all games that represents all the game in the csv file
public class GameSearcher {

    private ArrayList<Game> library = new ArrayList<>();

    // MODIFIES: this
    // EFFECTS: constructs the GameSearcher by loading in data from the csv
    public GameSearcher(String path) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int i = 0;
            while ((line = br.readLine()) != null) {
                String [] values = line.split(",");
                if (values.length >= 6) {
                    library.add(new Game(values[0], values[2], (Integer.valueOf(values[5]))));
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: looks for game with matching string Arraylist and return it if found
    public Game findGame(String title) throws UnrealGameException {
        for (Game g : library) {
            if (g.getGameTitle().equals(title)) {
                return g;
            }
        }
        throw new UnrealGameException();
    }
}
