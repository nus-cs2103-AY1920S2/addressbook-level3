package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.FindCommand;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand findCommand =
            new FindCommand(new ActivityContainsKeyWordsPredicate(Arrays.asList("Tutorial", "Exam")));
        assertParseSuccess(parser, "Tutorial Exam", findCommand);

        //multiple whitespaces in-between words
        assertParseSuccess(parser, "\n Tutorial \n \t Exam \t", findCommand);

    }
}
