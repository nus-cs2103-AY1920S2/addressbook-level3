//@@author yapdianhao
package seedu.jelphabot.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.reminder.ReminderShowsTask;

/**
 * The Reminder List Panel. Provides the basic application layout of productivity of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {

    private static final String FXML = "ReminderListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TabPane mainWindowTabPane;

    @FXML
    private ListView<ReminderShowsTask> reminderListView;

    public ReminderListPanel(ObservableList<ReminderShowsTask> reminderShowsTaskList, TabPane tabPane) {
        super(FXML);
        this.mainWindowTabPane = tabPane;
        reminderListView.setItems(reminderShowsTaskList);
        reminderListView.setCellFactory(listView -> new ReminderListPanel.ReminderListViewCell());
    }

    /**
     * Returns true if the productivity panel is currently being shown.
     */
    public boolean isShowing() {
        return mainWindowTabPane.isPressed();
    }

    /**
     * Shows the reminderList panel.
     */
    public void show() {
        mainWindowTabPane.getSelectionModel().select(4);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    static class ReminderListViewCell extends ListCell<ReminderShowsTask> {
        @Override
        protected void updateItem(ReminderShowsTask reminderShowsTask, boolean empty) {
            super.updateItem(reminderShowsTask, empty);

            if (empty || reminderShowsTask == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminderShowsTask).getRoot());
            }
        }
    }
}
