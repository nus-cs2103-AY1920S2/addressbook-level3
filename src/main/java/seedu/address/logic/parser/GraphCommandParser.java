package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AXIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GraphCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.graph.Axis;
import seedu.address.model.graph.EndDate;
import seedu.address.model.graph.Graph;
import seedu.address.model.graph.StartDate;

/**
 * Parses input arguments and creates a new GraphCommand object
 */
public class GraphCommandParser implements Parser<GraphCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * GraphCommand and returns an GraphCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GraphCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_NAME,
                PREFIX_AXIS, PREFIX_STARTDATE, PREFIX_ENDDATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXERCISE_NAME, PREFIX_AXIS,
                PREFIX_STARTDATE, PREFIX_ENDDATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
        }

        ExerciseName exerciseName = ParserUtil.parseExerciseName(argMultimap.getValue(PREFIX_EXERCISE_NAME).get());
        Axis axis = ParserUtil.parseAxis(argMultimap.getValue(PREFIX_AXIS).get());
        StartDate startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_STARTDATE).get());
        EndDate endDate = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_ENDDATE).get());

        if (!GraphCommand.isValidTimeFrame(startDate.value, endDate.value)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
        }

        Graph graph = new Graph(exerciseName, axis, startDate, endDate);
        return new GraphCommand(graph);
    }

    /*
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
