//@@author fatin99

package tatracker.ui.sessiontab;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.session.Session;
import tatracker.ui.Focusable;
import tatracker.ui.UiPart;

/**
 * Panel containing the list of sessions.
 */
public class SessionListPanel extends UiPart<Region> implements Focusable {
    private static final String NO_FILTER = "No filter";

    private static final String FXML = "SessionListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(SessionListPanel.class);

    @FXML
    private ListView<Session> sessionListView;

    @FXML
    private GridPane currentFiltersGrid;

    @FXML
    private Label currentDateFilters;

    @FXML
    private Label currentTypeFilters;

    @FXML
    private Label currentModuleFilters;

    public SessionListPanel(ObservableList<Session> sessionList) {
        super(FXML);
        logger.fine("Showing Session List");
        sessionListView.setItems(sessionList);
        sessionListView.setCellFactory(listView -> new SessionListViewCell());
        currentDateFilters.setText(NO_FILTER);
        currentModuleFilters.setText(NO_FILTER);
        currentTypeFilters.setText(NO_FILTER);

        sessionListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                sessionListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                sessionListView.setStyle("");
            }
        });
    }

    /**
     * Update Label in order to facilitate changing current filters
     */
    public void updateLabel(String dateFilter, String moduleFilter, String typeFilter) {
        logger.fine("Update session filters label");

        String actualDateFilter = dateFilter;
        if (dateFilter.isBlank()) {
            actualDateFilter = NO_FILTER;
        }

        String actualModuleFilter = moduleFilter;
        if (moduleFilter.isBlank()) {
            actualModuleFilter = NO_FILTER;
        }

        String actualTypeFilter = typeFilter;
        if (typeFilter.isBlank()) {
            actualTypeFilter = NO_FILTER;
        }

        currentDateFilters.setText(actualDateFilter);
        currentModuleFilters.setText(actualModuleFilter);
        currentTypeFilters.setText(actualTypeFilter);
    }

    @Override
    public void requestFocus() {
        sessionListView.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return sessionListView.isFocused();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code SessionCard}.
     */
    class SessionListViewCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);

            if (empty || session == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SessionCard(session, getIndex() + 1).getRoot());
            }
        }
    }
}
