package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Window Event hander class for printing log in console at time of close
class WindowEventHandler extends WindowAdapter {

    //Effects: calls event logging function with Eventlog.getInstance() as a parameter
    public void windowClosing(WindowEvent evt) {
        logEventsToConsole(EventLog.getInstance());
    }

    //Requires: EventLog is not Null
    //Effects: Prints all Events to console.
    private void logEventsToConsole(EventLog events) {
        for (Event next : events) {
            System.out.println(next.toString() + "\n");
        }
    }
}