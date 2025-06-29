package model;

import model.exceptions.GameCompletedException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;


// Represents a game with a title, genre, playtime, and status
public class Game implements Writable {

    private String title;                  // title of game
    private String genre;                  // genre of game
    private int length;                    // # of hours for completion
    private int playtime;                  // # of hours put in
    private boolean status;                // completion (true = completed, false = incomplete)


    // MODIFIES: this
    // EFFECTS: constructs a game with given title, genre, and play length; completion set to false
    public Game(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
        this.playtime = 0;
        this.status = false;
    }

    // MODIFIES: this
    // EFFECTS: constructs a game with given title, genre, play length, playtime, and status
    public Game(String title, String genre, int length, int playtime, Boolean status) {
        this.title = title;
        this.genre = genre;
        this.length = length;
        this.playtime = playtime;
        this.status = status;
    }

    // MODIFIES: this
    // EFFECTS: adds given amount of hours to Game if the status is false;
    //          if playtime exceeds play length after adding hours, sets status to true;

    public void addHours(Integer amount) throws GameCompletedException {
        if (this.status) {
            throw new GameCompletedException();
        } else {
            this.playtime += amount;
            EventLog.getInstance().logEvent(new Event(" Added " +  amount.toString() + " hours to "
                    + title));
            if (this.length <= this.playtime) {
                this.playtime = this.length;
                this.status = true;
                EventLog.getInstance().logEvent(new Event(" " + title + " is now completed"));
            }
        }
    }

    // EFFECTS: creates a new json backlog
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", title);
        json.put("genre", genre);
        json.put("length", length);
        json.put("playtime", playtime);
        json.put("status", status);
        return json;
    }

    // getters
    public String getGameTitle() {
        return this.title;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getLength() {
        return this.length;
    }

    public int getPlaytime() {
        return this.playtime;
    }

    public boolean getStatus() {
        return this.status;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return length == game.length && title.equals(game.title) && genre.equals(game.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, length);
    }
}
