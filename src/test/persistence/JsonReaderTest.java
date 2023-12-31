package persistence;

import model.ListOfPlans;
import model.WorkOutPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfPlans lop = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLOP.json");
        try {
            ListOfPlans lop = reader.read();
            assertEquals(emptyList(), lop.getListOfPlans());
            assertEquals(0, lop.getListOfPlans().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLOP.json");
        try {
            ListOfPlans lop = reader.read();
            List<WorkOutPlan> wkp = lop.getListOfPlans();
            assertEquals(2, wkp.size());
            checkThingy("Bench Press", 10, wkp.get(0).getPlan().get(1));
            checkThingy("Leg Press", 8, wkp.get(1).getPlan().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}