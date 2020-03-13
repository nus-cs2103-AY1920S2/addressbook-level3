package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.STEP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STEP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GOAL_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.GOAL_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STEP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GOAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRecipes.AMY;
import static seedu.address.testutil.TypicalRecipes.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Time;
import seedu.address.testutil.RecipeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe = new RecipeBuilder(BOB).withGoals(VALID_GOAL_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple times - last time accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TIME_DESC_AMY + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple steps - last step accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_AMY + STEP_DESC_BOB
                + GOAL_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple goals - all accepted
        Recipe expectedRecipeMultipleGoals = new RecipeBuilder(BOB).withGoals(VALID_GOAL_FRIEND, VALID_GOAL_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_HUSBAND + GOAL_DESC_FRIEND, new AddCommand(expectedRecipeMultipleGoals));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero goals
        Recipe expectedRecipe = new RecipeBuilder(AMY).withGoals().build();
        assertParseSuccess(parser, NAME_DESC_AMY + TIME_DESC_AMY + STEP_DESC_AMY,
                new AddCommand(expectedRecipe));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TIME_DESC_BOB + STEP_DESC_BOB, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TIME_BOB + STEP_DESC_BOB, expectedMessage);

        // missing step prefix
        assertParseFailure(parser, NAME_DESC_BOB + TIME_DESC_BOB + VALID_STEP_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TIME_BOB + VALID_STEP_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_HUSBAND + GOAL_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TIME_DESC + STEP_DESC_BOB
                + GOAL_DESC_HUSBAND + GOAL_DESC_FRIEND, Time.MESSAGE_CONSTRAINTS);

        // invalid step
        assertParseFailure(parser, NAME_DESC_BOB + TIME_DESC_BOB + INVALID_STEP_DESC
                + GOAL_DESC_HUSBAND + GOAL_DESC_FRIEND, Step.MESSAGE_CONSTRAINTS);

        // invalid goal
        assertParseFailure(parser, NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_BOB
                + INVALID_GOAL_DESC + VALID_GOAL_FRIEND, Goal.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TIME_DESC_BOB + STEP_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TIME_DESC_BOB + STEP_DESC_BOB
                + GOAL_DESC_HUSBAND + GOAL_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
