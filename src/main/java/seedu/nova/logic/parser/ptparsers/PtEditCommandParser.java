package seedu.nova.logic.parser.ptparsers;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.nova.logic.commands.ptcommands.PtEditCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PtEditCommand object
 */
public class PtEditCommandParser implements Parser<PtEditCommand> {

    @Override
    public PtEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_PROJECT, CliSyntax.PREFIX_WEEK,
                        CliSyntax.PREFIX_TASK, CliSyntax.PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PROJECT, CliSyntax.PREFIX_WEEK,
                CliSyntax.PREFIX_TASK, CliSyntax.PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PtEditCommand.MESSAGE_USAGE));
        }

        String project = ParserUtil.parseProject(argMultimap.getValue(CliSyntax.PREFIX_PROJECT).get());
        int week = ParserUtil.parseWeek(argMultimap.getValue(CliSyntax.PREFIX_WEEK).get());
        int taskNum = ParserUtil.parseTask(argMultimap.getValue(CliSyntax.PREFIX_TASK).get());
        String taskDesc = ParserUtil.parseTaskDesc(argMultimap.getValue(CliSyntax.PREFIX_DESC).get());

        return new PtEditCommand(week, project, taskDesc, taskNum);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

