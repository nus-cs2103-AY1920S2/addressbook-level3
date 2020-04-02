package seedu.recipe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;

import java.util.Collection;
import java.util.List;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.AddStepCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.recipe.Step;

/**
 * Parses input arguments and creates a new AddStepCommand object.
 */
public class AddStepCommandParser implements Parser<AddStepCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddStepCommand
     * and returns an AddStepCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStepCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStepCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STEP);
        List<Step> newSteps = parseStepsForAddStep(argMultimap.getAllValues(PREFIX_STEP));

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        return new AddStepCommand(index, newSteps);
    }

    /**
     * Parses {@code Collection<String> steps} into a {@code List<Step>}.
     */
    private List<Step> parseStepsForAddStep(Collection<String> steps) throws ParseException {
        assert steps != null;

        if (steps.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStepCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parseSteps(steps);
    }
}
