package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.commands.CommandTestUtil.FRUIT_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.FRUIT_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.GOAL_DESC_GRAIN;
import static seedu.recipe.logic.commands.CommandTestUtil.GOAL_DESC_PROTEIN;
import static seedu.recipe.logic.commands.CommandTestUtil.GRAIN_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.GRAIN_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_GOAL_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_STEP_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.OTHER_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.OTHER_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.recipe.logic.commands.CommandTestUtil.PROTEIN_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.PROTEIN_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.STEP_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.STEP_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.TIME_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.TIME_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_GRAIN;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_PROTEIN;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VEGETABLE_DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VEGETABLE_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalRecipes.FISH;
import static seedu.recipe.testutil.TypicalRecipes.TURKEY_SANDWICH;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;
import seedu.recipe.testutil.RecipeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe = new RecipeBuilder(FISH).withGoals(VALID_GOAL_GRAIN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_TURKEY_SANDWICH + NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple times - last time accepted
        assertParseSuccess(parser, NAME_DESC_FISH + TIME_DESC_TURKEY_SANDWICH + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple goals - all accepted
        Recipe expectedRecipeMultipleGoals = new RecipeBuilder(FISH).withGoals(VALID_GOAL_GRAIN, VALID_GOAL_PROTEIN)
                .build();
        assertParseSuccess(parser, NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, new AddCommand(expectedRecipeMultipleGoals));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero goals
        Recipe expectedRecipe = new RecipeBuilder(TURKEY_SANDWICH).withGoals().build();
        assertParseSuccess(parser, NAME_DESC_TURKEY_SANDWICH + TIME_DESC_TURKEY_SANDWICH
                + STEP_DESC_TURKEY_SANDWICH + GRAIN_DESC_TURKEY_SANDWICH + VEGETABLE_DESC_TURKEY_SANDWICH
                + PROTEIN_DESC_TURKEY_SANDWICH + FRUIT_DESC_TURKEY_SANDWICH + OTHER_DESC_TURKEY_SANDWICH,
                new AddCommand(expectedRecipe));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_FISH + TIME_DESC_FISH + STEP_DESC_FISH, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, NAME_DESC_FISH + VALID_TIME_FISH + STEP_DESC_FISH, expectedMessage);

        // missing ingredient prefix
        assertParseFailure(parser, NAME_DESC_FISH + TIME_DESC_FISH + VALID_STEP_FISH, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_FISH + VALID_TIME_FISH + VALID_STEP_FISH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, Name.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, NAME_DESC_FISH + INVALID_TIME_DESC + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, Time.MESSAGE_CONSTRAINTS);

        // invalid step
        assertParseFailure(parser, NAME_DESC_FISH + TIME_DESC_FISH + INVALID_STEP_DESC
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, Step.MESSAGE_CONSTRAINTS);

        // invalid goal
        assertParseFailure(parser, NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + INVALID_GOAL_DESC + VALID_GOAL_GRAIN, Goal.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GRAIN_DESC_FISH + VEGETABLE_DESC_FISH + PROTEIN_DESC_FISH + FRUIT_DESC_FISH + OTHER_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
