package seedu.jelphabot.model.task.tasklist;

import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;

/**
 * Wrapper class for a ObservableList of pinned Tasks.
 *
 * @@author yaojiethng
 */
public class PinnedTaskList extends SubgroupTaskList {
    public PinnedTaskList(ObservableList<Task> taskList, IntegerBinding startIndex) {
        super("Pinned", taskList, startIndex);
    }

}
