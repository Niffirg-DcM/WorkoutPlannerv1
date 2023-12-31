package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private Event event1;
    private Event event2;
    private Event event3;
    private List<Event> eventList;
    private EventLog eventLog;

    @BeforeEach
    public void loadEvents() {
        event1 = new Event("A1");
        event2 = new Event("A2");
        event3 = new Event("A3");
        eventLog = EventLog.getInstance();
        eventLog.logEvent(event1);
        eventLog.logEvent(event2);
        eventLog.logEvent(event3);
    }

    @Test
    public void testLogEvent() {
        eventList = new ArrayList<>();
        for (Event next : eventLog) {
            eventList.add(next);
        }
        assertTrue(eventList.contains(event1));
        assertTrue(eventList.contains(event2));
        assertTrue(eventList.contains(event3));
    }

    @Test
    public void testClear() {
        eventLog.clear();
        Iterator<Event> temp = eventLog.iterator();
        assertTrue(temp.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", temp.next().getDescription());
        assertFalse(temp.hasNext());
    }
}
