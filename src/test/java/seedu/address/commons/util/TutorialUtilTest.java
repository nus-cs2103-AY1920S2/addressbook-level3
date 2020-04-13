package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class TutorialUtilTest {

    @Test
    void checkStartEndTime_matchingInput_returnTrue() {
        LocalTime midnight = LocalTime.of(0, 0);
        LocalTime twoThreeFiveNine = LocalTime.of(23, 59);
        assertTrue(TutorialUtil.isStartEarlierThanEndTime(midnight, twoThreeFiveNine));
    }

    @Test
    void checkStartEndTime_wrongInput_returnFalse() {
        LocalTime midnight = LocalTime.of(0, 0);
        LocalTime twoThreeFiveNine = LocalTime.of(23, 59);
        assertFalse(TutorialUtil.isStartEarlierThanEndTime(twoThreeFiveNine, midnight));
    }

    @Test
    void checkDayOfWeek_matchingInput_returnTrue() {
        LocalDate calendarDate = LocalDate.of(2020, 1, 1);
        DayOfWeek dow = calendarDate.getDayOfWeek();
        assertTrue(TutorialUtil.checkDayOfWeek(dow, calendarDate));
    }

    @Test
    void checkDayOfWeek_wrongInput_returnFalse() {
        LocalDate calendarDate = LocalDate.of(2020, 1, 1);
        DayOfWeek wrongDow = calendarDate.plusDays(1).getDayOfWeek();
        assertFalse(TutorialUtil.checkDayOfWeek(wrongDow, calendarDate));
    }

    @Test
    void checkDuringSemester_notInSemester_returnFalse() {
        // Semester 1 AY1920
        LocalDate firstDay1920S1 = LocalDate.of(2019, 8, 5);
        assertFalse(TutorialUtil.checkDuringSemester(firstDay1920S1));

        LocalDate week1Mon1920S1 = LocalDate.of(2019, 8, 12);
        assertFalse(TutorialUtil.checkDuringSemester(week1Mon1920S1));

        LocalDate week2Sun1920S1 = LocalDate.of(2019, 8, 25);
        assertFalse(TutorialUtil.checkDuringSemester(week2Sun1920S1));

        LocalDate week6Sat1920S1 = LocalDate.of(2019, 9, 21);
        assertFalse(TutorialUtil.checkDuringSemester(week6Sat1920S1));

        LocalDate weekRecessMon1920S1 = LocalDate.of(2019, 9, 23);
        assertFalse(TutorialUtil.checkDuringSemester(weekRecessMon1920S1));

        LocalDate week13Sat1920S1 = LocalDate.of(2019, 11, 16);
        assertFalse(TutorialUtil.checkDuringSemester(week13Sat1920S1));

        // Semester 2 AY1920

        LocalDate firstDay1920S2 = LocalDate.of(2020, 1, 13);
        assertFalse(TutorialUtil.checkDuringSemester(firstDay1920S2));

        LocalDate week1Mon1920S2 = LocalDate.of(2020, 1, 20);
        assertFalse(TutorialUtil.checkDuringSemester(week1Mon1920S2));

        LocalDate week2Sun1920S2 = LocalDate.of(2020, 1, 26);
        assertFalse(TutorialUtil.checkDuringSemester(week2Sun1920S2));

        LocalDate week6Sat1920S2 = LocalDate.of(2020, 2, 22);
        assertFalse(TutorialUtil.checkDuringSemester(week6Sat1920S2));

        LocalDate weekRecessMon1920S2 = LocalDate.of(2020, 2, 24);
        assertFalse(TutorialUtil.checkDuringSemester(weekRecessMon1920S2));

        LocalDate week13Sat1920S2 = LocalDate.of(2020, 4, 18);
        assertFalse(TutorialUtil.checkDuringSemester(week13Sat1920S2));

        // Non Semester Dates

        LocalDate july1 = LocalDate.of(2020, 7, 1);
        assertFalse(TutorialUtil.checkDuringSemester(july1));
    }

    @Test
    void checkSemesterDate_correctInput_returnTrue() {
        LocalDate week3Mon1920s1 = LocalDate.of(2019, 8, 26);
        assertTrue(TutorialUtil.checkDuringSemester(week3Mon1920s1));

        LocalDate week3Mon1920S2 = LocalDate.of(2020, 1, 27);
        assertTrue(TutorialUtil.checkDuringSemester(week3Mon1920S2));

        LocalDate week3Mon1819S1 = LocalDate.of(2018, 8, 27);
        assertTrue(TutorialUtil.checkDuringSemester(week3Mon1819S1));

        LocalDate week3Mon1819S2 = LocalDate.of(2019, 1, 28);
        assertTrue(TutorialUtil.checkDuringSemester(week3Mon1819S2));

        LocalDate week3Mon1718S1 = LocalDate.of(2017, 8, 28);
        assertTrue(TutorialUtil.checkDuringSemester(week3Mon1718S1));

        LocalDate week3Mon1718S2 = LocalDate.of(2018, 1, 29);
        assertTrue(TutorialUtil.checkDuringSemester(week3Mon1718S1));
    }
}
