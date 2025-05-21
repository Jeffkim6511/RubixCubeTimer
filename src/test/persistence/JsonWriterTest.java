package persistence;

import model.Trial;
import model.TrialList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            TrialList tl = new TrialList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            tl = reader.read();
            assertEquals(0, tl.trialSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            TrialList tl = new TrialList();
            tl.addTrial(new Trial("First"));
            tl.addTrial(new Trial("Second"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            tl = reader.read();
            assertEquals("First", tl.getSpecificTrial("First").getId());
            assertTrue(tl.getSpecificTrial("First").getMixingFormula().length() > 49);
            assertEquals(0, tl.getSpecificTrial("First").getTotalTime());
            assertEquals(2, tl.trialSize());
            checkTrial("First", "F2 D' L' L2 B2 D F' B' D' F2 D' B L F' R2 F2 B2 D' L2 B' F' F' L D B",
                    0, tl.getTrial(1));
            checkTrial("Second", "B' R2 U' F2 R2 R2 D' R2 D2 D' R' D2 U L2 U' F2 L' F2 F2 D2 D D' U' B2 F2",
                    0, tl.getTrial(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}