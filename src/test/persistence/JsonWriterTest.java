package persistence;

import model.Game;
import model.Backlog;
import model.exceptions.GameAlreadyInException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Backlog bl = new Backlog("Alan");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Backlog bl  = new Backlog("Alan");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBacklog.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBacklog.json");
            bl = reader.read();
            assertEquals("Alan", bl.getName());
            assertEquals(0, bl.totalHours());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Backlog bl = new Backlog("Alan");
            bl.addGame(new Game("Mario", "Platformer", 15));
            bl.addGame(new Game("Zelda", "Adventure", 120));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBacklog.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBacklog.json");
            bl = reader.read();
            assertEquals("Alan", bl.getName());
            List<Game> games = bl.getBacklog();
            assertEquals(2, games.size());
            checkGame("Mario", "Platformer" , 15, 0, false, games.get(0));
            checkGame("Zelda", "Adventure" , 120, 0, false, games.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (GameAlreadyInException e) {
            throw new RuntimeException(e);
        }
    }
}