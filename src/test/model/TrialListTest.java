package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TrialListTest {
    public TrialList testTrials;
    public TrialList testTrialsOne;
    public TrialList emptyTestTrials;
    private Trial testTrial1;
    private Trial testTrial2;
    private Trial testTrial3;
    private Trial testTrial4;
    private Trial testTrial5;

    @BeforeEach
    void runBefore() {
        emptyTestTrials = new TrialList();
        testTrials = new TrialList();
        testTrial1 = new Trial("1");
        testTrial2 = new Trial("2");
        testTrial3 = new Trial("3");
        testTrial4 = new Trial("4");
        testTrial5 = new Trial("5");
        testTrials.addTrial(testTrial1);
        testTrials.addTrial(testTrial2);
        testTrials.addTrial(testTrial3);
        testTrials.addTrial(testTrial4);
        testTrials.addTrial(testTrial5);
    }

    @Test
    void TestConstructorEmpty() {
        assertEquals(0,emptyTestTrials.trialSize());
    }

    @Test
    void TestConstructorWithValues() {
        assertEquals(5,testTrials.trialSize());
    }


    @Test
    void TestGetTrialFirstObject() {
        Trial result = testTrial1;
        assertEquals(result , testTrials.getTrial(1));
        assertEquals(result.getId(), testTrials.getTrial(1).getId());
    }

    @Test
    void TestGetTrialMiddleObject() {
        Trial result = testTrial3;
        assertEquals(result , testTrials.getTrial(3));
        assertEquals(result.getId(), testTrials.getTrial(3).getId());
    }

    @Test
    void TestGetTrialLastObject() {
        Trial result = testTrial5;
        assertEquals(result , testTrials.getTrial(5));
        assertEquals(result.getId(), testTrials.getTrial(5).getId());
    }

    @Test
    void TestGetSpecificTrialFirstInList() {
        Trial result = testTrial1;
        assertEquals(result , testTrials.getSpecificTrial("1"));
    }

    @Test
    void TestGetSpecificTrialMiddleInList() {
        Trial result = testTrial3;
        assertEquals(result , testTrials.getSpecificTrial("3"));
    }

    @Test
    void TestGetSpecificTrialLastInList() {
        Trial result = testTrial5;
        assertEquals(result , testTrials.getSpecificTrial("5"));
    }

    @Test
    void TestFindBestTimeAllSameTimes() {
        Trial result = testTrial1;
        assertEquals(result , testTrials.findBestTime());
    }

    @Test
    void TestFindBestTimeAllDifferentTimes() {
        testTrial1.setTimer(100000000);
        testTrial2.setTimer(200000000);
        testTrial3.setTimer(300000000);
        testTrial4.setTimer(400000000);
        testTrial5.setTimer(500000000);
        assertEquals(testTrial1, testTrials.findBestTime());
    }

    @Test
    void TestFindBestTimeTwoSameTimes() {
        testTrial1.setTimer(100000000);
        testTrial2.setTimer(200000000);
        testTrial3.setTimer(100000000);
        testTrial4.setTimer(400000000);
        testTrial5.setTimer(500000000);
        assertEquals(testTrial1, testTrials.findBestTime());
    }

    @Test
    void TestFindWorstTimeAllSameTimes() {
        Trial result = testTrial1;
        assertEquals(result , testTrials.findWorstTime());
    }

    @Test
    void TestFindWorstTimeAllDifferentTimes() {
        testTrial1.setTimer(100000000);
        testTrial2.setTimer(200000000);
        testTrial3.setTimer(300000000);
        testTrial4.setTimer(400000000);
        testTrial5.setTimer(500000000);
        assertEquals(testTrial5, testTrials.findWorstTime());
    }

    @Test
    void TestFindWorstTimeTwoSameTimes() {
        testTrial1.setTimer(500000000);
        testTrial2.setTimer(200000000);
        testTrial3.setTimer(100000000);
        testTrial4.setTimer(400000000);
        testTrial5.setTimer(500000000);
        assertEquals(testTrial1, testTrials.findWorstTime());
    }

    @Test
    void TestAddTrial() {
        testTrialsOne = new TrialList();
        testTrialsOne.addTrial(testTrial1);
        assertEquals(1, testTrialsOne.trialSize());
    }

    @Test
    void TestTrialSizeEmpty() {
        assertEquals(0, emptyTestTrials.trialSize());
    }

    @Test
    void TestTrialSizeOne() {
        testTrialsOne = new TrialList();
        testTrialsOne.addTrial(testTrial1);
        assertEquals(1, testTrialsOne.trialSize());
    }

    @Test
    void TestTrialSizeFive() {
        assertEquals(5, testTrials.trialSize());
    }
}
