package seedu.zerotoone.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.schedule.DateTime;
import seedu.zerotoone.model.schedule.OneTimeSchedule;

/**
 * STEPH_TODO_JAVADOC
 */
public class CreateCommand extends ScheduleCommand {
    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_USAGE = "Usage: schedule create WORKOUT_ID d/<dateTime>";
    public static final String MESSAGE_SUCCESS = "Scheduled workout: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists";

    private final Index workoutId;
    private final DateTime dateTime;

    /**
     * STEPH_TODO_JAVADOC
     */
    public CreateCommand(Index workoutId, DateTime dateTIme) {
        requireNonNull(workoutId);
        requireNonNull(dateTIme);
        this.workoutId = workoutId;
        this.dateTime = dateTIme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList(); // TO_CHANGE_EXERCISE_TO_WORKOUT

        if (workoutId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Exercise workoutToSchedule = lastShownList.get(workoutId.getZeroBased()); // TO_CHANGE_EXERCISE_TO_WORKOUT
        OneTimeSchedule schedule = new OneTimeSchedule(workoutToSchedule, dateTime);
        if (model.hasSchedule(schedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        model.addSchedule(schedule);
        model.populateSortedScheduledWorkoutList();

        // TO_CHANGE_EXERCISE_TO_WORKOUT
        String outputMessage = String.format(MESSAGE_SUCCESS, workoutToSchedule.getExerciseName());
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
