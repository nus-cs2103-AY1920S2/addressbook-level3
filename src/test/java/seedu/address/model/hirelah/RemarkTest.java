package seedu.address.model.hirelah;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RemarkTest {
    public static final Instant defaultEarlierInstant = Instant.now();
    public static final Instant defaultLaterInstant = defaultEarlierInstant.plusMillis(10000);
    public static final Remark remarkAtEarlierInstant = new Remark(defaultEarlierInstant, "Random remark");
    public static final Remark remarkAtLaterInstant = new Remark(defaultLaterInstant, "Random remark");
    private static final Question defaultQuestion = new Question();
    private Remark defaultRemarkWithQuestion = new Remark(Instant.MAX, "Random remark", defaultQuestion);

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
        assertEquals(defaultEarlierInstant, defaultRemarkWithQuestion.getQuestion());
    }

    @Test
    void getQuestion_withQuestion_success() {
        assertEquals(defaultQuestion, defaultRemarkWithQuestion.getQuestion());
    }
}