package seedu.address.logic.conditions;

import seedu.address.model.bluetooth.BluetoothPings;

public class TimestampConditions implements Conditions<BluetoothPings> {
    private Long start;
    private Long end;

    public TimestampConditions(Long start, Long end) {
        this.start  = start;
        this.end    = end;
    }

    @Override
    public Boolean satisfies(BluetoothPings objToTest) {
        Long timestamp = objToTest.getEpochTs();
        return this.start <= timestamp && timestamp <= this.end;
    }
}
