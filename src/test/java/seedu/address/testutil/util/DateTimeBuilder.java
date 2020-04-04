package seedu.address.testutil.util;

import seedu.address.model.transaction.DateTime;

/**
 * A utility class to help with building DateTime objects.
 */
public class DateTimeBuilder {

    public static final String DEFAULT_START_DATE = "2020-01-01 00:00";
    public static final String DEFAULT_END_DATE = "2020-12-12 23:59";
    public static final String DEFAULT_OTHER_START_DATE = "2020-02-02 00:01";
    public static final String DEFAULT_OTHER_END_DATE = "2020-11-30 23:58";

    private DateTime startEndDate;

    public DateTimeBuilder() {
        this.startEndDate = new DateTime(DEFAULT_START_DATE);
    }

    /**
     * Initializes the DateTimeBuilder with the data of {@code startEndDate}.
     */
    public DateTimeBuilder(String startEndDate) {
        this.startEndDate = new DateTime(startEndDate);
    }

    public DateTime build() {
        return startEndDate;
    }
}
