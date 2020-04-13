package seedu.jelphabot.model.task.tasklist;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;

/**
 * Wrapper class for a ObservableList Tasks.
 *
 * @@author yaojiethng
 */
public class SubgroupTaskList {
    private final String groupName;
    private final ObservableList<Task> tasks;
    private final IntegerBinding size;
    private final IntegerBinding startIndex;

    SubgroupTaskList(String groupName, ObservableList<Task> tasks, IntegerBinding startIndex) {
        this.groupName = groupName;
        this.tasks = tasks;
        this.size = Bindings.size(tasks);
        this.startIndex = startIndex;
    }

    public ObservableList<Task> getList() {
        return tasks;
    }

    public String getGroupName() {
        return groupName;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return size.intValue();
    }

    public void clear() {
        tasks.clear();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public IntegerBinding sizeBinding() {
        return size;
    }

    public IntegerBinding startIndexBinding() {
        return startIndex;
    }
}
