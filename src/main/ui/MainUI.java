package ui;

import model.*;
import persistence.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

import static java.util.Arrays.asList;


//Main Gui Frame.
public class MainUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/ListOfPlans.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel panel;
    private ListOfPlans lop;

    //Effects: Constructor creates frame and panel to allow all GUI
    public MainUI() {
        lop = new ListOfPlans();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        frame = new JFrame();
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new GridLayout(0,1));

        addButtonPanel();

        frame.setPreferredSize(new Dimension(350,500));
        frame.add(panel,BorderLayout.CENTER);
        frame.addWindowListener(new WindowEventHandler());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setTitle("Workout Planner GUI");
        frame.pack();
        frame.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: creates text fields and labels and buttons then adds them to JPanel
    private void addButtonPanel() {
        List<JButton> list = new ArrayList<>();
        JButton btnDisplay = new JButton("Display List of Workout Plans");
        btnDisplay.setActionCommand("Display");
        list.add(btnDisplay);
        JButton btnAddRem = new JButton("Add/Remove an Existing Workout Plan");
        btnAddRem.setActionCommand("AddRem");
        list.add(btnAddRem);
        JButton btnModify = new JButton("Modify an Existing Workout Plan");
        btnModify.setActionCommand("Modify");
        list.add(btnModify);
        JButton btnLoad = new JButton("Load the Saved List of Workout Plans");
        btnLoad.setActionCommand("Load");
        list.add(btnLoad);
        JButton btnSave = new JButton("Save the List of Workout Plans");
        btnSave.setActionCommand("Save");
        list.add(btnSave);
        JButton btnFlex = new JButton("Flex!!!");
        btnFlex.setActionCommand("Flex");
        list.add(btnFlex);

        for (JButton elem : list) {
            elem.addActionListener(this); // Sets "this" object as an action listener for btn
            panel.add(elem);
        }
    }


    //MODIFIES ListOfPlans
    // EFFECTS: when the btn is clicked,
    // this.actionPerformed(ActionEvent e) will be called.
    // You could also set a different object, if you wanted
    // a different object to respond to the button click
    //This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Display")) {
            new ListOfPlansPanel(lop);
        } else if (e.getActionCommand().equals("AddRem")) {
            new AddRemovePlan();
        } else if (e.getActionCommand().equals("Modify")) {
            new EditLop();
        } else if (e.getActionCommand().equals("Load")) {
            lop = loadLOP();
        } else if (e.getActionCommand().equals("Save")) {
            saveLOP(lop);
        } else if (e.getActionCommand().equals("Flex")) {
            new Flex();
        }
    }



    // EFFECTS: saves the ListOfPlans to file
    private void saveLOP(ListOfPlans lop) {
        try {
            jsonWriter.open();
            jsonWriter.write(lop);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame,"Saved ListOfPlans to file:  " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame,"Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: ListOfPlans
    // EFFECTS: loads ListOfPlans from file, shows PopUp for confirmation or denial.
    private ListOfPlans loadLOP() {
        try {
            ListOfPlans lop = jsonReader.read();
            JOptionPane.showMessageDialog(frame,"Loaded from file: " + JSON_STORE);
            return lop;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,"Unable to read from file: " + JSON_STORE);
            System.out.println();
        }
        return new ListOfPlans();
    }


    //Class allows for addition and removal of workout plans from list of plans.
    public class AddRemovePlan implements ActionListener {
        private JFrame frame;
        private JPanel panel;
        private JTextArea text1;
        private JLabel label1;


        //MODIFIES: this
        //EFFECTS: allows for addition and removal of workout plans from
        // list of plans, shows PopUp for confirmation or denial.
        public AddRemovePlan() {
            frame = new JFrame();
            panel = new JPanel();

            panel.setBorder(BorderFactory.createEmptyBorder(30,10,30,10));
            panel.setLayout(new GridLayout(2,2));

            createTextLabelButtons();


            frame.setSize(800,800);
            frame.setLocation(740,0);
            frame.add(panel,BorderLayout.CENTER);

            frame.setTitle("Add or Remove Workout Plans");
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        }

        //MODIFIES: this
        //EFFECTS: creates text fields and labels and buttons then adds them to JPanel
        private void createTextLabelButtons() {
            text1 = new JTextArea("Enter Workout Number");
            label1 = new JLabel("Workout to be Removed:");
            JButton btnAdd = new JButton("Add New Workout");
            btnAdd.setActionCommand("add");
            btnAdd.addActionListener(this);
            JButton btnRemove = new JButton("Remove Workout");
            btnRemove.setActionCommand("remove");
            btnRemove.addActionListener(this);
            panel.add(label1);
            panel.add(text1);
            panel.add(btnAdd);
            panel.add(btnRemove);
        }

        //MODIFIES ListOfPlans, WorkoutPlan
        // EFFECTS: when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click
        //This is the method that is called when the JButton btn is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), "add")) {
                addPlan();
                new ListOfPlansPanel(lop);

            } else if (Objects.equals(e.getActionCommand(), "remove")) {
                removePlan();
                new ListOfPlansPanel(lop);

            }


        }

        // MODIFIES: Workout, ListOfPlans
        // EFFECTS: removes workout from list of plans, pop up for confirmation or denial
        private void removePlan() {
            int indexOfRemoval;

            if (!lop.getListOfPlans().isEmpty()) {
                try {
                    indexOfRemoval = Integer.parseInt(text1.getText()) - 1;
                    lop.removePlan(indexOfRemoval);
                    JOptionPane.showMessageDialog(frame, "Workout Removed!");
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(frame, "Incorrect Inputs!");
                }
            } else {
                JOptionPane.showMessageDialog(frame,"Nothing to remove!");
            }
        }

        // MODIFIES: Workout, ListOfPlans
        // EFFECTS: creates workout and adds it to list of plans, pop up for confirmation and further instructions
        private void addPlan() {
            WorkOutPlan wkp = new WorkOutPlan();
            lop.addPlan(wkp);
            JOptionPane.showMessageDialog(frame,"New Workout Created! Return to Main GUI to add/remove "
                    + "Exercises.");
        }
    }


    //Class allows for the addition and removal of WorkOutPlan from ListOfPlans
    public class EditLop implements ActionListener {
        private JFrame frame;
        private JPanel panel;
        private JTextArea text1;
        private JLabel label1;
        private JTextArea text2;
        private JLabel label2;
        private JTextArea text3;
        private JLabel label3;

        // MODIFIES: WorkoutPlan, ListOfPlans
        // EFFECTS: removes workout from list of plans, pop up for confirmation or denial
        public EditLop() {
            frame = new JFrame();
            panel = new JPanel();

            panel.setBorder(BorderFactory.createEmptyBorder(30,10,30,10));
            panel.setLayout(new GridLayout(4,2));

            createTextLabelButtons();

            frame.setSize(800,800);
            frame.setLocation(350,0);
            frame.add(panel,BorderLayout.CENTER);

            frame.setTitle("Add or Remove Exercises");
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        }

        //MODIFIES: this
        //EFFECTS: creates text fields and labels and buttons then adds them to JPanel
        private void createTextLabelButtons() {
            text1 = new JTextArea("Enter Workout Number");
            text2 = new JTextArea("Enter Exercise Name");
            text3 = new JTextArea("Enter Exercise Reps");
            label1 = new JLabel("Workout to be edited:");
            label2 = new JLabel("Exercise to be added:");
            label3 = new JLabel("Reps of Exercise:");
            JButton btnAdd = new JButton("Add to Workout");
            btnAdd.setActionCommand("add");
            btnAdd.addActionListener(this);
            JButton btnRemove = new JButton("Remove from Workout");
            btnRemove.setActionCommand("remove");
            btnRemove.addActionListener(this);
            panel.add(label1);
            panel.add(text1);
            panel.add(label2);
            panel.add(text2);
            panel.add(label3);
            panel.add(text3);
            panel.add(btnAdd);
            panel.add(btnRemove);
        }

        //MODIFIES ListOfPlans, WorkoutPlan, Exercise
        // EFFECTS: when the btn is clicked,
        // this.actionPerformed(ActionEvent e) will be called.
        // You could also set a different object, if you wanted
        // a different object to respond to the button click
        //This is the method that is called when the JButton btn is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), "add")) {
                addToLop();
                new ListOfPlansPanel(lop);
            } else if (Objects.equals(e.getActionCommand(), "remove")) {
                removeFromLop();
                new ListOfPlansPanel(lop);
            }


        }

        // MODIFIES: Exercise, Workout, ListOfPlans
        // EFFECTS: removes Exercise from Workout in list of plans, pop up for confirmation or denial
        private void removeFromLop() {
            int indexOfRemoval;
            String excToRemove;

            if (!lop.getListOfPlans().isEmpty()) {
                try {
                    indexOfRemoval = Integer.parseInt(text1.getText()) - 1;
                    excToRemove = text2.getText();
                    lop.removeExerciseFromPlan(indexOfRemoval, excToRemove);
                    JOptionPane.showMessageDialog(frame, "Removed " + text2.getText() + " from Workout "
                            + (indexOfRemoval + 1) + "!");
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(frame, "Incorrect Inputs!");
                }
            } else {
                JOptionPane.showMessageDialog(frame,"Nothing to Remove!");
            }
        }

        // MODIFIES: Exercise, WorkOutPlan, ListOfPlans
        // EFFECTS: adds Exercise to WorkOutPlan in list of plans, pop up for confirmation or denial
        private void addToLop() {
            int indexOfAddition;
            try {
                Exercise newExc = new Exercise(text2.getText(), Integer.parseInt(text3.getText()));
                if (!lop.getListOfPlans().isEmpty()) {
                    try {
                        indexOfAddition = Integer.parseInt(text1.getText()) - 1;
                        lop.getListOfPlans().get(indexOfAddition).addExercise(newExc);
                        JOptionPane.showMessageDialog(frame, "Added " + text2.getText() + " to Workout "
                                + (indexOfAddition + 1) + "!");
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        JOptionPane.showMessageDialog(frame, "Incorrect Inputs!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Nothing to Add to!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Incorrect Inputs!");
            }
        }
    }

    //Class displays flexing image
    public class Flex {
        private JFrame picFrame = new JFrame();
        private JPanel picPanel = new JPanel();

        //Effects: creates new panel with one of five randomly selected images on it
        public Flex() {
            Random rand = new Random();
            int upperbound = 5;
            int check = rand.nextInt(upperbound);
            BufferedImage myPicture = null;
            List<String> images = new ArrayList<>(asList("./data/images/Image1.jpg","./data/images/Image2.jpg",
                    "./data/images/Image3.jpg","./data/images/Image4.jpg","./data/images/Image5.jpg"));
            try {
                myPicture = ImageIO.read(new File(images.get(check)));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame,"Failed to Load Image!");
            }
            picPanel.add(new JLabel(new ImageIcon(myPicture)));
            picPanel.setBorder(BorderFactory.createEmptyBorder(30,10,30,10));
            picFrame.setSize(800,800);
            picFrame.setLocation(400,400);
            picFrame.add(picPanel,BorderLayout.CENTER);
            picFrame.pack();
            picFrame.setVisible(true);

        }

    }

}
