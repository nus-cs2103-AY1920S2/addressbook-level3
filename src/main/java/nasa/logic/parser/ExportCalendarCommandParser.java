package nasa.logic.parser;

import static nasa.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;

import nasa.logic.commands.ExportCalendarCommand;
import nasa.logic.parser.exceptions.ParseException;

//@@author kester-ng
/**
 * Parses input arguments and return an ExportCalendarCommand object.
 */
public class ExportCalendarCommandParser implements Parser<ExportCalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCalendarCommand
     * and returns an ExportCalendarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCalendarCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        Path filepath = null;
        if (argMultimap.getValue(PREFIX_FILEPATH).isPresent()) {
            filepath = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILEPATH).get());
        }
        return new ExportCalendarCommand(filepath);
    }
}
