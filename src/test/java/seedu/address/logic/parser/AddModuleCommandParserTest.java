package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.modulecommand.AddModuleCommand;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.NusModule;

class AddModuleCommandParserTest {

    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        NusModule expectedModule = new NusModule(new ModuleCode("CS2103"),
                4, Optional.empty(), new ArrayList<>());

        //with grade input
        assertParseSuccess(parser, " m/CS2103 g/A", new AddModuleCommand(expectedModule));

        //without grade input
        assertParseSuccess(parser, " m/CS2103", new AddModuleCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " CS2103", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, " g/A", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid module code
        assertParseFailure(parser, " m/dasdas", ModuleCode.MESSAGE_CONSTRAINTS);

        //invalid grade
        assertParseFailure(parser, " m/cs2030 g/dasd", Grade.MESSAGE_CONSTRAINTS);
    }
}
