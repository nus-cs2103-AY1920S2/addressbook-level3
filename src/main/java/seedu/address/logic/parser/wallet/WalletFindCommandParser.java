package seedu.address.logic.parser.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_KEYWORD_NOT_FOUND;
import static seedu.address.logic.commands.wallet.WalletFindCommand.ONLY_ONE_PARAMETER_ALLOWED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.wallet.WalletFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.AmountContainsKeywordsPredicate;
import seedu.address.model.transaction.DateContainsKeywordsPredicate;
import seedu.address.model.transaction.DescriptionContainsKeywordsPredicate;
import seedu.address.model.transaction.TagContainsKeywordsPredicate;
import seedu.address.model.transaction.WalletPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class WalletFindCommandParser implements Parser<WalletFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public WalletFindCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_TAG);

        WalletPredicate walletFindPredicate = null;

        int numOfTokensPresent = 0;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            numOfTokensPresent++;
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            numOfTokensPresent++;
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            numOfTokensPresent++;
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            numOfTokensPresent++;
        }

        if (numOfTokensPresent > 1) {
            throw new ParseException(
                    String.format(ONLY_ONE_PARAMETER_ALLOWED, WalletFindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            if (nameKeywords.length == 1 && nameKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
            }
            walletFindPredicate = new DescriptionContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        } else if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            String[] amountKeywords = argMultimap.getValue(PREFIX_AMOUNT).get().split("\\s+");
            if (amountKeywords.length == 1 && amountKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
            }

            for (String str : amountKeywords) {
                try {
                    ParserUtil.parseAmount(str);
                } catch (ParseException e) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletFindCommand.WRONG_AMT));
                }
                if (str.contains(".")) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletFindCommand.WRONG_AMT));
                }
            }

            walletFindPredicate = new AmountContainsKeywordsPredicate(Arrays.asList(amountKeywords));
        } else if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String[] dateKeywords = argMultimap.getValue(PREFIX_DATE).get().split("\\s+");

            if (dateKeywords.length == 1 && dateKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
            }

            for (String str : dateKeywords) {
                ParserUtil.parseDate(str);
            }

            walletFindPredicate = new DateContainsKeywordsPredicate(Arrays.asList(dateKeywords));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            if (tagKeywords.length == 1 && tagKeywords[0].equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
            }
            walletFindPredicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletFindCommand.MESSAGE_USAGE));
        }

        return new WalletFindCommand(walletFindPredicate);

    }
}
