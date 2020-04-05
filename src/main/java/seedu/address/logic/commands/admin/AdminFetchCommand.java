package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DATES;

import java.time.LocalDate;
import java.time.format.TextStyle;

import java.util.Locale;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.admin.DateContainsKeywordsPredicate;
import seedu.address.model.admin.exceptions.DateNotFoundException;

/**
 * Fetches the administrative details of the students list on a specific date.
 */
public class AdminFetchCommand extends AdminCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ADMIN_FETCH
            + " YYYY-MM-DD: to display the class admin details.";
    public static final String MESSAGE_SUCCESS = "Class admin details for %1$s listed!";
    private final DateContainsKeywordsPredicate predicate;

    public AdminFetchCommand(DateContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws DateNotFoundException {
        requireNonNull(model);
        model.updateFilteredDateList(predicate);
        if (model.getFilteredDateList().size() == 0) {
            model.updateFilteredDateList(PREDICATE_SHOW_ALL_DATES);
            throw new DateNotFoundException();
        } else {
            LocalDate date = model.getFilteredDateList().get(0).getDate();
            String fullDate = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " "
                    + date.getDayOfMonth() + " " + date.getYear();
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, fullDate));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AdminFetchCommand // instanceof handles nulls
                && predicate.equals(((AdminFetchCommand) other).predicate)); // state check
    }
}
