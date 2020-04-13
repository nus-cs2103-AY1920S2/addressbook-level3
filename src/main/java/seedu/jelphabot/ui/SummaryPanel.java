//@@author eedenong
package seedu.jelphabot.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.summary.Summary;

/**
 * The Summary Panel. Provides the basic application layout of the summary of tasks due today.
 */
public class SummaryPanel extends UiPart<Region> {

    private static final String FXML = "SummaryPanel.fxml";

    private static final int PREF_CELL_HEIGHT = 210;
    private final Logger logger = LogsCenter.getLogger(SummaryPanel.class);

    @FXML
    private TabPane mainWindowTabPane;

    @FXML
    private ListView<Summary> summaryPanelListView;

    public SummaryPanel(ObservableList<Summary> summaryList, TabPane tabPane) {
        super(FXML);
        logger.info("Initialising summary panel stage");
        this.mainWindowTabPane = tabPane;
        summaryPanelListView.setItems(summaryList);
        summaryPanelListView.setCellFactory(listView -> new SummaryTaskListViewCell());
    }

    /**
     * Switches to the summary panel.
     */
    public void show() {
        logger.info("Showing summary panel of application");
        mainWindowTabPane.getSelectionModel().select(0);
    }

    /**
     * Returns true if the summary panel is currently being shown.
     * @return
     */
    public boolean isShowing() {
        return mainWindowTabPane.isPressed();
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Summary} using a {@code SummaryCard}.
 */
class SummaryTaskListViewCell extends ListCell<Summary> {
    @Override
    protected void updateItem(Summary summary, boolean empty) {
        super.updateItem(summary, empty);

        if (empty || summary == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new SummaryCard(summary).getRoot());
        }
    }
}
