package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eylah.commons.core.index.Index;

import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.PaidCommand;

/**
 * Parses input arguments and creates a new PaidCommandItem object.
 */
public class PaidCommandParser implements Parser<PaidCommand> {


    public static final String VALIDATION_REGEX = "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?$";

    /**
     * For Paid Command we maintained consistency because INDEX did not have a flag like "-i" or "/i"
     * similar to AB3. We decided not to have a flag for amount too.
     * Thus the entire command is consistent in a way that there is no flags.
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


        return new PaidCommand(indexOfPerson, amountPaid);
    }


}




