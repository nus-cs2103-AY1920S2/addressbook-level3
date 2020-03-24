package seedu.address.testutil.statistics;

import seedu.address.model.statistics.StartEndDate;

/**
 * A utility class to help with building StartEndDate objects.
 */
public class StartEndDateBuilder {

    public static final String DEFAULT_START_DATE = "2020-01-01";
    public static final String DEFAULT_END_DATE = "2020-12-12";
    public static final String DEFAULT_OTHER_START_DATE = "2020-02-02";
    public static final String DEFAULT_OTHER_END_DATE = "2020-11-30";

    private StartEndDate startEndDate;

    public StartEndDateBuilder() {
        this.startEndDate = new StartEndDate(DEFAULT_START_DATE);
    }

    /**
     * Initializes the StartEndDateBuilder with the data of {@code startEndDate}.
     */
    public StartEndDateBuilder(String startEndDate) {
        this.startEndDate = new StartEndDate(startEndDate);
    }

    public StartEndDate build() {
        return startEndDate;
    }
}
