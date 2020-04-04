package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FLAG;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_INDEX;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteCommandParser.class);
    private static final String NEWLINE = System.lineSeparator();

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Flag listType = getFlag(args);
            String removeFlagString = removeFlagString(listType, args);
            hasIndex(removeFlagString);
            Index index = ParserUtil.parseIndex(removeFlagString);
            return new DeleteCommand(index, listType);
        } catch (ParseException pe) {
            logger.info("Invalid Delete Command arguments given for parsing");
            throw new ParseException(String.format("%s" + NEWLINE + "%s", pe.getMessage(),
                    DeleteCommand.MESSAGE_USAGE));
        }
    }

    private void hasIndex(String removeFlagString) throws ParseException {
        if (removeFlagString.trim().length() == 0) {
            throw new ParseException(MESSAGE_MISSING_INDEX);
        }
    }

    /**
     * Remove given {@code flag} string representation from given string {@code str}.
     */
    private String removeFlagString(Flag flag, String str) throws ParseException {
        requireNonNull(flag);
        requireNonNull(str);
        String flagRegex = getFlagRegex(flag);
        String output;
        String[] words = str.trim().split("\\s+");
        output = Arrays.stream(words)
                .filter(word -> !Pattern.matches(flagRegex, word))
                .map(word -> word + " ")
                .collect(Collectors.joining());
        return output.trim();
    }

    /**
     * Convert given {@code args} into its corresponding Flag object.
     */
    private Flag getFlag(String args) throws ParseException {
        requireNonNull(args);
        String orderListRegex = getFlagRegex(FLAG_ORDER_BOOK);
        String returnListRegex = getFlagRegex(FLAG_RETURN_BOOK);
        String[] argWords = args.trim().split("\\s+");

        if (hasRegex(orderListRegex, argWords)) {
            return FLAG_ORDER_BOOK;
        }
        if (hasRegex(returnListRegex, argWords)) {
            return FLAG_RETURN_BOOK;
        }
        logger.info(String.format("Invalid arguments given for getFlag function: %s", args));
        throw new ParseException(MESSAGE_MISSING_FLAG);
    }

    /**
     * Obtain the string regex for a given {@code flag}.
     */
    private String getFlagRegex(Flag flag) throws ParseException {
        requireNonNull(flag);
        String regex = "%s";
        if (flag.equals(FLAG_ORDER_BOOK)) {
            return String.format(regex, FLAG_ORDER_BOOK);
        }
        if (flag.equals(FLAG_RETURN_BOOK)) {
            return String.format(regex, FLAG_RETURN_BOOK);
        }
        logger.info(String.format("Invalid flag given for getFlagRegex: %s", flag.toString()));
        throw new ParseException(MESSAGE_MISSING_FLAG);
    }

    /**
     * Check if the given {@code regex} is present (full match) in any argument in the given {@code searchValues}.
     */
    private boolean hasRegex(String regex, String[] searchValues) {
        requireNonNull(regex);
        requireNonNull(searchValues);
        for (String value : searchValues) {
            if (Pattern.matches(regex, value)) {
                return true;
            }
        }
        return false;
    }
}
