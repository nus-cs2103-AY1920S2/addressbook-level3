package seedu.jelphabot.model.task.tasklist;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.exceptions.DuplicateTaskException;
import seedu.jelphabot.model.task.exceptions.TaskNotFoundException;

/**
 * Interface representing a TaskList which is split into sub-groups by predefined groups.
 * Each 'GroupedTaskList' is a container for `ObservableList&lt;Task> objects,
 * each containing a unique filter over the full task list.
 * Classes which extend GroupedTaskList are expected to provide a getter method for each grouping defined.
 *
 * @@author yaojiethng
 */
public abstract class GroupedTaskList implements ViewTaskList {

    protected final PinnedTaskList pinnedTaskList;
    /**
     * underlying task list
     */
    protected final ObservableList<Task> tasks;
    protected final ObservableList<SubgroupTaskList> subLists = FXCollections.observableArrayList();

    protected GroupedTaskList(ObservableList<Task> tasks, PinnedTaskList pinnedTaskList) {
        requireAllNonNull(tasks);
        requireNonNull(pinnedTaskList);
        this.tasks = tasks;
        this.pinnedTaskList = pinnedTaskList;
    }

    public static GroupedTaskList makeGroupedTaskList(ObservableList<Task> tasks, Category category,
        PinnedTaskList pinnedTasks) {
        return category.construct(tasks, pinnedTasks);
    }

    public abstract Category getCategory();

    @Override
    public Task get(int id) throws TaskNotFoundException {
        assert id < size();
        for (SubgroupTaskList sublist : subLists) {
            if (id < sublist.size()) {
                return sublist.get(id);
            } else {
                id -= sublist.size();
            }
        }
        throw new TaskNotFoundException();
    }

    @Override
    public Task get(Index index) {
        return get(index.getZeroBased());
    }

    @Override
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return tasks.stream().anyMatch(toCheck::isSameTask);
    }

    @Override
    public int size() {
        return tasks.size();
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        List<Task> tasks = new ArrayList<>();
        for (SubgroupTaskList sublist : subLists) {
            tasks.addAll(sublist.getList());
        }
        return tasks.iterator();
    }

    public abstract ObservableList<SubgroupTaskList> getSublists();

    /**
     * Gets an IntegerBinding for the next Sublist in this GroupedTaskList.
     */
    protected IntegerBinding subsequentElementStartIndex() {
        if (subLists.isEmpty()) {
            return Bindings.createIntegerBinding(() -> 0);
        } else {
            SubgroupTaskList lastSublist = subLists.get(subLists.size() - 1);
            return (IntegerBinding) lastSublist.startIndexBinding().add(lastSublist.sizeBinding());
        }
    }

    /* === Methods used for testing. Application classes should not call these methods as Tasks are intended
        to be modified through UniqueTaskList. === */

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    protected void add(Task toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        tasks.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    protected void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = tasks.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        tasks.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    protected void remove(Task target) {
        requireNonNull(target);
        if (!contains(target)) {
            throw new TaskNotFoundException();
        }
        tasks.remove(target);
    }

    /**
     * Removes all tasks from the list.
     */
    protected void clear() {
        this.pinnedTaskList.clear();
        this.tasks.clear();
        this.subLists.clear();
    }

    /**
     * Sets the inner moduleCodeTaskLists according to the contents of taskList.
     * Since taskList is retrieved from UniqueTaskList, it is assumed that there are no duplicate tasks.
     *
     * @param taskList the updated list of tasks.
     */
    protected abstract void setTasks(List<Task> taskList);

    protected void setTasks(GroupedTaskList replacement) {
        requireNonNull(replacement);
        subLists.setAll(replacement.getSublists());
        setTasks(replacement.tasks);
    }
    // === End of Methods used for testing ===

    /**
     * Category defines a set of fixed enum mappings from the commandArgument to the corresponding
     * constructor.
     */
    public enum Category {
        DATE("date", GroupedByDateTaskList::new),
        MODULE("module", GroupedByModuleTaskList::new);

        public final String commandArgument;
        private final BiFunction<ObservableList<Task>, PinnedTaskList, GroupedTaskList> constructor;

        Category(String commandArgument,
            BiFunction<ObservableList<Task>, PinnedTaskList, GroupedTaskList> groupedTaskListConstructor) {
            this.commandArgument = commandArgument;
            this.constructor = groupedTaskListConstructor;
        }

        private GroupedTaskList construct(ObservableList<Task> tasks, PinnedTaskList pinnedTasks) {
            return constructor.apply(tasks, pinnedTasks);
        }
    }
}
