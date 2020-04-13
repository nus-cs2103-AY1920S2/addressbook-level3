package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.field.Cap;

/**
 * Validations for different inputs without a separate class.
 */
public class Verifier {

    public static final int UNIVERSITY_MAX_LENGTH = 50;
    public static final String UNIVERSITY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";


    public static final int MAJOR_MAX_LENGTH = 50;
    public static final String MAJOR_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final int ROLE_MAX_LENGTH = 50;
    public static final String ROLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    /**
     * Checks if a field is within length limit.
     *
     * @param input field.
     * @param maxLength maximum length allowed.
     * @return true or false.
     */
    public static boolean isCorrectLength(String input, int maxLength) {
        if (input.length() <= maxLength) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if an input is empty.
     */
    public static boolean isNotEmpty(String input) {
        if (input.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the University field is valid.
     */
    public static boolean isValidUniversity(String university) {
        if (isNotEmpty(university) && isCorrectLength(university, UNIVERSITY_MAX_LENGTH)
                && university.matches(UNIVERSITY_VALIDATION_REGEX)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the Major field is valid.
     */
    public static boolean isValidMajor(String major) {
        if (isNotEmpty(major) && isCorrectLength(major, MAJOR_MAX_LENGTH)
                && major.matches(MAJOR_VALIDATION_REGEX)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the Cap field is valid.
     */
    public static boolean isValidCap(String currentCap, String maxCap) throws ParseException {
        double userCurrentCap;
        double userMaximumCap;

        try {
            userCurrentCap = Double.valueOf(currentCap);
            userMaximumCap = Double.valueOf(maxCap);
        } catch (NumberFormatException ex) {
            throw new ParseException(Cap.MESSAGE_CONSTRAINTS);
        }

        if (!isNotEmpty(currentCap)
                || !isNotEmpty(maxCap)
                || userCurrentCap > userMaximumCap
                || userCurrentCap < 0
                || userMaximumCap < 0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the Role field is valid.
     */
    public static boolean isValidRole(String role) {
        if (isNotEmpty(role) && isCorrectLength(role, ROLE_MAX_LENGTH)
                && role.matches(ROLE_VALIDATION_REGEX)) {
            return true;
        } else {
            return false;
        }
    }
}
