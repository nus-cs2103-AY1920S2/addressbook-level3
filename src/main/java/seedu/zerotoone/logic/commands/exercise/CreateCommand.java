package seedu.zerotoone.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * Adds an exercise to the exercise list.
 */
public class CreateCommand extends ExerciseCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": creates a new exercise. "
            + "Parameters: "
            + PREFIX_EXERCISE_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXERCISE_NAME + "Pushups ";

    public static final String MESSAGE_SUCCESS = "New exercise added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists";

    private final Exercise toAdd;

    /**
     * Creates a CreateCommand to add the specified {@code Exercise}
     */
    public CreateCommand(Exercise exercise) {
        requireNonNull(exercise);
        toAdd = exercise;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExercise(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        model.addExercise(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && toAdd.equals(((CreateCommand) other).toAdd));
    }
}
