package seedu.address.logic.commands.commandClear;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.AssignmentAddressBook;
import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelFinance.FinanceAddressBook;
import seedu.address.model.modelStaff.StaffAddressBook;
import seedu.address.model.modelStudent.StudentAddressBook;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear-all";
    public static final String MESSAGE_SUCCESS = "All databases have been removed! You cannot undo/redo anything else after clear-all command.\"";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setStaffAddressBook(new StaffAddressBook());
        model.setStudentAddressBook(new StudentAddressBook());
        model.setFinanceAddressBook(new FinanceAddressBook());
        model.setCourseAddressBook(new CourseAddressBook());
        model.setAssignmentAddressBook(new AssignmentAddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
