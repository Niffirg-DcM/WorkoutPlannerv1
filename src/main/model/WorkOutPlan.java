package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Is a list of exercises
public class WorkOutPlan implements Writable {

    private List<Exercise> exerciseList;

    //Effects: creates a workout plan with a list of exercises.
    public WorkOutPlan(List<Exercise> exercises) {
        exerciseList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created new Workout Plan"));
        for (Exercise elem : exercises) {
            exerciseList.add(elem);
        }
    }

    //Effects: creates a workout plan with one exercise.
    public WorkOutPlan(Exercise exercise) {
        exerciseList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created new Workout Plan"));
        exerciseList.add(exercise);
    }

    //Effects: creates a workout plan with no exercises.
    public WorkOutPlan() {
        exerciseList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created new Workout Plan"));
    }

    //Modifies: this
    //Effects: adds a new exercise to the workout plan
    public void addExercise(Exercise newExercise) {
        exerciseList.add(newExercise);
        EventLog.getInstance().logEvent(new Event("Added " + newExercise.getName() + " : "
                + newExercise.getReps() + " to Workout Plan"));
    }

    //Requires: non-zero sized string
    //Modifies: this
    //Effects: remove an exercise by a specified name from the workout plan
    public void removeExercise(String findExercise) {
        for (int i = 0; i < exerciseList.size(); i++) {
            if (exerciseList.get(i).getName().equals(findExercise)) {
                exerciseList.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed an instance of" + findExercise
                        + " from Workout Plan"));
                i--;
            }
        }
    }

    public List<Exercise> getPlan() {
        return exerciseList;
    }

    //Effects: convert to String for GUI
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ListOfExcercises", exerciseList);
        return json;
    }

}
