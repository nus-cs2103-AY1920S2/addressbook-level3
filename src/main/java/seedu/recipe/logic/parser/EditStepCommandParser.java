package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;

import java.util.List;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditStepCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.recipe.Step;

/**
 * Parses input arguments and creates a new EditStepCommand object
 */
public class EditStepCommandParser implements Parser<EditStepCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditStepCommand
     * and returns a EditStepCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStepCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStepCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STEP);
        String[] argsArray = argMultimap.getPreamble().trim().split(" ");

        Step editedStep = parseStepsForEditStep(argMultimap.getAllValues(PREFIX_STEP));

        Index index;
        int stepNumber;
        try {
            index = ParserUtil.parseIndex(argsArray[0]);
            stepNumber = ParserUtil.parseIndex(argsArray[1]).getZeroBased();
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStepCommand.MESSAGE_USAGE));
        }

        return new EditStepCommand(index, stepNumber, editedStep);
    }

    /**
     * Parses {@code List<String> steps} into a {@code Step}.
     */
    private Step parseStepsForEditStep(List<String> steps) throws ParseException {
        assert steps != null;

        if (steps.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStepCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parseStep(steps.get(0));
    }
}
