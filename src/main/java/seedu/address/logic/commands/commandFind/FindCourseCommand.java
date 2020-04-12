package seedu.address.logic.commands.commandFind;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.CourseNameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all courses in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCourseCommand extends Command {

    public static final String COMMAND_WORD = "find-course";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Finds all courses whose names contain any of "
                    + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
                    + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                    + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final CourseNameContainsKeywordsPredicate predicate;

    public FindCourseCommand(CourseNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCourseList(predicate);
        model.getMainWindow().callSwitchToCourse();
        return new CommandResult(
                String.format(Messages.MESSAGE_COURSES_LISTED_OVERVIEW,
                        model.getFilteredCourseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCourseCommand // instanceof handles nulls
                && predicate.equals(((FindCourseCommand) other).predicate)); // state check
    }
}
