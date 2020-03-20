package seedu.address.model;

import java.util.List;

public interface ReadOnlyStatistics {
    public int getMedals();

    public List<String> getPomDurationData();

    public List<String> getTasksDoneData();
}