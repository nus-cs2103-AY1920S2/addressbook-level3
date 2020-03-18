package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.KeywordContainsOrderPrefix;
import seedu.address.model.order.OrderContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COD,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COMMENT, PREFIX_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COD,
            PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COMMENT, PREFIX_TYPE)
            && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        List<String> keywords;
        if (!argMultimap.getPreamble().isEmpty()) {
            keywords = Arrays.asList(argMultimap.getPreamble().split("\\s+"));
            return new SearchCommand(new OrderContainsKeywordsPredicate(keywords));
        }

        KeywordContainsOrderPrefix keywordContainsOrderPrefix = new KeywordContainsOrderPrefix(argMultimap);
        StringBuilder keywordsString = new StringBuilder();
        if (keywordContainsOrderPrefix.getHasTid()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_TID);
        }

        if (keywordContainsOrderPrefix.getHasName()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_NAME);
        }

        if (keywordContainsOrderPrefix.getHasPhone()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_PHONE);
        }

        if (keywordContainsOrderPrefix.getHasAddress()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_ADDRESS);
        }

        if (keywordContainsOrderPrefix.getHasTimeStamp()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_DELIVERY_TIMESTAMP);
        }

        if (keywordContainsOrderPrefix.getHasWarehouse()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_WAREHOUSE);
        }

        if (keywordContainsOrderPrefix.getHasComment()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_COMMENT);
        }

        if (keywordContainsOrderPrefix.getHasCod()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_COD);
        }

        if (keywordContainsOrderPrefix.getHasItemType()) {
            keywordsString = addPrefixKeywordsToList(keywordsString, argMultimap, PREFIX_TYPE);
        }

        keywords = Arrays.asList(keywordsString.toString().split("\\s+"));
        return new SearchCommand(new OrderContainsKeywordsPredicate(keywords, keywordContainsOrderPrefix));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Add keywords tagged to the prefix given by the user to a StringBuilder.
     * Returns a StringBuilder with all the keywords tagged to the prefix appended to the given StringBuilder of
     * {@param givenKeyWords}.
     *
     * @param givenKeywords StringBuilder of all or partial of the keywords given by the user.
     * @param argumentMultimap An {@code ArgumentMultimap} object with keywords tagged to prefix.
     * @param prefix Prefix object to indicate which keywords to extract out from the argumentMultiMap.
     * @return Returns a StringBuilder object with all the keywords tagged to the prefix appneded to the StringBuilder.
     */
    private static StringBuilder addPrefixKeywordsToList(StringBuilder givenKeywords,
                                                        ArgumentMultimap argumentMultimap, Prefix prefix) {
        StringBuilder copyKeywords = givenKeywords;
        List<String> currentPrefixListOfKeywords = argumentMultimap.getAllValues(prefix);
        for (String each : currentPrefixListOfKeywords) {
            copyKeywords.append(each);
        }
        return copyKeywords;
    }
}
