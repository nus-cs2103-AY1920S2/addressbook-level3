package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_STEP_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.STEP_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.STEP_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.TIME_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.TIME_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_TURKEY_SANDWICH;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.recipe.EditCommand;
import seedu.recipe.logic.commands.recipe.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;

public class EditCommandParserTest {

    private static final String GOAL_EMPTY = " " + PREFIX_GOAL;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_TURKEY_SANDWICH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_TURKEY_SANDWICH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_TURKEY_SANDWICH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS); // invalid time
        assertParseFailure(parser, "1" + INVALID_STEP_DESC, Step.MESSAGE_CONSTRAINTS); // invalid step

        // invalid time followed by valid step
        assertParseFailure(parser, "1" + INVALID_TIME_DESC + STEP_DESC_TURKEY_SANDWICH, Time.MESSAGE_CONSTRAINTS);

        // valid time followed by invalid time. The test case for invalid time followed by valid time
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TIME_DESC_FISH + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_STEP_DESC + VALID_TIME_TURKEY_SANDWICH,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECIPE;
        String userInput = targetIndex.getOneBased() + TIME_DESC_FISH
                + STEP_DESC_TURKEY_SANDWICH + NAME_DESC_TURKEY_SANDWICH;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_TURKEY_SANDWICH)
                .withTime(VALID_TIME_FISH).withSteps(VALID_STEP_TURKEY_SANDWICH).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + TIME_DESC_FISH + STEP_DESC_TURKEY_SANDWICH;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_FISH)
                .withSteps(VALID_STEP_TURKEY_SANDWICH).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_TURKEY_SANDWICH;
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder()
                .withName(VALID_NAME_TURKEY_SANDWICH).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + TIME_DESC_TURKEY_SANDWICH;
        descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_TURKEY_SANDWICH).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // step
        userInput = targetIndex.getOneBased() + STEP_DESC_TURKEY_SANDWICH;
        descriptor = new EditRecipeDescriptorBuilder().withSteps(VALID_STEP_TURKEY_SANDWICH).build();

        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + TIME_DESC_TURKEY_SANDWICH + STEP_DESC_TURKEY_SANDWICH
                + TIME_DESC_TURKEY_SANDWICH + STEP_DESC_TURKEY_SANDWICH
                + TIME_DESC_FISH + STEP_DESC_FISH;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_FISH)
                .withSteps(VALID_STEP_TURKEY_SANDWICH, VALID_STEP_FISH)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + INVALID_TIME_DESC + TIME_DESC_FISH;
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_FISH).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + STEP_DESC_FISH + INVALID_TIME_DESC + TIME_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withTime(VALID_TIME_FISH).withSteps(VALID_STEP_FISH).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
