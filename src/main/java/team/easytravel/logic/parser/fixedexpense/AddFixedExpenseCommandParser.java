package team.easytravel.logic.parser.fixedexpense;

import static team.easytravel.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.stream.Stream;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.fixedexpense.AddFixedExpenseCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.Prefix;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.listmanagers.fixedexpense.Amount;
import team.easytravel.model.listmanagers.fixedexpense.Description;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpenseCategory;

/**
 * Parses input arguments and creates a new AddFixedExpenseCommand object
 */
public class AddFixedExpenseCommandParser implements Parser<AddFixedExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFixedExpenseCommand
     * and returns an AddFixedExpenseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddFixedExpenseCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CURRENCY, PREFIX_DESCRIPTION, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_CURRENCY, PREFIX_DESCRIPTION, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddFixedExpenseCommand.MESSAGE_USAGE));
        }


        String currency = ParserUtil.parseCurrency(argMultimap.getValue(PREFIX_CURRENCY).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        FixedExpenseCategory fixedExpenseCategory =
                ParserUtil.parseFixedExpenseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());

        FixedExpense fixedExpense = new FixedExpense(amount, description, fixedExpenseCategory);

        if (currency.equals("sgd")) {
            return new AddFixedExpenseCommand(fixedExpense, false);
        } else {
            return new AddFixedExpenseCommand(fixedExpense, true);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}
