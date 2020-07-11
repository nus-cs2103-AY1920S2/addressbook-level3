package csdev.couponstash.model.element;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.YearMonth;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Represents a view of a month on the Calendar in the CouponStash with a {@YearMonth}.
 */
public class MonthView {
    public static final String MESSAGE_CONSTRAINTS = "Year Month should be a date in the M-YYYY format.";

    private YearMonth yearMonth;
    private String value;

    /**
     * Constructs a {@code MonthView}.
     *
     * @param yearMonthString YearMonth String.
     */
    public MonthView(String yearMonthString) {
        requireNonNull(yearMonthString);
        checkArgument(DateUtil.isValidYearMonth(yearMonthString), MESSAGE_CONSTRAINTS);

        this.yearMonth = DateUtil.parseStringToYearMonth(yearMonthString);
        this.value = yearMonthString;
    }

    /**
     * Returns the yearMonth in YearMonth format.
     * @return The yearMonth of {@MonthView} in {@YearMonth}.
     */
    public YearMonth getYearMonth() {
        return this.yearMonth;
    }

    /**
     * Returns the yearMonth in String format.
     * @return The yearMonth of {@MonthView} in {@String}.
     */
    public String getValue() {
        return this.value;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthView// instanceof handles nulls
                && value.equals(((MonthView) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
