package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RemarkListTest {

    @Test
    void getRemarks_retrieveRemarks_success() {
        RemarkList actualRemarks = new RemarkList();
        assertEquals(new ArrayList<Remark>(), actualRemarks.getRemarks());
    }

    @Test
    void add_addRemarks_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITHOUT_QUESTION);
        ArrayList<Remark> expectedArray = new ArrayList<>();
        expectedArray.add(RemarkTest.REMARK_MIDDLE_WITHOUT_QUESTION);
        assertEquals(expectedArray, actualRemarks.getRemarks());
    }

    @Test
    void getStartRemarkTime_getStartTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertEquals(RemarkTest.DEFAULT_EARLIER_INSTANT, actualRemarks.getStartRemarkTime());
    }

    @Test
    void getLastRemarkTime_getLastTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertEquals(RemarkTest.DEFAULT_LATER_INSTANT, actualRemarks.getLastRemarkTime());
    }

    @Test
    void getInterviewDurationInMs_getInterviewTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertEquals(1000, actualRemarks.getInterviewDurationInMs());
    }

    @Test
    void isTimeInValidRange_validTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertTrue(actualRemarks.isTimeInValidRange(1000));
    }

    @Test
    void isTimeInValidRange_invalidTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertFalse(actualRemarks.isTimeInValidRange(1001));
    }

    @Test
    void getRemarkAtTime_getMiddleRemark_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        Remark result = actualRemarks.getRemarkAtTime(500);
        assertEquals(RemarkTest.REMARK_MIDDLE_WITH_QUESTION, actualRemarks.getRemarkAtTime(500));
    }

    @Test
    void isQuestionAnswered_noAnswer_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertFalse(actualRemarks.isQuestionAnswered(RemarkTest.DEFAULT_QUESTION_3));
    }

    @Test
    void getInstantOfQuestion_firstQuestion_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        actualRemarks.getRemarkOfQuestion(RemarkTest.DEFAULT_QUESTION_2);
        assertEquals(RemarkTest.REMARK_MIDDLE_WITH_QUESTION,
                actualRemarks.getRemarkOfQuestion(RemarkTest.DEFAULT_QUESTION_2));
    }
}
