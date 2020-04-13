package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TutorialUtil contains methods for manipulating Tutorials.
 */
public class TutorialUtil {

    /**
     * Check that {@code beginTime} provided is earlier than {@code endTime} provided
     * @return true if beginTime is before endTime
     */
    public static boolean isStartEarlierThanEndTime(LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(startTime, endTime);
        return startTime.compareTo(endTime) < 0;
    }

    /**
     * Check that {@code tutorialDay} provided is the same as {@code calendarDate} provided
     * @return true if both are on the same day of the week
     */
    public static boolean checkDayOfWeek(DayOfWeek tutorialDay, LocalDate calendarDate) {
        requireAllNonNull(tutorialDay, calendarDate);
        return tutorialDay.equals(calendarDate.getDayOfWeek());
    }

    /**
     * Checks if the given calendarDate falls within the NUS academic calendar as described
     * in the NUS Registrar.
     * @param calendarDate date to be checked
     */
    public static boolean checkDuringSemester(LocalDate calendarDate) {
        requireNonNull(calendarDate);
        if (calendarDate.getMonthValue() < 6) {
            LocalDate augustFirstLastYear = LocalDate.of(calendarDate.getYear() - 1, 8, 1);
            int daysToMonday = (7 - (augustFirstLastYear.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue())) % 7;
            LocalDate augustFirstMondayLastYear = augustFirstLastYear.plusDays(daysToMonday); // Monday of Week 0
            LocalDate janStart = augustFirstMondayLastYear.plusWeeks(23); // Monday of Week 1 in Sem 2

            LocalDate tutorialStartDateP1 = janStart.plusWeeks(2).minusDays(1); // Sunday of Week 2
            LocalDate tutorialEndDateP1 = janStart.plusWeeks(5).plusDays(5); // Saturday of Week 6
            LocalDate tutorialStartDateP2 = janStart.plusWeeks(7).minusDays(1); // Sunday of Recess Week
            LocalDate tutorialEndDateP2 = janStart.plusWeeks(13).plusDays(5); // Saturday of Week 13

            return (calendarDate.isAfter(tutorialStartDateP1) && calendarDate.isBefore(tutorialEndDateP1))
                || (calendarDate.isAfter(tutorialStartDateP2) && calendarDate.isBefore(tutorialEndDateP2));

        } else if (calendarDate.getMonthValue() >= 8) {
            LocalDate augustFirst = LocalDate.of(calendarDate.getYear(), 8, 1);
            int daysToMonday = (7 - (augustFirst.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue())) % 7;
            LocalDate augustFirstMonday = augustFirst.plusDays(daysToMonday); // Monday of Week 0

            LocalDate tutorialStartDateP1 = augustFirstMonday.plusWeeks(3); // Monday of Week 3
            LocalDate tutorialEndDateP1 = tutorialStartDateP1.plusWeeks(3).plusDays(5); // Sat of Week 6
            tutorialStartDateP1 = tutorialStartDateP1.minusDays(1); // Sunday of Week 2

            LocalDate tutorialStartDateP2 = tutorialStartDateP1.plusWeeks(5); // Sunday of Recess Week
            LocalDate tutorialEndDateP2 = tutorialEndDateP1.plusWeeks(8); // Sat of Week 13

            return (calendarDate.isAfter(tutorialStartDateP1) && calendarDate.isBefore(tutorialEndDateP1))
                || (calendarDate.isAfter(tutorialStartDateP2) && calendarDate.isBefore(tutorialEndDateP2));
        }
        return false;
    }
}
