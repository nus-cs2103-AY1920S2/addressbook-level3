package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.Duration;

import seedu.address.model.task.exceptions.InvalidReminderException;
import seedu.address.ui.MainWindow;


/**
 * Represents a Task's priority number in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidPriority(String)}
 */
public class Reminder {

    public static final String MESSAGE_CONSTRAINTS = "Reminder should be in format DD/MM/YY@HH:mm eg 04/11/20@10:30";
    public static final String VALIDATION_REGEX = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(2[0-9])@(([0-2][0-4]):([0-5][0-9]))";
    private final LocalDateTime reminderTime;
    private final String description;
    private final String name;
    private boolean hasFired;


    /**
     * Constructs a reminder with the corresponding time, description and name
     * 
     * @param reminderTime
     * @param Description
     * @param Name
     */
    public Reminder(LocalDateTime reminderTime, String description, String name) {
        this.reminderTime = reminderTime;
        this.description = description;
        this.name = name;
        this.hasFired = false;
        setReminder();
    }

    private void timedPrint(long delay) {
        MainWindow.triggerReminder(this, delay);
    }

    private long calculateDelay(LocalDateTime reminderTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, reminderTime);
        long delay = duration.getSeconds();
        return delay;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setHasFired() {
        this.hasFired = true;
    }

    public static boolean isValidReminder(String reminderString) {
        System.out.println(reminderString.matches(VALIDATION_REGEX));
        return reminderString.matches(VALIDATION_REGEX);
    }



    /**
     * Sets the reminder based on the time delay calculated. 
     * If it has fired then nothing is triggered.
     * 
     * @throws InvalidReminderException if the time delay is negative and has not been fired before.
     */
    private void setReminder() {
        long delay = calculateDelay(reminderTime);
        if (delay < 0) {
            if (!hasFired) {
                throw new InvalidReminderException();
            }
        } else {
            timedPrint(delay);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Reminder Name: ");
        sb.append(name);
        sb.append(", Reminder Description: ");
        sb.append(description);
        sb.append(", Reminder LocalDateTime: ");
        sb.append(reminderTime.toString());
        return sb.toString();
    }


}