package persistence;

import org.json.JSONObject;

// Represents an interface that is implemented by Backlog and Game
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
