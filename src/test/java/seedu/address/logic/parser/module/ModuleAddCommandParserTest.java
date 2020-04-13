package seedu.address.logic.parser.module;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModules.CS2101;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.module.ModuleAddCommand;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.Description;
import seedu.address.testutil.ModuleBuilder;

public class ModuleAddCommandParserTest {
    private ModuleAddCommandParser parser = new ModuleAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS2101).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS2101 + DESCRIPTION_DESC_CS2101,
                new ModuleAddCommand(expectedModule));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        // missing description
        Module expectedModule = new ModuleBuilder().withDescription(null).build();
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2103T, new ModuleAddCommand(expectedModule));
    }

    @Test
    public void parse_tooManyArguments_failure() {
        // multiple module codes
        assertParseFailure(parser, MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS2101
                + DESCRIPTION_DESC_CS2101, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));

        // multiple descriptions
        assertParseFailure(parser, MODULE_CODE_DESC_CS2101 + DESCRIPTION_DESC_CS2103T
                + DESCRIPTION_DESC_CS2101, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_MODULE_CODE_CS2101 + DESCRIPTION_DESC_CS2101, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODULE_CODE_CS2101 + VALID_DESCRIPTION_CS2101,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + DESCRIPTION_DESC_CS2101,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, MODULE_CODE_DESC_CS2101 + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + INVALID_DESCRIPTION_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_DESC_CS2101 + DESCRIPTION_DESC_CS2101,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddCommand.MESSAGE_USAGE));
    }
}
