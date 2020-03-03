package seedu.address.model.hirelah;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RemarkListTest {

    @Test
    void getRemarks_retrieveRemarks_success() {
        RemarkList actualRemarks = new RemarkList();
        assertEquals(new ArrayList<Remark>(), actualRemarks.getRemarks());
    }

    @Test
    void add_addRemarks_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        ArrayList<Remark> expectedArray = new ArrayList<>();
        expectedArray.add(RemarkTest.remarkAtEarlierInstant);
        assertEquals(expectedArray, actualRemarks.getRemarks());
    }

    @Test
    void getStartRemarkTime_getStartTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertEquals(RemarkTest.defaultEarlierInstant, actualRemarks.getStartRemarkTime());
    }

    @Test
    void getLastRemarkTime_getLastTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertEquals(RemarkTest.defaultLaterInstant, actualRemarks.getStartRemarkTime());
    }

    @Test
    void getInterviewDurationInMs_getInterviewTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertEquals(1000, actualRemarks.getInterviewDurationInMs());
    }

    @Test
    void isTimeInValidRange_validTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertTrue(actualRemarks.isTimeInValidRange(1000));
    }

    @Test
    void isTimeInValidRange_invalidTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertFalse(actualRemarks.isTimeInValidRange(1001));
    }

    @Test
    void getRemarkAtTime_getLastRemark_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertEquals(RemarkTest.remarkAtEarlierInstant, actualRemarks.getRemarkAtTime(0));
    }

    @Test
    void isQuestionAnswered_noAnswer_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkAtLaterInstant);
        assertFalse(actualRemarks.isQuestionAnswered(new Question()));
    }

    @Test
    void getTimeOfQuestionInMs() {
    }
}