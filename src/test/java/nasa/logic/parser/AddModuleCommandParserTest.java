package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_NAME_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static nasa.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.module.AddModuleCommand;
import nasa.logic.parser.module.AddModuleCommandParser;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // create the the cs1231 module, contains empty list
        Module cs1231 = new Module(new ModuleCode(VALID_MODULE_CODE_CS1231), new ModuleName(VALID_MODULE_NAME_CS1231));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS1231 + MODULE_NAME_DESC_CS1231,
                new AddModuleCommand(cs1231));

        // multiple module codes - only the last one is taken into account
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2030 + MODULE_CODE_DESC_CS1231 + MODULE_NAME_DESC_CS1231,
                new AddModuleCommand(cs1231));

        // multiple module names - only the last one is taken into account
        assertParseSuccess(parser, MODULE_CODE_DESC_CS1231 + MODULE_NAME_DESC_CS2030 + MODULE_NAME_DESC_CS1231,
                new AddModuleCommand(cs1231));

        /*
                // multiple tags - all accepted
                Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                        .build();
                assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));

         */
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, MODULE_NAME_DESC_CS1231, expectedMessage);

        // missing module name prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODULE_CODE_CS1231 + VALID_MODULE_NAME_CS1231,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_MODULE_DESC + MODULE_NAME_DESC_CS1231,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid module name
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + INVALID_MODULE_NAME_DESC,
                ModuleName.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MODULE_DESC + INVALID_MODULE_NAME_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_DESC_CS1231
                        + MODULE_NAME_DESC_CS1231,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
    }
}
