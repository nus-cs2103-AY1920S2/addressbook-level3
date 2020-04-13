package seedu.address.logic.parser.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recipe.RecipeAddIngredientCommand;
import seedu.address.logic.commands.recipe.RecipeAddStepCommand;
import seedu.address.logic.commands.recipe.RecipeAddTagCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveIngredientCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveStepCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.step.Step;
import seedu.address.model.tag.Tag;

public class RecipeCommandParserTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new IngredientName("Ingredient"),
            new IngredientQuantity("5"));
    private static final String VALID_ADD_INGREDIENT_ARGUMENT = "1 add ingredient i/Ingredient q/5";
    private static final String INVALID_ADD_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX = "add ingredient i/Ingredient q/5";
    private static final String INVALID_ADD_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME = "1 add ingredient q/5";
    private static final String VALID_REMOVE_INGREDIENT_ARGUMENT = "1 remove ingredient i/Ingredient q/5";
    private static final String VALID_REMOVE_INGREDIENT_NO_QUANTITY_ARGUMENT = "1 remove ingredient i/Ingredient";
    private static final String INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX = "remove ingredient i/Ingredient "
        + "q/5";
    private static final String INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME = "1 add ingredient q/5";

    private static final Step VALID_STEP = new Step("Step");
    private static final Optional<Index> VALID_ADD_STEP_INDEX = Optional.of(new Index(0));
    private static final Index VALID_REMOVE_STEP_INDEX = new Index(0);
    private static final String VALID_ADD_STEP_ARGUMENT = "1 add step x/1 s/Step";
    private static final String INVALID_ADD_STEP_ARGUMENT_NO_RECIPE_INDEX = "add step x/1 s/Step";
    private static final String INVALID_ADD_STEP_ARGUMENT_NO_STEP_INDEX = "1 add step x/s";
    private static final String INVALID_ADD_STEP_ARGUMENT_NO_STEP_DESCRIPTION = "1 add step s/Step";
    private static final String VALID_REMOVE_STEP_ARGUMENT = "1 remove step x/1";
    private static final String INVALID_REMOVE_STEP_ARGUMENT_NO_RECIPE_INDEX = "remove step x/1 s/Step";
    private static final String INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_INDEX = "1 remove step x/s";
    private static final String INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_DESCRIPTION = "1 remove step s/Step";

    private static final Tag VALID_TAG = new Tag("Tag");
    private static final String VALID_ADD_TAG_ARGUMENT = "1 add tag t/Tag";
    private static final String INVALID_ADD_TAG_ARGUMENT_NO_RECIPE_INDEX = "add tag t/Tag";
    private static final String INVALID_ADD_TAG_ARGUMENT_NO_TAG = "1 add tag";
    private static final String VALID_REMOVE_TAG_ARGUMENT = "1 remove tag t/Tag";
    private static final String INVALID_REMOVE_TAG_ARGUMENT_NO_RECIPE_INDEX = "remove tag t/Tag";
    private static final String INVALID_REMOVE_TAG_ARGUMENT_NO_TAG = "1 remove tag";

    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        assertEquals(new RecipeCommandParser().parse(VALID_ADD_INGREDIENT_ARGUMENT),
                new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT));
        assertEquals(new RecipeCommandParser().parse(VALID_ADD_STEP_ARGUMENT),
                new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_ADD_STEP_INDEX, VALID_STEP));
        assertEquals(new RecipeCommandParser().parse(VALID_ADD_TAG_ARGUMENT),
                new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG));
        assertEquals(new RecipeCommandParser().parse(VALID_REMOVE_INGREDIENT_ARGUMENT),
                new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT.getName(),
                        Optional.of(VALID_INGREDIENT.getQuantity())));
        assertEquals(new RecipeCommandParser().parse(VALID_REMOVE_INGREDIENT_NO_QUANTITY_ARGUMENT),
                new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT.getName(),
                        Optional.empty()));
        assertEquals(new RecipeCommandParser().parse(VALID_REMOVE_STEP_ARGUMENT),
                new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX));
        assertEquals(new RecipeCommandParser().parse(VALID_REMOVE_TAG_ARGUMENT),
                new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeCommandParser().parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_STEP_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_STEP_ARGUMENT_NO_STEP_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_STEP_ARGUMENT_NO_STEP_DESCRIPTION));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_TAG_ARGUMENT_NO_TAG));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ADD_TAG_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_INGREDIENT_NAME));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_STEP_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_DESCRIPTION));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_STEP_ARGUMENT_NO_STEP_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_TAG_ARGUMENT_NO_RECIPE_INDEX));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_REMOVE_TAG_ARGUMENT_NO_TAG));
        assertThrows(ParseException.class, () ->
                new RecipeCommandParser().parse(INVALID_ARGUMENT));
    }
}
