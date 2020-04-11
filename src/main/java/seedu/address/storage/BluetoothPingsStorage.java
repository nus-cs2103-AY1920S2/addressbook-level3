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

        fakePings.add(new BluetoothPings(15000000L, Arrays.asList(11, 2)));
        fakePings.add(new BluetoothPings(15000100L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(15000100L, Arrays.asList(3, 5)));
        fakePings.add(new BluetoothPings(15000200L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(15000200L, Arrays.asList(4, 19)));
        fakePings.add(new BluetoothPings(15000200L, Arrays.asList(6, 20)));
        fakePings.add(new BluetoothPings(15000300L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(15000400L, Arrays.asList(4, 15)));
        fakePings.add(new BluetoothPings(15000400L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(15000400L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(15000500L, Arrays.asList(12, 10)));
        fakePings.add(new BluetoothPings(15000500L, Arrays.asList(3, 14)));
        fakePings.add(new BluetoothPings(15000500L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(15000400L, Arrays.asList(9, 10)));
        fakePings.add(new BluetoothPings(15000500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(15000600L, Arrays.asList(6, 7)));
        fakePings.add(new BluetoothPings(15000700L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(15000800L, Arrays.asList(11, 9)));
        fakePings.add(new BluetoothPings(15001000L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(15001300L, Arrays.asList(12, 8)));
        fakePings.add(new BluetoothPings(15001500L, Arrays.asList(5, 8)));
        fakePings.add(new BluetoothPings(15001700L, Arrays.asList(3, 19)));
        fakePings.add(new BluetoothPings(15001900L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(15003000L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(15003200L, Arrays.asList(2, 9)));
        fakePings.add(new BluetoothPings(15003500L, Arrays.asList(13, 8)));
        fakePings.add(new BluetoothPings(15003700L, Arrays.asList(5, 6)));
        fakePings.add(new BluetoothPings(15003900L, Arrays.asList(14, 10)));
        fakePings.add(new BluetoothPings(15004500L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15004900L, Arrays.asList(2, 13)));
        fakePings.add(new BluetoothPings(15005000L, Arrays.asList(14, 7)));
        fakePings.add(new BluetoothPings(15005500L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15005800L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(15006000L, Arrays.asList(11, 2)));
        fakePings.add(new BluetoothPings(15007000L, Arrays.asList(3, 19)));
        fakePings.add(new BluetoothPings(15007500L, Arrays.asList(15, 7)));
        fakePings.add(new BluetoothPings(15008000L, Arrays.asList(18, 10)));
        fakePings.add(new BluetoothPings(15008300L, Arrays.asList(6, 9)));
        fakePings.add(new BluetoothPings(15008400L, Arrays.asList(2, 14)));
        fakePings.add(new BluetoothPings(15008600L, Arrays.asList(17, 9)));
        fakePings.add(new BluetoothPings(15008900L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(15009000L, Arrays.asList(5, 16)));
        fakePings.add(new BluetoothPings(15009300L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15009400L, Arrays.asList(9, 20)));
        fakePings.add(new BluetoothPings(15009500L, Arrays.asList(13, 6)));
        fakePings.add(new BluetoothPings(15009600L, Arrays.asList(7, 18)));
        fakePings.add(new BluetoothPings(15010000L, Arrays.asList(2, 7)));
        fakePings.add(new BluetoothPings(15011000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15011500L, Arrays.asList(11, 8)));
        fakePings.add(new BluetoothPings(15011600L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(15012000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15013000L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(15013500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(15015000L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(15017000L, Arrays.asList(1, 12)));
        fakePings.add(new BluetoothPings(15020100L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(15023100L, Arrays.asList(3, 5)));
        fakePings.add(new BluetoothPings(15022200L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(15025200L, Arrays.asList(4, 19)));
        fakePings.add(new BluetoothPings(15027200L, Arrays.asList(6, 10)));
        fakePings.add(new BluetoothPings(15029300L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(15032400L, Arrays.asList(14, 5)));
        fakePings.add(new BluetoothPings(15034400L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(15039400L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(15043500L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(15045500L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(15046500L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(15049400L, Arrays.asList(19, 10)));
        fakePings.add(new BluetoothPings(15050500L, Arrays.asList(5, 8)));
        fakePings.add(new BluetoothPings(15054600L, Arrays.asList(6, 7)));
        fakePings.add(new BluetoothPings(15055700L, Arrays.asList(8, 19)));
        fakePings.add(new BluetoothPings(15058800L, Arrays.asList(1, 19)));
        fakePings.add(new BluetoothPings(15064000L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(15065300L, Arrays.asList(2, 8)));
        fakePings.add(new BluetoothPings(15068500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(15070700L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(15071900L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(15074000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15076200L, Arrays.asList(2, 19)));
        fakePings.add(new BluetoothPings(15078500L, Arrays.asList(3, 8)));
        fakePings.add(new BluetoothPings(15080700L, Arrays.asList(5, 6)));
        fakePings.add(new BluetoothPings(15081900L, Arrays.asList(4, 20)));
        fakePings.add(new BluetoothPings(15083500L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(15085900L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(15086000L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(15089500L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(15090800L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15091000L, Arrays.asList(11, 2)));
        fakePings.add(new BluetoothPings(15098000L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(15100500L, Arrays.asList(5, 17)));
        fakePings.add(new BluetoothPings(15128000L, Arrays.asList(8, 10)));
        fakePings.add(new BluetoothPings(15148300L, Arrays.asList(6, 19)));
        fakePings.add(new BluetoothPings(15168400L, Arrays.asList(2, 4)));
        fakePings.add(new BluetoothPings(15188600L, Arrays.asList(17, 9)));
        fakePings.add(new BluetoothPings(15208900L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(15289000L, Arrays.asList(5, 6)));
        fakePings.add(new BluetoothPings(15309300L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15359400L, Arrays.asList(9, 10)));
        fakePings.add(new BluetoothPings(15389500L, Arrays.asList(13, 6)));
        fakePings.add(new BluetoothPings(15400000L, Arrays.asList(7, 8)));
        fakePings.add(new BluetoothPings(15412000L, Arrays.asList(4, 19)));
        fakePings.add(new BluetoothPings(15433000L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(15443500L, Arrays.asList(5, 18)));
        fakePings.add(new BluetoothPings(15425000L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(15457000L, Arrays.asList(1, 12)));
        fakePings.add(new BluetoothPings(15470100L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15493100L, Arrays.asList(13, 5)));
        fakePings.add(new BluetoothPings(15502200L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15555200L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(15597200L, Arrays.asList(6, 10)));
        fakePings.add(new BluetoothPings(15609300L, Arrays.asList(2, 3)));
        fakePings.add(new BluetoothPings(15612400L, Arrays.asList(14, 5)));
        fakePings.add(new BluetoothPings(15634400L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15659400L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(15673500L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(15695500L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(15706500L, Arrays.asList(2, 7)));
        fakePings.add(new BluetoothPings(15729400L, Arrays.asList(19, 10)));
        fakePings.add(new BluetoothPings(15730500L, Arrays.asList(5, 8)));
        fakePings.add(new BluetoothPings(15744600L, Arrays.asList(16, 7)));
        fakePings.add(new BluetoothPings(15765700L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(15788800L, Arrays.asList(11, 9)));
        fakePings.add(new BluetoothPings(15794000L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(15815300L, Arrays.asList(2, 18)));
        fakePings.add(new BluetoothPings(15838500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(15840700L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(15851900L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(15864000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15876200L, Arrays.asList(12, 9)));
        fakePings.add(new BluetoothPings(15878500L, Arrays.asList(3, 8)));
        fakePings.add(new BluetoothPings(15870700L, Arrays.asList(5, 16)));
        fakePings.add(new BluetoothPings(15881900L, Arrays.asList(14, 10)));
        fakePings.add(new BluetoothPings(15873500L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(15895900L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(15906000L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(15909500L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(15900800L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(15911000L, Arrays.asList(1, 12)));
        fakePings.add(new BluetoothPings(15918000L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(15910500L, Arrays.asList(15, 7)));
        fakePings.add(new BluetoothPings(15928000L, Arrays.asList(8, 10)));
        fakePings.add(new BluetoothPings(15928300L, Arrays.asList(16, 9)));
        fakePings.add(new BluetoothPings(15928400L, Arrays.asList(2, 4)));
        fakePings.add(new BluetoothPings(15948600L, Arrays.asList(17, 9)));
        fakePings.add(new BluetoothPings(15948900L, Arrays.asList(3, 4)));
        fakePings.add(new BluetoothPings(15959000L, Arrays.asList(5, 16)));
        fakePings.add(new BluetoothPings(15969300L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(15999400L, Arrays.asList(9, 10)));
        fakePings.add(new BluetoothPings(15999500L, Arrays.asList(3, 16)));
        fakePings.add(new BluetoothPings(16000000L, Arrays.asList(7, 18)));
        return fakePings;
    }

    public void init() {
        this.fakeStorage = this.genFakeData();
    }
}
