package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_SOC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditGroupCommand;

public class EditGroupCommandParserTest {

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index
        assertParseFailure(parser, VALID_GROUP_NAME_SOC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditGroupCommand.MESSAGE_USAGE));
    }
}
