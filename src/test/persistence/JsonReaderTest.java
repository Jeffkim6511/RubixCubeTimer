package persistence;

import model.TrialList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TrialList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTrialList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTrialList.json");
        try {
            TrialList tl = reader.read();
            assertEquals(0, tl.trialSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTrialList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTrialList.json");
        try {
            TrialList tl = reader.read();
            assertEquals(2, tl.trialSize());
            checkTrial("first", "B' R2 U' F2 R2 R2 D' R2 D2 D' R' D2 U L2 U' F2 L' F2 F2 D2 D D' U' B2 F2",
                    1000000000, tl.getTrial(1));
            checkTrial("second", "F2 D' L' L2 B2 D F' B' D' F2 D' B L F' R2 F2 B2 D' L2 B' F' F' L D B",
                    2000000000, tl.getTrial(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}