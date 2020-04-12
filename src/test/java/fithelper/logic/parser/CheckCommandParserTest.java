package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.commands.CommandTestUtil.APPLE;
import static fithelper.logic.commands.CommandTestUtil.WHITE_SPACE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import fithelper.logic.commands.CheckCommand;

public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parseTypeFieldMissingFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);

        assertParseFailure(parser, WHITE_SPACE + PREFIX_KEYWORD + APPLE, expectedMessage);
        assertParseFailure(parser, WHITE_SPACE, expectedMessage);
    }
}
