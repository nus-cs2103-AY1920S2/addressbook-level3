package seedu.address.logic.commands.sort;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.SortCommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Internship;

/**
 * Sort Internship items in the resume book.
 */
public class SortInternshipsCommand extends SortCommand {

    private final Comparator<Internship> sortComparator;
    private final String sortOrder;
    private final boolean isReverse;

    public SortInternshipsCommand(String sortOrder, boolean isReverse) {
        this.sortOrder = sortOrder;
        this.isReverse = isReverse;
        Comparator<Internship> baseComparator =
                sortOrder.equalsIgnoreCase("name")
                        ? Comparator.comparing(Internship::getName)
                        : Comparator.comparing(Internship::getFrom);
        sortComparator = isReverse ? baseComparator.reversed() : baseComparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.sortInternships(sortComparator);
        model.setInternshipToDisplay();
        model.commitResumeBook();

        return new SortCommandResult(
                String.format(MESSAGE_SUCCESS, Internship.class.getSimpleName()), model.getDisplayType());
    }

    // This equals implementation is a bit crude but it saves us the hassle of comparing two Comparator<> objects.
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SortInternshipsCommand
                && sortOrder.equals(((SortInternshipsCommand) other).sortOrder)
                && isReverse == ((SortInternshipsCommand) other).isReverse);
    }
}
