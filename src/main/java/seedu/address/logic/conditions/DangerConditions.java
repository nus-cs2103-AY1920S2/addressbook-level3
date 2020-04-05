package seedu.address.logic.conditions;

import seedu.address.model.bluetooth.BluetoothPingsSummary;

public class DangerConditions implements Conditions<BluetoothPingsSummary> {
    private int THRESHOLD;

    public DangerConditions(int threshold) {this.THRESHOLD = threshold;}

    @Override
    public Boolean satisfies(BluetoothPingsSummary objToTest) {
        return objToTest.getCounts() >= this.THRESHOLD;
    }
}
