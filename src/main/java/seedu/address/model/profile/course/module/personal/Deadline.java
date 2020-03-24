package seedu.address.model.profile.course.module.personal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.model.profile.course.module.exceptions.DateTimeException;


/**
 * Represents a Deadline in Personal.
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS = "Date and time field of deadline should be in the format: "
            + "YYYY-MM-DD and HH:mm respectively. Dates should be valid Gregorian calendar dates"
            + " and time should be in 24-hour format.";

    protected String description;
    protected LocalDate date;
    protected LocalTime time;

    private String inputTimePattern = "HH:mm";
    private DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(inputTimePattern);

    public Deadline(String description, String date, String time) throws DateTimeException {
        this.description = description;
        try {
            this.date = LocalDate.parse(date);
            this.time = LocalTime.parse(time, inputTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException("Try: d/YYYY-MM-DD HH:mm");
        }
    }

    public Deadline(String description) {
        this.description = description;
        this.date = null;
        this.time = null;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public DateTimeFormatter getInputTimeFormatter() {
        return inputTimeFormatter;
    }

    public String getInputTimePattern() {
        return inputTimePattern;
    }

    /**
     * Returns true if the given date and time are valid.
     */
    public static boolean isValidDeadline(String date, String time) { // No point checking the description/task
        try {
            LocalDate.parse(date);
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        String result = this.description;
        if (date != null && time != null) {
            result += " by " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ", "
                    + LocalTime.parse(time.toString(), inputTimeFormatter);
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Deadline) {
            boolean sameDescription = this.description.equals(((Deadline) other).description);
            boolean sameDate = (this.date == null && ((Deadline) other).date == null)
                    || (this.date != null && ((Deadline) other).date != null
                    && this.date.equals(((Deadline) other).date));
            boolean sameTime = (this.time == null && ((Deadline) other).time == null)
                    || (this.time != null && ((Deadline) other).time != null
                    && this.time.equals(((Deadline) other).time));
            return sameDescription && sameDate && sameTime;
        }
        return false;
    }
}
