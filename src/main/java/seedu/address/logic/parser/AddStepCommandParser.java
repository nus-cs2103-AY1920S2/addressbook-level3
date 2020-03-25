package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP;

import java.util.Collection;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddStepCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Step;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STEP);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_INDEX);
        }

        List<Step> newSteps = parseStepsForEdit(argMultimap.getAllValues(PREFIX_STEP));

        return new AddStepCommand(index, newSteps);
    }

    /**
     * Parses {@code Collection<String> steps} into a {@code List<Step>}.
     */
    private List<Step> parseStepsForEdit(Collection<String> steps) throws ParseException {
        assert steps != null;

        if (steps.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStepCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parseSteps(steps);
    }
}
