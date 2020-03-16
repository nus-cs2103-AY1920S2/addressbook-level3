package seedu.address.model.bluetooth;

import java.util.List;

public class BluetoothPings {
    private Long epochTs;
    private List<Integer> userIDs;

    public BluetoothPings(Long epochTs, List<Integer> userPairs) {
        this.epochTs = epochTs;
        this.userIDs = userPairs;
    }

    public List<Integer> getUserIDs() {
        return this.userIDs;
    }

    public Long getEpochTs() {
        return this.epochTs;
    }
}
