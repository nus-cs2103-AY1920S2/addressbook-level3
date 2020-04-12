package seedu.address.model.modelObjectTags.personTagValidations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Checker for a Valid Date in the address book.
 */
public class DateValidator {
    public static boolean validateDate(String date) {
        // First check if date is an empty string
        if (date.trim().equals("")) {
            return false;
        } else {
            // Then set the format of the date according to a specified format
            // TODO: IVAN MAKE GENERIC DATETIMEFORMATTER
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            boolean isValid = true;

            try {
                Date test = sdf.parse(date);
            } catch (ParseException e) {
                isValid = false;
            }

            return isValid;
        }
    }
}
