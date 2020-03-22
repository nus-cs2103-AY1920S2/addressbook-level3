package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import seedu.address.model.Model;

/**
 * Lists all internship applications in the internship diary to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all internship applications";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInternshipApplicationList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
