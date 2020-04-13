package seedu.address.logic.commands.calendar;

import static seedu.address.model.calendar.Calendar.DAYS_IN_WEEK;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.task.ScheduledTask;
import seedu.address.model.task.Task;
import seedu.address.model.util.DailySchedulable;
import seedu.address.model.util.DailySchedulableComparator;

/**
 * Finds empty slots in calendar to user.
 */
public class CalFindCommand extends CalCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CAL + " "
            + COMMAND_WORD_FIND + " empty"
            + ": Finds empty slots in calendar in the week.";

    public static final String MESSAGE_SUCCESS = "Here's the list of empty slots from today to Sunday:\n";

    /**
     * Returns the list of schedulable tasks and lessons in a day.
     */
    private List<DailySchedulable> generateSchedulables(
            Calendar date, int day, List<Task> tasks, List<Lesson> lessons) {
        List<DailySchedulable> schedulables = new ArrayList<>();
        lessons.forEach(x -> {
            if (x.getDayAndTime().getDay().getValue() == (day + 1)) {
                schedulables.add(x);
            }
        });

        tasks.forEach(task -> {
            //adding tasks with time only
            if (task instanceof ScheduledTask && date.isWithinDate(task)
                && !task.getComparableTime().get().equals(LocalTime.parse("00:00"))) {
                schedulables.add(task);
            }
        });

        schedulables.sort(new DailySchedulableComparator());
        return schedulables;
    }

    private String getDay(int dayIndex) {
        switch (dayIndex) {
        case 0:
            return "MONDAY";
        case 1:
            return "TUESDAY";
        case 2:
            return "WEDNESDAY";
        case 3:
            return "THURSDAY";
        case 4:
            return "FRIDAY";
        case 5:
            return "SATURDAY";
        default:
            return "SUNDAY";
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Calendar calendar = Calendar.getNowCalendar();
        List<Task> tasks = model.getFilteredTaskList();
        List<Lesson> lessons = model.getLessons();
        int dayOfWeek = calendar.getDayOfWeek();
        StringBuilder result = new StringBuilder(MESSAGE_SUCCESS);
        Calendar[] week = calendar.getWeek();
        for (int i = dayOfWeek - 1; i < DAYS_IN_WEEK; i++) {
            result.append(getDay(i) + ":\n");
            Calendar date = week[i];
            List<DailySchedulable> schedulables = generateSchedulables(date, i, tasks, lessons);

            LocalTime startTime = LocalTime.parse("00:00");
            LocalTime endTime;
            for (DailySchedulable schedulable : schedulables) {
                LocalTime time = schedulable.getComparableTime().get();
                if (startTime.isBefore(time)) {
                    endTime = time;
                    result.append(startTime.toString())
                            .append("-")
                            .append(endTime.toString())
                            .append("  ");
                }

                if (schedulable instanceof Lesson) {
                    startTime = ((Lesson) schedulable).getDayAndTime().getEndTime();
                } else if (startTime.isBefore(time)) {
                    startTime = time;
                }
            }

            if (startTime.isBefore(LocalTime.parse("23:59"))) {
                result.append(startTime.toString())
                        .append("-23:59\n");
            } else {
                result.append("\n");
            }

        }
        return new CommandResult(result.toString(), CommandType.CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof CalFindCommand;
    }
}
