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
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        ArrayList<Remark> expectedArray = new ArrayList<>();
        expectedArray.add(RemarkTest.remarkAtMiddleInstant);
        assertEquals(expectedArray, actualRemarks.getRemarks());
    }

    @Test
    void getStartRemarkTime_getStartTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkAtEarlierInstant);
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        assertEquals(RemarkTest.defaultEarlierInstant, actualRemarks.getStartRemarkTime());
    }

    @Test
    void getLastRemarkTime_getLastTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        assertEquals(RemarkTest.defaultLaterInstant, actualRemarks.getLastRemarkTime());
    }

    @Test
    void getInterviewDurationInMs_getInterviewTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        assertEquals(1000, actualRemarks.getInterviewDurationInMs());
    }

    @Test
    void isTimeInValidRange_validTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        assertTrue(actualRemarks.isTimeInValidRange(1000));
    }

    @Test
    void isTimeInValidRange_invalidTime_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        assertFalse(actualRemarks.isTimeInValidRange(1001));
    }

    @Test
    void getRemarkAtTime_getMiddleRemark_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        assertEquals(RemarkTest.remarkMiddleWithQuestion, actualRemarks.getRemarkAtTime(500));
    }

    @Test
    void isQuestionAnswered_noAnswer_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        assertFalse(actualRemarks.isQuestionAnswered(RemarkTest.defaultQuestion2));
    }

    @Test
    void getInstantOfQuestion_firstQuestion_success() {
        RemarkList actualRemarks = new RemarkList();
        actualRemarks.add(RemarkTest.remarkMiddleWithQuestion);
        actualRemarks.add(RemarkTest.remarkQuarterWithQuestion);
        assertEquals(RemarkTest.remarkAtMiddleInstant, actualRemarks.getRemarkOfQuestion(RemarkTest.defaultQuestion2));
    }
}
