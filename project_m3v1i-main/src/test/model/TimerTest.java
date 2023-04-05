package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class TimerTest {
    private Timer testStopWatch;
    private Timer testStopWatch2;
    long nanoToSeconds = 1000000000;
    long nanoToMilli = 1000000;
    @BeforeEach
    void runBefore() {
        testStopWatch = new Timer();
        testStopWatch2 = new Timer();
    }

    //Divide the answers with units of milliseconds and seconds
    //to determine whether the same seconds is obtained in the system time.
    @Test
    void testStart() {
        testStopWatch.start();
        long test = testStopWatch.getStartTime();
        long result = System.nanoTime() / nanoToSeconds;
        assertEquals(result, test / nanoToSeconds);
        assertTrue(testStopWatch.isTimeRunning());
    }



    @Test
    void testStop() {
        testStopWatch.stop();
        long test = testStopWatch.getStopTime();
        long result = System.nanoTime() / nanoToSeconds;
        assertEquals(result, test / nanoToSeconds);
        assertFalse(testStopWatch.isTimeRunning());
    }

    @Test
    void testGetElapsedMillisecondsTimeNotRunning() {
        testStopWatch.stop();
        long result = (testStopWatch.getStopTime() - testStopWatch.getStartTime()) / nanoToMilli;
        assertEquals(result, testStopWatch.getElapsedMilliseconds());
    }

    @Test
    void testGetElapsedMillisecondsTimeRunning() {
        testStopWatch.start();
        long result = (System.nanoTime() - testStopWatch.getStartTime()) / nanoToMilli;
        assertEquals(result, testStopWatch.getElapsedMilliseconds());
    }

    @Test
    void testGetElapsedSecondsTimeNotRunning() {
        testStopWatch.stop();
        long result = (testStopWatch.getStopTime() - testStopWatch.getStartTime()) / nanoToSeconds;
        assertEquals(result, testStopWatch.getElapsedSeconds());
    }

    @Test
    void testGetElapsedSecondsTimeRunning() {
        testStopWatch.start();
        long result = (System.nanoTime() - testStopWatch.getStartTime()) / nanoToSeconds;
        assertEquals(result, testStopWatch.getElapsedSeconds());
    }

    @Test
    void testGetElapsedSecondsTimeRunningLargeDifference() {
        testStopWatch2.changeTime(100000000);
        long result = 100000000 / nanoToSeconds;
        assertEquals(result, testStopWatch2.getElapsedSeconds());
    }

    //ChangeTime is used to test timers
    @Test
    void TestChangeTime() {
        testStopWatch.changeTime(300000000);
        assertEquals(0, testStopWatch.getStartTime());
        assertEquals(300000000,testStopWatch.getStopTime());
        assertEquals(300000000 /  nanoToMilli, testStopWatch.getElapsedMilliseconds());
    }

    @Test
    void TestGetStartTime() {
        testStopWatch.start();
        long result = System.nanoTime() / nanoToMilli;
        assertEquals(result, testStopWatch.getStartTime() / nanoToMilli);
    }

    @Test
    void TestGetStopTime() {
        testStopWatch.stop();
        long result = System.nanoTime() / nanoToMilli;
        assertEquals(result, testStopWatch.getStopTime() / nanoToMilli);
    }


    @Test
    void TestGetTimeRunningTrue() {
        testStopWatch.start();
        assertTrue( testStopWatch.isTimeRunning());
    }

    @Test
    void TestGetTimeRunningFalse() {
        testStopWatch.stop();
        assertFalse( testStopWatch.isTimeRunning());
    }

    @Test
    void TestGetTimeElapsedInMillisecondsFalse() {
        testStopWatch.changeTimeRunning(false);
        testStopWatch.changeTime(300000000);
        assertEquals(0, testStopWatch.getStartTime());
        assertEquals(300000000,testStopWatch.getStopTime());
        assertEquals(300000000 /  nanoToMilli, testStopWatch.getElapsedMilliseconds());
    }

    @Test
    void TestGetTimeElapsedInMillisecondsTrue() {
        testStopWatch.changeTimeRunning(true);
        testStopWatch.changeTime(300000000);
        assertEquals(0, testStopWatch.getStartTime());
        assertEquals(300000000,testStopWatch.getStopTime());
        assertEquals((System.nanoTime()-testStopWatch.getStartTime()) / nanoToMilli, testStopWatch.getElapsedMilliseconds());
    }

}
