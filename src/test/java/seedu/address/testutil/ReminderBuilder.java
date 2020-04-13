package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {
    public static final String DEFAULT_DESCRIPTION = "Return midterms paper to T02";
    public static final String DEFAULT_DATE = "2022-03-18";
    public static final String DEFAULT_TIME = "15:00";
    public static final String DEFAULT_DONE = "false";

    private Description description;
    private LocalDate date;
    private LocalTime time;
    private boolean done;

    public ReminderBuilder() throws ParseException {
        description = ParserUtil.parseDescription(DEFAULT_DESCRIPTION);
        date = ParserUtil.parseDate(DEFAULT_DATE);
        time = ParserUtil.parseTime(DEFAULT_TIME);
        done = Boolean.parseBoolean(DEFAULT_DONE);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        description = reminderToCopy.getDescription();
        date = reminderToCopy.getDate();
        time = reminderToCopy.getTime();
        done = reminderToCopy.getDone();
    }

    /**
     * Sets the {@code Description} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatter);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.time = LocalTime.parse(time, formatter);
        return this;
    }

    /**
     * Sets the {@code Done} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDone(String done) {
        this.done = Boolean.parseBoolean(done);
        return this;
    }

    public Reminder build() {
        return new Reminder(description, date, time, done);
    }
}
