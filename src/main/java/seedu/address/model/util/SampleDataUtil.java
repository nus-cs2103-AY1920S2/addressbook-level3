package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Alex Yeoh"), new Time("87438807"), new Step("alexyeoh@example.com"),
                getGoalSet("friends")),
            new Recipe(new Name("Bernice Yu"), new Time("99272758"), new Step("berniceyu@example.com"),
                getGoalSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"), new Time("93210283"), new Step("charlotte@example.com"),
                getGoalSet("neighbours")),
            new Recipe(new Name("David Li"), new Time("91031282"), new Step("lidavid@example.com"),
                getGoalSet("family")),
            new Recipe(new Name("Irfan Ibrahim"), new Time("92492021"), new Step("irfan@example.com"),
                getGoalSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"), new Time("92624417"), new Step("royb@example.com"),
                getGoalSet("colleagues"))
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
     * Returns a goal set containing the list of strings given.
     */
    public static Set<Goal> getGoalSet(String... strings) {
        return Arrays.stream(strings)
                .map(Goal::new)
                .collect(Collectors.toSet());
    }

}
