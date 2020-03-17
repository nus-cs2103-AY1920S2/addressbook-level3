package seedu.address.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORKOUT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_OF_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_OF_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;

/**
 * Adds an exercise to the exercise list.
 */
public class CreateCommand extends ExerciseCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": creates a new exercise. "
            + "Parameters: "
            + PREFIX_NEW_EXERCISE_NAME + "NAME "
            + PREFIX_NUM_OF_REPS + "REPS "
            + PREFIX_NUM_OF_SETS + "SETS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NEW_EXERCISE_NAME + "Pushups "
            + PREFIX_NUM_OF_REPS + "20 "
            + PREFIX_NUM_OF_SETS + "3 ";

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
