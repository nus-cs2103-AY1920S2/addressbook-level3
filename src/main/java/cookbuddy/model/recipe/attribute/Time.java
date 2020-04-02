package cookbuddy.model.recipe.attribute;

import static cookbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;


/**
 * Represents a Recipe's time in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHour(int)}, (@Link #isValidMin(int)},
 * {@Link #isValidSec(int)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS_HOUR = "Hours should be < 20";
    public static final String MESSAGE_CONSTRAINTS_MIN = "Mins should be <= 60";
    public static final String MESSAGE_CONSTRAINTS_SEC = "Secs should be <= 60";


    

    public final int hour;
    public final int min;
    public final int sec;

    /**
     * Constructs a {@code Time}.
     *
     * @param hour A valid hour.
     * @param min A valid minute.
     * @param sec A valid second.
     */
    public Time(int hour, int min, int sec) {
        requireNonNull(hour);
        requireNonNull(min);
        requireNonNull(sec);
        checkArgument(isValidHour(hour), MESSAGE_CONSTRAINTS_HOUR);
        checkArgument(isValidMin(min), MESSAGE_CONSTRAINTS_MIN);
        checkArgument(isValidSec(sec), MESSAGE_CONSTRAINTS_SEC);
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    /**
     * Returns true if a given string is a valid name.
     */

    public static boolean isValidHour(int test) {
        return (test < 20);
    }

    public boolean isValidMin(int test) {
        return (test <= 60);
    }

    public boolean isValidSec(int test) {
        return (test <= 60);
    }


    @Override
    public String toString() {
        String toReturn = "";
        if(isNull(hour) || isNull(min) || isNull(sec)) {
            toReturn += "-";
        } else {
            toReturn += String.valueOf(hour);
            toReturn += ":";
            toReturn += String.valueOf(min);
            toReturn += ":";
            toReturn += String.valueOf(sec);
        }
        return toReturn
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                    && hour == (((Time) other).hour))
                    && min == (((Time) other).min)
                    && sec == (((Time) other).sec); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(hour + min + sec).hashCode();
    }

}
