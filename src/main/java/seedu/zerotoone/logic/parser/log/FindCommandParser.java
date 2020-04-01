package seedu.zerotoone.logic.parser.log;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_SESSION_END;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_SESSION_START;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.zerotoone.logic.commands.log.FindCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ArgumentMultimap;
import seedu.zerotoone.logic.parser.util.ArgumentTokenizer;
import seedu.zerotoone.model.exercise.ExerciseName;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Optional<ExerciseName> exerciseNameOptional = Optional.empty();
        Optional<LocalDateTime> startTimeOptional = Optional.empty();
        Optional<LocalDateTime> endTimeOptional = Optional.empty();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME, PREFIX_SESSION_START,
            PREFIX_SESSION_END);
        if (argMultimap.getValue(PREFIX_EXERCISE_NAME).isPresent()) {
            exerciseNameOptional = Optional.of(new ExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_SESSION_START).isPresent()) {
            try {
                startTimeOptional =
                    Optional.of(LogParserUtil.parseDateTime(argMultimap.getValue(PREFIX_SESSION_START).get()));
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        if (argMultimap.getValue(PREFIX_SESSION_END).isPresent()) {
            try {
                endTimeOptional =
                    Optional.of(LogParserUtil.parseDateTime(argMultimap.getValue(PREFIX_SESSION_END).get()));
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        // workout session not implemented yet
        return new FindCommand(startTimeOptional, endTimeOptional, exerciseNameOptional, Optional.empty());
    }
}
