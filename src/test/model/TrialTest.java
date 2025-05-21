package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrialTest {
    private Trial testTrial;
    private Trial testTrial2;
    long nanoToSeconds = 1000000000;
    long nanoToMilli = 1000000;

    @BeforeEach
    void runBefore() {
        testTrial = new Trial("1");
        testTrial2 = new Trial("2");
    }


    @Test
    void testConstructor() {
        assertEquals("1", testTrial.getId());
        assertTrue(testTrial.getMixingFormula().length()>= 49);
        assertEquals(0, testTrial.getStartTime());
    }

    @Test
    void testConstructor2() {
        assertEquals("2", testTrial2.getId());
        assertTrue(testTrial2.getMixingFormula().length()>= 49);
        assertEquals(0, testTrial2.getStartTime());
    }

    @Test
    void testRandomizeString() {
        String testString = testTrial.randomizeString();
        //String generation must be more than 49 characters long as at least 25 characters are being generated and
        //one space between each string.
        assertTrue(testString.length() >= 49 );
    }

    @Test
    void TestStartTimer() {
        testTrial.startTimer();
        long result = System.nanoTime() / nanoToMilli;
        assertEquals(result, testTrial.getStartTime() / nanoToMilli);
    }

    @Test
    void TestStopTimer() {
        testTrial.stopTimer();
        long result = System.nanoTime() / nanoToMilli;
        assertEquals(result, testTrial.getStopTime() / nanoToMilli);
    }

    @Test
    void TestGetId() {
        assertEquals("1", testTrial.getId());
    }

    @Test
    void TestGetMixingFormula() {
        assertTrue(testTrial.getMixingFormula().length() >= 49);
    }

    @Test
    void TestGetTotalTimeInMilliSeconds() {
        long result = (testTrial.getStopTime() - testTrial.getStopTime()) / nanoToMilli;
        assertEquals(result, testTrial.getTotalTimeInMilliSeconds());
    }

    @Test
    void TestGetTotalTimeInSeconds() {
        testTrial.getTotalTimeInSeconds();
        long result = (testTrial.getStopTime() - testTrial.getStopTime()) / nanoToSeconds;
        assertEquals(result, testTrial.getTotalTimeInSeconds());
    }

    @Test
    void TestGetStartTime() {
        testTrial.startTimer();
        long result = System.nanoTime();
        assertEquals(result / nanoToMilli, testTrial.getStartTime() / nanoToMilli);
    }

    @Test
    void TestGetStopTime() {
        testTrial.stopTimer();
        long result = System.nanoTime();
        assertEquals(result / nanoToMilli, testTrial.getStopTime() / nanoToMilli);
    }

    @Test
    void TestSetTimer() {
        testTrial.setTimer(300000000);
        assertEquals(300000000 /  nanoToMilli, testTrial.getTotalTimeInMilliSeconds());
    }

}