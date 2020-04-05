package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.time.LocalDate;
import java.time.format.TextStyle;

import java.util.Locale;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.admin.Date;

/**
 * Saves the current most updated administrative list as today's date.
 */
public class AdminSaveCommand extends AdminCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ADMIN_SAVE
            + ": to save the most updated admin list as today's date.";
    public static final String MESSAGE_SUCCESS = "This admin list has been saved for %1$s";
    private final LocalDate toAdd;

    public AdminSaveCommand(LocalDate date) {
        toAdd = date;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        Date date = new Date(toAdd, model.getFilteredStudentList());
        model.addDate(date);
        String fullDate = toAdd.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " "
                + toAdd.getDayOfMonth() + " " + toAdd.getYear();
        return new CommandResult(String.format(MESSAGE_SUCCESS, fullDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AdminSaveCommand // instanceof handles nulls
                && toAdd.equals(((AdminSaveCommand) other).toAdd));
    }
}
