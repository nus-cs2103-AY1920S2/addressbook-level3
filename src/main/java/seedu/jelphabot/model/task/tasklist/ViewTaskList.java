package seedu.jelphabot.model.task.tasklist;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.task.Task;

/**
 * Represents a wrapper class for a list of tasks that are displayed in Ui.
 *
 * @@author yaojiethng
 */
public interface ViewTaskList extends Iterable<Task> {
    Task get(int id);

    Task get(Index index);

    int size();

    boolean isEmpty();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    boolean contains(Task task);
}
