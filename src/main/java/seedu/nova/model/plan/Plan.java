package seedu.nova.model.plan;

import java.time.LocalDate;
import java.util.List;

import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.event.Event;

/**
 * task container
 */
public interface Plan {
    void resetPlan();

    List<Task> getTaskList();

    boolean addTask(Task task);

    boolean deleteTask(Task task);

    Task searchTask(String name);

    Event generateTaskEvent(Task task, LocalDate date, Schedule sc) throws ImpossibleTaskException;
}
