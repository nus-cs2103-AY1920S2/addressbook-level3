package seedu.address.logic.parser.facilitator;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.EditFacilitatorDescriptorBuilder;

public class FacilEditCommandParserTest {

    private static final String PHONE_EMPTY = " " + PREFIX_PHONE + " ";
    private static final String EMAIL_EMPTY = " " + PREFIX_EMAIL + " ";
    private static final String OFFICE_EMPTY = " " + PREFIX_OFFICE + " ";
    private static final String MODULE_CODE_EMPTY = " " + PREFIX_MODULE_CODE + " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilEditCommand.MESSAGE_USAGE);

    private FacilEditCommandParser parser = new FacilEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index or name and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 /index string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValueWithIndex_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS); // invalid module code

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));

        // while parsing {@code PREFIX_MODULE_CODE} alone will reset the module codes of the {@code Facilitator} being
        // edited, parsing it together with a valid module code results in error
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2101 + MODULE_CODE_DESC_CS2103T
                + MODULE_CODE_EMPTY, ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2101 + MODULE_CODE_EMPTY
                + MODULE_CODE_DESC_CS2103T, ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + MODULE_CODE_EMPTY + MODULE_CODE_DESC_CS2101
                + MODULE_CODE_DESC_CS2103T, ModuleCode.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_OFFICE_AMY
                + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValueWithName_failure() {
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_MODULE_CODE_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS); // invalid module code

        // invalid phone followed by valid email
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_NAME_AMY + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));

        // while parsing {@code PREFIX_MODULE_CODE} alone will reset the module codes of the {@code Facilitator} being
        // edited, parsing it together with a valid module code results in error
        assertParseFailure(parser, VALID_NAME_AMY + MODULE_CODE_DESC_CS2101 + MODULE_CODE_DESC_CS2103T
                + MODULE_CODE_EMPTY, ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + MODULE_CODE_DESC_CS2101 + MODULE_CODE_EMPTY
                + MODULE_CODE_DESC_CS2103T, ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + MODULE_CODE_EMPTY + MODULE_CODE_DESC_CS2101
                + MODULE_CODE_DESC_CS2103T, ModuleCode.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_OFFICE_AMY
                + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }


    @Test
    public void parse_multipleRepeatedFieldsWithIndex_failure() {
        // multiple names
        String userInput = INDEX_FIRST.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY + OFFICE_DESC_AMY + EMAIL_DESC_AMY
                + MODULE_CODE_DESC_CS2101 + NAME_DESC_AMY + PHONE_DESC_AMY + OFFICE_DESC_AMY + EMAIL_DESC_AMY
                + MODULE_CODE_DESC_CS2101 + NAME_DESC_BOB + PHONE_DESC_BOB + OFFICE_DESC_BOB + EMAIL_DESC_BOB
                + MODULE_CODE_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_NAME));

        // multiple emails
        userInput = INDEX_FIRST.getOneBased() + PHONE_DESC_AMY + OFFICE_DESC_AMY + EMAIL_DESC_AMY
                + MODULE_CODE_DESC_CS2101 + OFFICE_DESC_AMY + EMAIL_DESC_AMY + MODULE_CODE_DESC_CS2101
                + OFFICE_DESC_BOB + EMAIL_DESC_BOB + MODULE_CODE_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_EMAIL));
    }

    @Test
    public void parse_multipleRepeatedFieldsWithName_failure() {
        // multiple offices
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + PHONE_DESC_AMY + OFFICE_DESC_AMY
                + MODULE_CODE_DESC_CS2101 + OFFICE_DESC_AMY + MODULE_CODE_DESC_CS2101
                + OFFICE_DESC_BOB + EMAIL_DESC_BOB + MODULE_CODE_DESC_CS2103T;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_OFFICE));
    }

    @Test
    public void parse_invalidValueFollowedByValidValueWithIndex_failure() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));

        // other valid values specified
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC + OFFICE_DESC_BOB
                + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));
    }

    @Test
    public void parse_invalidValueFollowedByValidValueWithName_failure() {
        // no other valid values specified
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));

        // other valid values specified
        userInput = name + EMAIL_DESC_BOB + INVALID_PHONE_DESC + OFFICE_DESC_BOB + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_PHONE));
    }

    @Test
    public void parse_allFieldsSpecifiedWithIndex_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + MODULE_CODE_DESC_CS2103T
                + EMAIL_DESC_AMY + OFFICE_DESC_AMY + NAME_DESC_AMY + MODULE_CODE_DESC_CS2101;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withOffice(VALID_OFFICE_AMY).withModuleCodes(VALID_MODULE_CODE_CS2103T, VALID_MODULE_CODE_CS2101)
                .build();
        FacilEditCommand expectedCommand = new FacilEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedWithName_success() {
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + PHONE_DESC_BOB + MODULE_CODE_DESC_CS2103T
                + EMAIL_DESC_AMY + OFFICE_DESC_AMY + NAME_DESC_AMY + MODULE_CODE_DESC_CS2101;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withOffice(VALID_OFFICE_AMY).withModuleCodes(VALID_MODULE_CODE_CS2103T, VALID_MODULE_CODE_CS2101)
                .build();
        FacilEditCommand expectedCommand = new FacilEditCommand(name, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecifiedWithIndex_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecifiedWithName_success() {
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(name, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecifiedWithIndex_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditFacilitatorDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new FacilEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditFacilitatorDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new FacilEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // office
        userInput = targetIndex.getOneBased() + OFFICE_DESC_AMY;
        descriptor = new EditFacilitatorDescriptorBuilder().withOffice(VALID_OFFICE_AMY).build();
        expectedCommand = new FacilEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // single module code
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2101;
        descriptor = new EditFacilitatorDescriptorBuilder().withModuleCodes(VALID_MODULE_CODE_CS2101).build();
        expectedCommand = new FacilEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // multiple module codes
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2101 + " " + VALID_MODULE_CODE_CS2103T;
        descriptor = new EditFacilitatorDescriptorBuilder()
                .withModuleCodes(VALID_MODULE_CODE_CS2101, VALID_MODULE_CODE_CS2103T).build();
        expectedCommand = new FacilEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecifiedWithName_success() {
        // name
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + NAME_DESC_AMY;
        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = name + PHONE_DESC_AMY;
        descriptor = new EditFacilitatorDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new FacilEditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = name + EMAIL_DESC_AMY;
        descriptor = new EditFacilitatorDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new FacilEditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // office
        userInput = name + OFFICE_DESC_AMY;
        descriptor = new EditFacilitatorDescriptorBuilder().withOffice(VALID_OFFICE_AMY).build();
        expectedCommand = new FacilEditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // single module code
        userInput = name + MODULE_CODE_DESC_CS2101;
        descriptor = new EditFacilitatorDescriptorBuilder().withModuleCodes(VALID_MODULE_CODE_CS2101).build();
        expectedCommand = new FacilEditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // multiple module codes
        userInput = name + MODULE_CODE_DESC_CS2101 + " " + VALID_MODULE_CODE_CS2103T;
        descriptor = new EditFacilitatorDescriptorBuilder()
                .withModuleCodes(VALID_MODULE_CODE_CS2101, VALID_MODULE_CODE_CS2103T).build();
        expectedCommand = new FacilEditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetPhoneWithIndex_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_EMPTY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withPhone(null).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetPhoneWithName_success() {
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + PHONE_EMPTY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withPhone(null).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(name, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetEmailWithIndex_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + EMAIL_EMPTY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withEmail(null).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetEmailWithName_success() {
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + EMAIL_EMPTY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withEmail(null).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(name, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetOfficeWithIndex_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + OFFICE_EMPTY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withOffice(null).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetOfficeWithName_success() {
        Name name = new Name(VALID_NAME_BOB);
        String userInput = name + OFFICE_EMPTY;

        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withOffice(null).build();
        FacilEditCommand expectedCommand = new FacilEditCommand(name, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
