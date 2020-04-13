package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * An observable list of RecentEvent objects that allows the
 * list to be displayed and updated instantly on the GUI.
 */
public class RecentEventList {

    public static final int MAX_EVENTS = 5;
    private ObservableList<RecentEvent> recentEventList = FXCollections.observableArrayList();

    /**
     * This method generates the list of the 5 most recent events with a target
     * person or group, from their PlaceList, ActivityList and TimeList.
     *
     * @param pl The target's PlaceList.
     * @param al The target's ActivityList.
     * @param tl The target's TimeList.
     */
    public void generate(PlaceList pl, ActivityList al, TimeList tl) {
        recentEventList.clear();
        int size = pl.getPlaceList().size();
        if (size < MAX_EVENTS) {
            for (int i = size - 1; i >= 0; i--) {
                recentEventList.add(new RecentEvent(
                        Integer.toString(i + 1),
                        pl.getPlaceList().get(i), al.getActivityList().get(i),
                        tl.getTimeList().get(i)));
            }
        } else {
            for (int i = 0; i < MAX_EVENTS; i++) {
                recentEventList.add(new RecentEvent(
                        Integer.toString(i + 1),
                        pl.getPlaceList().get(size - 1 - i),
                        al.getActivityList().get(size - 1 - i), tl.getTimeList().get(size - 1 - i)));
            }
        }
    }

    public ObservableList<RecentEvent> getList() {
        return recentEventList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RecentEventList)) {
            return false;
        }
        return ((RecentEventList) o).getList().equals(this.getList());
    }
}
