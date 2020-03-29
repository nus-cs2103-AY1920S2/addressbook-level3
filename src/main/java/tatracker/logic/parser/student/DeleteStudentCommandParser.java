package tatracker.logic.parser.student;

import tatracker.logic.commands.student.DeleteStudentCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;

import java.util.stream.Stream;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MATRIC;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object
 */
public class DeleteStudentCommandParser implements Parser<DeleteStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStudentCommand
     * and returns a DeleteStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRIC, PREFIX_GROUP, PREFIX_MODULE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRIC, PREFIX_GROUP, PREFIX_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteStudentCommand.MESSAGE_USAGE));
        }

        Matric matric = ParserUtil.parseMatric(argMultimap.getValue(PREFIX_MATRIC).get());

        String groupCode = argMultimap.getValue(PREFIX_GROUP).get();
        String moduleCode = argMultimap.getValue(PREFIX_MODULE).get();

        Group group = new Group(groupCode);
        Module module = new Module(moduleCode);

        return new DeleteStudentCommand(matric, group, module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
