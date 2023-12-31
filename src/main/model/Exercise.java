package model;

import org.json.JSONObject;
import persistence.Writable;

//is an exercise with a name and number of reps
public class Exercise implements Writable {

    private String name;
    private int reps;

    //Requires: non-zero sized string of name and non-zero int amount of reps
    //Effects: creates exercise with specified name and number of repetitions.
    public Exercise(String name, int reps) {
        this.name = name;
        this.reps = reps;
        EventLog.getInstance().logEvent(new Event("Created new Exercise: " + name + " : " + reps));
    }

    public String getName() {
        return this.name;
    }

    public int getReps() {
        return this.reps;
    }

    //Effects: convert exercise to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("reps", reps);
        return json;
    }

    //Effects: convert to String for GUI
    public String convertToString() {
        return "          " + name + " : " + reps;
    }
}
