package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfPlansTest {
    private ListOfPlans lop;
    private WorkOutPlan wkp1;
    private WorkOutPlan wkp2;
    private WorkOutPlan wkp3;
    private Exercise ex1;
    private Exercise ex2;

    @BeforeEach
    public void runBefore() {
        ex1 = new Exercise("Bench Press",10);
        ex2 = new Exercise("Squat",8);
        wkp1 = new WorkOutPlan();
        wkp2 = new WorkOutPlan(ex1);
        wkp3 = new WorkOutPlan(Arrays.asList(ex1,ex2));
        lop = new ListOfPlans();
    }

    @Test
    public void testInitial() {
        assertEquals(emptyList(), lop.getListOfPlans());
    }


    @Test
    public void testChangesOnLop() {
        lop.addPlan(wkp1);
        assertEquals(Arrays.asList(wkp1), lop.getListOfPlans());
        assertEquals(emptyList(),lop.getListOfPlans().get(0).getPlan());
        lop.addPlan(wkp2);
        assertEquals(Arrays.asList(wkp1,wkp2),lop.getListOfPlans());
        lop.removePlan(1);
        lop.addPlan(wkp3);
        assertEquals(Arrays.asList(wkp1,wkp3),lop.getListOfPlans());
        lop.removePlan(0);
        assertEquals(Arrays.asList(wkp3),lop.getListOfPlans());
        assertEquals(Arrays.asList(ex1,ex2),lop.getListOfPlans().get(0).getPlan());
        lop.removeExerciseFromPlan(0,"Bench Press");
        assertEquals(Arrays.asList(ex2),lop.getListOfPlans().get(0).getPlan());
    }

    @Test
    public void testChangesOnWkpInLop() {
        lop.addPlan(wkp3);
        assertEquals(Arrays.asList(ex1,ex2),lop.getListOfPlans().get(0).getPlan());
        lop.removeExerciseFromPlan(0,"Bench Press");
        assertEquals(Arrays.asList(ex2),lop.getListOfPlans().get(0).getPlan());
        lop.getListOfPlans().get(0).addExercise(ex2);
        assertEquals(Arrays.asList(ex2,ex2),lop.getListOfPlans().get(0).getPlan());
        lop.getListOfPlans().get(0).addExercise(ex1);
        assertEquals(Arrays.asList(ex2,ex2,ex1),lop.getListOfPlans().get(0).getPlan());
        lop.removeExerciseFromPlan(0,"Squat");
        assertEquals(Arrays.asList(ex1),lop.getListOfPlans().get(0).getPlan());
    }
}
