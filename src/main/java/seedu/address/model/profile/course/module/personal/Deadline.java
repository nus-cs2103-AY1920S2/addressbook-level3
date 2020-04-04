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
    public static final String MESSAGE_CONSTRAINTS = "Date and time field of deadline should be in the format "
            + "YYYY-MM-DD and HH:mm respectively.\n" + "Dates should be valid Gregorian calendar dates"
            + " and time should be in 24-hour format.\n"
            + "Example: 2020-04-25 18:54";

    protected String moduleCode;
    protected String description;
    protected LocalDate date;
    protected LocalTime time;
    private String tag;

    private String inputTimePattern = "HH:mm";
    private DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(inputTimePattern);

    public Deadline(String moduleCode, String description, String date, String time) throws DateTimeException {
        this.moduleCode = moduleCode;
        this.description = description;
        try {
            this.date = LocalDate.parse(date);
            this.time = LocalTime.parse(time, inputTimeFormatter);

            LocalDate today = LocalDate.now();
            if (this.date.isBefore(today.plusDays(5))) {
                tag = "RED";
            } else if (this.date.isBefore(today.plusDays(10))) {
                tag = "YELLOW";
            } else {
                tag = "GREEN";
            }

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

    public String getTag() {
        return tag;
    }

    public void setDescription(String newTask) {
        this.description = newTask;
    }

    public void setDateTime(String newDate, String newTime) throws DateTimeException {
        try {
            this.date = LocalDate.parse(newDate);
            this.time = LocalTime.parse(newTime, inputTimeFormatter);
        } catch (Exception e) {
            throw new DateTimeException("Try: d/YYYY-MM-DD HH:mm");
        }
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

    public String getModuleCode() {
        return this.moduleCode;
    }

    /**
     * Adds tag to new Deadline so that UI changes colour according to date.
     */
    public void addTag() {
        LocalDate today = LocalDate.now();
        if (this.date.isBefore(today.plusDays(5))) {
            tag = "RED";
        } else if (this.date.isBefore(today.plusDays(10))) {
            tag = "YELLOW";
        } else {
            tag = "GREEN";
        }
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
