package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.IngredientList;
import seedu.address.model.recipe.InstructionList;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.attribute.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {

    public static Recipe[] getSampleRecipes() {
        return new Recipe[]{
            new Recipe(new Name("Ham Sandwich"), new IngredientList("bread, 2 slices; ham, 1 slice"),
                    new InstructionList("put ham between bread; serve on plate"),
                        getTagSet("breakfast", "lunch")),
            new Recipe(new Name("Idiot Sandwich"), new IngredientList("bread, 2 slices"),
                        new InstructionList("put bread to opposite sides of head; Yell "
                                            + "\"I am an idiot sandwich!\""),
                        getTagSet("lunch", "dinner"))
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleAb = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.addRecipe(sampleRecipe);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
