package seedu.jelphabot.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.task.Task;

//@@author alam8064
/**
 * Panel containing the list of tasks.
 */
public class CalendarTaskListPanel extends UiPart<Region> {

    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<Task> taskListView;

    public CalendarTaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    static class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CalendarTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}

