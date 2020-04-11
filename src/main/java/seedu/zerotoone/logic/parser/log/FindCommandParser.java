package seedu.zerotoone.logic.parser.log;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_LOG_END;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_LOG_START;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_WORKOUT_NAME;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.zerotoone.logic.commands.log.FindCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ArgumentMultimap;
import seedu.zerotoone.logic.parser.util.ArgumentTokenizer;
import seedu.zerotoone.model.workout.WorkoutName;

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
        Optional<WorkoutName> workoutNameOptional = Optional.empty();
        Optional<LocalDateTime> startTimeOptional = Optional.empty();
        Optional<LocalDateTime> endTimeOptional = Optional.empty();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WORKOUT_NAME, PREFIX_LOG_START,
            PREFIX_LOG_END);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_WORKOUT_NAME).isPresent()) {
            workoutNameOptional = Optional.of(new WorkoutName(argMultimap.getValue(PREFIX_WORKOUT_NAME).get()));
        }
        try {
            if (argMultimap.getValue(PREFIX_LOG_START).isPresent()) {
                startTimeOptional =
                        Optional.of(LogParserUtil.parseDateTime(argMultimap.getValue(PREFIX_LOG_START).get()));
            }

            if (argMultimap.getValue(PREFIX_LOG_END).isPresent()) {
                endTimeOptional =
                    Optional.of(LogParserUtil.parseDateTime(argMultimap.getValue(PREFIX_LOG_END).get()));
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(startTimeOptional, endTimeOptional, workoutNameOptional);
    }
}
