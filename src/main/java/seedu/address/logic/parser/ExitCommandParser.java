package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExitCommand object
 */
public class ExitCommandParser implements Parser<ExitCommand> {

    private static final Pattern EXIT_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile("");

    /**
     * Parses the given {@code String} of arguments in the context of the ExitCommand
     * and returns a ExitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = EXIT_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExitCommand.MESSAGE_USAGE));
        }

        return new ExitCommand();
    }
}
