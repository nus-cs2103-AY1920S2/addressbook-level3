package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.Filter;

import static seedu.expensela.ui.CategoryLabelMaker.getColouredCategoryLabel;
import static seedu.expensela.ui.DateLabelMaker.getColouredDateLabel;

/**
 * Panel containing the list of transactions.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FilterPanel.class);

    private final Filter filter;

    @FXML
    private Label filterCategoryLabel;

    @FXML
    private Label filterMonthLabel;

    @FXML
    private Label colouredCategoryLabel;

    @FXML
    private FlowPane filterCategory;

    @FXML
    private Label colouredDateLabel;

    @FXML
    private FlowPane filterDateMonth;

    public FilterPanel(Filter filter) {
        super(FXML);
        this.filter = filter;
        filterCategoryLabel.setText("Category: ");
//        Label colouredCategoryLabel = getColouredCategoryLabel(filter.getFilterCategoryName());
        filterCategory.getChildren().add(getColouredCategoryLabel(filter.getFilterCategoryName()));
        filterMonthLabel.setText("Month: ");
//        Label colouredDateLabel = getColouredDateLabel(filter.getDateMonth());
        filterDateMonth.getChildren().add(getColouredDateLabel(filter.getDateMonth()));
    }
}

