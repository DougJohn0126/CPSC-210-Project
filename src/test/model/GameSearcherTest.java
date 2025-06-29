package model;

import model.exceptions.UnrealGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test suite for GameSearcher
public class GameSearcherTest {

    private GameSearcher testLibrary;
    private GameSearcher testLibrarySuccess;
    private GameSearcher testLibraryFail;
    private Game testGame1;
    private Game testGame2;
    private String path;

    @BeforeEach
    void runBefore() {
        testLibrary = new GameSearcher("./data/allNintendoGames.csv");
        testGame1 = new Game("Game1", "Action", 20);
        testGame2 = new Game("Game2", "RPG", 30);
    }

    //DISCLAIMER!!!!!!!
    // Since the array sizes are dependent on the csv file, and have an arbitrary size,
    // I will resort to testing that the object's arraylists are longer than 0 when constructed.

    @Test
    void testGameSearcher() {
        testLibrarySuccess = new GameSearcher("./data/allNintendoGames.csv");
    }

    @Test
    void testGameSearcherFail() {
        testLibraryFail = new GameSearcher("notRealPath");
    }

    @Test
    void testFindGame() {
        Game game = new Game("Mario Kart 8 Deluxe", "Racing", 97);
        try {
            assertEquals(testLibrary.findGame("Mario Kart 8 Deluxe"), game);
        } catch (UnrealGameException e) {
            fail("Game was not found!");
        }
    }

    @Test
    void testFindGameFail() {
        Game game = new Game("Mario Kart 8 Deluxe", "Racing", 97);
        Boolean notFound = false;
        try {
            assertEquals(testLibrary.findGame("unreal game"), game);
            fail("Game was found!");
        } catch (UnrealGameException e) {
            notFound = true;
        }
        assertTrue(notFound);
    }
}

