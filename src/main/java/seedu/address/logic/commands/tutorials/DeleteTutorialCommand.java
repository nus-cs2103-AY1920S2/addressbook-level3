package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;

/**
 * Deletes an existing Tutorial from TAble.
 */

public class DeleteTutorialCommand extends Command {
    public static final String COMMAND_WORD = "deleteTutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the index number used in the displayed Tutorial TAble.\n"
            + "Parameters: "
            + PREFIX_INDEX
            + "\nExample: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS = "Deleted Tutorial!\n%1$s\n"
            + "Please use listAttendance command to refresh the attendance list.";

    private final Index targetIndex;

    public DeleteTutorialCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial tutorialToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTutorial(tutorialToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, tutorialToDelete), false,
                false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTutorialCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTutorialCommand) other).targetIndex)); // state check
    }
}
