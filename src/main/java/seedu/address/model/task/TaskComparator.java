package seedu.address.model.task;

import java.util.Comparator;

/**
 * Compares 2 Tasks. Prioritizes Tasks with smaller dates (if any).
 */
public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (o1 instanceof NonScheduledTask && o2 instanceof NonScheduledTask) {
            return 0;
        }

        if (o1 instanceof NonScheduledTask) {
            return 1;
        }
        if (o2 instanceof NonScheduledTask) {
            return -1;
        }

        return o1.getTaskDateTime().get().compareTo(o2.getTaskDateTime().get());
    }
}
