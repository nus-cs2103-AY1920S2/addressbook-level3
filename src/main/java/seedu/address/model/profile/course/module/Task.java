package seedu.address.model.profile.course.module;

import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;




/**
 * Represents a Task in Module.
 */
public class Task {
    protected String description;
    protected LocalDate date;
    protected LocalTime time;

    private String inputTimePattern = "HH:mm";
    private DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(inputTimePattern);

    public Task(String description, String date, String time) throws DateTimeException {
        this.description = description;
        try {
            this.date = LocalDate.parse(date);
            this.time = LocalTime.parse(time, inputTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException("Try: d/YYYY-MM-DD HH:mm");
        }
    }

    public DateTimeFormatter getInputTimeFormatter() {
        return inputTimeFormatter;
    }

    public String getInputTimePattern() {
        return inputTimePattern;
    }

    @Override
    public String toString() {
        return "Task: " + this.description + " by " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ", "
                + LocalTime.parse(time.toString(), inputTimeFormatter) + " added.";
    }


}
