package seedu.expensela.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;
import seedu.expensela.model.transaction.Transaction;

/**
 * Filter class to handle category and date filter
 */
public class Filter {

    private Predicate<Transaction> categoryName;
    private Predicate<Transaction> dateMonth;

    public Filter(Predicate<Transaction> categoryName, Predicate<Transaction> dateMonth) {
        if (categoryName == null) {
            this.categoryName = new CategoryEqualsKeywordPredicate(Arrays.asList("ALL"));
        } else {
            this.categoryName = categoryName;
        }
        if (dateMonth == null) {
            this.dateMonth = new DateEqualsKeywordPredicate(Arrays.asList("ALL"));
        } else {
            this.dateMonth = dateMonth;
        }
    }

    public String getFilterCategoryName() {
        if (this.categoryName == null) {
            return "ALL";
        } else {
            return this.categoryName.toString();
        }
    }

    public Predicate<Transaction> getCategoryNamePredicate() {
        return categoryName;
    }

    public Predicate<Transaction> getDateMonthPredicate() {
        return dateMonth;
    }

    public void setFilterCategoryName(Predicate<Transaction> categoryName) {
        this.categoryName = categoryName;
    }

    public String getDateMonth() {
        if (this.dateMonth == null) {
            return "ALL";
        } else {
            return this.dateMonth.toString();
        }
    }

    public String getDateMonthText() {
        String dateMonthDigits;
        if (this.dateMonth == null) {
            return "ALL";
        } else {
            dateMonthDigits = this.dateMonth.toString();            // e.g. "2020-04"
            String month = dateMonthDigits.split("-")[1];
            String year = dateMonthDigits.split("-")[0];
            switch (month) {
                case ("01"):
                    return "Jan " + year;
                case ("02"):
                    return "Feb " + year;
                case ("03"):
                    return "Mar " + year;
                case ("04"):
                    return "Apr " + year;
                case ("05"):
                    return "May " + year;
                case ("06"):
                    return "Jun " + year;
                case ("07"):
                    return "Jul " + year;
                case ("08"):
                    return "Aug " + year;
                case ("09"):
                    return "Sep " + year;
                case ("10"):
                    return "Oct " + year;
                case ("11"):
                    return "Nov " + year;
                case ("12"):
                    return "Dec " + year;
                default:
                    return "";

            }
        }
    }

    public void setDateMonth(Predicate<Transaction> dateMonth) {
        this.dateMonth = dateMonth;
    }

    /**
     * Checks if filter is currently by month or not
     * @return true is currently filtered by month, else false
     */
    public boolean isFilterMonth() {
        if (getDateMonth().equals("ALL")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Filter)) {
            return false;
        }
        return ((Filter) other).categoryName.equals(this.categoryName)
                && ((Filter) other).dateMonth.equals(this.dateMonth);
    }

    @Override
    public String toString() {
        return "Category filter: " + categoryName.toString() + "\n Date filter: " + dateMonth;
    }
}
