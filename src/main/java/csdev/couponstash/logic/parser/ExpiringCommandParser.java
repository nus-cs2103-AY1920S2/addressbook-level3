package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.YearMonth;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.ExpiringCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.DateIsEqualsPredicate;
import csdev.couponstash.model.coupon.DateIsInMonthYearPredicate;
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
    @Override
    public ExpiringCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        try {
            if (args.trim().matches(DateUtil.DATE_VALIDATION_REGEX)) {
                ExpiryDate expiryDate = ParserUtil.parseExpiryDate(trimmedArgs);
                return new ExpiringCommand(new DateIsEqualsPredicate(expiryDate.value));
            } else {
                YearMonth yearMonth = ParserUtil.parseYearMonth(trimmedArgs);
                return new ExpiringCommand(new DateIsInMonthYearPredicate(yearMonth));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE), pe);
        }
    }
}

