package seedu.jelphabot.model.util;

import static seedu.jelphabot.model.util.SampleDateUtil.FIVE_DAYS_LATER;
import static seedu.jelphabot.model.util.SampleDateUtil.LAST_NIGHT;
import static seedu.jelphabot.model.util.SampleDateUtil.LAST_WEEK;
import static seedu.jelphabot.model.util.SampleDateUtil.NEXT_MONTH;
import static seedu.jelphabot.model.util.SampleDateUtil.NEXT_WEEK;
import static seedu.jelphabot.model.util.SampleDateUtil.TODAY_MORNING;
import static seedu.jelphabot.model.util.SampleDateUtil.TONIGHT;
import static seedu.jelphabot.model.util.SampleIndexUtil.ELEVENTH_INDEX;
import static seedu.jelphabot.model.util.SampleIndexUtil.FIFTH_INDEX;
import static seedu.jelphabot.model.util.SampleIndexUtil.FIRST_INDEX;
import static seedu.jelphabot.model.util.SampleIndexUtil.SECOND_INDEX;
import static seedu.jelphabot.model.util.SampleIndexUtil.THIRD_INDEX;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.TimeSpent;

/**
 * Contains utility methods for populating {@code JelphaBot} with sample data.
 */
public class SampleDataUtil {

    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Description("Individual Assignment 1"), Status.INCOMPLETE, new DateTime(LAST_NIGHT),
                new ModuleCode("CS3230"), Priority.HIGH, getTagSet("graded"),
                new TimeSpent(Duration.ofMinutes(7))
            ),
            new Task(new Description("Tutorial 3"), Status.INCOMPLETE, new DateTime(LAST_WEEK),
                new ModuleCode("ACC1101"), Priority.LOW, getTagSet("classpart", "SU-able"),
                new TimeSpent(Duration.ofMinutes(3))
            ),
            new Task(new Description("MidTerm Revision Papers"), Status.INCOMPLETE, new DateTime(NEXT_MONTH),
                new ModuleCode("ST2334"), Priority.MEDIUM, getTagSet("exam"),
                new TimeSpent(Duration.ofMinutes(4))
            ),
            new Task(new Description("Consultation"), Status.INCOMPLETE, new DateTime(NEXT_WEEK),
                new ModuleCode("PF1103"), Priority.MEDIUM, getTagSet("consult", "prepare"),
                new TimeSpent(Duration.ofMinutes(1))
            ),
            new Task(new Description("Revision"), Status.INCOMPLETE, new DateTime(NEXT_WEEK),
                new ModuleCode("ST2334"), Priority.MEDIUM, getTagSet("midterm", "prepare"),
                new TimeSpent(Duration.ofMinutes(1))
            ),
            new Task(new Description("Team Project"), Status.INCOMPLETE, new DateTime(NEXT_WEEK),
                new ModuleCode("CS2103T"), Priority.HIGH, getTagSet("final-submission", "team"),
                new TimeSpent(Duration.ofMinutes(1))
            ),
            new Task(new Description("Individual Assignment 2"), Status.INCOMPLETE, new DateTime(NEXT_WEEK),
                new ModuleCode("CS3230"), Priority.HIGH, getTagSet("graded"),
                new TimeSpent(Duration.ofMinutes(1))
            ),
            new Task(new Description("Tutorial 4"), Status.INCOMPLETE, new DateTime(TODAY_MORNING),
                new ModuleCode("ACC1101"), Priority.LOW, getTagSet("classpart", "Su-able"),
                new TimeSpent(Duration.ofMinutes(9))
            ),
            new Task(new Description("Graded Homework 3"), Status.INCOMPLETE, new DateTime(FIVE_DAYS_LATER),
                new ModuleCode("MA1101R"), Priority.HIGH, getTagSet("graded", "SU-able"),
                new TimeSpent(Duration.ofMinutes(4))
            ),
            new Task(new Description("Lab 1"), Status.INCOMPLETE, new DateTime(FIVE_DAYS_LATER),
                new ModuleCode("MA1101R"), Priority.LOW, getTagSet("ungraded", "SU-able"),
                new TimeSpent(Duration.ofMinutes(6))
            ),
            new Task(new Description("Assignment 3"), Status.INCOMPLETE, new DateTime(TONIGHT),
                new ModuleCode("ST2334"), Priority.MEDIUM, getTagSet("schoolwork"),
                new TimeSpent(Duration.ofMinutes(7))
            ),
            new Task(new Description("Readings"), Status.INCOMPLETE, new DateTime(NEXT_MONTH),
                new ModuleCode("EC1103"), Priority.MEDIUM, getTagSet("readings", "SU-able"),
                new TimeSpent(Duration.ofSeconds(7))
            )
        };
    }

    public static Reminder[] getSampleReminders() {
        return new Reminder[]{
            new Reminder(FIRST_INDEX, new ReminderDay("2"), new ReminderHour("0")),
            new Reminder(SECOND_INDEX, new ReminderDay("1"), new ReminderHour("5")),
            new Reminder(THIRD_INDEX, new ReminderDay("4"), new ReminderHour("1")),
            new Reminder (FIFTH_INDEX, new ReminderDay("7"), new ReminderHour("0")),
            new Reminder(ELEVENTH_INDEX, new ReminderDay("1"), new ReminderHour("0"))
        };
    }

    public static ReadOnlyJelphaBot getSampleJelphaBot() {
        JelphaBot sampleJelphaBot = new JelphaBot();
        for (Task sampleTask : getSampleTasks()) {
            sampleJelphaBot.addTask(sampleTask);
        }
        for (Reminder sampleReminder : getSampleReminders()) {
            sampleJelphaBot.addReminder(sampleReminder);
        }
        return sampleJelphaBot;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                   .map(Tag::new)
                   .collect(Collectors.toSet());
    }

}
