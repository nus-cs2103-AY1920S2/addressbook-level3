package seedu.address.model.bluetooth;

import java.util.List;

public class BluetoothPingsSummary {
    private List<Integer> userIDs;
    private Long counts;

    public BluetoothPingsSummary(List<Integer> userPairs, Long counts) {
        this.userIDs = userPairs;
        this.counts = counts;
    }

    public List<Integer> getUserIDs() {
        return this.userIDs;
    }

    public Long getCounts() {
        return this.counts;
    }
}
