package seedu.nova.model.plan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.event.Event;

/**
 * Plan with definite tasks
 */
public class StudyPlan implements Plan {
    private HashMap<TaskDetails, Task> map = new HashMap<>();

    public StudyPlan() {
        resetPlan();
    }

    public StudyPlan(Collection<Task> tasks) {
        for (Task t : tasks) {
            addTask(t);
        }
    }

    @Override
    public void resetPlan() {
        this.map = new HashMap<>();
    }

    @Override
    public List<Task> getTaskList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean addTask(Task task) {
        if (map.get(task.details) != null) {
            return false;
        } else {
            return map.put(task.details, task) == null;
        }
    }

    @Override
    public boolean deleteTask(Task task) {
        return map.remove(task.details, task);
    }

    @Override
    public Task searchTask(String name) {
        return map.get(TaskDetails.ofName(name));
    }

    @Override
    public Event generateTaskEvent(Task task, LocalDate date, Schedule sc) throws ImpossibleTaskException {
        return task.generateEventOnDay(date, sc);
    }
}
