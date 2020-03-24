package seedu.address.model.task;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import seedu.address.model.task.exceptions.InvalidReminderException;

/**
 * Represents a Task's priority number in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidPriority(String)}
 */
public class Reminder {

    public static final String MESSAGE_CONSTRAINTS =
            "Reminder should be in format DD/MM/YY@HH:mm eg 04/11/20@10:30";
    public static final DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("dd/MM/yy@HH:mm");
    private final LocalDateTime reminderDateTime;
    private boolean hasFired;
    private long delay;

    /**
     * Constructs a reminder with the corresponding time, description and name.
     *
     * @param reminderDateTime
     * @param Description
     * @param Name
     * @throws InvalidReminderException
     */
    public Reminder(LocalDateTime reminderDateTime) throws InvalidReminderException {
        this.reminderDateTime = reminderDateTime;
        this.hasFired = false;
        this.delay = Integer.MAX_VALUE;
        setDelay();
    }

    public Reminder(String dateTimeString) throws InvalidReminderException {
        this.reminderDateTime = stringFormatter.parse(dateTimeString, LocalDateTime::from);
        this.hasFired = false;
        this.delay = Integer.MAX_VALUE;
        setDelay();
    }

    private long calculateDelay(LocalDateTime reminderDateTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, reminderDateTime);
        long delay = duration.getSeconds();
        return delay;
    }

    public void setHasFired() {
        this.hasFired = true;
    }

    public static boolean isValidReminder(String dateTimeString) {
        try {
            stringFormatter.parse(dateTimeString);
        } catch (DateTimeParseException e) {
            System.out.println("test test");
            return false;
        }
        return true;
    }

    /**
     * Sets the reminder based on the time delay calculated. If it has fired then nothing is
     * triggered.
     *
     * @throws InvalidReminderException if the time delay is negative and has not been fired before.
     */
    private void setDelay() throws InvalidReminderException {
        long timeDelay = calculateDelay(reminderDateTime);
        if (timeDelay < 0) {
            if (!hasFired) {
                throw new InvalidReminderException();
            }
        } else {
            this.delay = timeDelay;
        }
    }

    /** @return the delay */
    public long getDelay() {
        return delay;
    }

    public String displayReminder() {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd LLLL 'at' h:ma");
        return reminderDateTime.format(customFormatter);
    }

    @Override
    public String toString() {
        return reminderDateTime.format(stringFormatter);
    }

}
