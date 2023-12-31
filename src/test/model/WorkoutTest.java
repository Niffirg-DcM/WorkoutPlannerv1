package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkoutTest {

    private WorkOutPlan wkp1;
    private WorkOutPlan wkp2;
    private WorkOutPlan wkp3;
    private Exercise ex1;
    private Exercise ex2;
    private List<Exercise> exs;


    @BeforeEach
    public void runBefore() {
        ex1 = new Exercise("Bench Press",10);
        ex2 = new Exercise("Squat",8);
        exs = new ArrayList<>(Arrays.asList(ex1,ex2));
        wkp1 = new WorkOutPlan();
        wkp2 = new WorkOutPlan(ex1);
        wkp3 = new WorkOutPlan(exs);

    }

    @Test
    public void testsInitial() {
        assertEquals(emptyList(), wkp1.getPlan());
        assertEquals(Arrays.asList(ex1), wkp2.getPlan());
        assertEquals(Arrays.asList(ex1,ex2), wkp3.getPlan());
    }

    @Test
    public void testsChanges() {
        wkp1.addExercise(ex2);
        assertEquals(Arrays.asList(ex2), wkp1.getPlan());
        wkp1.removeExercise("Squat");
        assertEquals(emptyList(), wkp1.getPlan());
        wkp2.addExercise(ex1);
        assertEquals(Arrays.asList(ex1,ex1), wkp2.getPlan());
        wkp2.removeExercise("Squat");
        assertEquals(Arrays.asList(ex1,ex1), wkp2.getPlan());
    }

}