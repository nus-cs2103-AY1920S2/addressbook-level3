package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;


/**
 * Represents a Person's Birthday in the Address Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Birthday {
    public static final String MESSAGE_CONSTRAINTS = "Birthday has to be in MM-dd format";
    private static final DateTimeFormatter inputFormat = new DateTimeFormatterBuilder().appendPattern("MM-dd")
        .parseDefaulting(ChronoField.YEAR, 2020).toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM");

    // Instance variables
    public final String birthday;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param date A valid birthday (Non-null).
     */
    public Birthday(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        birthday = date;
    }

    /**
     * @param test the input date to be tested.
     *
     * Returns true if a given string is a valid birthday: Either empty or in the correct format required.
     */
    public static boolean isValidDate(String test) {
        if (test.isEmpty()) {
            return true;
        }

        try {
            LocalDate.parse(test, inputFormat);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (birthday.isEmpty()) {
            return "";
        }

        LocalDate bday = LocalDate.parse(birthday, inputFormat);
        return bday.format(outputFormat);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && birthday.equals(((Birthday) other).birthday)); // state check
    }

    @Override
    public int hashCode() {
        return birthday.hashCode();
    }
}
