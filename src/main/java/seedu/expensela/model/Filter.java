package seedu.expensela.model;

import java.time.LocalDate;

/**
 * Filter class to handle category and date filter
 */
public class Filter {

    private String categoryName;
    private String dateMonth;

    public Filter(String categoryName, String dateMonth) {
        this.categoryName = categoryName;
        this.dateMonth = dateMonth;
    }

    public String getFilterCategoryName() {
        if (this.categoryName == null) {
            return "Please Specify Category";
        } else {
            return this.categoryName;
        }
    }

    public void setFilterCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDateMonth() {
        if (this.dateMonth == null) {
            return "Please Specify Date";
        } else {
            return this.dateMonth.toString();
        }
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
}
