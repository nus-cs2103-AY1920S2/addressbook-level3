package nasa.ui.activity;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Lesson;
import nasa.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ActivityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    public ActivityListPanel(ObservableList<Activity> activityList) {
        super(FXML);
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
    }

    public void setWidth(double width) {
        activityListView.setPrefWidth(width);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ActivityListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            super.updateItem(activity, empty);
            if (activity instanceof Deadline) {
                setStyle("-fx-background-color: #47D0E0;");
            } else if (activity instanceof Lesson) {
                setStyle("-fx-background-color: #aee4ff;");
            } else if (activity instanceof Event) {
                setStyle("-fx-background-color: #C1BDF1;");
            }
            prefWidthProperty().bind(activityListView.widthProperty().subtract(10));

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (activity instanceof Deadline) {
                    setGraphic(new DeadlineCard((Deadline) activity, getIndex() + 1).getRoot());
                } else if (activity instanceof Event) {
                    setGraphic(new EventCard((Event) activity, getIndex() + 1).getRoot());
                } else if (activity instanceof Lesson) {
                    setGraphic(new LessonCard((Lesson) activity, getIndex() + 1).getRoot());
                }
            }
        }
    }
}
