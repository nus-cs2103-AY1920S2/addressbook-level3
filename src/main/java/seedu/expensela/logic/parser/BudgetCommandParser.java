package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_RECURRING;

import java.util.stream.Stream;

import seedu.expensela.logic.commands.BudgetCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.monthlydata.Budget;

/**
 * Parses input arguments and creates a new BudgetCommand object
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand
     * and returns a BudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BUDGET, PREFIX_RECURRING);
        if (!arePrefixesPresent(argMultimap, PREFIX_BUDGET)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }
        try {
            Budget budget = ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get());
            boolean recurring = false;
            if (arePrefixesPresent(argMultimap, PREFIX_RECURRING)) {
                recurring = true;
            }
            return new BudgetCommand(budget.budgetAmount, recurring);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
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
