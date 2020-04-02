package seedu.address.testutil;

import seedu.address.model.Statistics;
import seedu.address.model.dayData.DayData;

/** A utility class to help with building Statistics objects. */
public class StatisticsBuilder {

    private Statistics statistics;

    public StatisticsBuilder() {
        statistics = new Statistics();
    }

    public StatisticsBuilder(Statistics statistics) {
        this.statistics = statistics;
    }

    /** Adds a new {@code DayData} to the {@code Statistics} that we are building. */
    public StatisticsBuilder withDayData(DayData dayData) {
        statistics.addDayData(dayData);
        return this;
    }

    public Statistics build() {
        return statistics;
    }
}
