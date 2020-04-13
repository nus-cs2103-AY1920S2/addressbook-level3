package seedu.jelphabot.model.task.tasklist;

import static seedu.jelphabot.commons.util.DateUtil.getDueSomedayPredicate;
import static seedu.jelphabot.commons.util.DateUtil.getDueThisWeekPredicate;
import static seedu.jelphabot.commons.util.DateUtil.getDueTodayPredicate;
import static seedu.jelphabot.commons.util.DateUtil.getOverduePredicate;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;

/**
 * A container for ObservableList&lt;Task&gt; that splits the TaskList into groups.
 * GroupedByDateTaskList groups Tasks by how close the due date is to the current date.
 * Separation is done over @code{ObservableList} through use of filters.
 * <p>
 *
 * @@author yaojiethng
 */
public class GroupedByDateTaskList extends GroupedTaskList {

    private static final Predicate<Task> isOverdue = getOverduePredicate();
    private static final Predicate<Task> isDueToday = getDueTodayPredicate();
    private static final Predicate<Task> isDueThisWeek = getDueThisWeekPredicate();
    private static final Predicate<Task> isDueSomeday = getDueSomedayPredicate();

    public GroupedByDateTaskList(ObservableList<Task> tasks, PinnedTaskList pinnedTaskList) {
        super(tasks, pinnedTaskList);
        addSublist("Overdue", isOverdue);
        addSublist("Due Today", isDueToday);
        addSublist("Due This Week", isDueThisWeek);
        addSublist("Due Someday", isDueSomeday);
    }

    protected GroupedByDateTaskList(PinnedTaskList pinnedTaskList) {
        super(FXCollections.observableArrayList(), pinnedTaskList);
    }

    @Override
    public Category getCategory() {
        return Category.DATE;
    }

    /**
     * Adds a new sublist.
     * The sublist must not already exist in the list.
     */
    private void addSublist(String title, Predicate<Task> predicate) {
        this.subLists.add(
            new SubgroupTaskList(title, tasks.filtered(predicate), subsequentElementStartIndex()));
    }

    @Override
    public ObservableList<SubgroupTaskList> getSublists() {
        return subLists;
    }

    /* === Methods used for testing. Application classes should not call these methods as Tasks are intended
    to be modified through UniqueTaskList. === */
    @Override
    protected void setTasks(List<Task> taskList) {
        this.tasks.setAll(taskList);
        this.subLists.clear();
        addSublist("Overdue", isOverdue);
        addSublist("Due Today", isDueToday);
        addSublist("Due This Week", isDueThisWeek);
        addSublist("Due Someday", isDueSomeday);
    }
    // === End of Methods used for testing ===

    // /**
    //  * A Listener that handles the updating of categories when the underlying list of Tasks is updated.
    //  */
    // private class TaskListChangeListener implements ListChangeListener<Task> {
    //     @Override
    //     public void onChanged(Change<? extends Task> change) {
    //         while (change.next()) {
    //             subLists.clear();
    //             addSublist("Overdue", isOverdue);
    //             addSublist("Due Today", isDueToday);
    //             addSublist("Due This Week", isDueThisWeek);
    //             addSublist("Due Someday", isDueSomeday);
    //             subLists.removeIf(SubgroupTaskList::isEmpty);
    //         }
    //     }
    // }
}
