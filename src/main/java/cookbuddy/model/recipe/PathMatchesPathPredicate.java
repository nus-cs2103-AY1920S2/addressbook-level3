package cookbuddy.model.recipe;

import java.util.function.Predicate;

import cookbuddy.model.recipe.attribute.Image;

/**
 * Tests that a {@code Meme}'s {@code Path} matches a path given.
 */
public class PathMatchesPathPredicate implements Predicate<Recipe> {
    private final Image path;

    public PathMatchesPathPredicate(Image path) {
        this.path = path;
    }

    @Override
    public boolean test(Recipe recipe) {
        return path.equals(recipe.getImageFilePath());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PathMatchesPathPredicate // instanceof handles nulls
            && path.equals(((PathMatchesPathPredicate) other).path)); // state check
    }

}
