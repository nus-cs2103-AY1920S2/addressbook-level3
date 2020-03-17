package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.conditions.Conditions;

/**
 * Contact tracing application logic rendering
 */
public interface AppLogic<T> {
    /**
     * Fetches all pings stored in model
     * @return
     */
    public ObservableList<T> getPings();

    /**
     * Same as {@code AppLogic} but returns pings on filter condition
     * @return
     */
    public ObservableList<T> filterPings(Conditions<T> cond);
}
