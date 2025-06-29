package persistence;

import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGame(String name, String category, Integer length, Integer playtime, Boolean status, Game game) {
        assertEquals(name, game.getGameTitle());
        assertEquals(category, game.getGenre());
        assertEquals(length, game.getLength());
        assertEquals(playtime, game.getPlaytime());
        assertEquals(status, game.getStatus());

    }
}