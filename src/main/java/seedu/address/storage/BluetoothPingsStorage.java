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

        fakePings.add(new BluetoothPings(1500000000L, Arrays.asList(11, 2)));
        fakePings.add(new BluetoothPings(1500000100L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(1500000100L, Arrays.asList(3, 5)));
        fakePings.add(new BluetoothPings(1500000200L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(1500000200L, Arrays.asList(4, 19)));
        fakePings.add(new BluetoothPings(1500000200L, Arrays.asList(6, 20)));
        fakePings.add(new BluetoothPings(1500000300L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(1500000400L, Arrays.asList(4, 15)));
        fakePings.add(new BluetoothPings(1500000400L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(1500000400L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(1500000500L, Arrays.asList(12, 10)));
        fakePings.add(new BluetoothPings(1500000500L, Arrays.asList(3, 14)));
        fakePings.add(new BluetoothPings(1500000500L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(1500000400L, Arrays.asList(9, 10)));
        fakePings.add(new BluetoothPings(1500000500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(1500000600L, Arrays.asList(6, 7)));
        fakePings.add(new BluetoothPings(1500000700L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(1500000800L, Arrays.asList(11, 9)));
        fakePings.add(new BluetoothPings(1500001000L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(1500001300L, Arrays.asList(12, 8)));
        fakePings.add(new BluetoothPings(1500001500L, Arrays.asList(5, 8)));
        fakePings.add(new BluetoothPings(1500001700L, Arrays.asList(3, 19)));
        fakePings.add(new BluetoothPings(1500001900L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(1500003000L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(1500003200L, Arrays.asList(2, 9)));
        fakePings.add(new BluetoothPings(1500003500L, Arrays.asList(13, 8)));
        fakePings.add(new BluetoothPings(1500003700L, Arrays.asList(5, 6)));
        fakePings.add(new BluetoothPings(1500003900L, Arrays.asList(14, 10)));
        fakePings.add(new BluetoothPings(1500004500L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500004900L, Arrays.asList(2, 13)));
        fakePings.add(new BluetoothPings(1500005000L, Arrays.asList(14, 7)));
        fakePings.add(new BluetoothPings(1500005500L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500005800L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(1500006000L, Arrays.asList(11, 2)));
        fakePings.add(new BluetoothPings(1500007000L, Arrays.asList(3, 19)));
        fakePings.add(new BluetoothPings(1500007500L, Arrays.asList(15, 7)));
        fakePings.add(new BluetoothPings(1500008000L, Arrays.asList(18, 10)));
        fakePings.add(new BluetoothPings(1500008300L, Arrays.asList(6, 9)));
        fakePings.add(new BluetoothPings(1500008400L, Arrays.asList(2, 14)));
        fakePings.add(new BluetoothPings(1500008600L, Arrays.asList(17, 9)));
        fakePings.add(new BluetoothPings(1500008900L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(1500009000L, Arrays.asList(5, 16)));
        fakePings.add(new BluetoothPings(1500009300L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500009400L, Arrays.asList(9, 20)));
        fakePings.add(new BluetoothPings(1500009500L, Arrays.asList(13, 6)));
        fakePings.add(new BluetoothPings(1500009600L, Arrays.asList(7, 18)));
        fakePings.add(new BluetoothPings(1500010000L, Arrays.asList(2, 7)));
        fakePings.add(new BluetoothPings(1500011000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500011500L, Arrays.asList(11, 8)));
        fakePings.add(new BluetoothPings(1500011600L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(1500012000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500013000L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(1500013500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(1500015000L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(1500017000L, Arrays.asList(1, 12)));
        fakePings.add(new BluetoothPings(1500020100L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(1500023100L, Arrays.asList(3, 5)));
        fakePings.add(new BluetoothPings(1500022200L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(1500025200L, Arrays.asList(4, 19)));
        fakePings.add(new BluetoothPings(1500027200L, Arrays.asList(6, 10)));
        fakePings.add(new BluetoothPings(1500029300L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(1500032400L, Arrays.asList(14, 5)));
        fakePings.add(new BluetoothPings(1500034400L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(1500039400L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(1500043500L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(1500045500L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(1500046500L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(1500049400L, Arrays.asList(19, 10)));
        fakePings.add(new BluetoothPings(1500050500L, Arrays.asList(5, 8)));
        fakePings.add(new BluetoothPings(1500054600L, Arrays.asList(6, 7)));
        fakePings.add(new BluetoothPings(1500055700L, Arrays.asList(8, 19)));
        fakePings.add(new BluetoothPings(1500058800L, Arrays.asList(1, 19)));
        fakePings.add(new BluetoothPings(1500064000L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(1500065300L, Arrays.asList(2, 8)));
        fakePings.add(new BluetoothPings(1500068500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(1500070700L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(1500071900L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(1500074000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500076200L, Arrays.asList(2, 19)));
        fakePings.add(new BluetoothPings(1500078500L, Arrays.asList(3, 8)));
        fakePings.add(new BluetoothPings(1500080700L, Arrays.asList(5, 6)));
        fakePings.add(new BluetoothPings(1500081900L, Arrays.asList(4, 20)));
        fakePings.add(new BluetoothPings(1500083500L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(1500085900L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(1500086000L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(1500089500L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(1500090800L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500091000L, Arrays.asList(11, 2)));
        fakePings.add(new BluetoothPings(1500098000L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(1500100500L, Arrays.asList(5, 17)));
        fakePings.add(new BluetoothPings(1500128000L, Arrays.asList(8, 10)));
        fakePings.add(new BluetoothPings(1500148300L, Arrays.asList(6, 19)));
        fakePings.add(new BluetoothPings(1500168400L, Arrays.asList(2, 4)));
        fakePings.add(new BluetoothPings(1500188600L, Arrays.asList(17, 9)));
        fakePings.add(new BluetoothPings(1500208900L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(1500289000L, Arrays.asList(5, 6)));
        fakePings.add(new BluetoothPings(1500309300L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500359400L, Arrays.asList(9, 10)));
        fakePings.add(new BluetoothPings(1500389500L, Arrays.asList(13, 6)));
        fakePings.add(new BluetoothPings(1500400000L, Arrays.asList(7, 8)));
        fakePings.add(new BluetoothPings(1500412000L, Arrays.asList(4, 19)));
        fakePings.add(new BluetoothPings(1500433000L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(1500443500L, Arrays.asList(5, 18)));
        fakePings.add(new BluetoothPings(1500425000L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(1500457000L, Arrays.asList(1, 12)));
        fakePings.add(new BluetoothPings(1500470100L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500493100L, Arrays.asList(13, 5)));
        fakePings.add(new BluetoothPings(1500502200L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500555200L, Arrays.asList(14, 9)));
        fakePings.add(new BluetoothPings(1500597200L, Arrays.asList(6, 10)));
        fakePings.add(new BluetoothPings(1500609300L, Arrays.asList(2, 3)));
        fakePings.add(new BluetoothPings(1500612400L, Arrays.asList(14, 5)));
        fakePings.add(new BluetoothPings(1500634400L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500659400L, Arrays.asList(4, 17)));
        fakePings.add(new BluetoothPings(1500673500L, Arrays.asList(2, 10)));
        fakePings.add(new BluetoothPings(1500695500L, Arrays.asList(13, 4)));
        fakePings.add(new BluetoothPings(1500706500L, Arrays.asList(2, 7)));
        fakePings.add(new BluetoothPings(1500729400L, Arrays.asList(19, 10)));
        fakePings.add(new BluetoothPings(1500730500L, Arrays.asList(5, 8)));
        fakePings.add(new BluetoothPings(1500744600L, Arrays.asList(16, 7)));
        fakePings.add(new BluetoothPings(1507065700L, Arrays.asList(8, 9)));
        fakePings.add(new BluetoothPings(1500788800L, Arrays.asList(11, 9)));
        fakePings.add(new BluetoothPings(1500794000L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(1500815300L, Arrays.asList(2, 18)));
        fakePings.add(new BluetoothPings(1500838500L, Arrays.asList(15, 8)));
        fakePings.add(new BluetoothPings(1500840700L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(1500851900L, Arrays.asList(12, 7)));
        fakePings.add(new BluetoothPings(1500864000L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500876200L, Arrays.asList(12, 9)));
        fakePings.add(new BluetoothPings(1500878500L, Arrays.asList(3, 8)));
        fakePings.add(new BluetoothPings(1500870700L, Arrays.asList(5, 16)));
        fakePings.add(new BluetoothPings(1500881900L, Arrays.asList(14, 10)));
        fakePings.add(new BluetoothPings(1508073500L, Arrays.asList(6, 8)));
        fakePings.add(new BluetoothPings(1500895900L, Arrays.asList(12, 3)));
        fakePings.add(new BluetoothPings(1500906000L, Arrays.asList(4, 7)));
        fakePings.add(new BluetoothPings(1500909500L, Arrays.asList(6, 18)));
        fakePings.add(new BluetoothPings(1500900800L, Arrays.asList(4, 9)));
        fakePings.add(new BluetoothPings(1500911000L, Arrays.asList(1, 12)));
        fakePings.add(new BluetoothPings(1500918000L, Arrays.asList(3, 9)));
        fakePings.add(new BluetoothPings(1500910500L, Arrays.asList(15, 7)));
        fakePings.add(new BluetoothPings(1500928000L, Arrays.asList(8, 10)));
        fakePings.add(new BluetoothPings(1500928300L, Arrays.asList(16, 9)));
        fakePings.add(new BluetoothPings(1500928400L, Arrays.asList(2, 4)));
        fakePings.add(new BluetoothPings(1500948600L, Arrays.asList(17, 9)));
        fakePings.add(new BluetoothPings(1500948900L, Arrays.asList(3, 4)));
        fakePings.add(new BluetoothPings(1500959000L, Arrays.asList(5, 16)));
        fakePings.add(new BluetoothPings(1500969300L, Arrays.asList(16, 8)));
        fakePings.add(new BluetoothPings(1500999400L, Arrays.asList(9, 10)));
        fakePings.add(new BluetoothPings(1500999500L, Arrays.asList(3, 16)));
        fakePings.add(new BluetoothPings(1500999600L, Arrays.asList(7, 18)));
        return fakePings;
    }

    public void init() {
        this.fakeStorage = this.genFakeData();
    }
}
