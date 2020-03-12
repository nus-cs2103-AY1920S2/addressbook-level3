//TODO: To be enabled or changed when refactoring is completed
//package com.notably.logic.parser;
//
//import static com.notably.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static com.notably.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
//import static com.notably.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
//import static com.notably.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static com.notably.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
//import static com.notably.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static com.notably.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static com.notably.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static com.notably.logic.parser.CliSyntax.PREFIX_TAG;
//import static com.notably.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static com.notably.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static com.notably.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static com.notably.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static com.notably.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
//
//import org.junit.jupiter.api.Test;
//
//import com.notably.commons.core.index.Index;
//import com.notably.logic.commands.EditCommand;
//import com.notably.logic.commands.EditCommand.EditPersonDescriptor;
//import com.notably.testutil.EditPersonDescriptorBuilder;
//
//public class EditCommandParserTest {
//
//    private static final String TAG_EMPTY = " " + PREFIX_TAG;
//
//    private static final String MESSAGE_INVALID_FORMAT =
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
//
//    private EditCommandParser parser = new EditCommandParser();
//
//    @Test
//    public void parse_missingParts_failure() {
//        // no index specified
//        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
//
//        // no field specified
//        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);
//
//        // no index and no field specified
//        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidPreamble_failure() {
//        // negative index
//        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
//
//        // zero index
//        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
//
//        // invalid arguments being parsed as preamble
//        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
//
//        // invalid prefix being parsed as preamble
//        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//    }
//
//    @Test
//    public void parse_allFieldsSpecified_success() {
//        Index targetIndex = INDEX_SECOND_PERSON;
//        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
//                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;
//
//    }
//
//    @Test
//    public void parse_someFieldsSpecified_success() {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;
//
//
//    }
//
//    @Test
//    public void parse_oneFieldSpecified_success() {
//        // name
//        Index targetIndex = INDEX_THIRD_PERSON;
//
//    }
//
//    @Test
//    public void parse_invalidValueFollowedByValidValue_success() {
//        // no other valid values specified
//        Index targetIndex = INDEX_FIRST_PERSON;
//    }
//
//    @Test
//    public void parse_resetTags_success() {
//        Index targetIndex = INDEX_THIRD_PERSON;
//        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(parser, userInput, expectedCommand);
//    }
//}
