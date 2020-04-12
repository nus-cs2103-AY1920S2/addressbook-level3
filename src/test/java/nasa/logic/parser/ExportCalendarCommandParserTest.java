package nasa.logic.parser;

import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.ExportCalendarCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;

public class ExportCalendarCommandParserTest {

    private ExportCalendarCommandParser parser = new ExportCalendarCommandParser();
    private Model model = new ModelManager(new NasaBook(), new HistoryBook<>(), new HistoryBook<>(), new UserPrefs());

    @Test
    public void parse_argsWithoutFilePath_success() {
        ExportCalendarCommand command = new ExportCalendarCommand(null);
        assertParseSuccess(parser, "", command);
    }

    @Test
    public void parse_argsWithFilePath_success() throws ParseException {
        ExportCalendarCommand command = new ExportCalendarCommand(Path.of("./Calendar"));
        assertParseSuccess(parser, " f/./Calendar", command);
    }
}
