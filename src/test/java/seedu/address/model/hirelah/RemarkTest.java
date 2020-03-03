package seedu.address.model.hirelah;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RemarkTest {
    public static final Instant defaultEarlierInstant = Instant.now();
    public static final Instant defaultQuarterInstant = defaultEarlierInstant.plusMillis(250);
    public static final Instant defaultMiddleInstant = defaultEarlierInstant.plusMillis(500);
    public static final Instant defaultLaterInstant = defaultEarlierInstant.plusMillis(1000);
    public static final Remark remarkAtEarlierInstant = new Remark(defaultEarlierInstant, "Start");
    public static final Remark remarkAtMiddleInstant = new Remark(defaultMiddleInstant, "Random remark");
    public static final Remark remarkAtLaterInstant = new Remark(defaultEarlierInstant, "Stop");
    public static final Question defaultQuestion1 = new Question("Is this a question?");
    public static final Question defaultQuestion2 = new Question("Is this another question?");
    public static final Question defaultQuestion3 = new Question("Is this another another question?");
    public static final Remark remarkMiddleWithQuestion = new Remark(defaultMiddleInstant, "Random remark", defaultQuestion2);
    public static final Remark remarkQuarterWithQuestion = new Remark(defaultQuarterInstant, "Random remark", defaultQuestion1);

    @Test
    void getTime_maxTime_success() {
        assertEquals(defaultMiddleInstant, remarkAtMiddleInstant.getTime());
    }

    @Test
    void getQuestion_noQuestion_success() {
        assertEquals(null, remarkAtMiddleInstant.getQuestion());
    }

    @Test
    void getTime_withQuestion_success() {
        assertEquals(defaultMiddleInstant, remarkMiddleWithQuestion.getTime());
    }

    @Test
    void getQuestion_withQuestion_success() {
        assertEquals(defaultQuestion1, remarkMiddleWithQuestion.getQuestion());
    }
}
