package seedu.address.testutil.transaction;

import java.time.LocalDateTime;

import seedu.address.model.transaction.DateTime;

/**
 * A utility class to help with building DateTime objects.
 */
public class DateTimeBuilder {

    public static final String DEFAULT_START_DATE = "2020-01-01 00:00";
    public static final String DEFAULT_END_DATE = "2020-12-12 23:59";
    public static final String DEFAULT_OTHER_START_DATE = "2020-02-02 00:01";
    public static final String DEFAULT_OTHER_END_DATE = "2020-11-30 23:58";

    private LocalDateTime dateTime;

    public DateTimeBuilder() {
        this.dateTime = LocalDateTime.parse(DEFAULT_START_DATE, DateTime.DATE_TIME_FORMAT);
    }

    /**
     * Initializes the DateTimeBuilder with the data of {@code startEndDate}.
     */
    public DateTimeBuilder(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, DateTime.DATE_TIME_FORMAT);
    }

    /**
     * Initializes the DateTimeBuilder with the data of {@code dateTimeToCopy}.
     */
    public DateTimeBuilder(DateTime dateTimeToCopy) {
        dateTime = dateTimeToCopy.value;
    }

    public DateTime build() {
        return new DateTime(dateTime);
    }
}
