package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.recipe.DeleteGoalCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parser for DeleteGoalCommand.
 */
public class DeleteGoalCommandParser implements Parser<DeleteGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoalCommand
     * and returns a DeleteGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGoalCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoalCommand.MESSAGE_USAGE));
        }
        String[] argsArray = args.trim().split("\\s", 2);

        Index index;
        String goalName;
        try {
            index = ParserUtil.parseIndex(argsArray[0]);
            goalName = argsArray[1];
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoalCommand.MESSAGE_USAGE));
        }

        return new DeleteGoalCommand(index, goalName);
    }

}
