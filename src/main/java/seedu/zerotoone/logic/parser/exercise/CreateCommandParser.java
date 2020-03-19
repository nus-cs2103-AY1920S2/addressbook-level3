package seedu.zerotoone.logic.parser.exercise;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.zerotoone.logic.commands.exercise.CreateCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ArgumentMultimap;
import seedu.zerotoone.logic.parser.util.ArgumentTokenizer;
import seedu.zerotoone.logic.parser.util.Prefix;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;

/**
 * Parses input arguments and creates a new CreateCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXERCISE_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }

        ExerciseName exerciseName =
            ExerciseParserUtil.parseExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME).get());
        List<ExerciseSet> exerciseSets = new ArrayList<>();
        Exercise exercise = new Exercise(exerciseName, exerciseSets);

        return new CreateCommand(exercise);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
