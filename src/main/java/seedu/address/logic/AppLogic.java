package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.filters.Filters;
import seedu.address.model.bluetooth.BluetoothPings;

/**
 * Contact tracing application logic rendering
 */
public interface AppLogic {
    /**
     * Fetches all pings stored in model
     * @return
     */
    public ObservableList<BluetoothPings> getPings();

    /**
     * Same as {@code AppLogic} but returns pings on filter condition
     * @return
     */
    public ObservableList<BluetoothPings> filterPings(Filters filter);
}
