package nasa.ui.activity;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.activity.Deadline;
import nasa.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class DeadlineListPanel extends UiPart<Region> {
    private static final String FXML = "DeadlineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);

    @FXML
    private Label label;
    @FXML
    private ListView<Deadline> deadlineListView;

    public DeadlineListPanel(ObservableList<Deadline> deadlineList) {
        super(FXML);
        deadlineListView.setItems(deadlineList);
        deadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
        deadlineListView.setMaxHeight(deadlineList.size() * 50 + 1);
    }

    public void setWidth(double width) {
        deadlineListView.setPrefWidth(width);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class DeadlineListViewCell extends ListCell<Deadline> {
        @Override
        protected void updateItem(Deadline deadline, boolean empty) {
            super.updateItem(deadline, empty);

            prefWidthProperty().bind(deadlineListView.widthProperty().subtract(10));
            deadlineListView.setMaxHeight((deadlineListView.getItems().size() + 1) * 100);

            if (empty || deadline == null) {
                setStyle("");
                setGraphic(null);
                setText(null);
            } else {
                setStyle("-fx-background-color: #47D0E0;");
                setGraphic(new DeadlineCard(deadline, getIndex() + 1).getRoot());
            }
        }
    }
}
