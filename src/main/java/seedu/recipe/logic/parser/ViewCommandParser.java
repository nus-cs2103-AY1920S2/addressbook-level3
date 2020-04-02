package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.recipe.logic.commands.ViewCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.PlannedRecipeWithinDateRangePredicate;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.equals("week")) {
            System.out.println("hi");
        }

        PlannedRecipeWithinDateRangePredicate predicate = new PlannedRecipeWithinDateRangePredicate(
                new Date(LocalDate.parse("2020-03-30")), new Date(LocalDate.parse("2020-04-06")));
        // model.updateFilteredRecipeList(predicate);
        return new ViewCommand(predicate);
    }

}
