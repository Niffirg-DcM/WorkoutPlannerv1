package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class EventTest {
    private Event evt;
    private String date;


    @BeforeEach
    public void runBefore() {
        evt = new Event("event");   // (1)
        date = Calendar.getInstance().getTime().toString();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("event", evt.getDescription());
        assertFalse(evt.equals(null));
        assertFalse(evt.equals(date));
        assertEquals(date, evt.getDate().toString());
        assertEquals(date + "\n" + "event", evt.toString());
        assertEquals(13 * evt.getDate().hashCode() + evt.getDescription().hashCode(),evt.hashCode());
    }
}
