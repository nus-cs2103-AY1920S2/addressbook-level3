package seedu.address.logic.parser.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recipe.RecipeRemoveIngredientCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveStepCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.tag.Tag;

public class RecipeRemoveCommandParserTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new IngredientName("Ingredient"),
            new IngredientQuantity("5"));
    private static final String VALID_REMOVE_INGREDIENT_ARGUMENT = "1 ingredient i/Ingredient q/5";
    private static final String INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX = "ingredient i/Ingredient "
            + "q/5";
    private static final String INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME = "1 ingredient q/5";
    private static final String VALID_PARSE_REMOVE_INGREDIENT_ARGUMENT = "1 i/Ingredient q/5";
    private static final String VALID_PARSE_REMOVE_INGREDIENT_NO_QUANTITY_ARGUMENT = "1 i/Ingredient";

    private static final Index VALID_STEP_INDEX = new Index(0);
    private static final String VALID_REMOVE_STEP_ARGUMENT = "1 step x/1";
    private static final String INVALID_REMOVE_STEP_ARGUMENT_NO_RECIPE_INDEX = "step x/1 s/Step";
    private static final String INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_INDEX = "1 step x/s";
    private static final String INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_DESCRIPTION = "1 step s/Step";
    private static final String VALID_PARSE_REMOVE_STEP_ARGUMENT = "1 x/1";

    private static final Tag VALID_TAG = new Tag("Tag");
    private static final String VALID_REMOVE_TAG_ARGUMENT = "1 tag t/Tag";
    private static final String INVALID_REMOVE_TAG_ARGUMENT_NO_RECIPE_INDEX = "tag t/Tag";
    private static final String INVALID_REMOVE_TAG_ARGUMENT_NO_TAG = "1 tag";
    private static final String VALID_PARSE_REMOVE_TAG_ARGUMENT = "1 t/Tag";

    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        assertEquals(new RecipeRemoveCommandParser().parse(VALID_REMOVE_INGREDIENT_ARGUMENT),
                new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT.getName(),
                        Optional.of(VALID_INGREDIENT.getQuantity())));
        assertEquals(new RecipeRemoveCommandParser().parse(VALID_REMOVE_STEP_ARGUMENT),
                new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX));
        assertEquals(new RecipeRemoveCommandParser().parse(VALID_REMOVE_TAG_ARGUMENT),
                new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_STEP_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_DESCRIPTION));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_TAG_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_REMOVE_TAG_ARGUMENT_NO_TAG));
        assertThrows(ParseException.class, () ->
                new RecipeRemoveCommandParser().parse(INVALID_ARGUMENT));
    }

    @Test
    public void parseRemoveIngredient_validInput() throws ParseException {
        assertEquals(new RecipeRemoveCommandParser().parseRemoveIngredient(VALID_PARSE_REMOVE_INGREDIENT_ARGUMENT),
                new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT.getName(),
                        Optional.of(VALID_INGREDIENT.getQuantity())));
    }

    @Test
    public void parseRemoveIngredientWithoutQuantity_validInput() throws ParseException {
        assertEquals(new RecipeRemoveCommandParser()
                        .parseRemoveIngredient(VALID_PARSE_REMOVE_INGREDIENT_NO_QUANTITY_ARGUMENT),
                new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT.getName(), Optional.empty()));
    }

    @Test
    public void parseRemoveIngredient_invalidInput() {
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser().parseRemoveIngredient(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser()
            .parseRemoveIngredient(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME));
    }

    @Test
    public void parseRemoveStep_validInput() throws ParseException {
        assertEquals(new RecipeRemoveCommandParser().parseRemoveStep(VALID_PARSE_REMOVE_STEP_ARGUMENT),
            new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX));
    }

    @Test
    public void parseRemoveStep_invalidInput() {
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser().parseRemoveStep(INVALID_REMOVE_STEP_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser().parseRemoveStep(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_INDEX));
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser().parseRemoveStep(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_DESCRIPTION));
    }

    @Test
    public void parseRemoveTag_validInput() throws ParseException {
        assertEquals(new RecipeRemoveCommandParser().parseRemoveTag(VALID_PARSE_REMOVE_TAG_ARGUMENT),
            new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG));
    }

    @Test
    public void parseRemoveTag_invalidInput() {
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser().parseRemoveTag(INVALID_REMOVE_TAG_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
            new RecipeRemoveCommandParser().parseRemoveTag(INVALID_REMOVE_TAG_ARGUMENT_NO_TAG));
    }

    @Test
    public void containsIngredient_validInput() {
        assertTrue(new RecipeRemoveCommandParser().containsIngredient(VALID_REMOVE_INGREDIENT_ARGUMENT));
        assertFalse(new RecipeRemoveCommandParser()
            .containsIngredient(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME));
    }

    @Test
    public void containsStep_validInput() {
        assertTrue(new RecipeRemoveCommandParser().containsStep(VALID_REMOVE_STEP_ARGUMENT));
        assertFalse(new RecipeRemoveCommandParser().containsStep(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_DESCRIPTION));
    }

    @Test
    public void containsTag_validInput() {
        assertTrue(new RecipeRemoveCommandParser().containsTag(VALID_REMOVE_TAG_ARGUMENT));
        assertFalse(new RecipeRemoveCommandParser().containsTag(INVALID_REMOVE_TAG_ARGUMENT_NO_TAG));
    }
}
