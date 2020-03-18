package fithelper.model.today;

import fithelper.model.entry.UniqueEntryList;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the Today Page in the FitHelper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Today {

    private TodayDate todayDate;
   private final UniqueEntryList todayFoodEntries = new UniqueEntryList();
    private final UniqueEntryList todaySportsEntries = new UniqueEntryList();

    /**
     * Every field must be present and not null.
     */
    public Today(TodayDate todayDate) {
        requireAllNonNull(todayDate);
        this.todayDate = todayDate;
    }

    /**
     * Constructor overloading.
     */
    public Today() {
        this.todayDate = new TodayDate();
    }

    public TodayDate getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(TodayDate todayDate) {
        this.todayDate = todayDate;
    }

    public String getTodayDateStr() {
        return this.todayDate.getTodayDateStr();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(todayDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Today Date: ")
                .append(getTodayDate());
        return builder.toString();
    }
}
