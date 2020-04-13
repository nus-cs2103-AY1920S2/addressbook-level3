package seedu.jelphabot.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.task.tasklist.GroupedTaskList;
import seedu.jelphabot.model.task.tasklist.SubgroupTaskList;

/**
 * Panel containing the list of tasks.
 * Tasks are further sorted into subgroups
 *
 * @@author yaojiethng
 */
public class GroupedTaskListPanel extends UiPart<Region> {

    private static final String FXML = "GroupedTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    private final ObservableList<SubgroupTaskList> subLists;
    private final GroupedTaskList groupedTaskList;

    @javafx.fxml.FXML
    private ListView<SubgroupTaskList> taskListGroups;

    public GroupedTaskListPanel(GroupedTaskList groupedTaskList) {
        super(FXML);
        this.groupedTaskList = groupedTaskList;
        this.subLists = groupedTaskList.getSublists();
        taskListGroups.setCellFactory(viewCell -> new GroupedTaskListViewCell());
        taskListGroups.setItems(subLists);
        logger.log(Level.INFO,
            String.format("Initialized %s panel with %d categories and %d tasks.", groupedTaskList.getCategory(),
                groupedTaskList.getSublists().size(), groupedTaskList.size()
            )
        );
    }

    public GroupedTaskList.Category getCategory() {
        return groupedTaskList.getCategory();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code GroupedTaskCard}.
     */
    static class GroupedTaskListViewCell extends ListCell<SubgroupTaskList> {
        @Override
        protected void updateItem(SubgroupTaskList task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SubgroupTaskListPanel(task, task.startIndexBinding()).getRoot());
            }
        }
    }
}
