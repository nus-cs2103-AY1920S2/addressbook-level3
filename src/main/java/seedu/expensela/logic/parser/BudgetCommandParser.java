package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.expensela.commons.core.index.Index;
import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.commands.BudgetCommand;
import seedu.expensela.logic.commands.DeleteCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.monthlydata.Budget;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BUDGET, PREFIX_RECURRING);
        if (!arePrefixesPresent(argMultimap,PREFIX_BUDGET, PREFIX_RECURRING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            Budget budget = ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get());
            return new BudgetCommand(budget.budgetAmount);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}
