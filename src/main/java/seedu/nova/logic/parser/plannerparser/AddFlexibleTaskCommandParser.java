package seedu.nova.logic.parser.plannerparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_MAX_DURATION;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_MIN_DURATION;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.Duration;
import java.util.stream.Stream;

import seedu.nova.logic.commands.plannercommands.PlannerAddFlexibleTaskCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parser for flexible command
 */
public class AddFlexibleTaskCommandParser implements Parser<PlannerAddFlexibleTaskCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public PlannerAddFlexibleTaskCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TIME,
                PREFIX_MIN_DURATION, PREFIX_MAX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_TIME, PREFIX_MIN_DURATION, PREFIX_MAX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get();
        Duration totalMinute = ParserUtil.parseMinuteDuration(argMultimap.getValue(PREFIX_TIME).get());
        Duration minD = ParserUtil.parseMinuteDuration(argMultimap.getValue(PREFIX_MIN_DURATION).get());
        Duration maxD = ParserUtil.parseMinuteDuration(argMultimap.getValue(PREFIX_MAX_DURATION).get());


        return new PlannerAddFlexibleTaskCommand(taskName, minD, maxD, totalMinute);
    }
}
