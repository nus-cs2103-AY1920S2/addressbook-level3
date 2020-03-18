package seedu.address.logic.parser;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COD,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COMMENT, PREFIX_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_COD,
            PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COMMENT, PREFIX_TYPE)
            && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        List<String> keywords = new ArrayList<>();
        if (!argMultimap.getPreamble().isEmpty()) {
            keywords = Arrays.asList(argMultimap.getPreamble().split("\\s+"));
            return new SearchCommand(new OrderContainsKeywordsPredicate(keywords));
        }

        boolean hasTID = argMultimap.getValue(PREFIX_TID).isPresent();
        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean hasAddress = argMultimap.getValue(PREFIX_ADDRESS).isPresent();
        boolean hasTimeStamp = argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent();
        boolean hasWarehouse = argMultimap.getValue(PREFIX_WAREHOUSE).isPresent();
        boolean hasCOD = argMultimap.getValue(PREFIX_COD).isPresent();
        boolean hasComment = argMultimap.getValue(PREFIX_COMMENT).isPresent();
        boolean hasItemType = argMultimap.getValue(PREFIX_TYPE).isPresent();

        if (hasTID) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_TID);
        }

        if (hasName) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_NAME);
        }

        if (hasPhone) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_PHONE);
        }

        if (hasAddress) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_ADDRESS);
        }

        if (hasTimeStamp) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_DELIVERY_TIMESTAMP);
        }

        if (hasWarehouse) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_WAREHOUSE);
        }

        if (hasComment) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_COMMENT);
        }

        if (hasCOD) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_COD);
        }

        if (hasItemType) {
            keywords = addPrefixKeywordsToList(keywords, argMultimap, PREFIX_TYPE);
        }


        return new SearchCommand(new OrderContainsKeywordsPredicate(keywords, hasTID, hasName, hasPhone, hasAddress,
            hasTimeStamp, hasWarehouse, hasCOD, hasComment, hasItemType));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Add keywords tagged to the prefix given by the user to a list.
     * Returns a list with all the keywords tagged to the prefix appended to the given list in
     * {@param givenListOfString}.
     * @param givenListOfString Existing list of keywords.
     * @param argumentMultimap An {@code ArgumentMultimap} object.
     * @param prefix Keywords of this prefix will be added into the output list.
     * @return A list of string of the updated keywords.
     */
    private static List<String> addPrefixKeywordsToList(List<String> givenListOfString,
                                                        ArgumentMultimap argumentMultimap, Prefix prefix) {
        List<String> copyKeywordsList = givenListOfString;
        List<String> currentPrefixListOfKeywords = (argumentMultimap.getAllValues(prefix));
        currentPrefixListOfKeywords
            .stream()
            .map(each -> each.split("\\s+"))
            .flatMap(Arrays::stream)
            .forEach(copyKeywordsList::add);
        return copyKeywordsList;
    }
}
