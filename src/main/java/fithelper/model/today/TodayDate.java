package fithelper.model.today;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents Today's Date in FitHelper Today Page.
 */
public class TodayDate {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDate todayDate;
    private String todayDateStr;

    public TodayDate() {
        this.todayDate = LocalDate.now();
        this.todayDateStr = dtf.format(todayDate);
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public String getTodayDateStr() {
        return todayDateStr;
    }

    public void setTodayDateStr(String todayDateStr) {
        this.todayDateStr = todayDateStr;
    }

    @Override
    public String toString() {
        return this.todayDateStr;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodayDate // instanceof handles nulls
                && this.todayDateStr.equals(((TodayDate) other).getTodayDateStr())); // state check
    }

    @Override
    public int hashCode() {
        return this.todayDate.hashCode();
    }
}
