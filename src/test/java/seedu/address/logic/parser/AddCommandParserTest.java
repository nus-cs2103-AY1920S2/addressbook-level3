package seedu.address.logic.parser;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    /* todo: after refactoring add command
    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe = new RecipeBuilder(FISH).withGoals(VALID_GOAL_GRAIN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_TURKEY_SANDWICH + NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple times - last time accepted
        assertParseSuccess(parser, NAME_DESC_FISH + TIME_DESC_TURKEY_SANDWICH + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple steps - last step accepted
        assertParseSuccess(parser, NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_TURKEY_SANDWICH + STEP_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_GRAIN, new AddCommand(expectedRecipe));

        // multiple goals - all accepted
        Recipe expectedRecipeMultipleGoals = new RecipeBuilder(FISH).withGoals(VALID_GOAL_GRAIN, VALID_GOAL_PROTEIN)
                .build();
        assertParseSuccess(parser, NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, new AddCommand(expectedRecipeMultipleGoals));
    }*/

    /* todo: after refactoring add command
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero goals
        Recipe expectedRecipe = new RecipeBuilder(TURKEY_SANDWICH).withGoals().build();
        assertParseSuccess(parser, NAME_DESC_TURKEY_SANDWICH + TIME_DESC_TURKEY_SANDWICH + STEP_DESC_TURKEY_SANDWICH,
                new AddCommand(expectedRecipe));
    }*/

    /* todo: after refactoring add command
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_FISH + TIME_DESC_FISH + STEP_DESC_FISH, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, NAME_DESC_FISH + VALID_TIME_FISH + STEP_DESC_FISH, expectedMessage);

        // missing step prefix
        assertParseFailure(parser, NAME_DESC_FISH + TIME_DESC_FISH + VALID_STEP_FISH, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_FISH + VALID_TIME_FISH + VALID_STEP_FISH, expectedMessage);
    }*/

    /* todo: after refactoring add command
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, Name.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, NAME_DESC_FISH + INVALID_TIME_DESC + STEP_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, Time.MESSAGE_CONSTRAINTS);

        // invalid step
        assertParseFailure(parser, NAME_DESC_FISH + TIME_DESC_FISH + INVALID_STEP_DESC
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN, Step.MESSAGE_CONSTRAINTS);

        // invalid goal
        assertParseFailure(parser, NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + INVALID_GOAL_DESC + VALID_GOAL_GRAIN, Goal.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TIME_DESC_FISH + STEP_DESC_FISH,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_FISH + TIME_DESC_FISH + STEP_DESC_FISH
                + GOAL_DESC_PROTEIN + GOAL_DESC_GRAIN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }*/
}
