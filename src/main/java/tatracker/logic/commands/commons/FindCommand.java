package tatracker.logic.commands.commons;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.KEYWORD;
import static tatracker.logic.parser.Prefixes.MORE_KEYWORDS;

import java.util.List;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.student.NameContainsKeywordsPredicate;

/**
 * Finds and lists all students in TA-Tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.FIND,
            "Finds all students whose names contain any of the specified keywords (case-insensitive)"
                    + " and displays them as a list with index numbers.",
            List.of(KEYWORD),
            List.of(MORE_KEYWORDS),
            KEYWORD, MORE_KEYWORDS
    );

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilteredStudentList();
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()),
                Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
