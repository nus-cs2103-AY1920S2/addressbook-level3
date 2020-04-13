package seedu.jelphabot.ui;

import java.util.logging.Logger;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.SubgroupTaskList;

/**
 * Panel containing one grouped list of tasks.
 * Tasks are further sorted into pinned, dueToday, dueThisWeek, dueSomeday
 *
 * @@author yaojiethng
 */
public class SubgroupTaskListPanel extends UiPart<Region> {

    private static final int PREF_CELL_HEIGHT = 110;
    private static final String FXML = "SubgroupTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    private final SubgroupTaskList subGroupTaskList;
    private final NumberBinding startIndex;

    @FXML
    private TitledPane category;

    @FXML
    private ListView<Task> groupingList;

    public SubgroupTaskListPanel(SubgroupTaskList subGroupTaskList, NumberBinding startIndex) {
        super(FXML);
        this.subGroupTaskList = subGroupTaskList;
        this.startIndex = startIndex;
        setCategoryTitle(subGroupTaskList.getGroupName(), subGroupTaskList.sizeBinding());
        groupingList.setCellFactory(viewCell -> new SubgroupTaskListViewCell(startIndex));
        groupingList.prefHeightProperty().bind(subGroupTaskList.sizeBinding().multiply(PREF_CELL_HEIGHT));
        groupingList.setItems(subGroupTaskList.getList());
    }

    public NumberBinding getLastElementIndex() {
        return startIndex.add(subGroupTaskList.sizeBinding());
    }

    public void setCategoryTitle(String groupName, IntegerBinding totalSize) {
        category.textProperty().bind(totalSize.asString(groupName + " [%d]"));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code GroupedTaskCard}.
     */
    static class SubgroupTaskListViewCell extends ListCell<Task> {
        private NumberBinding startIndex;

        SubgroupTaskListViewCell(NumberBinding startIndex) {
            this.startIndex = startIndex;
        }

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(
                    task,
                    startIndex.add(getIndex() + 1)
                ).getRoot());
            }
        }
    }
}

