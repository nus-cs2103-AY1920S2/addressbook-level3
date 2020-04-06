package seedu.expensela.model;

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
