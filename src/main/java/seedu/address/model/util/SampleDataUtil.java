package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Phone;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {

    public static Recipe[] getSamplePersons() {
        return new Recipe[]{
            new Recipe(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        getTagSet("friends")),
            new Recipe(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        getTagSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        getTagSet("neighbours")),
            new Recipe(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        getTagSet("family")),
            new Recipe(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        getTagSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyRecipeBook getSampleAddressBook() {
        RecipeBook sampleAb = new RecipeBook();
        for (Recipe sampleRecipe : getSamplePersons()) {
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
