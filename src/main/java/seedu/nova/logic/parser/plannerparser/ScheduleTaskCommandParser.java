package seedu.nova.logic.parser.plannerparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.nova.logic.commands.plannercommands.PlannerScheduleTaskCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Schedule Task Command Parser
 */
public class ScheduleTaskCommandParser implements Parser<PlannerScheduleTaskCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public PlannerScheduleTaskCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get();
        LocalDate d = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());


        return new PlannerScheduleTaskCommand(taskName, d);
    }
}
