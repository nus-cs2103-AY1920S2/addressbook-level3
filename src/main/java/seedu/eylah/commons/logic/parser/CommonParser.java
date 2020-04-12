package seedu.eylah.commons.logic.parser;

import java.util.regex.Pattern;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.parser.exception.ParseException;

/**
 * Parses user input.
 */
public interface CommonParser<E> {

    /**
     * Used for initial separation of command word and args.
     */
    Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    Command<E> parseCommand(String userInput) throws ParseException;

}
