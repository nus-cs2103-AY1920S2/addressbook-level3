package cookbuddy.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Calorie;
import cookbuddy.model.recipe.attribute.Difficulty;
import cookbuddy.model.recipe.attribute.Ingredient;
import cookbuddy.model.recipe.attribute.IngredientList;
import cookbuddy.model.recipe.attribute.Instruction;
import cookbuddy.model.recipe.attribute.InstructionList;
import cookbuddy.model.recipe.attribute.Name;
import cookbuddy.model.recipe.attribute.Photograph;
import cookbuddy.model.recipe.attribute.Rating;
import cookbuddy.model.recipe.attribute.Serving;
import cookbuddy.model.recipe.attribute.Tag;
import cookbuddy.model.recipe.attribute.Time;
import cookbuddy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Ham Sandwich";
    public static final List<Ingredient> DEFAULT_INGREDIENT_LIST = List.of(new Ingredient("bread, 2 slices"),
            new Ingredient("ham, 1 slice"));
    public static final List<Instruction> DEFAULT_INSTRUCTION_LIST = List.of(
            new Instruction("place ham between " + "slices of bread"), new Instruction("eat"));
    public static final Photograph DEFAULT_PHOTOGRAPH = Photograph.PLACEHOLDER_PHOTOGRAPH;
    public static final String DEFAULT_CALORIE = "300";
    public static final int DEFAULT_SERVING = 1;
    public static final int DEFAULT_RATING = 3;
    public static final int DEFAULT_DIFFICULTY = 3;


    private Name name;
    private IngredientList ingredients;
    private InstructionList instructions;
    private Photograph photograph;
    private Calorie calorie;
    private Serving serving;
    private Rating rating;
    private Difficulty difficulty;
    private Set<Tag> tags;

    private Recipe recipe;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        ingredients = new IngredientList(DEFAULT_INGREDIENT_LIST);
        instructions = new InstructionList(DEFAULT_INSTRUCTION_LIST);
        photograph = DEFAULT_PHOTOGRAPH;
        calorie = new Calorie(DEFAULT_CALORIE);
        serving = new Serving(DEFAULT_SERVING);
        rating = new Rating(DEFAULT_RATING);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        ingredients = recipeToCopy.getIngredients();
        instructions = recipeToCopy.getInstructions();
        photograph = recipeToCopy.getPhotograph();
        calorie = recipeToCopy.getCalorie();
        serving = recipeToCopy.getServing();
        rating = recipeToCopy.getRating();
        difficulty = recipeToCopy.getDifficulty();
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
    public RecipeBuilder withIngredients(List<Ingredient> ingredients) {
        this.ingredients = new IngredientList(ingredients);
        return this;
    }

    /**
     * Sets the {@code InstructionList} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withInstructions(List<Instruction> instructions) {
        this.instructions = new InstructionList(instructions);
        return this;
    }

    /**
     * Sets the {@code Photograph} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withPhotograph(Photograph photograph) {
        this.photograph = photograph;
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withCalorie(String calorie) {
        this.calorie = new Calorie(calorie);
        return this;
    }

    /**
     * Sets the {@code Serving} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withServing(int serving) {
        this.serving = new Serving(serving);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withRating(int rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withDifficulty(int difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    public Recipe build() {
        Recipe toReturn = new Recipe(name, ingredients, instructions, photograph, calorie, serving, rating, difficulty,
                tags);
        recipe = toReturn;
        return toReturn;
    }

    public Recipe addTime(int h, int m, int s) {
        recipe.setTime(new Time(h, m, s));
        return recipe;
    }
}
