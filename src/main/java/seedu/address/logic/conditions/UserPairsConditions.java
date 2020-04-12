package seedu.address.logic.conditions;

import seedu.address.model.bluetooth.BluetoothPings;

public class UserPairsConditions implements Conditions<BluetoothPings> {
    private int USER_A;
    private int USER_B;

    public UserPairsConditions(int USER_A, int USER_B) {
        this.USER_A = USER_A;
        this.USER_B = USER_B;
    }

    @Override
    public Boolean satisfies(BluetoothPings objToTest) {
        return objToTest.getUserIDs().contains(this.USER_A) && objToTest.getUserIDs().contains(this.USER_B);
    }
}
