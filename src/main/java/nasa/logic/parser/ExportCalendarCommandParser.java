package nasa.logic.parser;

import static nasa.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;
import nasa.logic.commands.ExportCalendarCommand;
import nasa.logic.parser.exceptions.ParseException;

public class ExportCalendarCommandParser implements Parser<ExportCalendarCommand> {

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
