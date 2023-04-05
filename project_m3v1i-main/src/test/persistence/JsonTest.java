package persistence;

import model.Trial;
import model.TrialList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkTrial(String name, String mf, long time, Trial trial) {
        assertEquals(name, trial.getId());
        assertTrue(trial.getMixingFormula().length() >= 49);
        assertEquals(time, trial.getTotalTime());
    }
}
