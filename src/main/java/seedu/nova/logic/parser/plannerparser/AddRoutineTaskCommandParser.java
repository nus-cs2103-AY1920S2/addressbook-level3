package seedu.nova.logic.parser.plannerparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.time.Duration;
import java.util.stream.Stream;

import seedu.nova.logic.commands.plannercommands.PlannerAddRoutineTaskCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.plan.TaskFreq;

/**
 * Add routine task command parser
 */
public class AddRoutineTaskCommandParser implements Parser<PlannerAddRoutineTaskCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public PlannerAddRoutineTaskCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_FREQ, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_FREQ, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get();
        TaskFreq freq = ParserUtil.parseTaskFreq(argMultimap.getValue(PREFIX_FREQ).get());
        Duration d = ParserUtil.parseMinuteDuration(argMultimap.getValue(PREFIX_DURATION).get());


        return new PlannerAddRoutineTaskCommand(taskName, d, freq);
    }
}
