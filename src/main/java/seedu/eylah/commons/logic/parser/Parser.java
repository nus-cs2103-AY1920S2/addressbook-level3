package seedu.eylah.commons.logic.parser;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.parser.exception.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command<?>> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
