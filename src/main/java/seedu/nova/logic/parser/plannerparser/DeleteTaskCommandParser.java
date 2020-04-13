package seedu.nova.logic.parser.plannerparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.stream.Stream;

import seedu.nova.logic.commands.plannercommands.DeleteTaskCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * DeleteTaskCommandParser
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public DeleteTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String taskName = argMultimap.getValue(PREFIX_TASK_NAME).get();
        return new DeleteTaskCommand(taskName);
    }
}
