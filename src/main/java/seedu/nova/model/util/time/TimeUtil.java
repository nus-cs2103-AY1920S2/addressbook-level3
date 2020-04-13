package seedu.nova.model.util.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Useful functions for LocalTime
 */
public interface TimeUtil {
    DateTimeFormatter[] DEFAULT_DATE_F = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-mm-dd")
    };
    DateTimeFormatter[] DEFAULT_TIME_F = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("hh:mm a")
    };
    LocalTime BEGIN_DAY_TIME = LocalTime.of(0, 0, 0);
    LocalTime END_DAY_TIME = LocalTime.of(23, 59, 59);
    LocalDate EX_DATE = LocalDate.of(1970, 1, 1);

    /**
     * Parse date string to LocalDate
     * @param date date string
     * @return LocalDate
     * @throws DateTimeParseException if format not recognised
     */
    static LocalDate parseDate(String date) throws DateTimeParseException {
        for (DateTimeFormatter f : DEFAULT_DATE_F) {
            try {
                return LocalDate.parse(date, f);
            } catch (DateTimeParseException ignored) {
                continue;
            }
        }
        throw new DateTimeParseException("date format wrong", date, 0);
    }

    /**
     * Parse time string to LocalTime
     * @param time time string
     * @return LocalTime
     * @throws DateTimeParseException if format not recognised
     */
    static LocalTime parseTime(String time) throws DateTimeParseException {
        for (DateTimeFormatter f : DEFAULT_TIME_F) {
            try {
                return LocalTime.parse(time, f);
            } catch (DateTimeParseException ignored) {
                continue;
            }
        }
        throw new DateTimeParseException("time format wrong", time, 0);
    }

    /**
     * Number of weeks the two date spans, including the weeks that both dates are in.
     * @param start start date
     * @param end end date
     * @return number of weeks
     */
    static int noOfWeeks(LocalDate start, LocalDate end) {
        LocalDate monStart = getMondayOfWeek(start);
        LocalDate monEnd = getMondayOfWeek(end);
        return 2 + (int) Duration.between(LocalDateTime.of(monStart, BEGIN_DAY_TIME),
                LocalDateTime.of(monEnd, BEGIN_DAY_TIME)).toDays() / 7;
    }

    /**
     * Make LocalDate
     * @param dow day of week
     * @param sameWeekWith week of
     * @return LocalDate
     */
    static LocalDate dateOfSameWeek(DayOfWeek dow, LocalDate sameWeekWith) {
        int offset = dow.getValue() - sameWeekWith.getDayOfWeek().getValue();
        return sameWeekWith.plusDays(offset);
    }

    /**
     * Get Monday of Week
     * @param weekOf same week with this
     * @return LocalDate of monday of week
     */
    static LocalDate getMondayOfWeek(LocalDate weekOf) {
        return weekOf.minusDays(weekOf.getDayOfWeek().getValue() - 1);
    }

    /**
     * Get LocalDateTime
     * @param dow day of week
     * @param sameWeekWith
     * @param time
     * @return
     */
    static LocalDateTime toDateTime(DayOfWeek dow, LocalDate sameWeekWith, LocalTime time) {
        return LocalDateTime.of(dateOfSameWeek(dow, sameWeekWith), time);
    }
}
