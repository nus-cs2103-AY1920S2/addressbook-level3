package seedu.expensela.model;

/**
 * ToggleListOrChart class to toggle between viewing transaction list or chart analytics
 */
public class ToggleView {

    private boolean isViewList = true;
    private String range = "ALL";

    public boolean getIsViewList() {
        return this.isViewList;
    }

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
