package seedu.address.storage;

import seedu.address.model.bluetooth.BluetoothPings;

import java.util.ArrayList;
import java.util.Arrays;

public class BluetoothPingsStorage extends InMemoryStorage<BluetoothPings> {
    public BluetoothPingsStorage() {
        super();
        this.init();
    }

    /**
     * TODO: Would be nice if can read from JSON file to initialize fake data
     * @return
     */
    private ArrayList<BluetoothPings> genFakeData() {
        ArrayList<BluetoothPings> fakePings = new ArrayList<BluetoothPings>();
        fakePings.add(new BluetoothPings(15000000L, Arrays.asList(1, 2)));
        fakePings.add(new BluetoothPings(15000100L, Arrays.asList(1, 2)));
        fakePings.add(new BluetoothPings(15000200L, Arrays.asList(1, 2)));
        fakePings.add(new BluetoothPings(15000300L, Arrays.asList(1, 2)));
        fakePings.add(new BluetoothPings(15000400L, Arrays.asList(1, 2)));
        fakePings.add(new BluetoothPings(15000500L, Arrays.asList(1, 2)));
        return fakePings;
    }

    public void init() {
        this.fakeStorage = this.genFakeData();
    }
}
