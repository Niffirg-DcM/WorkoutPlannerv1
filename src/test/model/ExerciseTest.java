package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExerciseTest {

    private Exercise ex1;

    @Test
    public void testsInitial() {
        assertNull(ex1);
    }

    @Test
    public void testsChanges() {
        ex1 = new Exercise("Bench Press",12);
        assertEquals("Bench Press",ex1.getName());
        assertEquals(12,ex1.getReps());
        assertEquals("          Bench Press : 12",ex1.convertToString());

    }

    @Test
    public void testJson() {
        ex1 = new Exercise("Bench Press",12);
        JSONObject json = ex1.toJson();
        assertEquals("Bench Press",json.get("name"));
        assertEquals(12,json.get("reps"));
    }
}
