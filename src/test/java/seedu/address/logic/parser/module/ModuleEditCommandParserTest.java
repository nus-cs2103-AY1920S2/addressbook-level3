package seedu.address.logic.parser.module;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.module.ModuleEditCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.Description;
import seedu.address.testutil.EditModuleDescriptorBuilder;

public class ModuleEditCommandParserTest {

    private static final String DESCRIPTION_EMPTY = " " + PREFIX_DESCRIPTION + " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleEditCommand.MESSAGE_USAGE);

    private ModuleEditCommandParser parser = new ModuleEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index or module code specified
        assertParseFailure(parser, DESCRIPTION_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "CS2103T", MESSAGE_INVALID_FORMAT);

        // no index or module code and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DESCRIPTION_DESC_CS2103T,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        // zero index
        assertParseFailure(parser, "0" + DESCRIPTION_DESC_CS2103T,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        // invalid module code
        assertParseFailure(parser, "AB12345C" + DESCRIPTION_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "CS2103T some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 /index string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "CS2103T /index string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_tooManyArgumentsWithIndex_failure() {
        // valid module code followed by invalid module code. The test case for invalid module code followed by valid
        // module code is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2103T + INVALID_MODULE_CODE_DESC,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));

        // valid description followed by invalid description. The test case for invalid description followed by valid
        // description is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_CS2103T + INVALID_DESCRIPTION_DESC,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));
    }


    @Test
    public void parse_tooManyArgumentsWithModuleCode_failure() {
        // valid module code followed by invalid module code. The test case for invalid module code followed by valid
        // module code is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "CS2103T" + MODULE_CODE_DESC_CS2103T + INVALID_MODULE_CODE_DESC,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));

        // valid description followed by invalid description. The test case for invalid description followed by valid
        // description is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "CS2103T" + DESCRIPTION_DESC_CS2103T + INVALID_DESCRIPTION_DESC,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // index preamble
        String userInput = INDEX_FIRST.getOneBased() + MODULE_CODE_DESC_CS2103T + DESCRIPTION_DESC_CS2103T
                + MODULE_CODE_DESC_CS2103T + DESCRIPTION_DESC_CS2103T
                + MODULE_CODE_DESC_CS2101 + DESCRIPTION_DESC_CS2101;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));

        // module code preamble
        userInput = VALID_MODULE_CODE_CS2103T + MODULE_CODE_DESC_CS2103T + DESCRIPTION_DESC_CS2103T
                + MODULE_CODE_DESC_CS2103T + DESCRIPTION_DESC_CS2103T
                + MODULE_CODE_DESC_CS2101 + DESCRIPTION_DESC_CS2101;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_failure() {
        // index preamble with no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));

        // module code preamble with no other valid values specified
        userInput = VALID_MODULE_CODE_CS2101 + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));

        // index preamble with other valid values specified
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2103T
                + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));

        // module code preamble with other valid values specified
        userInput = VALID_MODULE_CODE_CS2101 + MODULE_CODE_DESC_CS2103T
                + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));
    }

    @Test
    public void parse_invalidValueWithIndexPreamble_failure() {
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS); // invalid module code
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description

        // invalid module code followed by valid description
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC + DESCRIPTION_DESC_CS2103T,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // valid module code followed by invalid description
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2103T + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC + INVALID_DESCRIPTION_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValueWithModuleCodePreamble_failure() {
        assertParseFailure(parser, "CS2103T" + INVALID_MODULE_CODE_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS); // invalid module code
        assertParseFailure(parser, "CS2103T" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description

        // invalid module code followed by valid description
        assertParseFailure(parser, "CS2103T" + INVALID_MODULE_CODE_DESC + DESCRIPTION_DESC_CS2103T,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // valid module code followed by invalid description
        assertParseFailure(parser, "CS2103T" + MODULE_CODE_DESC_CS2103T + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "CS2103T" + INVALID_MODULE_CODE_DESC + INVALID_DESCRIPTION_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // index preamble
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2103T + DESCRIPTION_DESC_CS2103T;
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS2103T).withDescription(VALID_DESCRIPTION_CS2103T).build();
        ModuleEditCommand expectedCommand = new ModuleEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module code preamble
        userInput = VALID_MODULE_CODE_CS2101 + MODULE_CODE_DESC_CS2103T + DESCRIPTION_DESC_CS2103T;
        expectedCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2101), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldsSpecified_success() {
        // index preamble with module code
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2103T;
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS2103T).build();
        ModuleEditCommand expectedCommand = new ModuleEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module code preamble with module code
        userInput = VALID_MODULE_CODE_CS2101 + MODULE_CODE_DESC_CS2103T;
        expectedCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2101), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // index preamble with description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CS2103T;
        descriptor = new EditModuleDescriptorBuilder().withDescription(VALID_DESCRIPTION_CS2103T).build();
        expectedCommand = new ModuleEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module code preamble with description
        userInput = VALID_MODULE_CODE_CS2101 + DESCRIPTION_DESC_CS2103T;
        expectedCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2101), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDescription_success() {
        // index preamble
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_EMPTY;
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withDescription(null).build();
        ModuleEditCommand expectedCommand = new ModuleEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module code preamble
        userInput = VALID_MODULE_CODE_CS2103T + DESCRIPTION_EMPTY;
        expectedCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
