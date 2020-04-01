package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.DeleteStepCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteStepCommand object
 */
public class DeleteStepCommandParser implements Parser<DeleteStepCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStepCommand
     * and returns a DeleteStepCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStepCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStepCommand.MESSAGE_USAGE));
        }
        String[] argsArray = args.trim().split("\\s", 2);

        Index index;
        Integer[] stepNumbers;
        try {
            index = ParserUtil.parseIndex(argsArray[0]);
            stepNumbers = Arrays.stream(ParserUtil.parseMultipleIndex(argsArray[1]))
                    .map(Index::getZeroBased)
                    .toArray(Integer[]::new);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStepCommand.MESSAGE_USAGE));
        }

        return new DeleteStepCommand(index, stepNumbers);
    }

}
