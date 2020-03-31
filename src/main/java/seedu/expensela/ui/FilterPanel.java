package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label filterTypeLabel;

    @FXML
    private Label filterNameLabel;

    public FilterPanel(Filter filter) {
        super(FXML);
        this.filter = filter;
        filterTypeLabel.setText("Category: " + filter.getFilterCategoryName());
        filterNameLabel.setText("Month: " + filter.getDateMonth());
    }
}

