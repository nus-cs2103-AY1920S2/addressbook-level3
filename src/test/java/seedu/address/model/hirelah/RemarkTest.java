package seedu.address.model.hirelah;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RemarkTest {
    public static final Instant defaultEarlierInstant = Instant.now();
    public static final Instant defaultMiddleInstant = defaultEarlierInstant.plusMillis(500);
    public static final Instant defaultLaterInstant = defaultEarlierInstant.plusMillis(1000);
    public static final Remark remarkAtEarlierInstant = new Remark(defaultEarlierInstant, "Random remark");
    public static final Remark remarkAtMiddleInstant = new Remark(defaultMiddleInstant, "Random remark");
    public static final Remark remarkAtLaterInstant = new Remark(defaultLaterInstant, "Random remark");
    public static final Question defaultQuestion = new Question("Is this a question?");
    public static final Remark remarkBeginningWithQuestion = new Remark(defaultEarlierInstant, "Random remark", defaultQuestion);
    public static final Remark remarkMiddleWithQuestion = new Remark(defaultMiddleInstant, "Random remark", defaultQuestion);

    @Test
    void getTime_maxTime_success() {
        assertEquals(defaultEarlierInstant, remarkAtEarlierInstant.getTime());
    }

    @Test
    void getQuestion_noQuestion_success() {
        assertEquals(null, remarkAtEarlierInstant.getQuestion());
    }

    @Test
    void getTime_withQuestion_success() {
        assertEquals(defaultEarlierInstant, remarkBeginningWithQuestion.getTime());
    }

    @Test
    void getQuestion_withQuestion_success() {
        assertEquals(defaultQuestion, remarkBeginningWithQuestion.getQuestion());
    }
}