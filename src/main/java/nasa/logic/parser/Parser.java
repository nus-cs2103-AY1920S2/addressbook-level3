package nasa.logic.parser;

import nasa.logic.commands.Command;
import nasa.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     * @param userInput String
     * @return T
     */
    T parse(String userInput) throws ParseException;
}
