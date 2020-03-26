package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.good.Good;
import seedu.address.model.good.UniqueGoodList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An {@code Inventory} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedInventory extends Inventory implements Version<Inventory> {

    private Inventory currentState;

    public VersionedInventory() {
        currentState = new Inventory();
    }

    /**
     * Creates a VersionedInventory using the Goods in the {@code toBeCopied}.
     */
    public VersionedInventory(ReadOnlyList<Good> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the goods list in current state with {@code goods}.
     * {@code goods} must not contain duplicate goods.
     */
    public void setGoods(List<Good> goods) {
        currentState.setGoods(goods);
    }

    public int index(Good toFind) {
        return currentState.index(toFind);
    }

    /**
     * Resets the existing data in the current state with {@code newData}.
     */
    public void resetData(ReadOnlyList<Good> newData) {
        setGoods(newData.getReadOnlyList());
    }

    //// good-level operations

    /**
     * Returns true if a good with the same identity as {@code good} exists in the address book.
     */
    public boolean hasGood(Good good) {
        return currentState.hasGood(good);
    }

    /**
     * Adds a good to the current state.
     * The good must not already exist in the current state.
     */
    public void addGood(Good p) {
        currentState.addGood(p);
    }

    /**
     * Replaces the given good {@code target} in the current state with {@code editedGood}.
     * {@code target} must exist in the current state.
     * The good identity of {@code editedGood} must not be the same as another existing good in the current state.
     */
    public void setGood(Good target, Good editedGood) {
        currentState.setGood(target, editedGood);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the current state.
     */
    public void removeGood(Good key) {
        currentState.removeGood(key);
    }

    @Override
    public void commit() {
        return;
    }

    @Override
    public void undo() {
        return;
    }

    @Override
    public Inventory getCurrentState() {
        return currentState;
    }

    //// util methods

    @Override
    public String toString() {
        return currentState.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Good> getReadOnlyList() {
        return currentState.getReadOnlyList();
    }

    @Override
    protected UniqueGoodList getGoods() {
        return currentState.getGoods();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedInventory // instanceof handles nulls
                && currentState.equals(((VersionedInventory) other).currentState))
                || (other instanceof Inventory // instanceof handles nulls
                && currentState.equals(((Inventory) other)));
    }

    @Override
    public int hashCode() {
        return currentState.hashCode();
    }
}
