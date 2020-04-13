//@@author potatocombat

package tatracker.logic.parser.student;

import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.student.DeleteStudentCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.student.Matric;

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
                ArgumentTokenizer.tokenize(args, MATRIC, GROUP, MODULE);

        if (!argMultimap.arePrefixesPresent(MATRIC, GROUP, MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(DeleteStudentCommand.DETAILS.getUsage()));
        }

        Matric matric = ParserUtil.parseMatric(argMultimap.getValue(MATRIC).get());

        String groupCode = argMultimap.getValue(GROUP).get();
        String moduleCode = argMultimap.getValue(MODULE).get();

        return new DeleteStudentCommand(matric, groupCode, moduleCode);
    }
}
