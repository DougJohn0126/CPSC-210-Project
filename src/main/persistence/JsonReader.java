package persistence;

import model.Game;
import model.Backlog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.exceptions.GameAlreadyInException;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads backlog from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Backlog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBacklog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses backlog from JSON object and returns it
    private Backlog parseBacklog(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Backlog bl = new Backlog(name);
        addGames(bl, jsonObject);
        return bl;
    }

    // MODIFIES: bl
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addGames(Backlog bl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(bl, nextGame);
        }
    }

    // MODIFIES: bl
    // EFFECTS: parses game from JSON object and adds it to workroom
    private void addGame(Backlog bl, JSONObject jsonObject) {
        String title = jsonObject.getString("name");
        String genre = jsonObject.getString("genre");
        Integer length = jsonObject.getInt("length");
        Integer playtime = jsonObject.getInt("playtime");
        Boolean status = jsonObject.getBoolean("status");
        Game game = new Game(title, genre, length, playtime, status);
        bl.addGame(game);
    }
}
