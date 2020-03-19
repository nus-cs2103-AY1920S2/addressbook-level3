package seedu.zerotoone.logic.parser.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

import java.util.NoSuchElementException;

import seedu.zerotoone.logic.commands.exercise.CreateCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ArgumentMultimap;
import seedu.zerotoone.logic.parser.util.ArgumentTokenizer;
import seedu.zerotoone.model.exercise.ExerciseName;

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
        requireNonNull(args);

        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME);
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException("");
            }

            ExerciseName exerciseName = ExerciseParserUtil.parseExerciseName(
                        argMultimap.getValue(PREFIX_EXERCISE_NAME).get());

            return new CreateCommand(exerciseName);
        } catch (ParseException | NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE), e);
        }
    }
}
