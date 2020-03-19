package seedu.zerotoone.logic.parser.exercise.set;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_NUM_OF_REPS;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.stream.Stream;

import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.commands.exercise.set.AddCommand;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ArgumentMultimap;
import seedu.zerotoone.logic.parser.util.ArgumentTokenizer;
import seedu.zerotoone.logic.parser.util.Prefix;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NUM_OF_REPS, PREFIX_WEIGHT);

        Index index;

        try {
            index = SetParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), e);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NUM_OF_REPS, PREFIX_WEIGHT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        NumReps numReps = SetParserUtil.parseNumReps(argMultimap.getValue(PREFIX_NUM_OF_REPS).get());
        Weight weight = SetParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());
        ExerciseSet exerciseSet = new ExerciseSet(weight, numReps);

        return new AddCommand(index, exerciseSet);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
