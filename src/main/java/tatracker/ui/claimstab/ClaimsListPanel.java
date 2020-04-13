//@@author fatin99

package tatracker.ui.claimstab;

import static tatracker.model.TaTracker.getCurrentlyShownModuleClaim;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.session.Session;
import tatracker.ui.UiPart;

/**
 * Panel containing the list of done sessions.
 */
public class ClaimsListPanel extends UiPart<Region> {
    private static final String FXML = "ClaimsListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(ClaimsListPanel.class);

    @FXML
    private ListView<Session> claimsListView;

    @FXML
    private Label totalEarnings;

    private final ReadOnlyTaTracker taTracker;

    public ClaimsListPanel(ObservableList<Session> claimsList, ReadOnlyTaTracker taTracker) {
        super(FXML);
        this.taTracker = taTracker;

        logger.fine("Showing Claims List");
        claimsListView.setItems(claimsList);
        claimsListView.setCellFactory(listView -> new ClaimsListViewCell());
        totalEarnings.setText("Total Earnings: " + this.taTracker.getTotalEarnings());

        claimsListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                claimsListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                claimsListView.setStyle("");
            }
        });
    }

    /**
     * Update Label in order to facilitate incrementing total earnings
     */
    public void updateLabel() {
        logger.fine("Update label: Total Earning");
        totalEarnings.setText("Total Earnings: " + taTracker.getTotalEarnings());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a done {@code Session} using a {@code ClaimsCard}.
     */
    class ClaimsListViewCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session claim, boolean empty) {
            super.updateItem(claim, empty);
            getStyleClass().removeAll("filtered", "list-cell");

            if (empty || claim == null) {
                setGraphic(null);
                setText(null);
                getStyleClass().add("list-cell");
                setStyle("");
            } else {
                setGraphic(new ClaimsCard(claim, getIndex() + 1).getRoot());
                if ((getCurrentlyShownModuleClaim() != null)) {
                    getStyleClass().add("filtered");
                } else {
                    getStyleClass().add("list-cell");
                    setStyle("");
                }
            }
        }
    }
}
