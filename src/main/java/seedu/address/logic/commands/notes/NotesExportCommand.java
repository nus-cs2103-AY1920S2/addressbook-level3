package seedu.address.logic.commands.notes;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Represents a NotesExportCommand which exports currently stored notes, into a .csv file format.
 * Subclass of NotesCommand
 */
public class NotesExportCommand extends NotesCommand {

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " "
            + PREFIX_EXPORT + " [with no other parameters]";

    public static final String MESSAGE_SUCCESS = "Notes are exported to studentNotes.csv in the data folder";

    /**
     * The execute() command which returns CommandResult containing success message.
     * @param model {@code Model} which the command should operate on.
     * @return a new CommandResult
     */
    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NotesExportCommand)) {
            return false;
        }

        return true;
    }
}
