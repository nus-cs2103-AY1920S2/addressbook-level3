package seedu.zerotoone.logic.commands.exercise.set;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.ExerciseSet;

/**
 * Adds an exerciseSet to the exerciseSet list.
 */
public class AddCommand extends SetCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = SetCommand.COMMAND_WORD + " "
            + COMMAND_WORD + ": creates a new set. "
            + "Parameters: "
            + PREFIX_EXERCISE_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXERCISE_NAME + "Pushups ";

    public static final String MESSAGE_SUCCESS = "New set added: %1$s";

    private final ExerciseSet toAdd;

    /**
     * Creates a CreateCommand to add the specified {@code ExerciseSet}
     */
    public AddCommand(ExerciseSet exerciseSet) {
        requireNonNull(exerciseSet);
        toAdd = exerciseSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // model.addExerciseSet(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
