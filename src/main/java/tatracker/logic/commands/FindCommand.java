package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.KEYWORD;
import static tatracker.logic.parser.Prefixes.MORE_KEYWORDS;

import java.util.List;

import tatracker.commons.core.Messages;
import tatracker.logic.parser.Prefix;
import tatracker.model.Model;
import tatracker.model.student.NameContainsKeywordsPredicate;

/**
 * Finds and lists all students in TA-Tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            "find",
            "Finds all students whose names contain any of the specified keywords (case-insensitive)"
                    + " and displays them as a list with index numbers.",
            List.of(KEYWORD),
            List.of(MORE_KEYWORDS),
            KEYWORD, MORE_KEYWORDS
    );

    public static final String COMMAND_WORD = "find";

    public static final List<Prefix> PARAMETERS = List.of(KEYWORD);
    public static final List<Prefix> OPTIONALS = List.of(MORE_KEYWORDS);

    public static final String INFO = "Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilteredStudentList();
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
