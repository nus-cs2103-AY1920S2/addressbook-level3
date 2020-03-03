package seedu.address.model.hirelah;

import org.junit.jupiter.api.Test;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

import static org.junit.jupiter.api.Assertions.*;

class TranscriptTest {

    @Test
    void getTranscriptAtTime_invalidTimeRange_failure() {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.remarkAtEarlierInstant);
        actualTranscript.getRemarkList().add(RemarkTest.remarkAtEarlierInstant);
        assertThrows(IllegalActionException.class, () -> actualTranscript.getTranscriptAtTime(1));
    }

    @Test
    void getTranscriptAtTime_validTimeRange_success() throws IllegalActionException {
        Transcript actualTranscript = new Transcript();
        actualTranscript.getRemarkList().add(RemarkTest.remark);
        actualTranscript.getRemarkList().add(RemarkTest.remarkAtLaterInstant);
        assertEquals(RemarkTest.remarkAtEarlierInstant, actualTranscript.getTranscriptAtTime(0));
    }

    @Test
    void getTranscriptAtQuestion_unansweredQuestion_failure() {
        Transcript actualTranscript = new Transcript();
        assertThrows(IllegalActionException.class, () -> actualTranscript.getTranscriptAtQuestion(RemarkTest.defaultQuestion1));
    }
}