package seedu.address.model;

import seedu.address.model.dayData.DayData;

import java.util.List;

public interface ReadOnlyStatistics {
    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate
     * persons.
     * @return
     */
    List<DayData> getDayDataList();
}
