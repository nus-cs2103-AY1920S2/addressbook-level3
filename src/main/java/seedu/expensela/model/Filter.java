package seedu.expensela.model;

import java.time.LocalDate;

public class Filter {

    private String categoryName;
    private LocalDate dateMonth;

    public Filter(String categoryName, LocalDate dateMonth) {
        this.categoryName = categoryName;
        this.dateMonth = dateMonth;
    }

    public String getFilterCategoryName() {
        return this.categoryName;
    }

    public void setFilterCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDate getDateMonth() {
        return this.dateMonth;
    }

    public void setDateMonth(LocalDate dateMonth) {
        this.dateMonth = dateMonth;
    }
}
