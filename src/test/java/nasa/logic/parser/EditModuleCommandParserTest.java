package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_NAME_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_NAME_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static nasa.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static nasa.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.testutil.EditModuleDescriptorBuilder;
import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditModuleCommand;
import nasa.logic.commands.EditModuleCommand.EditModuleDescriptor;
import nasa.logic.parser.EditModuleCommandParser;

public class EditModuleCommandParserTest {

    private static final  String MESSAGE_INVALID_FORMAT =
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
        assertParseFailure(parser, INVALID_MODULE_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        String validPreamble = MODULE_DESC_CS2030.trim();

        assertParseFailure(parser, validPreamble + MODULE_NAME_DESC_CS1231, ModuleName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, validPreamble + INVALID_MODULE_DESC, ModuleCode.MESSAGE_CONSTRAINTS); // invalid phone

        // invalid module code followed by valid name
        assertParseFailure(parser, validPreamble + INVALID_MODULE_DESC + MODULE_NAME_DESC_CS1231, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid module name followed by valid module code
        assertParseFailure(parser, validPreamble + INVALID_MODULE_NAME_DESC + MODULE_DESC_CS1231, ModuleName.MESSAGE_CONSTRAINTS);

        // two module codes input (in addition to preamble)
        assertParseFailure(parser, validPreamble + MODULE_DESC_CS1231 + MODULE_DESC_CS2030, MESSAGE_INVALID_FORMAT);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, validPreamble + INVALID_MODULE_NAME_DESC + INVALID_MODULE_DESC,
                ModuleName.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String validPreamble = MODULE_DESC_CS2030.trim();
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CS2030);

        String userInput = validPreamble + MODULE_DESC_CS1231 + MODULE_NAME_DESC_CS1231;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withModuleName(VALID_MODULE_NAME_CS1231)
                .withModuleCode(VALID_MODULE_CS1231).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_someFieldsSpecified_success() {
        /*
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

         */
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        /*
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditModuleDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditModuleDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditModuleDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditModuleDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

         */
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        /*
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

         */
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        /*
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditModuleDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

         */
    }

    @Test
    public void parse_resetTags_success() {
        /*
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withTags().build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

         */
    }
}


