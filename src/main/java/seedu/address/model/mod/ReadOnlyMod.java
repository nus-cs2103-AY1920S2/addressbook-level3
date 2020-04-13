package seedu.address.model.mod;

import java.util.List;

/**
 * Unmodifiable view of all modules.
 */

public interface ReadOnlyMod {
    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    List<Mod> getAllMods();
}
