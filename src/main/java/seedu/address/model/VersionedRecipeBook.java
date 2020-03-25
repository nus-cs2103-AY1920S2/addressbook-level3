package seedu.address.model;

import java.util.ArrayList;

/**
 * Maintains different versions (states) of RecipeBooks in a list.
 */
public class VersionedRecipeBook extends RecipeBook {

    private final ArrayList<ReadOnlyRecipeBook> addressBookStateList;
    private int currentStatePointer;

    public VersionedRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.addressBookStateList = new ArrayList<>();
        addressBookStateList.add(recipeBook);

        this.currentStatePointer = 0;
    }

    /**
     * Checks if it is possible to undo. Returns true if there is at least {@code numberOfUndo} older states
     * of the RecipeBook (relative to the current state) stored in the list.
     */
    public boolean canUndo(int numberOfUndo) {
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
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
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
        assert numberOfRedo >= 0;
        if (numberOfRedo > 0) {
            return numberOfRedo <= addressBookStateList.size() - 1 - currentStatePointer;
        } else {
            return currentStatePointer < addressBookStateList.size() - 1;
        }
    }

    /**
     * Adds the new state of the RecipeBook to the list when the RecipeBook undergoes a state change.
     * If the current state is not the last item on the list, the new state will override all states
     * after the current state.
     */
    public void commit(ReadOnlyRecipeBook recipeBook) {
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
        if (currentStatePointer != addressBookStateList.size() - 1) {
            while (addressBookStateList.size() - 1 > currentStatePointer) {
                int lastIndex = addressBookStateList.size() - 1;
                addressBookStateList.remove(lastIndex);
            }
        }
        addressBookStateList.add(recipeBook);
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
            assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
            numberOfUndo--;
        }
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Fast forwards the RecipeBook by {@code numberOfRedo} states.
     */
    public ReadOnlyRecipeBook redo(int numberOfRedo) {
        assert numberOfRedo >= 0;
        if (numberOfRedo == 0) {
            currentStatePointer = addressBookStateList.size() - 1;
        }
        while (numberOfRedo > 0) {
            currentStatePointer++;
            assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
            numberOfRedo--;
        }
        return addressBookStateList.get(currentStatePointer);
    }

}
