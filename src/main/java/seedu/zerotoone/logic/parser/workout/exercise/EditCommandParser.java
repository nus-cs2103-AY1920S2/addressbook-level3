package seedu.zerotoone.logic.parser.workout.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.workout.exercise.EditCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        logger.info("Parsing workout exercise edit command: " + args);

        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");
        if (splitArgs.length != 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Index workoutId = WorkoutExerciseParserUtil.parseIndex(splitArgs[0]);
        Index exerciseId = WorkoutExerciseParserUtil.parseIndex(splitArgs[1]);
        Index newExerciseId = WorkoutExerciseParserUtil.parseIndex(splitArgs[2]);
        return new EditCommand(workoutId, exerciseId, newExerciseId);
    }
}
