package seedu.recipe.model;

import java.util.ArrayList;

/**
 * Maintains different versions (states) of RecipeBooks in a list.
 */
public class VersionedRecipeBook extends RecipeBook {

    private final ArrayList<ReadOnlyRecipeBook> recipeBookStateList;
    private int currentStatePointer;

    public VersionedRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.recipeBookStateList = new ArrayList<>();
        recipeBookStateList.add(recipeBook);

        this.currentStatePointer = 0;
    }

    /**
     * Checks if it is possible to undo. Returns true if there is at least {@code numberOfUndo} older states
     * of the RecipeBook (relative to the current state) stored in the list.
     */
    public boolean canUndo(int numberOfUndo) {
        assert currentStatePointer >= 0 && currentStatePointer < recipeBookStateList.size();
        assert numberOfUndo >= 0;
        if (numberOfUndo > 0) {
            return numberOfUndo <= currentStatePointer;
        } else {
            return currentStatePointer > 0;
        }
    }

    /**
     * Checks if it is possible to redo. Returns true if there is are at least {@code numberOfRedo} newer states
     * of the RecipeBook (relative to the current state) stored in the list.
     */
    public boolean canRedo(int numberOfRedo) {
        assert currentStatePointer >= 0 && currentStatePointer < recipeBookStateList.size();
        assert numberOfRedo >= 0;
        if (numberOfRedo > 0) {
            return numberOfRedo <= recipeBookStateList.size() - 1 - currentStatePointer;
        } else {
            return currentStatePointer < recipeBookStateList.size() - 1;
        }
    }

    /**
     * Adds the new state of the RecipeBook to the list when the RecipeBook undergoes a state change.
     * If the current state is not the last item on the list, the new state will override all states
     * after the current state.
     */
    public void commit(ReadOnlyRecipeBook recipeBook) {
        assert currentStatePointer >= 0 && currentStatePointer < recipeBookStateList.size();
        if (currentStatePointer != recipeBookStateList.size() - 1) {
            while (recipeBookStateList.size() - 1 > currentStatePointer) {
                int lastIndex = recipeBookStateList.size() - 1;
                recipeBookStateList.remove(lastIndex);
            }
        }
        recipeBookStateList.add(recipeBook);
        currentStatePointer++;
    }

    /**
     * Reverts the RecipeBook back by {@code numberOfUndo} states.
     */
    public ReadOnlyRecipeBook undo(int numberOfUndo) {
        assert numberOfUndo >= 0;
        if (numberOfUndo == 0) {
            currentStatePointer = 0;
        }
        while (numberOfUndo > 0) {
            currentStatePointer--;
            assert currentStatePointer >= 0 && currentStatePointer < recipeBookStateList.size();
            numberOfUndo--;
        }
        return recipeBookStateList.get(currentStatePointer);
    }

    /**
     * Fast forwards the RecipeBook by {@code numberOfRedo} states.
     */
    public ReadOnlyRecipeBook redo(int numberOfRedo) {
        assert numberOfRedo >= 0;
        if (numberOfRedo == 0) {
            currentStatePointer = recipeBookStateList.size() - 1;
        }
        while (numberOfRedo > 0) {
            currentStatePointer++;
            assert currentStatePointer >= 0 && currentStatePointer < recipeBookStateList.size();
            numberOfRedo--;
        }
        return recipeBookStateList.get(currentStatePointer);
    }

}
