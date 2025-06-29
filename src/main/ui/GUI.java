package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

import model.Backlog;
import model.Event;
import model.EventLog;
import model.Game;
import model.GameSearcher;
import model.exceptions.GameAlreadyInException;
import model.exceptions.GameCompletedException;
import model.exceptions.GameNotFoundException;
import model.exceptions.UnrealGameException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


// Represents and creates the graphical user interface (GUI)
public class GUI extends JFrame implements ActionListener {

    private static final String csvPath = "./data/allNintendoGames.csv";
    private static final String JSON_STORE = "./data/backlog.json";

    private Backlog backlog;
    private GameSearcher library;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel title;
    private JPanel gameList;
    private JPanel fields;
    private JPanel buttons;
    private JPanel infoPanel;

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;

    private JRadioButton allButton;
    private JRadioButton incompleteButton;
    private JRadioButton completeButton;
    private ButtonGroup group;

    private Image img;

    private JLabel label;
    private JLabel image;
    private JLabel hoursTotal;
    private JLabel hoursPlayed;
    private JLabel gameInfo;
    private JLabel space;

    private Integer hours1;
    private Integer hours2;


    private JTextField addField;
    private JTextField addHoursAmountField;

    private DefaultListModel<String> data;
    private JList list;

    private Mode mode;

