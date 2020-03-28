package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.model.order.returnorder.ReturnOrderContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Flag flag = null;

        if (areFlagsPresent(args)) {
            flag = extractFlag(args);
            args = removeFlags(args);
        }

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COD,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COMMENT, PREFIX_TYPE, PREFIX_EMAIL);

        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE,
            PREFIX_ADDRESS, PREFIX_COD, PREFIX_DELIVERY_TIMESTAMP, PREFIX_EMAIL,
            PREFIX_WAREHOUSE, PREFIX_COMMENT, PREFIX_TYPE);

        if (!prefixesPresent && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        List<String> keywords = argMultimap.getAllPrefixValues();

        if (flag == null) {
            return prefixesPresent
                ? new SearchCommand(new OrderContainsKeywordsPredicate(keywords, argMultimap),
                    new ReturnOrderContainsKeywordsPredicate(keywords, argMultimap))
                : new SearchCommand(new OrderContainsKeywordsPredicate(keywords),
                new ReturnOrderContainsKeywordsPredicate(keywords));
        }


        if (flag.equals(CliSyntax.FLAG_ORDER_LIST)) {
            return prefixesPresent
                ? new SearchCommand(new OrderContainsKeywordsPredicate(keywords, argMultimap))
                : new SearchCommand(new OrderContainsKeywordsPredicate(keywords));
        } else if (flag.equals(CliSyntax.FLAG_RETURN_LIST)) {
            return prefixesPresent
                ? new SearchCommand(new ReturnOrderContainsKeywordsPredicate(keywords, argMultimap))
                : new SearchCommand(new ReturnOrderContainsKeywordsPredicate(keywords));
        }

        return prefixesPresent
            ? new SearchCommand(new OrderContainsKeywordsPredicate(keywords, argMultimap),
                new ReturnOrderContainsKeywordsPredicate(keywords, argMultimap))
            : new SearchCommand(new OrderContainsKeywordsPredicate(keywords),
            new ReturnOrderContainsKeywordsPredicate(keywords));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns a StringBuilder object of all the values user keyed in.
     *
     * @param argumentMultimap An {@code ArgumentMultimap} object containing all the keywords tagged to a
     *                         specific prefix
     * @return returns a StringBuilder object of all the values user keyed in with a trailing whitespace behind.
     */
    private static StringBuilder addPrefixKeywordsToList(ArgumentMultimap argumentMultimap) {
        StringBuilder keywords = new StringBuilder();
        List<String> currentPrefixListOfKeywords = argumentMultimap.getAllPrefixValues();
        for (String each : currentPrefixListOfKeywords) {
            keywords.append(each + " ");
        }
        return keywords;
    }

    /**
     * @param string
     * @return
     */
    private boolean areFlagsPresent(String string) {
        return Arrays.stream(string.trim().split("\\s"))
            .anyMatch(str ->
                StringUtil.containsWordIgnoreCase(CliSyntax.FLAG_ORDER_LIST.getFlag(), str)
                    || StringUtil.containsWordIgnoreCase(CliSyntax.FLAG_RETURN_LIST.getFlag(), str));
    }

    private Flag extractFlag(String arg) {
        List<String> argArr = Arrays.asList(arg.trim().split("\\s"));
        if ((argArr.contains(CliSyntax.FLAG_ORDER_LIST.getFlag())
                && argArr.contains(CliSyntax.FLAG_RETURN_LIST.getFlag()))
            || argArr.contains(CliSyntax.FLAG_ORDER_RETURN_LIST.getFlag())) {
            return CliSyntax.FLAG_ORDER_RETURN_LIST;
        } else if (argArr.contains(CliSyntax.FLAG_RETURN_LIST.getFlag())) {
            return CliSyntax.FLAG_RETURN_LIST;
        } else {
            return CliSyntax.FLAG_ORDER_LIST;
        }
    }

    private String removeFlags(String args) {
        List<String> listOfArgs = Arrays.asList(args.split("\\s"));
        StringBuilder returnString = new StringBuilder();
        for (String each : listOfArgs) {
            if (each.equals(CliSyntax.FLAG_ORDER_RETURN_LIST.getFlag())
                || each.equals(CliSyntax.FLAG_RETURN_LIST.getFlag())
                || each.equals(CliSyntax.FLAG_ORDER_LIST.getFlag())) {
                continue;
            }
            returnString.append(each + " ");
        }
        return returnString.toString().trim();
    }
}
