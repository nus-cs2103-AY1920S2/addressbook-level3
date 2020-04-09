package seedu.expensela.model;

/**
 * ToggleView class to toggle between viewing transaction list or chart analytics
 */
public class ToggleView {

    private boolean isViewList = true;

    public boolean getIsViewList() {
        return this.isViewList;
    }

    public void switchIsViewList() {
        this.isViewList = !this.isViewList;
    }

    public void setIsViewList(boolean isViewList) {
        this.isViewList = isViewList;
    }
}
