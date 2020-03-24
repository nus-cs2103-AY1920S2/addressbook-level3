package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import csdev.couponstash.logic.commands.ExpiringCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.DateIsBeforePredicate;
import csdev.couponstash.model.coupon.ExpiryDate;

/**
 * Parses input arguments and creates a new ExpiringCommand object
 */
public class ExpiringCommandParser implements Parser<ExpiringCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpiringCommand
     * and returns a ExpiringCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExpiringCommand parse(String args) throws ParseException {
        try {
            ExpiryDate expiryDate = ParserUtil.parseExpiryDate(args.trim());
            return new ExpiringCommand(new DateIsBeforePredicate(expiryDate.value));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE), pe);
        }
    }
}

