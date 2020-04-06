package seedu.expensela.model;

import java.time.LocalDate;

/**
 * ToggleListOrChart class to toggle between viewing transaction list or chart analytics
 */
public class ToggleView {

    private boolean isViewList = true;
    private String range = LocalDate.now().toString().substring(0, 7);

    public boolean getIsViewList() {
        return this.isViewList;
    }

    /**
     * Currently filter is either ALL or my a certain month. Checks if it's knowledge of filter in month or not.
     * @return
     */
    public boolean isRangeMonth() {
        if (range.equals("ALL")) {
            return false;
        } else {
            return true;
        }
    }

    public void switchIsViewList() {
        this.isViewList = !this.isViewList;
    }

    public void setIsViewList(boolean isViewList) {
        this.isViewList = isViewList;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
