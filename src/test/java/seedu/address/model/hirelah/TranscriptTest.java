package seedu.address.model.hirelah;

import org.junit.jupiter.api.Test;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

import static org.junit.jupiter.api.Assertions.*;

class TranscriptTest {

    @Test
    void getTranscriptAtTime_invalidTimeRange_failure() {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.remarkStartWithoutQuestion);
        actualTranscript.getRemarkList().add(RemarkTest.remarkStartWithoutQuestion);
        assertThrows(IllegalActionException.class, () -> actualTranscript.getTranscriptAtTime(1));
    }

    @Test
    void getTranscriptAtTime_validTimeRange_success() throws IllegalActionException {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.remarkStartWithoutQuestion);
        actualTranscript.getRemarkList().add(RemarkTest.remarkMiddleWithQuestion);
        actualTranscript.getRemarkList().add(RemarkTest.remarkStopWithoutQuestion);

        assertEquals(RemarkTest.remarkMiddleWithQuestion, actualTranscript.getTranscriptAtTime(500));
    }

    @Test
    void getTranscriptAtQuestion_unansweredQuestion_failure() {
        Transcript actualTranscript = new Transcript();
        assertThrows(IllegalActionException.class, () -> actualTranscript.getTranscriptAtQuestion(RemarkTest.defaultQuestion1));
    }

    @Test
    void getTranscriptAtQuestion_answeredQuestion_success() throws IllegalActionException {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.remarkStartWithoutQuestion);
        actualTranscript.getRemarkList().add(RemarkTest.remarkQuarterWithQuestion);
        actualTranscript.getRemarkList().add(RemarkTest.remarkStopWithoutQuestion);
        assertEquals(RemarkTest.remarkQuarterWithQuestion, actualTranscript.getTranscriptAtQuestion(RemarkTest.defaultQuestion1));
    }

}
