package fithelper.commons.util;

import static fithelper.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Parses a string to a date.
 */
public class DateUtil {
    public static final DateTimeFormatter PARSEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String MESSAGE_CONSTRAINTS = "Given date cannot be parsed.\n"
            + "Date should be in format: yyyy-MM-dd";

    /**
     * Returns a parsed LocalDate
     * @param dateStr The string to be parsed
     */
    public static LocalDate dateParsed(String dateStr) {
        checkArgument(isValidDate(dateStr), MESSAGE_CONSTRAINTS);
        return LocalDate.parse(dateStr, PARSEFORMAT);
    }


    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, PARSEFORMAT);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
