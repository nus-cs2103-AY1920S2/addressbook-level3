package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_MONTH_YEAR;

import java.time.YearMonth;

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
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExpiringCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_EXPIRY_DATE, PREFIX_MONTH_YEAR);
        boolean isMonthYearPresent = argMultiMap.getValue(PREFIX_MONTH_YEAR).isPresent();
        boolean isExpiryDatePresent = argMultiMap.getValue(PREFIX_EXPIRY_DATE).isPresent();
        boolean isBothPresent = isExpiryDatePresent && isMonthYearPresent;
        boolean isBothAbsent = !isExpiryDatePresent && !isMonthYearPresent;

        if (isBothAbsent || isBothPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));
        } else if (isMonthYearPresent) {
            // Month-Year only
            YearMonth yearMonth = ParserUtil.parseYearMonth(argMultiMap.getValue(PREFIX_MONTH_YEAR).get());
            return new ExpiringCommand(new DateIsInMonthYearPredicate(yearMonth));
        } else {
            // Expiry Date only
            ExpiryDate expiryDate = ParserUtil.parseExpiryDate(argMultiMap.getValue(PREFIX_EXPIRY_DATE).get());
            return new ExpiringCommand(new DateIsEqualsPredicate(expiryDate.value));
        }
    }
}

