package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import model.Game;
import model.Backlog;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Backlog bl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }
    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBacklog.json");
        try {
            Backlog bl = reader.read();
            assertEquals("Alan", bl.getName());
            assertEquals(135, bl.totalHours());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBacklog.json");
        try {
            Backlog bl = reader.read();
            assertEquals("Alan", bl.getName());
            List<Game> games = bl.getBacklog();
            assertEquals(2, games.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoomFail() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBacklog.json");
        try {
            Backlog bl = reader.read();
            assertEquals("Alan", bl.getName());
            List<Game> games = bl.getBacklog();
            assertEquals(2, games.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
        try {
            Backlog bl = reader.read();
            assertEquals("Alan", bl.getName());
            List<Game> games = bl.getBacklog();
            assertEquals(2, games.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
