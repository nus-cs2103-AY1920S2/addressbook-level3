package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.expensesplitter.logic.commands.PaidCommand;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PaidCommandItem object.
 */
public class PaidCommandParser implements Parser<PaidCommand> {

    /**
     * Parsing.
     * @param args
     * @return
     * @throws ParseException
     */
    @Override
    public PaidCommand parse(String args) throws ParseException {

        Index indexOfPerson;
        String amountPaid;
        String[] helper = args.trim().split(" ");

        try {
            indexOfPerson = ParserUtil.parseIndex(helper[0]);

        } catch (ParseException ex) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE), ex);
        }

        try {
            amountPaid = helper[1];
        } catch (IndexOutOfBoundsException ex) {
            amountPaid = "";
        }


        /*
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        int index = ParserUtil.parseIndexV2(argMultimap.getValue(PREFIX_INDEX).get());

         */

        return new PaidCommand(indexOfPerson, amountPaid);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}




