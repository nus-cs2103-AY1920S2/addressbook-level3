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
import tatracker.ui.sessiontab.SessionCard;

/**
 * Panel containing the list of sessions.
 */
public class ClaimsListPanel extends UiPart<Region> {
    private static final String FXML = "ClaimsListPanel.fxml";
    private static final String BACKGROUND_COLOUR = "#5f4d42";
    private static final String BORDER_COLOUR = "#917b3e";
    private static final String LABEL_BACKGROUND_COLOUR = "#424d5f";
    private static final String LABEL_BORDER_COLOUR = "#3e7b91";
    private static final String BORDER_WIDTH = "1";
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
        totalEarnings.setStyle("-fx-background-color: " + LABEL_BACKGROUND_COLOUR + "; "
                + "-fx-border-color: " + LABEL_BORDER_COLOUR + "; "
                + "-fx-border-width: " + BORDER_WIDTH + ";");
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
                setStyle("");
            } else {
                setGraphic(new SessionCard(claims, getIndex() + 1).getRoot());
                if ((getCurrentlyShownModuleClaim() != null)) {
                    setStyle("-fx-background-color: " + BACKGROUND_COLOUR + "; "
                            + "-fx-border-color: " + BORDER_COLOUR + "; "
                            + "-fx-border-width: " + BORDER_WIDTH + ";");
                } else {
                    setStyle("");
                }
            }
        }
    }
}
