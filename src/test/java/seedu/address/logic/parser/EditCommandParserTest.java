package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GOAL_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.GOAL_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GOAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Time;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

public class EditCommandParserTest {

    private static final String GOAL_EMPTY = " " + PREFIX_GOAL;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS); // invalid time
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_GOAL_DESC, Goal.MESSAGE_CONSTRAINTS); // invalid goal

        // invalid time followed by valid email
        assertParseFailure(parser, "1" + INVALID_TIME_DESC + EMAIL_DESC_AMY, Time.MESSAGE_CONSTRAINTS);

        // valid time followed by invalid time. The test case for invalid time followed by valid time
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TIME_DESC_BOB + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_GOAL} alone will reset the goals of the {@code Recipe} being edited,
        // parsing it together with a valid goal results in error
        assertParseFailure(parser, "1" + GOAL_DESC_FRIEND + GOAL_DESC_HUSBAND + GOAL_EMPTY, Goal.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + GOAL_DESC_FRIEND + GOAL_EMPTY + GOAL_DESC_HUSBAND, Goal.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + GOAL_EMPTY + GOAL_DESC_FRIEND + GOAL_DESC_HUSBAND, Goal.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_TIME_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECIPE;
        String userInput = targetIndex.getOneBased() + TIME_DESC_BOB + GOAL_DESC_HUSBAND
                + EMAIL_DESC_AMY + NAME_DESC_AMY + GOAL_DESC_FRIEND;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTime(VALID_TIME_BOB).withEmail(VALID_EMAIL_AMY)
                .withGoals(VALID_GOAL_HUSBAND, VALID_GOAL_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + TIME_DESC_BOB + EMAIL_DESC_AMY;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + TIME_DESC_AMY;
        descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditRecipeDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // goals
        userInput = targetIndex.getOneBased() + GOAL_DESC_FRIEND;
        descriptor = new EditRecipeDescriptorBuilder().withGoals(VALID_GOAL_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + TIME_DESC_AMY + EMAIL_DESC_AMY
                + GOAL_DESC_FRIEND + TIME_DESC_AMY + EMAIL_DESC_AMY + GOAL_DESC_FRIEND
                + TIME_DESC_BOB + EMAIL_DESC_BOB + GOAL_DESC_HUSBAND;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_BOB)
                .withEmail(VALID_EMAIL_BOB).withGoals(VALID_GOAL_FRIEND, VALID_GOAL_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + INVALID_TIME_DESC + TIME_DESC_BOB;
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_TIME_DESC + TIME_DESC_BOB;

        descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_BOB).withEmail(VALID_EMAIL_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetGoals_success() {
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + GOAL_EMPTY;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withGoals().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
