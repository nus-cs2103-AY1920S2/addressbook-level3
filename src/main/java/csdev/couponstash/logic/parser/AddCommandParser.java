package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_LIMIT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_PHONE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_USAGE;

import java.util.Set;
import java.util.stream.Stream;

import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.Phone;
import csdev.couponstash.model.coupon.RemindDate;
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
     * @param moneySymbol String representing the money symbol.
     */
    public AddCommandParser(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_SAVINGS, PREFIX_EXPIRY_DATE,
                        PREFIX_USAGE, PREFIX_LIMIT, PREFIX_REMIND, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EXPIRY_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Savings savings = ParserUtil.parseSavings(argMultimap.getAllValues(PREFIX_SAVINGS), this.moneySymbol);
        ExpiryDate expiryDate = ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get());
        Usage usage = ParserUtil.parseUsage(argMultimap.getValueForOptionalField(PREFIX_USAGE).get());
        Limit limit = ParserUtil.parseLimit(argMultimap.getValueForOptionalField(PREFIX_LIMIT).get());
        RemindDate remindDate = ParserUtil.parseRemindDate(argMultimap.getValueForOptionalField(PREFIX_REMIND).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Coupon coupon = new Coupon(name, phone, savings, expiryDate, usage, limit, remindDate, tagList);

        return new AddCommand(coupon);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
