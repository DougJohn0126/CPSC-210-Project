package ui;

import model.Backlog;
import model.Game;
import model.GameSearcher;
import model.exceptions.GameAlreadyInException;
import model.exceptions.GameNotFoundException;
import model.exceptions.UnrealGameException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

//Game backlog application
public class GameApp {

    private static final String JSON_STORE = "./data/backlog.json";

    private Backlog backlog;
    private Scanner input;
    private GameSearcher library;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the game app
    public GameApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGameApp();
    }

    // MODIFIES: this
    // EFFECTS: initiates full library and processes user input
    private void runGameApp() {
        library = new GameSearcher("./data/allNintendoGames.csv");
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes backlog and inputs
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.print("Enter user name");
        String name = input.next();
        backlog = new Backlog(name);
        System.out.print("Welcome back: " + name);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add game");
        System.out.println("\tw -> withdraw");
        System.out.println("\tb -> see backlog");
        System.out.println("\th -> see hours");
        System.out.println("\ts -> save backlog to file");
        System.out.println("\tl -> load backlog from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddGame();
        } else if (command.equals("w")) {
            doWithdrawal();
        } else if (command.equals("b")) {
            printBacklog();
        } else if (command.equals("h")) {
            seeHours();
        } else if (command.equals("s")) {
            saveBacklog();
        } else if (command.equals("l")) {
            loadBacklog();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds game to library if it is in library
    private void doAddGame() {
        System.out.print("Enter name of game to add");
        String name = input.next();
        Game game;
        try {
            game = library.findGame(name);
            backlog.addGame(game);
            System.out.print("Game added!!");
        } catch (UnrealGameException e) {
            System.out.print("No game found");
        } catch (GameAlreadyInException e) {
            System.out.print("Game already in");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes game from backlog
    private void doWithdrawal() {
        System.out.print("Enter name of game to remove");
        String name = input.next();
        try {
            backlog.removeGame(name);
        } catch (GameNotFoundException e) {
            System.out.print("No game found");
        }

    }

    // EFFECTS: prints out all games in backlog
    private void printBacklog() {
        backlog.printGames();
    }

    // EFFECTS: prints out total number of hours to finish backlog
    private void seeHours() {
        System.out.print("Time to finish backlog: " + backlog.totalHours() + " hours");
    }

    // EFFECTS: saves the workroom to file
    private void saveBacklog() {
        try {
            jsonWriter.open();
            jsonWriter.write(backlog);
            jsonWriter.close();
            System.out.println("Saved " + backlog.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadBacklog() {
        try {
            backlog = jsonReader.read();
            System.out.println("Loaded " + backlog.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
