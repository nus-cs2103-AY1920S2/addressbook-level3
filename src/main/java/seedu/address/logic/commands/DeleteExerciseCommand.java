package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.statistics.PersonalBestFinder;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.UniqueExerciseList;

/**
 * Deletes an {@code Exercise} identified using it's displayed index from the exercise table(GUI).
 *
 * @author @yonggiee
 */
public class DeleteExerciseCommand extends Command {

    public static final String COMMAND_WORD = "delete-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the exercise identified by the index number used in the displayed Exercise Table.\n"
        + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted Exercise:\n%1$s";
    public static final String MESSAGE_CLIENT_NOT_IN_VIEW = "You currently do not have a client in view, "
        + "use the view-c command to view a client first";

    private final Index targetIndex;

    public DeleteExerciseCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasClientInView()) {
            throw new CommandException(MESSAGE_CLIENT_NOT_IN_VIEW);
        }

        Client clientToEdit = model.getClientInView();
        UniqueExerciseList clientToEditExerciseList = clientToEdit.getExerciseList();

        if (targetIndex.getZeroBased() >= clientToEditExerciseList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise toRemove = clientToEditExerciseList.getExercise(targetIndex);

        // mutates the list belonging to the client by adding the exercise
        clientToEditExerciseList.remove(toRemove);

        PersonalBestFinder.generateAndSetPersonalBest(clientToEdit);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove.getForOutput()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExerciseCommand // instanceof handles nulls
                        && targetIndex.equals(((DeleteExerciseCommand) other).targetIndex)); // state check
    }
}
