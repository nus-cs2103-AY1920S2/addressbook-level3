package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * Panel containing the list of groups.
 */
public class SuggestListPanel extends UiPart<Region> {
    private static final String FXML = "SuggestListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SuggestListPanel.class);

    @FXML
    private ListView<Event> suggestListView;

    public SuggestListPanel(ObservableList<Event> eventList) {
        super(FXML);
        suggestListView.setItems(eventList);
        suggestListView.setCellFactory(listView -> new SuggestListCell());
    }

    /**
     * Represents a cell to view the Group in a list.
     */
    class SuggestListCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SuggestCard(event, getIndex() + 1).getRoot());
            }
        }
    }
}


