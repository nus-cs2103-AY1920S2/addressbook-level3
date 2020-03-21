package seedu.address.model.recipe;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.goal.Goal;

/**
 * Tests that a {@code Recipe}'s {@code Time}, {@code Ingredient}, or {@code Goal} matches any of the arguments given.
 */
public class RecipeMatchesKeywordsPredicate implements Predicate<Recipe> {
    private final List<Time> time;
    private final Set<Goal> goals;
    private final boolean favourites;

    public RecipeMatchesKeywordsPredicate(List<Time> time, Set<Goal> goals, boolean favourites) {
        this.time = time;
        this.goals = goals;
        this.favourites = favourites;
    }

    @Override
    public boolean test(Recipe recipe) {
        boolean toFilter = true;
        if (time.size() == 1) {
            toFilter = toFilter && time.get(0).isLessThan(recipe.getTime());
        } else if (time.size() == 2) {
            toFilter = toFilter && recipe.getTime().isWithinRange(time.get(0), time.get(1));
        }

        if (!goals.isEmpty()) {
            toFilter = toFilter && goals.stream().anyMatch(goal -> recipe.getGoals().contains(goal));
        }

        if (favourites) {
            toFilter = toFilter && recipe.isFavourite();
        }

        return toFilter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeMatchesKeywordsPredicate // instanceof handles nulls
                && time.equals(((RecipeMatchesKeywordsPredicate) other).time)
                && goals.equals(((RecipeMatchesKeywordsPredicate) other).goals)
                && favourites == ((RecipeMatchesKeywordsPredicate) other).favourites); // state check
    }

}
