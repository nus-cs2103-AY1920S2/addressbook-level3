//@@author Clouddoggo

package seedu.jelphabot.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.productivity.Productivity;

/**
 * The Productivity Panel. Provides the basic application layout of productivity of tasks.
 */
public class ProductivityPanel extends UiPart<Region> {

    private static final String FXML = "ProductivityPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProductivityPanel.class);

    @FXML
    private TabPane mainWindowTabPane;
    @FXML
    private ListView<Productivity> productivityListView;

    public ProductivityPanel(ObservableList<Productivity> productivityList, TabPane tabPane) {
        super(FXML);
        logger.info("Initialising productivity panel stage");
        this.mainWindowTabPane = tabPane;
        productivityListView.setItems(productivityList);
        productivityListView.setCellFactory(listView -> new ProductivityListViewCell());
    }

    /**
     * Swtiches to the productivity panel.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing productivity panel of application.");
        mainWindowTabPane.getSelectionModel().select(3);
    }

    /**
     * Returns true if the productivity panel is currently being shown.
     */
    public boolean isShowing() {
        return mainWindowTabPane.isPressed();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Productivity} using a {@code ProductivityCard}.
     */
    static class ProductivityListViewCell extends ListCell<Productivity> {
        @Override
        protected void updateItem(Productivity productivity, boolean empty) {
            super.updateItem(productivity, empty);

            if (empty || productivity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProductivityCard(productivity).getRoot());
            }
        }
    }
}
