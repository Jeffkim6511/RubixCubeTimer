package ui;

import model.Trial;
import model.TrialList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//RubixCube application
public class RubixCubeApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private Trial current;
    private TrialList trials;
    private TrialList emptyTrials;
    private Scanner input;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;


    //EFFECTS: runs the RubixCube application
    public RubixCubeApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRubixCube();

    }

    //EFFECTS: processes the users input and sets keepGoing to true
    private void runRubixCube() {
        boolean keepGoing = true;
        String command;

        start();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you soon!");
    }



    // MODIFIES: this
    // EFFECTS: initializes trials
    private void start() {
        trials = new TrialList();
        emptyTrials = new TrialList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    //EFFECTS: Displays the main menu and options.
    private void displayMainMenu() {
        System.out.println("\nWhat Would You Like to do Today?");
        System.out.println("\ts -> Start Timer");
        System.out.println("\tp -> See Past Results");
        System.out.println("\tz -> Save trials to file");
        System.out.println("\tx -> Load trials from file");
        System.out.println("\td -> Delete trials from file");
        System.out.println("\tf -> Delete current trials");
        System.out.println("\tq -> Quit");
        System.out.println("========================================================");
    }

    //EFFECTS: goes to different functions depending on what key is pressed
    private void processCommand(String command) {
        switch (command) {
            case "s":
                startTimer();
                break;
            case "p":
                startPastResults();
                break;
            case "z":
                saveTrials();
                break;
            case "x":
                loadTrials();
                break;
            case "d":
                deleteTrials();
                break;
            case "f":
                deleteCurrentTrials();

            default:
                System.out.println("Please choose another option");
                break;
        }
    }

    private void deleteCurrentTrials() {
        trials = new TrialList();
        System.out.println("Trials deleted");
    }

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
    }

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
    }

    //EFFECTS: Loads trials from file
    private void loadTrials() {
        try {
            trials = jsonReader.read();
            System.out.println("Successfully loaded Trials");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: Prints out a summary of past trials
    private void startPastResults() {
        if (trials.trialSize() == 0) {
            System.out.println("There are no trials made yet.");
        } else {
            boolean keepGoing = true;
            String key;
            printResults();
            for (int i = 0; i < trials.trialSize(); i++) {
                int current = i + 1;
                System.out.println("Attempt: " + trials.getTrial(current).getId());
                System.out.println("Time: " + trials.getTrial(current).getTotalTimeInMilliSeconds() + " Milliseconds");
            }
            System.out.println("Press r to return to main menu");
            while (keepGoing) {
                key = input.next();
                key = key.toLowerCase();

                if (key.equals("r")) {
                    keepGoing = false;
                } else {
                    System.out.println("Please press r");
                }
            }
        }
    }

    //EFFECTS: Prints a summary of results from trial
    private void printResults() {
        System.out.println("========================================================");
        System.out.println("You have made " + trials.trialSize() + " attempts!");
        System.out.println("Your best time was: " + trials.findBestTime().getId());
        System.out.println("Your worst time was: " + trials.findWorstTime().getId());
        System.out.println("========================================================");
        System.out.println("Your List of attempts:");
    }


    //EFFECTS: The general timer that starts and stops
    private void startTimer() {
        String key;
        String id;
        System.out.println("Please name the trial");
        key = input.next();
        key = key.toLowerCase();
        processIdCommand(key);
        id = turnKeyToId(key);
        System.out.println("Press 1 to start and 2 to stop the timer");
        System.out.println("========================================================");

        loop(key, id, "1");
        loop(key, id, "2");
    }

    //EFFECT: Loop to prevent user from pressing other keys and breaking timer
    public void loop(String key, String id, String option) {
        boolean keepGoing = true;
        while (keepGoing) {
            key = input.nextLine();
            if (key.equals(option)) {
                keepGoing = false;
            } else {
                System.out.println("Please press " + option);
            }
        }
        processTimeCommand(key, id);
    }

    //MODIFIES: TrialList
    //EFFECTS: Adds a new trial with id given by user and confirms the creation of a new trial
    private void processIdCommand(String key) {
        current = new Trial(key);
        trials.addTrial(current);
        System.out.println(key + " created Successfully!");
        System.out.println("========================================================");
        System.out.println("Mixing Formula:");
        System.out.println(current.getMixingFormula());
    }

    //EFFECTS: Turns the key into a string
    public String turnKeyToId(String key) {
        return key;
    }

    //EFFECTS: Starts and stops the timer for the specific trial and tells you how long it took
    private void processTimeCommand(String key, String id) {
        current = trials.getSpecificTrial(id);
        if (key.equals("1")) {
            current.startTimer();
            System.out.println("Timer Started");
            System.out.println("Press 2 to stop Timer");
        } else if (key.equals("2")) {
            current.stopTimer();
            System.out.println("Timer Stopped");
            System.out.println("You took " + current.getTotalTimeInMilliSeconds() + " Milliseconds!");
            System.out.println("or " + current.getTotalTimeInSeconds() + " Seconds!");
        } else {
            System.out.println("Please choose another option");

        }
    }
}
