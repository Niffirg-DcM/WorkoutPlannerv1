package ui;

import model.Exercise;
import model.ListOfPlans;
import model.WorkOutPlan;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;

//Displays frame with List of plans
public class ListOfPlansPanel extends JFrame {
    private ListOfPlans lop;
    private JFrame frame;
    private JPanel panel;


    public ListOfPlansPanel(ListOfPlans list) {
        lop = list;

        frame = new JFrame();
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(new GridLayout(0,1));
        output();

        frame.setSize(800,800);
        frame.setLocation(350,238);
        frame.add(panel,BorderLayout.CENTER);

        frame.setTitle("Display");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    //EFFECTS: adds each component of ListOfPlans to the panel
    private void output() {
        JLabel addTo;
        int count = 1;
        addTo = new JLabel("List Of Plans!");
        addTo.setFont(new Font("Times", Font.PLAIN, 16));
        panel.add(addTo);
        panel.add(new JLabel(""));
        for (WorkOutPlan wkp : lop.getListOfPlans()) {
            addTo = new JLabel("\t Workout Plan " + count + ": ");
            addTo.setFont(new Font("Times", Font.PLAIN, 16));
            panel.add(addTo);
            for (Exercise exc : wkp.getPlan()) {
                addTo = new JLabel(exc.convertToString());
                addTo.setFont(new Font("Times", Font.PLAIN, 15));
                panel.add(addTo);
            }
            panel.add(new JLabel(""));
            count++;
        }

    }

}
