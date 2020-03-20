package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.listmanagers.activity.Activity;

/**
 * Panel containing the list of activityPanel.
 */
public class ActivityPanel extends UiPart<Region> {

    private static final String FXML = "ActivityPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    public ActivityPanel(ObservableList<Activity> activityList) {
        super(FXML);
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Activity} using a {@code ActivityCard}.
     */
    class ActivityListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActivityCard(activity, getIndex() + 1).getRoot());
            }
        }
    }

}
