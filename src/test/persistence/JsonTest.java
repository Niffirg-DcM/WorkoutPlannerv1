package persistence;

import model.Exercise;
import model.WorkOutPlan;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkThingy(String name, int reps, Exercise exc) {
        assertEquals(name, exc.getName());
        assertEquals(reps, exc.getReps());
    }
}
