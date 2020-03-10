package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class RemarkTest {
    public static final Instant DEFAULT_EARLIER_INSTANT = Instant.now();
    public static final Instant DEFAULT_QUARTER_INSTANT = DEFAULT_EARLIER_INSTANT.plusMillis(250);
    public static final Instant DEFAULT_MIDDLE_INSTANT = DEFAULT_EARLIER_INSTANT.plusMillis(500);
    public static final Instant DEFAULT_LATER_INSTANT = DEFAULT_EARLIER_INSTANT.plusMillis(1000);
    public static final Remark REMARK_START_WITHOUT_QUESTION = new Remark(DEFAULT_EARLIER_INSTANT, "Start");
    public static final Remark REMARK_MIDDLE_WITHOUT_QUESTION = new Remark(DEFAULT_MIDDLE_INSTANT, "Random remark");
    public static final Remark REMARK_STOP_WITHOUT_QUESTION = new Remark(DEFAULT_LATER_INSTANT, "Stop");
    public static final Question DEFAULT_QUESTION_1 = new Question("Is this a question?");
    public static final Question DEFAULT_QUESTION_2 = new Question("Is this another question?");
    public static final Question DEFAULT_QUESTION_3 = new Question("Is this another another question?");
    public static final Remark REMARK_QUARTER_WITH_QUESTION = new Remark(DEFAULT_QUARTER_INSTANT,
            "Random remark", DEFAULT_QUESTION_1);
    public static final Remark REMARK_MIDDLE_WITH_QUESTION = new Remark(DEFAULT_MIDDLE_INSTANT,
            "Random remark", DEFAULT_QUESTION_2);

    @Test
    void getTime_maxTime_success() {
        assertEquals(DEFAULT_MIDDLE_INSTANT, REMARK_MIDDLE_WITHOUT_QUESTION.getTime());
    }

    @Test
    void getQuestion_noQuestion_success() {
        assertEquals(null, REMARK_MIDDLE_WITHOUT_QUESTION.getQuestion());
    }

    @Test
    void getTime_withQuestion_success() {
        assertEquals(DEFAULT_MIDDLE_INSTANT, REMARK_MIDDLE_WITH_QUESTION.getTime());
    }

    @Test
    void getQuestion_withQuestion_success() {
        assertEquals(DEFAULT_QUESTION_2, REMARK_MIDDLE_WITH_QUESTION.getQuestion());
    }
}
