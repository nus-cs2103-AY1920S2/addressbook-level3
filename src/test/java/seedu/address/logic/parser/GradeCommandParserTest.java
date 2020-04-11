package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.modulecommand.GradeCommand;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;

class GradeCommandParserTest {

    private GradeCommandParser parser = new GradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ModuleCode expectedModuleCode = new ModuleCode("CS2103");

        assertParseSuccess(parser, " m/CS2103 g/A", new GradeCommand(expectedModuleCode, Grade.A));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " CS2103 g/A", expectedMessage);

        assertParseFailure(parser, " m/CS2103", expectedMessage);

        assertParseFailure(parser, " g/A", expectedMessage);

        assertParseFailure(parser, " M/CS2103 A", expectedMessage);

        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid module code
        assertParseFailure(parser, " m/dasdas g/A", ModuleCode.MESSAGE_CONSTRAINTS);

        //invalid grade
        assertParseFailure(parser, " m/cs2030 g/dasd", Grade.MESSAGE_CONSTRAINTS);
    }

}
