package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_NAME_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2030;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS2030;
import static nasa.logic.commands.module.EditModuleCommand.EXCESS_MODULE_CODE;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.module.EditModuleCommand;
import nasa.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import nasa.logic.parser.module.EditModuleCommandParser;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.testutil.EditModuleDescriptorBuilder;

public class EditModuleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE);

    private EditModuleCommandParser parser = new EditModuleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no existing module code specified
        assertParseFailure(parser, MODULE_NAME_DESC_CS2030 , MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid module code to be edited specified
        assertParseFailure(parser, INVALID_MODULE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        String validPreamble = MODULE_CODE_DESC_CS2030.trim();
        assertParseFailure(parser, validPreamble + INVALID_MODULE_NAME_DESC,
                ModuleName.MESSAGE_CONSTRAINTS); // invalid module name
        assertParseFailure(parser, validPreamble + INVALID_MODULE_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS); // invalid module code

        // invalid module code followed by valid name
        assertParseFailure(parser, validPreamble + INVALID_MODULE_DESC + MODULE_NAME_DESC_CS1231,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid module name followed by valid module code
        assertParseFailure(parser, validPreamble + INVALID_MODULE_NAME_DESC + MODULE_CODE_DESC_CS1231,
                ModuleName.MESSAGE_CONSTRAINTS);

        // two module codes input (in addition to preamble)
        assertParseFailure(parser, validPreamble + MODULE_CODE_DESC_CS1231 + MODULE_CODE_DESC_CS2030,
                EXCESS_MODULE_CODE);

        // multiple invalid values, but module code errors are always caught before module name errors
        assertParseFailure(parser, validPreamble + INVALID_MODULE_NAME_DESC + INVALID_MODULE_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String validPreamble = MODULE_CODE_DESC_CS2030.trim();
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);

        String userInput = validPreamble + MODULE_CODE_DESC_CS1231 + MODULE_NAME_DESC_CS1231;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withModuleName(VALID_MODULE_NAME_CS1231)
                .withModuleCode(VALID_MODULE_CODE_CS1231).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String validPreamble = MODULE_CODE_DESC_CS2030.trim();
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);

        // edit module code only
        String userInput = validPreamble + MODULE_CODE_DESC_CS1231;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS1231).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // edit module name only
        userInput = validPreamble + MODULE_NAME_DESC_CS1231;
        descriptor = new EditModuleDescriptorBuilder().withModuleName(VALID_MODULE_NAME_CS1231).build();
        expectedCommand = new EditModuleCommand(targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedModuleNames_acceptsLast() {
        String validPreamble = MODULE_CODE_DESC_CS2030.trim();
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);

        String userInput = validPreamble + MODULE_CODE_DESC_CS1231 + MODULE_NAME_DESC_CS1231 + MODULE_NAME_DESC_CS2030;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withModuleName(VALID_MODULE_NAME_CS2030)
                .withModuleCode(VALID_MODULE_CODE_CS1231).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidModuleNameFollowedByValidModuleName_success() {
        String validPreamble = MODULE_CODE_DESC_CS2030.trim();
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);

        String userInput = validPreamble + MODULE_CODE_DESC_CS1231 + INVALID_MODULE_NAME_DESC + MODULE_NAME_DESC_CS1231
                + MODULE_NAME_DESC_CS2030;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withModuleName(VALID_MODULE_NAME_CS2030)
                .withModuleCode(VALID_MODULE_CODE_CS1231).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


