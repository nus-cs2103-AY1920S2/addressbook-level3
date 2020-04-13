package seedu.address.logic.parser.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.cookbook.CookbookListCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CookbookListCommand object
 */
public class CookbookListCommandParser {

    private static final Pattern COOKBOOK_LIST_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile("");

    /**
     * Parses the given {@code String} of arguments in the context of the CookbookListCommand
     * and returns a CookbookListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookbookListCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = COOKBOOK_LIST_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CookbookListCommand.MESSAGE_USAGE));
        }

        return new CookbookListCommand();
    }
}
