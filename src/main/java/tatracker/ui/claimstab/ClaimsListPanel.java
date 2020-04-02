package tatracker.ui.claimstab;

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
import tatracker.ui.sessiontab.SessionCard;

/**
 * Panel containing the list of sessions.
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

        claimsListView.setItems(claimsList);
        claimsListView.setCellFactory(listView -> new ClaimsListViewCell());
        totalEarnings.setText("Total Earnings: " + this.taTracker.getTotalEarnings());
    }

    /**
     * Update Label in order to facilitate incrementing total earnings
     */
    public void updateLabel() {
        totalEarnings.setText("Total Earnings: " + taTracker.getTotalEarnings());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code ClaimsCard}.
     */
    class ClaimsListViewCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session claims, boolean empty) {
            super.updateItem(claims, empty);

            if (empty || claims == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SessionCard(claims, getIndex() + 1).getRoot());
            }
        }
    }
}
