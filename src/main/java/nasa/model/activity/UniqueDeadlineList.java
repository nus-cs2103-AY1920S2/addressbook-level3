package nasa.model.activity;

import nasa.commons.core.index.Index;

public class UniqueDeadlineList extends UniqueActivityList<Deadline> {
    public void setSchedule(Name activity, Index type) {
        getActivityList().forEach(x -> ((Deadline) x).setSchedule(type.getZeroBased()));
    }
}
