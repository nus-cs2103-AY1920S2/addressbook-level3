package seedu.expensela.ui;

import static seedu.expensela.ui.CategoryLabelMaker.getColouredCategoryLabel;
import static seedu.expensela.ui.DateLabelMaker.getColouredDateLabel;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.Filter;



/**
 * Panel containing the list of transactions.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FilterPanel.class);
    private final Filter filter;

    @FXML
    private FlowPane filterCategory;

    @FXML
    private FlowPane filterDateMonth;

    public FilterPanel(Filter filter) {
        super(FXML);
        this.filter = filter;
        filterCategory.getChildren().add(getColouredCategoryLabel("Category: " + filter.getFilterCategoryName()));
        filterDateMonth.getChildren().add(getColouredDateLabel("Month: " + filter.getDateMonth()));
    }
}

