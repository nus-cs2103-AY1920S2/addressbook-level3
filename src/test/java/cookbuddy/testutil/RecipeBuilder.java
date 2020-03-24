package cookbuddy.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Calorie;
import cookbuddy.model.recipe.attribute.Ingredient;
import cookbuddy.model.recipe.attribute.IngredientList;
import cookbuddy.model.recipe.attribute.InstructionList;
import cookbuddy.model.recipe.attribute.Name;
import cookbuddy.model.recipe.attribute.Serving;
import cookbuddy.model.recipe.attribute.Tag;
import cookbuddy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Ham Sandwich";
    public static final List<Ingredient> DEFAULT_INGREDIENT_LIST = List.of(new Ingredient("bread, 2 slices"),
            new Ingredient("ham, 1 slice"));
    public static final String DEFAULT_INSTRUCTION_LIST = "place ham between slices of bread; eat";
    public static final String DEFAULT_CALORIE = "300";
    public static final String DEFAULT_SERVING = "2";


    private Name name;
    private IngredientList ingredients;
    private InstructionList instructions;
    private Calorie calorie;
    private Serving serving;
    private Set<Tag> tags;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        ingredients = new IngredientList(DEFAULT_INGREDIENT_LIST);
        instructions = new InstructionList(DEFAULT_INSTRUCTION_LIST);
        tags = new HashSet<>();
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        ingredients = recipeToCopy.getIngredients();
        instructions = recipeToCopy.getInstructions();
        tags = new HashSet<>(recipeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code IngredientList} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredients(String ingredients) {
        this.ingredients = new IngredientList(ingredients);
        return this;
    }

    /**
     * Sets the {@code InstructionList} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withInstructions(String instructions) {
        this.instructions = new InstructionList(instructions);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, ingredients, instructions, tags);
    }
}
