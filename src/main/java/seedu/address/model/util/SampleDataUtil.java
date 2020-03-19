package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.attribute.Ingredient;
import seedu.address.model.recipe.attribute.IngredientList;
import seedu.address.model.recipe.attribute.Instruction;
import seedu.address.model.recipe.attribute.InstructionList;
import seedu.address.model.recipe.attribute.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {

    public static Recipe[] getSampleRecipes() {
        Name name1 = new Name("Ham Sandwich");
        IngredientList ingList1 = new IngredientList(List.of(
                new Ingredient("bread, 2 slices"),
                new Ingredient("ham, 1 slice")));
        InstructionList insList1 = new InstructionList(List.of(
                new Instruction("put ham between bread"),
                new Instruction("serve on plate")));
        Set<Tag> tagSet1 = getTagSet("breakfast", "lunch");

        Recipe recipe1 = new Recipe(name1, ingList1, insList1, tagSet1);

        Name name2 = new Name("Idiot Sandwich");
        IngredientList ingList2 = new IngredientList(List.of(new Ingredient("bread, 2 slices")));
        InstructionList insList2 = new InstructionList(List.of(
                new Instruction("put bread to opposite sides of head"),
                new Instruction("Yell 'I am an idiot sandwich!'")));
        Set<Tag> tagSet2 = getTagSet("lunch", "dinner");

        Recipe recipe2 = new Recipe(name2, ingList2, insList2, tagSet2);

        return new Recipe[] {recipe1, recipe2};
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
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}
