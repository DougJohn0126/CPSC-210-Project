package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test suite for Game
public class GameTest {

    private Game testGame1;
    private Game testGame2;

    @BeforeEach
    void runBefore() {
        testGame1 = new Game("Game1", "Action", 20);
        testGame2 = new Game("Game2", "RPG", 30);
    }

    @Test
    void testConstructor() {
        assertEquals("Game1", testGame1.getGameTitle());
        assertEquals("Action", testGame1.getGenre());
        assertEquals(20, testGame1.getLength());
        assertFalse(testGame1.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        Game testGame = new Game("Game1", "Action", 20);
        assertEquals(testGame, testGame1);
        assertEquals(testGame.hashCode(), testGame1.hashCode());
    }
    @Test
    void testEqualsAndHashCodeFail() {
        Game testGame = new Game("Game1", "Action", 20);
        assertFalse(testGame.equals(testGame2));
        assertFalse(testGame.equals("New-co"));
        assertFalse(testGame.equals(null));
        assertFalse(testGame.hashCode() == testGame2.hashCode());
    }

    @Test
    void testEqualsOtherBranches() {
        assertFalse(testGame1.equals("New-co"));
        assertFalse(testGame1.equals(null));
    }

    @Test
    void testEqualsCompareFieldsFail() {
        assertFalse(testGame1.equals(new Game("Game3", "Action", 20)));
        assertFalse(testGame1.equals(new Game("Game1", "Fighting", 20)));
        assertFalse(testGame1.equals(new Game("Game1", "Action", 100)));
        assertFalse(testGame1.equals(new Game("Game3", "Fighting", 100)));
    }
}
