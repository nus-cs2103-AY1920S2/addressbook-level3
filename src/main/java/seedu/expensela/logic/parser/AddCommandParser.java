package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_REMARK;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Remark;
import seedu.expensela.model.transaction.Transaction;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        boolean isNotIncome = true;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_REMARK,
                        PREFIX_CATEGORY, PREFIX_INCOME);

        if (!arePrefixesPresent(argMultimap, PREFIX_INCOME)) {
            isNotIncome = false;
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get(), isNotIncome);

            Date date;
            if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
                //Set date to today's date
                date = ParserUtil.parseDate(LocalDate.now().toString());
            } else {
                date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            }

            Remark remark;
            if (arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
                remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
            } else {
                remark = ParserUtil.parseRemark(" ");
            }

            Category category;
            if (arePrefixesPresent(argMultimap, PREFIX_CATEGORY)) {
                category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            } else {
                category = ParserUtil.parseCategory("MISC");
            }

            Transaction transaction = new Transaction(name, amount, date, remark, category);

            return new AddCommand(transaction);
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
