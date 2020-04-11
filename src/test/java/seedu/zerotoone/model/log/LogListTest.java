package seedu.zerotoone.model.log;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LogListTest {

    @Test
    void testToString() {
        LogList logList = new LogList();
        assertEquals("0 Logs", logList.toString());
        assertEquals(logList, logList);
        assertEquals("0 Logs", logList.toString());
    }

    @Test
    void testEquals() {
        LogList logList = new LogList();
        assertEquals(logList, logList);
        assertEquals(new LogList(), logList);
    }
}
