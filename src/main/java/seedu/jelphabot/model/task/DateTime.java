// @@author Clouddoggo
package seedu.jelphabot.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Class representing the Date and Time of the modelled task.
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS =
        "Date should be of the format Month-Day-Year where Month is a three-letter abbreviation. "
            + "Time should be in the 24 hour format HH mm.";

    private static final String STANDARD_FORMAT = "MMM-d-uuuu HH mm";
    private static final String DISPLAY_FORMAT = "d-MMM-uuuu HH mm";
    public static final DateTimeFormatter STANDARD_FORMATTER =
        DateTimeFormatter.ofPattern(STANDARD_FORMAT).withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DISPLAY_FORMATTER =
        DateTimeFormatter.ofPattern(DISPLAY_FORMAT).withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter ACCEPTED_INPUT_FORMATTER =
        new DateTimeFormatterBuilder()
            .appendOptional(STANDARD_FORMATTER)
            .appendOptional(DISPLAY_FORMATTER)
            .appendOptional(DateTimeFormatter.ofPattern("MMM/d/uuuu HH mm"))
            .appendOptional(DateTimeFormatter.ofPattern("d/M/u HH mm"))
            .appendOptional(DateTimeFormatter.ofPattern("d MMM uuuu HH mm"))
            .toFormatter().withResolverStyle(ResolverStyle.STRICT);

    public final LocalDateTime value;

    /**
     * Constructs an {@code DateTime}.
     *
     * @param dateTime A string which parses to a valid datetime.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.value = LocalDateTime.parse(dateTime, ACCEPTED_INPUT_FORMATTER);
    }

    /**
     * Constructs an {@code DateTime}.
     *
     * @param dateTime A LocalDateTime object.
     */
    public DateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        this.value = dateTime;
    }

    /**
     * Returns if the given string is a valid datetime format, specified in the List dateFormatStrings.
     *
     * @param test The date to be checked.
     * @return The boolean representing whether the date provided is valid.
     */
    public static boolean isValidDateTime(String test) {
        try {
            LocalDateTime.parse(test, ACCEPTED_INPUT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Converts stored dateTime value to the display format (Display format is the format the datetime is shown in
     * the view, defined in #DateTime.DISPLAY_FORMAT).
     *
     * @return dateString converted to display format.
     */
    public String getDisplayValue() {
        return value.format(DISPLAY_FORMATTER);
    }

    /**
     * Converts stored dateTime value to the standard format
     * (Standard format is the DateTimeFormatter used for storing the date in Json and is defined in
     * #DateTime.STANDARD_FORMAT).
     *
     * @return A string with the Date rendered in standard format
     */
    @Override
    public String toString() {
        return value.format(STANDARD_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof DateTime // instanceof handles nulls
                           && value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public LocalDate getDate() {
        return value.toLocalDate();
    }

    public LocalDateTime getDateTime() {
        return value;
    }
}