    // EFFECTS: creates a new GUI with different attributes
    public GUI() {
        super("Game App");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        library = new GameSearcher(csvPath);
        backlog = new Backlog("User");

        initializeAll();
        setMenu();
        addAll();
        setUp();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                for (Event e:  EventLog.getInstance()) {
                    System.out.println(e.getDate() + e.getDescription());
                }
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setGameInfo();
            }
        });
    }

    // EFFECTS: helper function to set up the GUI
    private void setMenu() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(580, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Game App");
        addLabel(welcomeLabel);
    }

    //EFFECTS: helper function to change options of GUI
    public void setUp() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        title.setVisible(true);
        allButton.setSelected(true);
        mode = Mode.AllGames;
    }

    // EFFECTS: helper to helper functions that set up the GUI
    private void initializeAll() {
        initializePanels();
        generateButtons();
        generateRadioButtons();
        generateLabels();
        generateFields();
        generateList();
    }

    // MODIFIES: this
    // EFFECTS: makes all panels
    public void initializePanels() {
        title = new JPanel();
        title.setBackground(Color.red);
        gameList = new JPanel();
        gameList.setBorder(new TitledBorder("Games"));
        gameList.setLayout(new GridLayout(1, 2));
        fields = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fields.setPreferredSize(new Dimension(90, 200));
        buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setPreferredSize(new Dimension(145, 200));
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setPreferredSize(new Dimension(500, 20));

    }

    // MODIFIES: this
    // EFFECTS: Creates buttons with titles and assigned actions
    public void generateButtons() {
        button1 = new JButton("Add Game");
        button1.setActionCommand("addGame");
        button1.addActionListener(this);

        button2 = new JButton("Remove Game");
        button2.setActionCommand("removeGame");
        button2.addActionListener(this);

        button3 = new JButton("Add Hours");
        button3.setActionCommand("addHours");
        button3.addActionListener(this);

        button4 = new JButton("Generate Hours");
        button4.setActionCommand("generateHours");
        button4.addActionListener(this);

        button5 = new JButton("Save Backlog");
        button5.setActionCommand("saveBacklog");
        button5.addActionListener(this);

        button6 = new JButton("Load Last Backlog");
        button6.setActionCommand("loadBacklog");
        button6.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates fields for addGame and addHours game respectfully
    public void generateFields() {
        addField = new JTextField(7);
        addHoursAmountField = new JTextField(2);
        addField.setPreferredSize(new Dimension(7,26));
        addHoursAmountField.setPreferredSize(new Dimension(7,26));
    }

    // EFFECTS: creates labels for visual component and total hours
    public void generateLabels() {
        image = new JLabel();
        imageSetter("./data/good.jpg");
        label = new JLabel("   ");
        hoursTotal = new JLabel("0 hours total");
        hoursPlayed = new JLabel("0 hours played");
        gameInfo = new JLabel("No game selected");
        space = new JLabel("          ");
    }

    // EFFECTS: creates list of game and assigns it to list in jPanel
    public void generateList() {
        data = new DefaultListModel<>();
        list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setPreferredSize(new Dimension(200, 200));
        gameList.add(list);
    }

    // EFFECTS: creates radio buttons and groups them for game sorting options
    public void generateRadioButtons() {
        allButton =  new JRadioButton("All Games");
        incompleteButton = new JRadioButton("Incomplete");
        completeButton = new JRadioButton("Completed");
        allButton.addActionListener(this);
        incompleteButton.addActionListener(this);
        completeButton.addActionListener(this);
        group = new ButtonGroup();
        group.add(allButton);
        group.add(incompleteButton);
        group.add(completeButton);
    }

    // MODIFIES: this
    // EFFECTS: adds label to the main menu panel
    public void addLabel(JLabel j1) {
        j1.setFont(new Font("ComicSans", Font.BOLD, 40));
        title.add(j1);
    }

    // MODIFIES: this
    // EFFECTS: helper function to add all buttons to GUI
    public void addAll() {
        add(title);
        add(gameList);

        fields.add(addField);
        fields.add(addHoursAmountField);
        fields.add(hoursTotal);
        fields.add(hoursPlayed);

        buttons.add(button1);
        buttons.add(button3);
        buttons.add(button2);
        buttons.add(button5);
        buttons.add(button6);

        infoPanel.add(gameInfo);

        add(fields);
        add(buttons);
        add(infoPanel);
        add(image);
        add(label);

        add(allButton);
        add(incompleteButton);
        add(completeButton);
    }


    // EFFECTS: calls the given methods when buttons are clicked on
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addGame")) {
            doAddGame(addField.getText());
            addField.setText("");
        }
        if (e.getActionCommand().equals("removeGame")) {
            doRemoveGame(String.valueOf(list.getSelectedValue()));
        }
        if (e.getActionCommand().equals("addHours")) {
            try {
                doAddHours((String) list.getSelectedValue(), Integer.valueOf(addHoursAmountField.getText()));
                addHoursAmountField.setText("");
            } catch (Exception g) {
                imageSetter("./data/bad.jpg");
                label.setText("Not valid number");
            }
        }
        if (e.getActionCommand().equals("saveBacklog")) {
            doSaveBacklog();
        }
        if (e.getActionCommand().equals("loadBacklog")) {
            doLoadBacklog();
        }
        radioButtons();
    }

    // EFFECTS: changes the display option for list of games depending on which radio
    //          button is chosen and calls function to display
    private void radioButtons() {
        if (allButton.isSelected()) {
            mode = Mode.AllGames;
        }
        if (incompleteButton.isSelected()) {
            mode = Mode.Incomplete;
        }
        if (completeButton.isSelected()) {
            mode = Mode.Complete;
        }
        displayNewBacklog();
    }

    // MODIFIES: this
    // EFFECTS: adds game with given name and if found in backlog; does nothing otherwise;
    //          sets visual component to respective images regardless
    private void doAddGame(String name) {
        Game game;
        try {
            game = library.findGame(name);
            backlog.addGame(game);
            label.setText("Game added!!");
            imageSetter("./data/good.jpg");
        } catch (UnrealGameException e) {
            label.setText("Game not found...");
            imageSetter("./data/bad.jpg");
        } catch (GameAlreadyInException e) {
            label.setText("Game already in..");
            imageSetter("./data/bad.jpg");
        }
    }


    //MODIFIES: this
    //EFFECTS: removes game with given name if found in backlog; otherwise does nothing
    private void doRemoveGame(String name) {
        try {
            backlog.removeGame(name);
            label.setText("Game removed");
        } catch (GameNotFoundException e) {
            label.setText("Game not found...");
            imageSetter("./data/bad.jpg");
        }
        displayNewBacklog();
    }

    //MODIFIES: this
    //EFFECTS: add hours with given name if found in backlog; otherwise does nothing
    private void doAddHours(String name, Integer hours) {
        try {
            Game game = backlog.findGame(name);
            game.addHours(hours);
            label.setText("Hours Added");
            imageSetter("./data/good.jpg");
        } catch (GameNotFoundException e) {
            label.setText("Game not found...");
            imageSetter("./data/bad.jpg");
        } catch (GameCompletedException e) {
            label.setText("Game already completed");
            imageSetter("./data/bad.jpg");
        }
        setGameInfo();

    }

    //MODIFIES: this
    //EFFECTS: filters list to reflect games in the backlog after command
    public void displayNewBacklog() {
        data.clear();
        switch (mode) {
            case Incomplete:
                for (Game g: backlog) {
                    if (g.getStatus() == false) {
                        data.addElement(g.getGameTitle());
                    }
                }
                break;
            case Complete:
                for (Game g: backlog) {
                    if (g.getStatus() == true) {
                        data.addElement(g.getGameTitle());
                    }
                }
                break;
            default:
                for (Game g: backlog) {
                    data.addElement(g.getGameTitle());
                }
                break;
        }
        doGenerateHours();
    }

    // MODIFIES: this
    // EFFECTS: changes hours label to reflect current time to finish games
    public void doGenerateHours()  {
        resetHours();
        switch (mode) {
            case Incomplete:
                for (Game g: backlog) {
                    if (g.getStatus() == false) {
                        rallyHours(g);
                    }
                }
                break;
            case Complete:
                for (Game g: backlog) {
                    if (g.getStatus() == true) {
                        rallyHours(g);
                    }
                }
                break;
            default:
                for (Game g: backlog) {
                    rallyHours(g);
                }
                break;
        }
        newHours();
    }

    // EFFECTS: helper function to reset hour counters in preparation of accumulating the new hours
    private void resetHours() {
        hours1 = 0;
        hours2 = 0;
    }

    // EFFECTS: sets labels to display new hours
    private  void newHours() {
        this.hoursTotal.setText(hours1 + " hours total");
        this.hoursPlayed.setText(hours2 + " hour played");
    }

    // EFFECTS: adds given Game's length and playtime respectively to variables
    private void rallyHours(Game g) {
        hours1 += g.getLength();
        hours2 += g.getPlaytime();
    }

    // EFFECTS: saves the backlog to file
    public void doSaveBacklog() {
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
    // EFFECTS: loads backlog from file
    public void doLoadBacklog() {
        try {
            backlog = jsonReader.read();
            System.out.println("Loaded " + backlog.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        list = new JList(data);
    }

    // MODIFIES: this
    // EFFECTS: sets image label to given file path image
    public void imageSetter(String path) {
        try {
            img = ImageIO.read(new File(path));
            Image modified = img.getScaledInstance(100, 100, 1);
            image.setIcon(new ImageIcon(modified));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: sets label to display info corresponding with game displayed
    private void setGameInfo() {
        if ((list.getSelectedValue()) == null) {
            gameInfo.setText("No selection");
        } else {
            try {
                Game temp = library.findGame((String) list.getSelectedValue());
                gameInfo.setText("Title: " + temp.getGameTitle() + "    Genre: " + temp.getGenre()
                        + "    Length: " + Integer.toString(temp.getLength())
                        + " hours    Hours put in: " + Integer.toString(temp.getPlaytime())
                        + " hours");
            } catch (UnrealGameException e) {
                System.out.printf("");
            }
        }
    }

    // enumerator for display mode
    public enum Mode {
        AllGames, Incomplete, Complete
    }

}