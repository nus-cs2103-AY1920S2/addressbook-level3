package seedu.address.logic.commands.notes;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.notes.Notes;

/**
 *  Represents NotesDeleteCommand which deletes a note from storage.
 */
public class NotesDeleteCommand extends Command {

    public static final String COMMAND_WORD = "notesd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " <Index>";

    public static final String MESSAGE_SUCCESS = "Student Note deleted.";

    private final Index targetIndex;

    public NotesDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Overriden execute method which deletes a specified note from a student, and returning the
     * updated student to the model.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Notes> lastShownList = model.getFilteredNotesList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTES_DISPLAYED_INDEX);
        }

        Notes toBeDeleted = lastShownList.get(targetIndex.getZeroBased());
        model.deleteNote(toBeDeleted);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotesDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((NotesDeleteCommand) other).targetIndex)); // state check
    }

}
