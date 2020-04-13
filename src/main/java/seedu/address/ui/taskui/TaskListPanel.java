package seedu.address.ui.taskui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private ListView<Task> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListPanel.TaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: transparent;");
            } else {
                setGraphic(new TaskListCard(task, getIndex() + 1).getRoot());
                if (task.isTaskDone()) {
                    setStyle("-fx-background-color: #4BB543; -fx-background-radius: 15px; "
                            + "-fx-background-insets: 3px, 0px; -fx-padding: 10px");
                } else {
                    setStyle("-fx-background-color: #9d6365; -fx-background-radius: 15px; "
                            + "-fx-background-insets: 3px, 0px; -fx-padding: 10px");
                }
            }
        }
    }
}
