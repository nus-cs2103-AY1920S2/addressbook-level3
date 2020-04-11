package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.taskcommand.addcommand.ModuleTaskCommand;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.Priority;
import seedu.address.testutil.TypicalNusModules;

class ModuleTaskCommandParserTest {

    private ModuleTaskCommandParser parser = new ModuleTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ModuleTask expectedModuleTask = TypicalNusModules.TYPICAL_MODULE_TASK_1;

        assertParseSuccess(parser, " desc/assignment m/CS2030 date/02-02-2020 pri/4",
                new ModuleTaskCommand(expectedModuleTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleTaskCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " m/CS2030 date/02-02-2020 pri/4", expectedMessage);
        assertParseFailure(parser, " desc/assignment date/02-02-2020 pri/4", expectedMessage);
        assertParseFailure(parser, " desc/assignment m/CS2030 pri/4", expectedMessage);
        assertParseFailure(parser, " desc/assignment m/CS2030 date/02-02-2020", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        //invalid module code
        assertParseFailure(parser, " desc/assignment m/dasdas date/02-02-2020 pri/4",
                ModuleCode.MESSAGE_CONSTRAINTS);

        //invalid priority
        assertParseFailure(parser, " desc/assignment m/CS2030 date/02-02-2020 pri/asdas",
                Priority.MESSAGE_CONSTRAINTS);

        //invalid priority
        assertParseFailure(parser, " desc/assignment m/CS2030 date/02-02-2020 pri/6",
                Priority.MESSAGE_CONSTRAINTS);

        //invalid priority
        assertParseFailure(parser, " desc/assignment m/CS2030 date/02-02-2020 pri/0",
                Priority.MESSAGE_CONSTRAINTS);
    }
}
