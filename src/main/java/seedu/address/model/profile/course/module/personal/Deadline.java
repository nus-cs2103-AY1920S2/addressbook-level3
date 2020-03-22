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

    @Override
    public String toString() {
        String result = this.description;
        if (date != null && time != null) {
            result += " by " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ", "
                    + LocalTime.parse(time.toString(), inputTimeFormatter);
        }
        return result;
    }


}
