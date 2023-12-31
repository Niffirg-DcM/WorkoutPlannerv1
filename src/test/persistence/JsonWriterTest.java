package persistence;

import model.Exercise;
import model.ListOfPlans;
import model.WorkOutPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfPlans lop = new ListOfPlans();
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
            ListOfPlans lop = new ListOfPlans();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLOP.json");
            writer.open();
            writer.write(lop);
            writer.close();

//            JsonReader reader = new JsonReader("./data/testWriterEmptyLOP.json");
//            lop = reader.read();
//            assertEquals(0, lop.getListOfPlans().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ListOfPlans lop = new ListOfPlans();
            WorkOutPlan wkp1 = new WorkOutPlan();
            WorkOutPlan wkp2 = new WorkOutPlan();
            wkp1.addExercise(new Exercise("Bench Press", 11));
            lop.addPlan(wkp1);
            wkp2.addExercise(new Exercise("Leg Press", 9));
            lop.addPlan(wkp2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLOP.json");
            writer.open();
            writer.write(lop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLOP.json");
            lop = reader.read();
            List<WorkOutPlan> wkp = lop.getListOfPlans();
            assertEquals(2, wkp.size());
            checkThingy("Bench Press", 11, wkp.get(0).getPlan().get(0));
            checkThingy("Leg Press", 9, wkp.get(1).getPlan().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}