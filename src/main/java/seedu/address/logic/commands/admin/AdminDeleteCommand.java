package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DATES;

import java.time.format.TextStyle;
import java.util.Locale;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.DateContainsKeywordsPredicate;
import seedu.address.model.admin.exceptions.DateNotFoundException;

/**
 * Deletes the administrative details of the students list on a specific date.
 */
public class AdminDeleteCommand extends AdminCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ADMIN_DELETE
            + "YYYY-MM-DD: to delete the class admin details at the specified date.\n"
            + "For example: admin delete 2020-04-23";
    public static final String MESSAGE_SUCCESS = "Admin list has been deleted for %1$s";
    private final DateContainsKeywordsPredicate predicate;

    public AdminDeleteCommand(DateContainsKeywordsPredicate predicate) { //predicate here is a LocalDate object
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws DateNotFoundException {
        requireNonNull(model);
        model.updateFilteredDateList(predicate);
        if (model.getFilteredDateList().size() == 0) { //no match
            model.updateFilteredDateList(PREDICATE_SHOW_ALL_DATES);
            throw new DateNotFoundException();
        } else {
            Date dateToDelete = model.getFilteredDateList().get(0);
            String fullDate = dateToDelete.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " "
                    + dateToDelete.getDate().getDayOfMonth() + " " + dateToDelete.getDate().getYear();
            model.deleteDate(dateToDelete);
            return new CommandResult(String.format(MESSAGE_SUCCESS, fullDate));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AdminDeleteCommand // instanceof handles nulls
                && predicate.equals(((AdminDeleteCommand) other).predicate)); // state check
    }
}
