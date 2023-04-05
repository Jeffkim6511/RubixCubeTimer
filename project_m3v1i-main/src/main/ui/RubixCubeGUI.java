package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.Trial;
import model.TrialList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RubixCubeGUI extends JFrame implements MouseListener, Runnable {
    //Fields
    private static final String JSON_STORE = "./data/workroom.json";
    private Trial current;
    private TrialList trials;
    private TrialList emptyTrials;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JFrame jframe;
    private JPanel mainPanel;
    private JPanel main;
    private JPanel timer;
    private JPanel results;
    private final int width = 700;
    int page = 0;

    //Buttons
    private JButton timerButton;
    private JButton resultsButton;
    private JButton deleteTrialsButton;
    private JButton deleteFilesButton;
    private JButton saveButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton loadButton;
    private JButton nextPageButton;
    private JButton backPageButton;
    private JButton resultBackButton;
    private JButton timerBackButton;
    private JButton anotherTrialButton;
    private JButton confirmButton;

    //Fields to create cardLayout and text fields
    private JTextField userText;
    private CardLayout cardLayout;
    private JLabel currentText = new JLabel("");


    //EFFECTS:runs the RubixCube application
    public RubixCubeGUI() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeFields();
        initializeGraphics();

    }

    //EFFECTS: creates a new trialList and an empty trialList
    private void initializeFields() {
        trials = new TrialList();
        emptyTrials = new TrialList();
    }

    //MODIFIES: This
    //EFFECTS: creates all the graphics
    private void initializeGraphics() {
        //initializes Window
        jframe = new JFrame();
        int height = 500;
        jframe.setMinimumSize(new Dimension(width, height));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        initializeMainMenuPanel();
        initializeTimerPanel();
        initializeResultsPanel();

        //creates cardLayout for frame
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(main, "main");
        mainPanel.add(timer, "timer");
        mainPanel.add(results, "results");
        jframe.add(mainPanel);

    }

    //MODIFIES: This
    //EFFECTS: Creates the graphics for Main Menu Panel
    private void initializeMainMenuPanel() {
        //initializes Panel
        ImageIcon image = new ImageIcon("./data/RubixCubeIcon.png");
        main = new JPanel();
        main.setLayout(null);

        JLabel label = new JLabel("Main Menu");
        label.setIcon(image);
        label.setFont(new Font("MV Boli",Font.BOLD, 40));
        label.setBounds(240,20,500,295);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setHorizontalTextPosition(JLabel.CENTER);
        main.add(label);

        createTimerAndResultsButtons();
        createFileButtons();
        addMainMenuButtons();
        main.add(currentText);
    }

    //MODIFIES: This
    //EFFECTS: Creates graphics for Timer Panel
    private void initializeTimerPanel() {
        //Initialize Timer Panel
        timer = new JPanel();
        timer.setLayout(null);

        //Create Graphics
        JLabel label = new JLabel("Timer");
        label.setFont(new Font("MV Boli",Font.BOLD, 40));
        label.setBounds(5,-20, 500, 100);
        timer.add(label);

        JLabel label2 = new JLabel("Name your trial:");
        label2.setBounds(5, 60, 200,25);
        timer.add(label2);

        userText = new JTextField(20);
        userText.setBounds(120,60,150,25);
        timer.add(userText);
        createTimerButtons();
    }

    //MODIFIES: This
    //EFFECTS: Refreshes the graphics for the Timer Panel
    private void refreshTimerPanel() {
        timer.removeAll();
        JLabel label = new JLabel("Timer");
        label.setFont(new Font("MV Boli",Font.BOLD, 40));
        label.setBounds(5,-20, 500, 100);
        timer.add(label);
        JLabel label2 = new JLabel("Name your trial:");
        label2.setBounds(5, 60, 200,25);
        timer.add(label2);
        userText = new JTextField(20);
        userText.setBounds(120,60,150,25);
        timer.add(userText);
        createTimerButtons();
        timer.repaint();
    }

    //MODIFIES: This
    //EFFECTS: Creates the Graphics for Results Panel
    private void initializeResultsPanel() {
        results = new JPanel();
        results.setLayout(null);
        JLabel label = new JLabel("Results");
        label.setFont(new Font("MV Boli",Font.BOLD, 40));
        label.setBounds(0,-20, 500, 100);
        results.add(label);
        createResultsButtons();
        printResults(page);
    }

    //MODIFIES: This
    //EFFECTS: Updates the Graphics for Results Panel
    private void updateResultsPanel(int page) {
        results.removeAll();
        JLabel label = new JLabel("Results");
        label.setFont(new Font("MV Boli",Font.BOLD, 40));
        label.setBounds(0,-20, 500, 100);
        results.add(label);
        createResultsButtons();
        printResults(page);
        results.repaint();
    }

    //MODIFIES: This
    //EFFECTS: Creates the buttons for Timer and Results in Main Menu Panel
    private void createTimerAndResultsButtons() {
        ImageIcon timerImg = new ImageIcon("./data/Timer.png");
        ImageIcon resultImg = new ImageIcon("./data/Result.png");
        timerButton = new JButton("Timer");
        timerButton.setBounds(20, 80, 225, 225);
        timerButton.addMouseListener(this);
        timerButton.setIcon(timerImg);
        timerButton.setHorizontalTextPosition(JLabel.CENTER);
        timerButton.setVerticalTextPosition(JLabel.TOP);
        timerButton.setFont(new Font("MV Boli",Font.PLAIN, 20));
        resultsButton = new JButton("Results");
        resultsButton.setBounds(455, 80, 225, 225);
        resultsButton.addMouseListener(this);
        resultsButton.setIcon(resultImg);
        resultsButton.setHorizontalTextPosition(JLabel.CENTER);
        resultsButton.setVerticalTextPosition(JLabel.TOP);
        resultsButton.setFont(new Font("MV Boli",Font.PLAIN, 20));
    }

    //MODIFIES: This
    //EFFECTS: Creates the file manipulation buttons for Main Menu Panel
    private void createFileButtons() {
        saveButton = new JButton("Save Trials");
        saveButton.setBounds(20, 320, 290, 50);
        saveButton.addMouseListener(this);
        loadButton = new JButton("Load Trials");
        loadButton.setBounds(390, 320, 290, 50);
        loadButton.addMouseListener(this);
        deleteTrialsButton = new JButton("Delete Trials");
        deleteTrialsButton.setBounds(390, 390, 290, 50);
        deleteTrialsButton.addMouseListener(this);
        deleteFilesButton = new JButton("Delete Files");
        deleteFilesButton.setBounds(20, 390, 290, 50);
        deleteFilesButton.addMouseListener(this);
    }

    //MODIFIES: This
    //EFFECTS: Adds the buttons for Main Menu Panel
    private void addMainMenuButtons() {
        main.add(timerButton);
        main.add(resultsButton);
        main.add(saveButton);
        main.add(loadButton);
        main.add(deleteFilesButton);
        main.add(deleteTrialsButton);
    }

    //MODIFIES: This
    //EFFECTS: creates and adds the buttons for Timer Panel
    private void createTimerButtons() {
        confirmButton = new JButton("Confirm");
        confirmButton.addMouseListener(this);
        confirmButton.setBounds(270,60,100,25);
        timer.add(confirmButton);
        timerBackButton = new JButton("Back");
        timerBackButton.addMouseListener(this);
        timerBackButton.setBounds(0,440,100,25);
        timer.add(timerBackButton);
    }

    //MODIFIES: This
    //EFFECTS: Creates the buttons for Results Panel
    private void createResultsButtons() {
        resultBackButton = new JButton("Back");
        resultBackButton.addMouseListener(this);
        resultBackButton.setBounds(0,440,100,25);
        results.add(resultBackButton);

    }

    //EFFECTS: Runs the components inside the frame and sets the window to be visible
    @Override
    public void run() {
        // Arrange the components inside the window
        jframe.pack();
        // By default, the window is not visible. Make it visible.
        jframe.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: saves and/or load files
    private void saveAndLoad(MouseEvent e) {
        if (e.getSource() == loadButton) {
            loadTrials();
        } else {
            saveTrials();
        }
    }

    //MODIFIES: This
    //EFFECTS: Updates the page and results panel
    private void changePage(MouseEvent e) {
        if (e.getSource() == nextPageButton) {
            updateResultsPanel(page += 1);
        } else {
            updateResultsPanel(page -= 1);
        }
        cardLayout.show(mainPanel, "results");
    }

    //MODIFIES: This
    //EFFECTS: Starts and stops the timer and its text
    private void changeTimerText(MouseEvent e) {
        if (e.getSource() == stopButton) {
            stopTimerText();
        } else {
            startTimerText();
        }
    }

    //MODIFIES: This
    //EFFECTS: deletes file and/or trial
    private void deleteFile(MouseEvent e) {
        if (e.getSource() == deleteFilesButton) {
            deleteTrials();
        } else {
            deleteCurrentTrials();
        }
    }

    //MODIFIES: This
    //EFFECTS: Displays time taken and stops timer
    private void stopTimerText() {
        current.stopTimer();
        JLabel label = new JLabel("and Timer Stopped");
        label.setBounds(94,300,200,25);
        timer.add(label);
        JLabel label2 = new JLabel("Your time was " + current.getTotalTimeInMilliSeconds() + " milliseconds!");
        label2.setBounds(5,330,500,25);
        timer.add(label2);
        JLabel label3 = new JLabel("Create another attempt?");
        label3.setBounds(5,360,500,25);
        timer.add(label3);
        anotherTrialButton = new JButton("New Attempt");
        anotherTrialButton.setBounds(5, 390, 195,50);
        anotherTrialButton.addMouseListener(this);
        timer.add(anotherTrialButton);
        timer.repaint();
    }

    //MODIFIES: This
    //EFFECTS: Starts timer and says timer has started
    private void startTimerText() {
        current.startTimer();
        JLabel label = new JLabel("Timer Started");
        label.setBounds(5,300,300,25);
        timer.add(label);
        timer.repaint();
    }


    //MODIFIES: This
    //EFFECTS: Informs if file has been saved or deleted, and if trialList has been saved or deleted
    private void changeCurrentDialogue(String key) {
        int centerWidth = width / 2;
        if (key.equals("deleteFiles")) {
            main.remove(currentText);
            currentText = new JLabel("File Deleted");
            main.add(currentText);
            currentText.setBounds(centerWidth - 35, 435, 200, 25);
        } else if (key.equals("deleteTrials")) {
            main.remove(currentText);
            currentText = new JLabel("Trials Deleted");
            main.add(currentText);
            currentText.setBounds(centerWidth - 35, 435, 200, 25);
        } else if (key.equals("load")) {
            main.remove(currentText);
            currentText = new JLabel("File loaded");
            main.add(currentText);
            currentText.setBounds(centerWidth - 35, 435, 200, 25);
        } else if (key.equals("saveFiles")) {
            main.remove(currentText);
            currentText = new JLabel("Trials saved");
            main.add(currentText);
            currentText.setBounds(centerWidth - 35, 435, 430, 25);
        }
    }


    //MODIFIES: This
    //EFFECTS: Deletes trials in trialList
    private void deleteCurrentTrials() {
        trials = new TrialList();
        changeCurrentDialogue("deleteTrials");
    }

    //MODIFIES: This
    //EFFECTS: deletes the trials from file
    private void deleteTrials() {
        try {
            jsonWriter.open();
            jsonWriter.write(emptyTrials);
            jsonWriter.close();
            System.out.println("Trials in file deleted successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to delete file:" + JSON_STORE);
        }

        changeCurrentDialogue("deleteFiles");
    }

    //MODIFIES: This
    //EFFECTS: saves the trials to file
    private void saveTrials() {
        try {
            jsonWriter.open();
            jsonWriter.write(trials);
            jsonWriter.close();
            System.out.println("Trials saved successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to " + JSON_STORE);
        }
        changeCurrentDialogue("saveFiles");
    }

    //MODIFES: This
    //EFFECTS: Loads trials from file
    private void loadTrials() {
        try {
            trials = jsonReader.read();
            System.out.println("Successfully loaded Trials");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        changeCurrentDialogue("load");
    }


    //MODIFIES: This
    //EFFECTS: Prints a summary of results from trial
    private void printResults(int page) {
        JLabel current;
        if (trials.trialSize() == 0) {
            current = new JLabel("There are no trials made yet.");
            current.setBounds(0,60,200, 25);
            results.add(current);
        } else {
            current = new JLabel("You have made " + trials.trialSize() + " attempts!");
            results.add(current);
            current.setBounds(0,60,200, 25);
            current = new JLabel("Your best time was: " + trials.findBestTime().getId());
            results.add(current);
            current.setBounds(200,60,200, 25);
            current = new JLabel("Your worst time was: " + trials.findWorstTime().getId());
            results.add(current);
            current.setBounds(400,60,200, 25);
            current = new JLabel("Your List of attempts:");
            current.setBounds(0,90,200, 25);
            results.add(current);
            printTrials(page);
        }
    }

    //MODIFIES: This
    //EFFECTS: Prints trials from index page * 10
    private void printTrials(int page) {
        int startingNumber = page * 10;

        if (trials.trialSize() - startingNumber >= 10) {
            generateText(10, startingNumber);
        } else {
            generateText(trials.trialSize() - startingNumber, startingNumber);
        }
        if (startingNumber != 0) {
            createBackPageButton();
        }
        if (trials.trialSize() > 10) {
            createNextPageButton();
        }
    }

    //MODIFIES: This
    //EFFECTS: Creates a pack page button
    private void createBackPageButton() {
        backPageButton = new JButton("Previous Page");
        backPageButton.addMouseListener(this);
        backPageButton.setBounds(120,440,130,25);
        results.add(backPageButton);
    }

    //MODIFIES: This
    //EFFECTS: prints text and generates trial results for results panel
    private void generateText(int end, int startNum) {
        JLabel currentLabel;
        Trial currentTrial;
        int x = 5;
        int y = 120;
        int w = 200;
        int h = 25;

        for (int i = 0; i < end; i++) {
            int tempInt = i + 1 + startNum;
            currentTrial = trials.getTrial(tempInt);
            currentLabel = new JLabel(currentTrial.getId() + ": "
                    + currentTrial.getTotalTimeInMilliSeconds() + " milliseconds");
            currentLabel.setBounds(x, y, w, h);
            results.add(currentLabel);
            y += 30;
        }
    }

    //MODIFIES: This
    //EFFECTS: creates a new next page Button and places it in results panel
    private void createNextPageButton() {
        nextPageButton = new JButton("Next Page");
        nextPageButton.addMouseListener(this);
        nextPageButton.setBounds(250,440,100,25);
        results.add(nextPageButton);
    }

    //MODIFIES: This
    //EFFECTS: The general timer that starts and stops
    private void startTimer(String name) {
        current = new Trial(name);
        trials.addTrial(current);
        updateTimer(current);
    }

    //MODIFIES: This
    //EFFECTS: Updates timer panel with new buttons and dialogue
    private void updateTimer(Trial current) {
        JLabel label = new JLabel(current.getId() +  " created successfully!");
        label.setBounds(5,90,1000,25);
        timer.add(label);
        JLabel label2 = new JLabel("Mixing formula:");
        label2.setBounds(5,120,100,25);
        timer.add(label2);
        JLabel label3 = new JLabel(current.getMixingFormula());
        label3.setBounds(5,150,1000,25);
        timer.add(label3);
        JLabel label4 = new JLabel("Press the button to begin and stop");
        label4.setBounds(5,180,1000,25);
        timer.add(label4);
        startButton = new JButton("Start");
        startButton.setBounds(5, 210, 220, 75);
        startButton.addMouseListener(this);
        timer.add(startButton);
        stopButton = new JButton("Stop");
        stopButton.setBounds(250, 210, 220, 75);
        stopButton.addMouseListener(this);
        timer.add(stopButton);
        timer.repaint();
    }

    //EFFECTS: when button is clicked, sends the input to proper method
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == saveButton || e.getSource() == loadButton) {
            saveAndLoad(e);
        } else if (e.getSource() == deleteFilesButton || e.getSource() == deleteTrialsButton) {
            deleteFile(e);
        } else if (e.getSource() == timerButton) {
            cardLayout.show(mainPanel, "timer");
        } else if (e.getSource() == resultsButton) {
            updateResultsPanel(0);
            cardLayout.show(mainPanel, "results");
        } else if (e.getSource() == resultBackButton || e.getSource() == timerBackButton) {
            refreshTimerPanel();
            cardLayout.show(mainPanel, "main");
        } else if (e.getSource() == confirmButton) {
            startTimer(userText.getText());
        } else if (e.getSource() == startButton || e.getSource() == stopButton) {
            changeTimerText(e);
        } else if (e.getSource() == anotherTrialButton) {
            refreshTimerPanel();
            cardLayout.show(mainPanel, "timer");
        } else if (e.getSource() == nextPageButton || e.getSource() == backPageButton) {
            changePage(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Not used
    }

    //EFFECTS: sends information that the button was released
    @Override
    public void mouseReleased(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Not Used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Not Used
    }
}

