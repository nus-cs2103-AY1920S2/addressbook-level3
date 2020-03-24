package seedu.address.model.profile.course.module.personal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;


/**
 * Represents a Deadline in Personal.
 */
public class Deadline {
    protected String moduleCode;
    protected String description;
    protected LocalDate date;
    protected LocalTime time;

    private String inputTimePattern = "HH:mm";
    private DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(inputTimePattern);

    public Deadline(String moduleCode, String description, String date, String time) throws DateTimeException {
        this.moduleCode = moduleCode;
        this.description = description;
        try {
            this.date = LocalDate.parse(date);
            this.time = LocalTime.parse(time, inputTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException("Try: d/YYYY-MM-DD HH:mm");
        }
    }

    public Deadline(String moduleCode, String description) {
        this.moduleCode = moduleCode;
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

    public String getStringDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    public String getStringTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedString = time.format(formatter);
        return formattedString;
    }

    public DateTimeFormatter getInputTimeFormatter() {
        return inputTimeFormatter;
    }

    public String getInputTimePattern() {
        return inputTimePattern;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    @Override
    public String toString() {
        String result = this.moduleCode + ": " + this.description;
        if (date != null && time != null) {
            result += " by " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ", "
                    + LocalTime.parse(time.toString(), inputTimeFormatter);
        }
        return result;
    }


}
