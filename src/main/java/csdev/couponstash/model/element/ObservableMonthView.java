package csdev.couponstash.model.element;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;

import csdev.couponstash.commons.util.DateUtil;
import javafx.beans.value.ObservableValueBase;

/**
 * Represents an observable view of a month on the Calendar in the CouponStash with a {@MonthView}.
 */
public class ObservableMonthView extends ObservableValueBase<MonthView> {
    private MonthView monthView = new MonthView(DateUtil.formatYearMonthToString(YearMonth.now()));
    private String value;

    /**
     * Constructs an {@code ObservableMonthView}.
     *
     */
    public ObservableMonthView() {
        this.value = monthView.getValue();
    }

    /**
     * Sets the value of the ObservableMonthView.
     * @param yearMonth Valid YearMonth.
     */
    public void setValue(String yearMonth) {
        requireNonNull(yearMonth);
        this.monthView = new MonthView(yearMonth);
        this.value = monthView.getValue();
        fireValueChangedEvent();
    }

    /**
     * Returns the {@MonthView}of the {@ObservableMonthView}.
     * @return {@MonthView}of the {@ObservableMonthView}
     */
    @Override
    public MonthView getValue() {
        return this.monthView;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ObservableMonthView// instanceof handles nulls
                && value.equals(((ObservableMonthView) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
