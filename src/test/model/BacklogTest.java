package model;

import model.exceptions.GameAlreadyInException;
import model.exceptions.GameCompletedException;
import model.exceptions.GameNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// test suite for backlog
class BacklogTest {

    private Backlog testBacklog;
    private Game testGame1;
    private Game testGame2;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void runBefore() {
        testBacklog = new Backlog("Felix");
        testGame1 = new Game("Game1", "Action", 20);
        testGame2 = new Game("Game2", "RPG", 30);
        try {
            testBacklog.addGame(testGame1);
            assertTrue(testBacklog.getBacklog().contains(testGame1));
            assertEquals(1, testBacklog.getBacklog().size());
        } catch (GameAlreadyInException e) {
            fail("Game should've been added!");
        }
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testConstructor() {
        assertEquals("Felix", testBacklog.getName());
        assertTrue(testBacklog.getAccountId() > 0);
        ArrayList<Game> listOfGame = new ArrayList<Game>();
        listOfGame.add(testGame1);
        assertEquals(listOfGame, testBacklog.getBacklog());
    }

    @Test
    void testAddMultipleGames() {
        try {
            testBacklog.addGame(testGame2);
            assertTrue(testBacklog.getBacklog().contains(testGame2));
            assertEquals(2, testBacklog.getBacklog().size());
        } catch (GameAlreadyInException e) {
            fail("Game should've been added!");
        }
    }

    @Test
    void testAddSameGame() {
        try {
            testBacklog.addGame(testGame1);
            fail("Game should not have been added");
        }catch (GameAlreadyInException e) {
            assertTrue(testBacklog.getBacklog().contains(testGame1));
            assertEquals(1, testBacklog.getBacklog().size());
        }
    }

    @Test
    void testRemoveGame() {
        try {
            testBacklog.removeGame(testGame1.getGameTitle());
            assertFalse(testBacklog.getBacklog().contains(testGame1));
            assertEquals(0, testBacklog.getBacklog().size());
        } catch (GameNotFoundException e) {
            fail("Game should've been removed!");
        }
    }

    @Test
    void testRemoveGameFail() {
        try {
            testBacklog.removeGame(testGame2.getGameTitle());
            fail("Game should've not been removed!");
        }catch (GameNotFoundException e){
            assertTrue(testBacklog.getBacklog().contains(testGame1));
            assertEquals(1, testBacklog.getBacklog().size());
        }
    }

    @Test
    void addHoursGame() {
        try {
            assertEquals (testBacklog.findGame("Game1").getPlaytime(), 0);
        } catch (GameNotFoundException e) {
            fail("Game should've been found!");
        }

        try {
            testBacklog.addHours("Game1", 5);
        } catch (GameCompletedException e) {
            fail("Hours should've been added!");
        } catch (GameNotFoundException e) {
            fail("Hours should've been added!");
        }

        try {
            assertEquals (testBacklog.findGame("Game1").getPlaytime(), 5);
        } catch (GameNotFoundException e) {
            fail("Game should've been found!");
        }
    }

    @Test
    void addHoursGameNotFoundFail() {
        try {
            assertEquals (testBacklog.findGame("Game1").getPlaytime(), 0);
        } catch (GameNotFoundException e) {
            fail("Game should've been found!");
        }

        try {
            testBacklog.addHours("UnrealGame", 5);
            fail("Hours should've not been added");
        } catch (GameCompletedException e) {
            fail("Wrong Exception");
        } catch (GameNotFoundException e) {
            // all good!
        }
        try {
            assertEquals (testBacklog.findGame("Game1").getPlaytime(), 0);
        } catch (GameNotFoundException e) {
            fail("Game should've been found!");
        }
    }

    @Test
    void addHoursGameCompletedFail() {
        try {
            assertEquals (testBacklog.findGame("Game1").getPlaytime(), 0);
        } catch (GameNotFoundException e) {
            fail("Game should've been found!");
        }

        try {
            testBacklog.addHours("Game1", 20);
        } catch (GameCompletedException e) {
            fail("Hours should've been added!");
        } catch (GameNotFoundException e) {
            fail("Hours should've been added!");
        }
        try {
            assertEquals (testBacklog.findGame("Game1").getPlaytime(), 20);
        } catch (GameNotFoundException e) {
            fail("Game should've been found!");
        }
        try {
            testBacklog.addHours("Game1", 5);
            fail("Hours should've not been added");
        } catch (GameCompletedException e) {
            // all good!
        } catch (GameNotFoundException e) {
            fail("Wrong Exception!");
        }
    }

    @Test
    void testFindGameFail() throws GameAlreadyInException {
        try{
            testBacklog.findGame("Unreal Game");
            fail ("Game should've not been found!");
        } catch (GameNotFoundException e) {
           //all good!
        }
    }

    @Test
    void testTotalHours() {
        assertEquals(20, testBacklog.totalHours());
        try {
            testBacklog.addGame(testGame2);
            assertTrue(testBacklog.getBacklog().contains(testGame2));
            assertEquals(2, testBacklog.getBacklog().size());
        } catch (GameAlreadyInException e) {
            fail("Game should've been added!");
        }
        assertEquals(50, testBacklog.totalHours());
    }
    @Test
    void testPrintGames() {
        testBacklog.printGames();
        assertEquals("Game1", outputStreamCaptor.toString().trim());
    }

    @Test
    void testIterator() {
        testBacklog.addGame(testGame2);
        int i = 0;
        for (Game g: testBacklog) {
            i++;
        }
        assertEquals(i, 2);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}