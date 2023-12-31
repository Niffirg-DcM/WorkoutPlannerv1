package persistence;

import model.Exercise;
import model.WorkOutPlan;
import model.ListOfPlans;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads listofplans from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfPlans read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfPlans(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses listofplans from JSON object and returns it
    private ListOfPlans parseListOfPlans(JSONObject jsonObject) {
        ListOfPlans lop = new ListOfPlans();
        addPlans(lop, jsonObject);
        return lop;
    }

    // MODIFIES: listofplans
    // EFFECTS: parses workoutplans from JSON object and adds them to listofplans
    private void addPlans(ListOfPlans lop, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("WorkoutPlans");
        for (Object json : jsonArray) {
            JSONObject nextWkp = (JSONObject) json;
            addExercises(lop, nextWkp);
        }
    }

    // MODIFIES: workoutplan
    // EFFECTS: parses workoutplans from JSON object and adds them to listofplans
    private void addExercises(ListOfPlans lop, JSONObject jsonObject) {
        WorkOutPlan wkp = new WorkOutPlan();
        JSONArray jsonArray = jsonObject.getJSONArray("ListOfExcercises");
        for (Object json : jsonArray) {
            JSONObject nextExc = (JSONObject) json;
            addExercise(wkp, nextExc);
        }
        lop.addPlan(wkp);
    }


    // MODIFIES: wkp
    // EFFECTS: parses exercise from JSON object and adds it to lop
    private void addExercise(WorkOutPlan wkp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int reps = jsonObject.getInt("reps");
        Exercise exc = new Exercise(name,reps);
        wkp.addExercise(exc);
    }
}
