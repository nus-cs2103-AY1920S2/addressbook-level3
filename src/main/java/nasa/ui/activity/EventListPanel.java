package nasa.ui.activity;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.activity.Event;
import nasa.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private Label label;
    @FXML
    private ListView<Event> eventListView;

    public EventListPanel(ObservableList<Event> eventList) {
        super(FXML);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        eventListView.setMaxHeight(1);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            prefWidthProperty().bind(eventListView.widthProperty().subtract(10));
            eventListView.setMaxHeight((eventListView.getItems().size() + 1) * 75);

            if (empty || event == null) {
                setStyle("");
                setGraphic(null);
                setText(null);
            } else {
                if (!event.isOver()) {
                    setStyle("-fx-background-color: #aee4ff;");
                } else {
                    setStyle("-fx-background-color: #FFB2AE;");
                }
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }
}
