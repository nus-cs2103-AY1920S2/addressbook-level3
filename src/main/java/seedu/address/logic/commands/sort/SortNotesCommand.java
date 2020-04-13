package seedu.address.logic.commands.sort;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.SortCommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Note;

/**
 * Sorts Note items in the resume book.
 */
public class SortNotesCommand extends SortCommand {

    private final Comparator<Note> sortComparator;
    private final String sortOrder;
    private final boolean isReverse;

    public SortNotesCommand(String sortOrder, boolean isReverse) {
        this.sortOrder = sortOrder;
        this.isReverse = isReverse;
        Comparator<Note> baseComparator =
                sortOrder.equalsIgnoreCase("name")
                        ? Comparator.comparing(Note::getName)
                        : Comparator.comparing(Note::getTime);
        sortComparator = isReverse ? baseComparator.reversed() : baseComparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.sortNotes(sortComparator);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitResumeBook();

        return new SortCommandResult(
                String.format(MESSAGE_SUCCESS, Note.class.getSimpleName()), model.getDisplayType());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SortNotesCommand
                && sortOrder.equals(((SortNotesCommand) other).sortOrder)
                && isReverse == ((SortNotesCommand) other).isReverse);
    }
}
