package seedu.zerotoone.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.commands.exercise.ExerciseCommand;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * Deletes a exercise identified using it's displayed index from the exercise list.
 */
public class StartCommand extends Command {
    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = "Usage: start EXERCISE_ID";
    public static final String MESSAGE_START_EXERCISE_SUCCESS = "Started Exercise: %1$s";
    private final Index exerciseId;

    public StartCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.exerciseId = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Exercise exerciseToStart = lastShownList.get(exerciseId.getZeroBased());
        model.start(exerciseToStart);

        String outputMessage = String.format(MESSAGE_START_EXERCISE_SUCCESS,
                exerciseToStart.getExerciseName().toString());
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && exerciseId.equals(((StartCommand) other).exerciseId)); // state check
    }
}
