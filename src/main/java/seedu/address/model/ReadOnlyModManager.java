package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of a mod manager.
 */
public interface ReadOnlyModManager {


    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns an unmodifiable view of the facilitators list.
     * This list will not contain any duplicate facilitators.
     */
    ObservableList<Facilitator> getFacilitatorList();

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns a list of lesson list.
     * This list will not contain any duplicate lessons.
     * @return List of lessons.
     */
    List<Lesson> getLessonList();

}
