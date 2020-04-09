package seedu.address.logic.conditions;

import seedu.address.model.bluetooth.BluetoothPings;

public class UserIDConditions implements Conditions<BluetoothPings> {
    private int userid;

    public UserIDConditions(int userid) {
        this.userid = userid;
    }

    @Override
    public Boolean satisfies(BluetoothPings objToTest) {
        return objToTest.getUserIDs().contains(this.userid);
    }
}
