package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_LIMIT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_PROMO_CODE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_START_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_USAGE;

import java.time.LocalDate;
import java.util.Set;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.Condition;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.RemindDate;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    private final String moneySymbol;


    /**
     * Constructor for a AddCommandParser. Requires the
     * money symbol set in UserPrefs as this will be
     * used as the prefix for the monetary amount
     * in the savings field.
     *
     * @param moneySymbol String representing the money symbol.
     */
    public AddCommandParser(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PROMO_CODE, PREFIX_SAVINGS, PREFIX_EXPIRY_DATE,
                        PREFIX_START_DATE, PREFIX_USAGE, PREFIX_LIMIT, PREFIX_TAG, PREFIX_REMIND, PREFIX_CONDITION);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_EXPIRY_DATE, PREFIX_SAVINGS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_USAGE).isPresent()) {
            throw new ParseException(Usage.MESSAGE_UNEDITABLE);
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        PromoCode promoCode = ParserUtil.parsePromoCode(
                argMultimap.getValueForOptionalField(PREFIX_PROMO_CODE, "").get()
        );
        ExpiryDate expiryDate = ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get());
        Savings savings = ParserUtil.parseSavings(argMultimap.getAllValues(PREFIX_SAVINGS), this.moneySymbol);
        StartDate startDate = ParserUtil.parseStartDate(
                argMultimap.getValueForOptionalField(
                        PREFIX_START_DATE, DateUtil.formatDateToString(LocalDate.now())
                ).get()
        );

        // Default remind date is 3 days before expiry date
        RemindDate remindDate = ParserUtil.parseRemindDate(
                argMultimap.getValueForOptionalField(
                        PREFIX_REMIND,
                        DateUtil.formatDateToString(expiryDate.getDate().minusDays(3))
                ).get()
        );

        Usage usage = new Usage(); // default 0 usage
        Limit limit = ParserUtil.parseLimit(argMultimap.getValueForOptionalField(PREFIX_LIMIT, "1").get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Condition condition = ParserUtil.parseCondition(
                argMultimap.getValueForOptionalField(
                        PREFIX_CONDITION, Condition.DEFAULT_NO_CONDITION)
                        .get()
        );

        Coupon coupon = new Coupon(
                name, promoCode, savings, expiryDate, startDate, usage, limit, tagList, remindDate, condition
        );

        return new AddCommand(coupon);
    }
}
