package seedu.expensela.model;

public class Filter {


    /**
     *
     */

    // the type of filter applied e.g. filter by category/filter by date
    private static String filterType;

    // the name of what the user wants to filter by e.g. for filterType = category, filterName = food
    private static String filterName;

    public String getFilterType() {
        return this.filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterName() {
        return this.filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}
