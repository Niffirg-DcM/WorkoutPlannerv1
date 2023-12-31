package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

//Is a list of workouts
public class ListOfPlans implements Writable {
    private List<WorkOutPlan> lop;

    public ListOfPlans() {
        lop = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created List of Plans"));
    }

    //Modifies: this
    //Effects:  adds a workout to the list of workout plans
    public void addPlan(WorkOutPlan wkp) {
        lop.add(wkp);
        EventLog.getInstance().logEvent(new Event("Added new Workout Plan"));
    }

    //Modifies: this
    //Effects:  removes a workout to the list of workout plans
    public void removePlan(int i) {
        lop.remove(i);
        EventLog.getInstance().logEvent(new Event("Removed Workout Plan " + i));
    }


    public List<WorkOutPlan> getListOfPlans() {
        return lop;
    }


    //Requires: int within bounds of 0 and the size of the list of workout plans and not null string
    //Modifies: this, WorkOutPlan
    //Effects:  outputs each workout plan within the list of plans and each element within them.
    // the workout plans are divided by the number of their plan in the list.
    public void removeExerciseFromPlan(int i, String str) {
        WorkOutPlan wkp = lop.get(i);
        wkp.removeExercise(str);
        lop.set(i,wkp);
    }

    //Effects: convert to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("WorkoutPlans", lopToJson());
        EventLog.getInstance().logEvent(new Event("Saved Lop"));
        return json;
    }

    // EFFECTS: returns things in this workoutplan as a JSON array
    private JSONArray lopToJson() {
        JSONArray jsonArray = new JSONArray();

        for (WorkOutPlan t : lop) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
