package nasa.model;

import javafx.collections.ObservableList;
import nasa.model.history.ModuleListHistory;
import nasa.model.module.Module;
import nasa.model.module.UniqueModuleList;

public interface ReadOnlyHistory<T> {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<T> getModuleListHistory();
}
