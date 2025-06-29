package model;

import model.exceptions.GameAlreadyInException;
import model.exceptions.GameCompletedException;
import model.exceptions.GameNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;


// Represents a backlog having an id, owner name and arraylist of games
public class Backlog implements Writable, Iterable<Game> {

    private int accountId;
    private String name;
    private ArrayList<Game> backlog;

    // MODIFIES: this, EventLog
    // EFFECTS: constructs a new backlog with random id, given name, and empty ArrayList of games
    public Backlog(String name) {
        this.accountId = ThreadLocalRandom.current().nextInt(0, 999 + 1);
        this.name = name;
        this.backlog = new ArrayList<Game>();
        EventLog.getInstance().logEvent(new Event(" Created new backlog"));
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds given game to backlog if it is not in backlog already
    public void addGame(Game game) throws GameAlreadyInException {
        if (this.backlog.contains(game)) {
            throw new GameAlreadyInException();
        }
        this.backlog.add(game);
        EventLog.getInstance().logEvent(new Event(" Added  " + game.getGameTitle()));
    }

    // MODIFIES: this, EventLog
    // EFFECTS: removes game with matching name from backlog if it is in backlog
    public void removeGame(String title) throws GameNotFoundException {
        Boolean notFound = true;
        Game removeGame = null;
        for (Game g : this.backlog) {
            if (g.getGameTitle().equals(title)) {
                notFound = false;
                removeGame = g;
            }
        }
        if (notFound) {
            throw new GameNotFoundException();
        }
        EventLog.getInstance().logEvent(new Event(" Removed  " + title));
        this.backlog.remove(removeGame);
    }

    // EFFECTS: return game with matching name in Arraylist of game; otherwise throws exception
    public Game findGame(String title) throws GameNotFoundException {
        for (Game g : this.backlog) {
            if (g.getGameTitle().equals(title)) {
                return g;
            }
        }
        throw new GameNotFoundException();
    }

    // MODIFIES: game,
    // EFFECTS: adds hours to game with matching name and status to completed if playtime > = length;
    //          otherwise throws exception
    public void addHours(String title, Integer hours) throws GameCompletedException, GameNotFoundException {
        Boolean notFound = true;
        for (Game g : this.backlog) {
            if (g.getGameTitle().equals(title)) {
                try {
                    g.addHours(hours);
                    notFound = false;
                } catch (GameCompletedException e) {
                    throw e;
                }
            }
        }
        if (notFound) {
            throw new GameNotFoundException();
        }
    }

    // EFFECTS: returns int that represents the total number of hours in backlog
    public int totalHours() {
        int i;
        int hours;
        hours = 0;
        for (i = 0; i <= (this.backlog.size() - 1); i++) {
            hours = hours + backlog.get(i).getLength();
        }
        EventLog.getInstance().logEvent(new Event(" Generated new total hours"));
        return hours;
    }

    // EFFECTS: prints all games in backlog
    public void printGames() {
        for (Game game: backlog) {
            System.out.println(game.getGameTitle());
        }
    }

    // EFFECTS: creates a new json backlog
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("accountId", accountId);
        json.put("games", gamesToJson());
        return json;
    }

    // EFFECTS: returns games in Arraylist of games as a JSON array
    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : backlog) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }

    // getters
    public int getAccountId() {
        return this.accountId;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Game> getBacklog() {
        return this.backlog;
    }

    // iterator
    @Override
    public Iterator<Game> iterator() {
        return backlog.iterator();
    }
}
