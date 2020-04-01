package seedu.recipe.logic.parser.plan;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.plan.DeletePlanCommand;
import seedu.recipe.logic.parser.ArgumentMultimap;
import seedu.recipe.logic.parser.ArgumentTokenizer;
import seedu.recipe.logic.parser.Parser;
import seedu.recipe.logic.parser.ParserUtil;
import seedu.recipe.logic.parser.Prefix;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.Date;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePlanCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePlanCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        return new DeletePlanCommand(index, date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
