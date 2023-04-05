package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Random;

// Represents a Trial having an id made by user, a random mixing formula, and timer.
public class Trial implements Writable {

    private final String id;
    private String mixingFormula;
    private final Timer timer;
    private final String[] randomVariable = {"F", "B", "U", "D", "L", "R",
            "F'", "B'", "U'", "D'", "L'", "R'",
            "F2", "B2", "U2", "D2", "L2", "R2"};


    //REQUIRES: A String ID that has a length > 0
    //EFFECTS: Constructs a new Trial with a random set of strings for mixingFormula,
    //         a trial ID specified by the user, and a new timer.
    public Trial(String name) {
        this.id = name;
        this.mixingFormula = this.randomizeString();
        this.timer = new Timer();
    }

    //EFFECTS: Constructs a random sequence of 25 strings using the strings in randomVariable
    public String randomizeString() {
        StringBuilder resultSoFar = new StringBuilder();
        String randomString;
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            int number = random.nextInt(18);
            randomString = randomVariable[number];
            resultSoFar.append(" ").append(randomString);
        }
        return resultSoFar.toString();
    }

    //EFFECT: starts the timer
    public void startTimer() {
        timer.start();
    }

    //EFFECT: Stops the timer
    public void stopTimer() {
        timer.stop();
    }


    //EFFECT: gets ID
    public String getId() {
        return id;
    }

    //EFFECT: gets mixing formula
    public String getMixingFormula() {
        return mixingFormula;
    }

    //EFFECT: Returns the time elapsed in long
    public long getTotalTime() {
        return timer.getElapsedTime();
    }

    //EFFECT: Returns the time elapsed in milliseconds
    public long getTotalTimeInMilliSeconds() {
        return timer.getElapsedMilliseconds();
    }

    //EFFECT: Returns the time elapsed in seconds
    public long getTotalTimeInSeconds() {
        return timer.getElapsedSeconds();
    }

    //EFFECT: Returns the starting time
    public long getStartTime() {
        return timer.getStartTime();
    }

    //EFFECT: Returns Stopping time
    public long getStopTime() {
        return timer.getStopTime();
    }


    //MODIFIES: This
    //EFFECT: Sets time elapsed to a certain number
    //        *Mainly used for tests*
    public void setTimer(long num) {
        timer.changeTime(num);
    }


    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", id);
        json.put("Mixing Formula", mixingFormula);
        json.put("time", timer.getElapsedTime());
        return json;
    }

    //EFFECTS: Changes mixing formula to a chosen String
    //         *Mainly used for testing*
    public void setMixingFormula(String ml) {
        this.mixingFormula = ml;
    }
}



