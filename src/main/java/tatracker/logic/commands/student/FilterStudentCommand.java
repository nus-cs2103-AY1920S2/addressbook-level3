/*
package tatracker.logic.commands.student;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.FindCommand;
import tatracker.model.Model;
import tatracker.model.student.NameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;


/**
 * Finds and lists all students under student tab of taTracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
/*
public class FilterStudentCommand extends Command {

    public static String COMMAND_WORD = "student filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters students accordingly."
            + "Parameters: "
            + "[" + PREFIX_MODULE + "MODULE] "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103T"
            + PREFIX_GROUP + "G06";

    public static final String MESSAGE_SUCCESS = "Filtered List";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    public static final String MESSAGE_INVALID_GROUP = "There is no module with the given group.";

    private final NameContainsKeywordsPredicate predicate;

    public FilterStudentCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
*/
