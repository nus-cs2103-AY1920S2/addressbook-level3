package seedu.address.model.mod;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all module data at the ModTAble level
 * Duplicates are not allowed
 */

public class ModTAble implements ReadOnlyMod {
    private static Mod emptyMod = new Mod(new Mod(new ModCode("TA8135"), "TAble"),
        "Use the viewModInfo command on a module to view it!");

    private final UniqueModList mods;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        mods = new UniqueModList();
    }
    private final ObservableList<Mod> viewedModSingletonList =
        FXCollections.observableArrayList(Collections.singletonList(emptyMod));

    public ModTAble() {}

    /**
     * Creates a ModTAble using the modules in the {@code toBeCopied}
     */
    public ModTAble(ReadOnlyMod toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Creates a ModTAble using the modules in the {@code modList}
     */
    public ModTAble(List<Mod> modList) {
        this.mods.setMods(modList);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code mods}.
     * {@code mods} must not contain duplicate modules.
     */
    public void setMods(List<Mod> mods) {
        this.mods.setMods(mods);
    }

    /**
     * Resets the existing data of this {@code ModTAble} with {@code newData}.
     */
    public void resetData(ReadOnlyMod newData) {
        requireNonNull(newData);
        setMods(newData.getAllMods());
    }

    //// Mod-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in TAble.
     */
    public boolean hasMod(Mod module) {
        requireNonNull(module);
        return mods.contains(module);
    }

    /**
     * Adds a module to TAble.
     * The module must not already exist in TAble.
     */
    public void addMod(Mod module) {
        mods.add(module);
    }


    /**
     * Removes {@code key} from this {@code ModTAble}.
     * {@code key} must exist in the ModTAble.
     */
    public void removeMod(Mod key) {
        mods.remove(key);
        if (viewedModSingletonList.contains(key)) {
            viewedModSingletonList.clear();
            viewedModSingletonList.add(ModTAble.emptyMod);
        }
    }


    /**
     * Returns {@code mod} from {@code TAble}.
     * {@code module} must exist in the address book.
     */
    public void setMod(Mod target, Mod editedMod) {
        requireAllNonNull(target, editedMod);
        mods.setMod(target, editedMod);
        if (editedMod.isSameMod(viewedModSingletonList.get(0))) {
            viewedModSingletonList.clear();
            viewedModSingletonList.add(editedMod);
        }
    }

    /**
     * Gets the currently viewed Mod, and finds the most recent state of the mod in TAble.
     * @return Mod of the current view.
     */
    public ObservableList<Mod> getViewedModSingletonList() {
        if (!viewedModSingletonList.contains(emptyMod) && !mods.contains(viewedModSingletonList.get(0))) {
            Mod temp = viewedModSingletonList.get(0);
            assert mods.getAllMods().stream().anyMatch(m -> m.isSameMod(temp));
            viewedModSingletonList.clear();
            viewedModSingletonList.add(mods.getAllMods()
                .stream()
                .filter(m -> m.isSameMod(temp))
                .findAny().get());
        }
        return viewedModSingletonList;
    }

    /**
     * Changes viewed mod to {@code mod}.
     * @param mod Module to change current view to.
     */
    public void setViewedModSingletonList(Mod mod) {
        assert mods.contains(mod);
        viewedModSingletonList.clear();
        viewedModSingletonList.add(mod);
    }

    //// util methods

    @Override
    public String toString() {
        return mods.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Mod> getAllMods() {
        return mods.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.mod.ModTAble // instanceof handles nulls
                && mods.equals(((seedu.address.model.mod.ModTAble) other).mods));
    }

    @Override
    public int hashCode() {
        return mods.hashCode();
    }
}
