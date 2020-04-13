package seedu.address.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ConsultUtil contains methods for manipulating Consults.
 */
public class ConsultUtil {

    /**
     * Check that {@code beginDateTime} provided is earlier than {@code endDateTime} provided
     * @return true if beginDateTime is before endDateTime
     */
    public static boolean checkStartEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.isBefore(endDateTime);
    }

    /**
     * Method to check that the {@code consultDateTime} is the same date as the {@code calendarDate} in the Calendar.
     */
    public static boolean checkSameDate(LocalDateTime consultDateTime, LocalDate calendarDate) {
        LocalDate startDate = LocalDate.of(consultDateTime.getYear(), consultDateTime.getMonthValue(),
            consultDateTime.getDayOfMonth());
        return startDate.isEqual(calendarDate);
    }

    /**
     * Method to check that the {@code beginConsultDateTime} is the same date as the {@code endConsultDateTime}.
     */
    public static boolean checkSameDate(LocalDateTime beginConsultDateTime, LocalDateTime endConsultDateTime) {
        return beginConsultDateTime.toLocalDate().equals(endConsultDateTime.toLocalDate());
    }
}
