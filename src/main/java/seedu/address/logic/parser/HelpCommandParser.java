package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    private static final Pattern HELP_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile("");

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = HELP_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        return new HelpCommand();
    }
}

