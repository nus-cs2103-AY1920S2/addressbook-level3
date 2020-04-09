package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.exceptions.InvalidReminderException;

public class Recurring {
    private final RecurType type;
    private final LocalDateTime referenceDateTime;

    public static final String MESSAGE_CONSTRAINTS =
            "Recurring should be in the format d or w, for eg: rec/d";
    public static final String MESSAGE_RECURRING_TASK_SUCCESS = "Recurring Task: %1$s";

    public static final String VALIDATION_REGEX = "[dw]";
    public static final DateTimeFormatter stringFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yy@HH:mm");

    public Recurring(String recurringStringStorage) throws ParseException {
        String recurTypeString = recurringStringStorage.substring(0, 1);
        String dateTimeString = recurringStringStorage.substring(1);
        this.type = parseRecurType(recurTypeString);
        this.referenceDateTime = parseDateTime(dateTimeString);
    }

    public Recurring(String recurringString, LocalDateTime referenceDateTime)
            throws ParseException {
        this.type = parseRecurType(recurringString);
        this.referenceDateTime = referenceDateTime;
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        return stringFormatter.parse(dateTimeString, LocalDateTime::from);
    }

    public RecurType parseRecurType(String recurringString) throws ParseException {
        if (recurringString.equals("d")) {
            return RecurType.DAILY;
        } else if (recurringString.equals("w")) {
            return RecurType.WEEKLY;
        } else {
            throw new ParseException(Recurring.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns a copy of the task that is being reset with everything being the same except for
     * whether the method is Done
     *
     * @param taskToReset
     * @return copied task with done set to undone
     */
    public Done resetDone(Done done) {
        if (done.getIsDone()) {
            done = new Done("N");
        }
        return done;
    }

    public Optional<Reminder> resetReminder(Optional<Reminder> currentOptReminder) {
        if (currentOptReminder.isPresent()) {
            Reminder currentReminder = currentOptReminder.get();
            LocalDateTime currentDateTime = currentReminder.getDateTime();
            if (shouldUpdateReminder(currentDateTime)) {
                LocalDateTime newDateTime = currentDateTime.plusDays(type.getDayInterval());
                try {
                    currentOptReminder = Optional.of(new Reminder(newDateTime));
                } catch (InvalidReminderException e) {
                    e.printStackTrace();
                }
            }
        }
        return currentOptReminder;
    }

    public boolean shouldUpdateReminder(LocalDateTime reminderDateTime) {
        Duration duration = Duration.between(LocalDateTime.now(), reminderDateTime);
        boolean hasPassed = duration.getSeconds() < 0;
        return hasPassed;
    }

    public Task resetTask(Task taskToReset) {
        assert taskToReset != null;
        Name updatedName = taskToReset.getName();
        Priority updatedPriority = taskToReset.getPriority();
        Description updatedDescription = taskToReset.getDescription();
        Set<Tag> updatedTags = taskToReset.getTags();
        Done updatedDone = resetDone(taskToReset.getDone());
        Optional<Reminder> updatedOptReminder = resetReminder(taskToReset.getOptionalReminder());
        Optional<Recurring> sameOptRecurring = taskToReset.getOptionalRecurring();
        return new Task(
                updatedName,
                updatedPriority,
                updatedDescription,
                updatedDone,
                updatedTags,
                updatedOptReminder,
                sameOptRecurring);
    }

    /** Returns true if a given string is a valid name. */
    public static boolean isValidRecurring(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /** Returns Daily or Weekly for display on the card. */
    public String displayRecurring() {
        return StringUtil.getTitleCase(type.name());
    }

    public TimerTask generateTimerTask(Model model, Index index) {

        return new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(
                        () -> {
                            requireNonNull(model);

                            List<Task> lastShownList = model.getFilteredTaskList();
                            Task taskToReset = lastShownList.get(index.getZeroBased());
                            Task resetedTask = resetTask(taskToReset);
                            model.setTask(taskToReset, resetedTask);
                        });
            }
        };
    }

    /**
     * Handles the triggering of the recurring behaviour. First time it is triggered is either a day
     * or week after the reference date.
     *
     * @param model
     * @param index
     */
    public void triggerRecurring(Model model, Index index) {
        TimerTask repeatedTask = generateTimerTask(model, index);
        Timer timer = new Timer("Timer");
        long period = type.getInterval();
        long delayToFirstTrigger =
                Duration.between(
                                LocalDateTime.now(),
                                referenceDateTime.plusDays(type.getDayInterval()))
                        .getSeconds();
        delayToFirstTrigger = delayToFirstTrigger >= 0 ? delayToFirstTrigger * 1000 : 0;
        timer.scheduleAtFixedRate(
                repeatedTask, delayToFirstTrigger, period); // might run twice in the first time
    }

    public long getDelayToFirstTrigger() {
        return Duration.between(
                        LocalDateTime.now(),
                        referenceDateTime.plusDays(type.getDayInterval()))
                .getSeconds();
    }

    public long getPeriod() {
        return type.getInterval();
    }
    

    @Override
    public String toString() {
        String typeString = type.name().substring(0, 1).toLowerCase();
        String dateTimeString = referenceDateTime.format(stringFormatter);
        return typeString + dateTimeString;
    }
}
