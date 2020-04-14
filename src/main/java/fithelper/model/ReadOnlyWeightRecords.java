package fithelper.model;

import fithelper.model.weight.Weight;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a log book
 */
public interface ReadOnlyWeightRecords {

    /**
     * Returns an unmodifiable view of the weight list.
     * This list will not contain any duplicate weight records.
     */
    ObservableList<Weight> getWeightList();

}

