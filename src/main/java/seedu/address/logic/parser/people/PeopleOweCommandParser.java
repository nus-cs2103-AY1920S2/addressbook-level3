// @@author cheyannesim
package seedu.address.logic.parser.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.people.PeopleOweCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Description;

/**
 * Parses input arguments and creates a new PeopleOweCommand object
 */
public class PeopleOweCommandParser implements Parser<PeopleOweCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PeopleOweCommand
     * and returns a PeopleOweCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PeopleOweCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT,
                PREFIX_DATE);

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            System.out.println(args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleOweCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            System.out.println(args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleOweCommand.MESSAGE_USAGE));
        }

        Index index;
        Date date;
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_NAME).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        if (amount.isZero()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleOweCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleOweCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            date = Date.getDefault();
        }

        Debt debt = new Debt(description, amount, date);
        return new PeopleOweCommand(index, debt);
    }
}
