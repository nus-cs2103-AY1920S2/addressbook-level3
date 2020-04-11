package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the end date of the graph of exercises. Guarantees: immutable; is
 * valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate {
    public static final String MESSAGE_CONSTRAINTS =
            "End date input should be in the form DD-MM-YYYY and should not be before start date or blank. \n"
            + "It should only be from within one year before to the current date. eg. 18-12-2019. ";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final LocalDate value;
    public final String displayValue;

    public EndDate(String date) {
        requireNonNull(date);
        checkArgument(isValidEndDate(date), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(date, DATE_TIME_FORMATTER);
        this.displayValue = date; // assuming date string is valid
    }

    /**
     * Tests if endDate is a valid date, within a year prior to current date.
     *
     * @param test String to be tested.
     * @return Returns true if a given string is a valid date.
     */
    public static Boolean isValidEndDate(String test) {
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
                || (other instanceof EndDate // instanceof handles nulls
                        && value.equals(((EndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
