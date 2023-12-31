package ui;

import model.Exercise;
import model.ListOfPlans;
import model.WorkOutPlan;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//Console version of App
public class Builder {
    private static final String JSON_STORE = "./data/ListOfPlans.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    ListOfPlans lop;

    //Modifies: ListOfPlans, WorkOutPlan, Exercise
    //Effects: starts an instance of the workout planner
    public Builder() {
        Scanner scan = new Scanner(System.in);
        lop = new ListOfPlans();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        String choice;
        String exit = "f";

        lop = optionToLoad();

        while (exit.equals("f")) {
            options();
            choice = scan.nextLine();

            exit = outcome(choice,lop);

        }
        optionToSave(lop);
    }

    private ListOfPlans optionToLoad() {
        Scanner scan = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Would you like to load a save? y/n");
            choice = scan.nextLine();

        } while (!(choice.equals("y") || choice.equals("n")));
        if (choice.equals("y")) {
            ListOfPlans lop = loadLOP();
            return lop;
        }
        System.out.println();
        return new ListOfPlans();
    }

    private void optionToSave(ListOfPlans lop) {
        Scanner scan = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Would you like to save before exiting? y/n");
            choice = scan.nextLine();

        } while (!(choice.equals("y") || choice.equals("n")));
        if (choice.equals("y")) {
            saveLOP(lop);
        }
    }

    // EFFECTS: saves the listofplans to file
    private void saveLOP(ListOfPlans lop) {
        try {
            jsonWriter.open();
            jsonWriter.write(lop);
            jsonWriter.close();
            System.out.println("Saved ListOfPlans to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads listofplans from file
    private ListOfPlans loadLOP() {
        try {
            ListOfPlans lop = jsonReader.read();
            System.out.println("Loaded ListOfPlans from " + JSON_STORE);
            return lop;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        return new ListOfPlans();
    }

    //Modifies: ListOfPlans, WorkOutPlan, Exercise
    //Effects: determines choice of user on what action they want to take on their workout plans
    private static String outcome(String choice, ListOfPlans lop) {

        switch (choice) {
            case "c":
                choiceC(lop);
                return "f";
            case "s":
                choiceS(lop);
                return "f";
            case "r":
                choiceR(lop);
                return "f";
            case "e":
                choiceE(lop);
                return "f";
            case "a":
                choiceA(lop);
                return "f";

            default:
                System.out.println("thanks for using this application!");
                return "t";
        }
    }

    //Effects: outputs a list of possible exercises for the user to view.
    private static void showOptions() {
        List<String> examples = new ArrayList<>(Arrays.asList("Squat", "Lunge", "Push-up", "Sit-up","Pull-up",
                "Deadlift", "Bent-over Row","Lateral Raise","Dumbell Flyes","Bench Press","Dumbell Press",
                "Shoulder Press", "Tricep Extension", "Bicep Curl", "Calve Raises"));
        System.out.println("Here are some example exercises to choose from, or make your own:");
        for (int i = 0; i < 12; i++) {
            System.out.println(examples.get(i) + "   " + examples.get(i + 1) + "   " + examples.get(i + 2));
        }

    }

    //Modifies: ListOfPlan, WorkOutPlan, Exercise
    //Effects: calls functions to choose the plan to add an exercise to and then lets to user decide the exercise.
    private static void choiceA(ListOfPlans lop) {
        int indexOfAddition;
        if (!lop.getListOfPlans().isEmpty()) {
            choiceS(lop);
            indexOfAddition = choosePlan(lop.getListOfPlans().size());
            showOptions();
            chooseExercise(lop.getListOfPlans().get(indexOfAddition));
        } else {
            System.out.println("nothing to add to!");
        }
    }

    //Modifies: WorkOutPlan
    //Effects: calls functions to choose the plan to remove an exercise from and then lets to user decide the exercise.
    // tells user if there is nothing to be removed.
    private static void choiceE(ListOfPlans lop) {
        int indexOfRemoval;
        String excToRemove;

        if (!lop.getListOfPlans().isEmpty()) {
            choiceS(lop);
            indexOfRemoval = choosePlan(lop.getListOfPlans().size());
            excToRemove = whichRemove(lop.getListOfPlans().get(indexOfRemoval));
            lop.removeExerciseFromPlan(indexOfRemoval,excToRemove);
        } else {
            System.out.println("nothing to remove!");
        }
    }

    //Modifies: ListOfPlans
    //Effects: calls functions to choose the plan to remove and tells user if there is nothing to be removed.
    private static void choiceR(ListOfPlans lop) {
        if (!lop.getListOfPlans().isEmpty()) {
            choiceS(lop);
            lop.removePlan(choosePlan(lop.getListOfPlans().size()));
        } else {
            System.out.println("nothing to remove!");
        }
    }

    //Effects: calls functions to show the plans already existing.
    private static void choiceS(ListOfPlans lop) {
        if (!lop.getListOfPlans().isEmpty()) {
            showListOfPlans(lop);
        } else {
            System.out.println("nothing to show!");
        }
    }

    //Modifies: ListOfPlans, WorkOutPlan, Exercise
    //Effects: calls function to show exercise possibilities to be added then creates a workout plan
    // to add an exercise to
    private static void choiceC(ListOfPlans lop) {
        showOptions();
        lop.addPlan(create());
    }

    //Effects: shows current plan and allows user to choose which exercise to remove.
    private static String whichRemove(WorkOutPlan wkp) {
        Scanner scan = new Scanner(System.in);
        String str;
        showPlan(wkp);
        System.out.println("Which Exercise should we remove? [enter its name]");
        str = scan.nextLine();
        return str;
    }

    //Effects: lets the user choose which workoutplan to select.
    private static int choosePlan(int max) {
        Scanner scan = new Scanner(System.in);
        int i;
        do {
            System.out.println("which plan would you like to select? [1 being the first plan]");
            i = scan.nextInt();
        } while ((i > max) || (i < 1));

        return (i - 1);
    }

    //Effects: shows all options to choose from in the program.
    private static void options() {
        System.out.println("Select your choice: ");
        System.out.println("Create a plan:   [c]");
        System.out.println("See the plans:   [s]");
        System.out.println("Remove a plan:   [r]");
        System.out.println("add an exercise: [a]");
        System.out.println("Remove exercise: [e]");
        System.out.println("Quit Planner:    [any other character]");
    }

    //Modifies: WorkOutPlan, Exercise
    //Effects: lets the user add any number of exercises
    private static WorkOutPlan create() {
        Scanner scan = new Scanner(System.in);
        WorkOutPlan wkp = new WorkOutPlan();
        String choice;
        System.out.println("Should we add an exercise? [y|n]");
        choice = scan.nextLine();

        while (choice.equals("y")) {
            System.out.println("Lets add an exercise!");
            chooseExercise(wkp);

            System.out.println("Should we add another exercise? [y|n]");
            choice = scan.nextLine();
        }
        return wkp;
    }

    //Modifies: WorkOutPlan, Exercise
    //Effects: Creates an exercise based on the user's specification of name and reps then adds it to the workoutplan.
    private static void chooseExercise(WorkOutPlan wkp) {
        Scanner scan = new Scanner(System.in);
        String name;
        int reps;
        System.out.println("what is the name of the exercise?");
        name = scan.nextLine();
        System.out.println("how many reps should we do?");
        reps = scan.nextInt();
        scan.nextLine();
        Exercise exs = new Exercise(name, reps);
        wkp.addExercise(exs);
    }

    //Effects:  outputs each workout plan within the list of plans and each element within them.
    // the workout plans are divided by the number of their plan in the list.
    public static void showListOfPlans(ListOfPlans lop) {
        for (int i = 0; i < lop.getListOfPlans().size(); i++) {
            System.out.println("Workout Plan " + (i + 1));
            showPlan(lop.getListOfPlans().get(i));
            System.out.println("");
        }
    }

    //Effects: prints a line for each element in the workout plan that states it's name and it's reps.
    public static void showPlan(WorkOutPlan wkp) {
        for (Exercise elem : wkp.getPlan()) {
            System.out.println("Do " + elem.getName() + " for " + elem.getReps() + " reps!");
        }
    }

}