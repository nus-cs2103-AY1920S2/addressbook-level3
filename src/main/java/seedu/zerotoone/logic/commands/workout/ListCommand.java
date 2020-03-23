package seedu.zerotoone.logic.commands.workout;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;

/**
 * Lists all workouts in the workout list to the user.
 */
public class ListCommand extends WorkoutCommand {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all workouts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
