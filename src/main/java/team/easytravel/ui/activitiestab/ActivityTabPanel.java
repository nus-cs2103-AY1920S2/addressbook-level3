package team.easytravel.ui.activitiestab;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.ui.TabPanel;

/**
 * Panel containing the list of activityPanel.
 */
public class ActivityTabPanel extends TabPanel {
    public static final String TAB_NAME = "Activities Tab";

    private static final String FXML = "activitiestab/ActivityTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityTabPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    public ActivityTabPanel(ObservableList<Activity> activityList) {
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
            super.updateItem(activity, empty);
            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActivityCard(activity, getIndex() + 1).getRoot());
            }
        }
    }

}
