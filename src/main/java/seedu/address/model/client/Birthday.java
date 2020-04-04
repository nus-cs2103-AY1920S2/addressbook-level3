package seedu.address.model.client;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Client's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_CONSTRAINTS =
            "Birthday input should be in the format DD-MM-YYYY, and not be more current than the current date";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final LocalDate value;
    public final String displayValue;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid birth date in the form DD-MM-YYYY.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_CONSTRAINTS);
        this.value = birthday.isEmpty() ? null : LocalDate.parse(birthday, DATE_TIME_FORMATTER);
        this.displayValue = birthday; //assuming birthday string is valid
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        LocalDate testBirthday;
        if (test.isEmpty()) {
            return true;
        }
        try {
            testBirthday = LocalDate.parse(test, DATE_TIME_FORMATTER);
            return (LocalDate.now().compareTo(testBirthday) > 0);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.value == null) {
            return "";
        }
        return this.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getAge() {
        if (this.value == null) {
            return "-";
        }
        long age = DAYS.between(LocalDate.now(), this.value) / 365;
        return Long.toString(age);
    }

}
