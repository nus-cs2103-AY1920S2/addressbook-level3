package nasa.model;

import javafx.collections.ObservableList;
import nasa.model.module.Module;

/**
 * Unmodifiable view of an Nasa book
 */
public interface ReadOnlyNasaBook {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();
}
