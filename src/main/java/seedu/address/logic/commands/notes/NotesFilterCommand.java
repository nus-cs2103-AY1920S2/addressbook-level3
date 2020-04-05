package seedu.address.logic.commands.notes;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.notes.NotesContainKeywordsPredicate;

/**
 * Represents a NotesFilterCommand which filters the notes displayed based on input keywords.
 */
public class NotesFilterCommand extends Command {
    public static final String COMMAND_WORD = "notesf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " <Keywords(s)>\n"
            + "This command looks for keywords present in Student, "
            + "DateTime, Priority and Content fields";

    public static final String MESSAGE_SUCCESS = "Displaying Notes with Keywords: ";

    private final NotesContainKeywordsPredicate predicate;

    /**
     * Constructor of NotesFilterCommand
     * @param predicate
     */
    public NotesFilterCommand(NotesContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Overriden execute method which returns a CommandResult with success message.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredNotesList(predicate);
        return new CommandResult(MESSAGE_SUCCESS + predicate.getKeywords().toString());
    }
}
