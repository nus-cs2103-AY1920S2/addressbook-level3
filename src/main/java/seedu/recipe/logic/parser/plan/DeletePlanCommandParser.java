package seedu.recipe.logic.parser.plan;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.plan.DeletePlanCommand;
import seedu.recipe.logic.parser.Parser;
import seedu.recipe.logic.parser.ParserUtil;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePlanCommand object
 */
public class DeletePlanCommandParser implements Parser<DeletePlanCommand> {

    /**
     * Parses the given {@code args} of arguments in the context of the DeletePlanCommand
     * and returns an PlanCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeletePlanCommand parse(String args) throws ParseException {
        Index[] indexes = ParserUtil.parseMultipleIndex(args);

        return new DeletePlanCommand(indexes);
    }

}
