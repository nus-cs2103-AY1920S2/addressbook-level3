package seedu.address.logic.aggregators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import seedu.address.model.bluetooth.BluetoothPings;
import seedu.address.model.bluetooth.BluetoothPingsSummary;

public class GroupByIDPairsAggregators implements Aggregators<BluetoothPings, BluetoothPingsSummary> {
    @Override
    public ArrayList<BluetoothPingsSummary> collect(ArrayList<BluetoothPings> initialList) {
        ArrayList<BluetoothPingsSummary> pingsSummary = new ArrayList<BluetoothPingsSummary>();

        Map<List<Integer>, Long> collection = initialList.stream()
                                                         .collect(Collectors.groupingBy(
                                                            BluetoothPings::getUserIDs,
                                                            Collectors.counting()
                                                         ));
        collection.forEach(
                (userIdList, threshold) -> {
                    pingsSummary.add(new BluetoothPingsSummary(userIdList, threshold));
        });

        return pingsSummary;
    }
}
