package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.hirelah.exceptions.IllegalActionException;

class TranscriptTest {

    @Test
    void getTranscriptAtTime_invalidTimeRange_failure() {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        assertThrows(IllegalActionException.class, () -> actualTranscript.getTranscriptAtTime(1));
    }

    @Test
    void getTranscriptAtTime_validTimeRange_success() throws IllegalActionException {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);

        assertEquals(RemarkTest.REMARK_MIDDLE_WITH_QUESTION, actualTranscript.getTranscriptAtTime(500));
    }

    @Test
    void getTranscriptAtQuestion_unansweredQuestion_failure() {
        Transcript actualTranscript = new Transcript();
        assertThrows(IllegalActionException.class, () ->
                actualTranscript.getTranscriptAtQuestion(RemarkTest.DEFAULT_QUESTION_1));
    }

    @Test
    void getTranscriptAtQuestion_answeredQuestion_success() throws IllegalActionException {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualTranscript.getRemarkList().add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertEquals(RemarkTest.REMARK_QUARTER_WITH_QUESTION,
                actualTranscript.getTranscriptAtQuestion(RemarkTest.DEFAULT_QUESTION_1));
    }

}
