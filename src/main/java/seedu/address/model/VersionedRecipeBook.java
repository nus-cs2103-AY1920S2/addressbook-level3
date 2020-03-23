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
     * Checks if it is possible to undo. Returns true if there is an older state of the RecipeBook
     * (relative to the current state) stored in the list.
     */
    public boolean canUndo() {
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
        return currentStatePointer > 0;
    }

    /**
     * Checks if it is possible to redo. Returns true if there is a newer state of the RecipeBook
     * (relative to the current state) stored in the list.
     */
    public boolean canRedo() {
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
        return currentStatePointer < addressBookStateList.size() - 1;
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
     * Changes the state of the RecipeBook to the previous state.
     */
    public ReadOnlyRecipeBook undo() {
        currentStatePointer--;
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Changes the state of the RecipeBook to the next available state.
     */
    public ReadOnlyRecipeBook redo() {
        currentStatePointer++;
        assert currentStatePointer >= 0 && currentStatePointer < addressBookStateList.size();
        return addressBookStateList.get(currentStatePointer);
    }

}
