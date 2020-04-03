package seedu.address.logic.conditions;

import seedu.address.model.bluetooth.BluetoothPings;

public class DangerConditions implements Conditions<BluetoothPings> {
    private int THRESHOLD;

    public DangerConditions(int threshold) {this.THRESHOLD = threshold;}

    @Override
    public Boolean satisfies(BluetoothPings objToTest) {
        return null;
    }
}
