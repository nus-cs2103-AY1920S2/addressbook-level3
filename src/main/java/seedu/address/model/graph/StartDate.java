package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the start date of the graph of exercises. Guarantees: immutable; is
 * valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Start date input should be in the form DD-MM-YYYY and should not be after end date or blank. \n"
            + "The accepted range of dates is today to one year before today. eg. 18-07-2019.";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final LocalDate value;
    public final String displayValue;

    public StartDate(String date) {
        requireNonNull(date);
        checkArgument(isValidStartDate(date), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(date, DATE_TIME_FORMATTER);
        this.displayValue = date; // assuming date string is valid
    }

    /**
     * Tests if startDate is a valid date, within a year prior to current date.
     *
     * @param test String to be tested.
     * @return Returns true if a given string is a valid date.
     */
    public static Boolean isValidStartDate(String test) {
        try {
            LocalDate testDate = LocalDate.parse(test, DATE_TIME_FORMATTER);
            LocalDate dateNow = LocalDate.now();
            LocalDate dateNowMinusOneYear = dateNow.minusYears(1);

            if (dateNow.isBefore(testDate)) {
                return false;
            } else if (dateNowMinusOneYear.isAfter(testDate)) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                        && value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
