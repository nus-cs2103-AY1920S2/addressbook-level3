package seedu.address.logic.commands.commandAdd;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandDelete.DeleteCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a course to the address book.
 */
public class AddCourseCommand extends AddCommand {

    public static final String COMMAND_WORD = "add-course";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a course to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Python OOP "
            + PREFIX_AMOUNT + "1000 "
            + PREFIX_TAG + "Easy "
            + PREFIX_TAG + "Basics ";

    public static final String MESSAGE_SUCCESS = "New course added: %1$s";
    public static final String MESSAGE_DUPLICATE_COURSE = "This course already exists in the address book";

    private final Course toAdd;

    private Integer index;

    /**
     * Creates an AddCommand to add the specified {@code Assignment}
     */
    public AddCourseCommand(Course course) {
        requireNonNull(course);
        toAdd = course;
    }

    public AddCourseCommand(Course course, Integer index) {
        requireAllNonNull(course, index);
        this.toAdd = course;
        this.index = index;
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new DeleteCourseCommand(this.toAdd);
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);

        if (model.has(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_COURSE);
        }

        if (index == null) {
            model.add(toAdd);
        } else {
            model.addAtIndex(toAdd, index);
        }
        EdgeManager.revokeEdgesFromDeleteEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCourseCommand // instanceof handles nulls
                && toAdd.equals(((AddCourseCommand) other).toAdd));
    }
}
