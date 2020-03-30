package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.recipe.logic.commands.ViewCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipeWithinDateRangePredicate;

public class ViewCommandParser implements Parser<ViewCommand> {

    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.equals("week")) {
            System.out.println("hi");
        }

        PlannedRecipeWithinDateRangePredicate predicate = new PlannedRecipeWithinDateRangePredicate(
                new PlannedDate(LocalDate.parse("2020-03-30")), new PlannedDate(LocalDate.parse("2020-04-06")));
        // model.updateFilteredRecipeList(predicate);
        return new ViewCommand(predicate);
    }

}
