package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.PaidCommand;

/**
 * Parses input arguments and creates a new PaidCommandItem object.
 */
public class PaidCommandParser implements Parser<PaidCommand> {


    public static final String VALIDATION_REGEX = "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?$";

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
            amountPaid = "all";
        }


        if (!amountPaid.matches(VALIDATION_REGEX) && !amountPaid.equals("all")) {
            throw new ParseException(PaidCommand.PROPER_AMOUNT);
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




