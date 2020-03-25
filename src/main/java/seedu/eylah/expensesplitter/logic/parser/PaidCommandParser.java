package seedu.eylah.expensesplitter.logic.parser;

import java.util.stream.Stream;

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

        //System.out.println(args.trim().split(" ")[0]);
        //System.out.println(args.substring(2).trim());
        int index = Integer.valueOf(args.trim().split(" ")[0]);
        String amountPaid = args.substring(2).trim();


        /*
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        int index = ParserUtil.parseIndexV2(argMultimap.getValue(PREFIX_INDEX).get());

         */


        return new PaidCommand(index - 1, amountPaid);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}




