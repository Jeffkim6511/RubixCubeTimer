package model;


public class Timer {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean timeRunning = false;


    //MODIFIES: This
    //EFFECTS: Changes timeRunning to true and changes start time to the current system time.
    public void start() {
        this.startTime = System.nanoTime();
        this.timeRunning = true;
    }

    //MODIFIES: This
    //EFFECTS: Changes timeRunning to false and changes stop time to the current system time.
    public void stop() {
        this.stopTime = System.nanoTime();
        this.timeRunning = false;
    }

    //EFFECTS: Retrieves the current Elapsed time in milliseconds
    public long getElapsedMilliseconds() {
        long nanoSecondsPerMillisecond = 1000000;
        long elapsedTime;
        if (timeRunning) {
            elapsedTime = (System.nanoTime() - startTime);
        } else {
            elapsedTime = (stopTime - startTime);
        }
        return elapsedTime / nanoSecondsPerMillisecond;
    }

    //EFFECTS: Retrieves the current Elapsed time in seconds
    public long getElapsedSeconds() {
        long nanoSecondsPerSecond = 1000000000;
        long elapsedTime;
        if (timeRunning) {
            elapsedTime = (System.nanoTime() - startTime);
        } else {
            elapsedTime = (stopTime - startTime);
        }
        return elapsedTime / nanoSecondsPerSecond;
    }

    //EFFECTS: Retrieves the current Elapsed time in long
    public long getElapsedTime() {
        long elapsedTime;
        if (timeRunning) {
            elapsedTime = (System.nanoTime() - startTime);
        } else {
            elapsedTime = (stopTime - startTime);
        }
        return elapsedTime;
    }

    //EFFECT: gets the starting time of the timer
    public long getStartTime() {
        return startTime;
    }

    //EFFECT: gets the stopping time of the timer
    public long getStopTime() {
        return stopTime;
    }

    //EFFECT: gets the time elapsed from the timer
    public boolean isTimeRunning() {
        return timeRunning;
    }


    //MODIFIES: This
    //EFFECTS: Changes end time to make elapsed time the long number given.
    public void changeTime(long num) {
        this.startTime = 0;
        this.stopTime = num;
    }

    public void changeTimeRunning(boolean newValue) {
        this.timeRunning = newValue;
    }
}

