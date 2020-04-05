package seedu.address.logic.commands.notes;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Represents a NotesExportCommand which exports currently stored notes, into a .txt file format.
 */
public class NotesExportCommand extends Command {

    public static final String COMMAND_WORD = "notesex";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [with no other parameters]";

    public static final String MESSAGE_SUCCESS = "Notes are exported to studentNotes.csv in the data folder";

    /**
     * Overriden execute command which returns CommandResult containing success message.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
