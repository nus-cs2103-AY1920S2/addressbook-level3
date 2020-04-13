package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.UniqueFacilitatorList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameModule and .isSameFacilitator and .isSameTask comparison)
 */
public class ModManager implements ReadOnlyModManager {

    private final UniqueModuleList modules;
    private final UniqueFacilitatorList facilitators;
    private final UniqueTaskList tasks;
    private final LessonList lessons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
        facilitators = new UniqueFacilitatorList();
        tasks = new UniqueTaskList();
        lessons = new LessonList();
    }

    public ModManager() {}

    /**
     * Creates a ModManager using the Modules, Facilitators, Tasks and Lessons in the {@code toBeCopied}
     */
    public ModManager(ReadOnlyModManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the facilitator list with {@code facilitators}.
     * {@code facilitators} must not contain duplicate facilitators.
     */
    public void setFacilitators(List<Facilitator> facilitators) {
        this.facilitators.setFacilitators(facilitators);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Replaces the contents of the lesson list with {@code lessons}.

      * Replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons.setLessons(lessons);
    }

    /**
     * Resets the existing data of this {@code ModManager} with {@code newData}.
     */
    public void resetData(ReadOnlyModManager newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
        setFacilitators(newData.getFacilitatorList());
        setTasks(newData.getTaskList());
        setLessons(newData.getLessonList());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same module code as {@code moduleCode} exists in Mod Manager.
     */
    public boolean hasModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        return modules.containsModuleCode(moduleCode);
    }

    /**
     * Returns true if a module with the same identity as {@code module} exists in Mod Manager.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the Mod Manager.
     * The module must not already exist in Mod Manager.
     */
    public void addModule(Module p) {
        modules.add(p);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in Mod Manager.
     * The module identity of {@code editedModule} must not be the same as another existing module
     * in Mod Manager.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code ModManager}.
     * {@code key} must exist in Mod Manager.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Returns the module with the specified {@code moduleCode}.
     */
    public Optional<Module> findModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return modules.find(moduleCode);
    }

    /**
     * Returns the list of modules in this {@code ModManager}.
     * @return the list of modules.
     */
    public List<Module> getModules() {
        return modules.getModuleList();
    }

    //// facilitator-level operations

    /**
     * Returns true if a facilitator with the same identity as {@code facilitator} exists in Mod Manager.
     */
    public boolean hasFacilitator(Facilitator facilitator) {
        requireNonNull(facilitator);
        return facilitators.contains(facilitator);
    }

    /**
     * Adds a facilitator to the Mod Manager.
     * The facilitator must not already exist in Mod Manager.
     */
    public void addFacilitator(Facilitator p) {
        facilitators.add(p);
    }

    /**
     * Replaces the given facilitator {@code target} in the list with {@code editedFacilitator}.
     * {@code target} must exist in Mod Manager.
     * The facilitator identity of {@code editedFacilitator} must not be the same as another existing facilitator
     * in Mod Manager.
     */
    public void setFacilitator(Facilitator target, Facilitator editedFacilitator) {
        requireNonNull(editedFacilitator);

        facilitators.setFacilitator(target, editedFacilitator);
    }

    /**
     * Removes {@code key} from this {@code ModManager}.
     * {@code key} must exist in Mod Manager.
     */
    public void removeFacilitator(Facilitator key) {
        facilitators.remove(key);
    }

    /**
     * Returns the list of modules in this {@code ModManager}.
     * @return the list of modules.
     */
    public List<Facilitator> getFacilitators() {
        return facilitators.getFacilitatorList();
    }

    /**
     * Removes {@code key} from the facilitators of from this {@code ModManager}.
     * {@code key} must exist in Mod Manager.
     */
    public void removeModuleCodeFromFacilitatorList(ModuleCode key) {
        facilitators.removeModuleCode(key);
    }

    /**
     * Replaces the given module code {@code target} in the facilitator list with {@code editedModuleCode}.
     * {@code target} must exist in Mod Manager.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in Mod Manager.
     */
    public void setModuleCodeInFacilitatorList(ModuleCode target, ModuleCode editedModuleCode) {
        facilitators.setModuleCode(target, editedModuleCode);
    }

    //// task-level operations

    /**
     * Returns true if a facilitator with the same identity as {@code facilitator} exists in Mod Manager.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.hasThisTask(task);
    }

    /**
     * Adds a task to the Mod Manager.
     * The task must not already exist in Mod Manager.
     */
    public void addTask(Task p) {
        tasks.add(p);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in Mod Manager.
     * The task identity of {@code editedTask} must not be the same as another existing task
     * in Mod Manager.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code ModManager}.
     * {@code key} must exist in Mod Manager.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    public void removeTasksWithModuleCode(ModuleCode target) {
        tasks.removeWithModuleCode(target);
    }

    /**
     * Replaces the given module code {@code target} in the task list with {@code editedModuleCode}.
     * {@code target} must exist in Mod Manager.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in Mod Manager.
     */
    public void setModuleCodeInTaskList(ModuleCode target, ModuleCode editedModuleCode) {
        tasks.setModuleCode(target, editedModuleCode);
    }

    /**
     * Returns the list of modules in this {@code ModManager}.
     * @return the list of modules.
     */
    public List<Task> getTasks() {
        return tasks.getTaskList();
    }

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in Mod Manager.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessons.contains(lesson);
    }

    /**
     * Adds a lesson to the list of lessons.
     * @param lesson The lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessons.addLesson(lesson);
    }

    /**
     * Replaces the given lesson {@code target} in the list with {@code edited}.
     * {@code target} must exist in Mod Manager.
     * The lesson identity of {@code edited} must not be the same as another existing lesson
     * in Mod Manager.
     */
    public void setLesson(Lesson target, Lesson edited) {
        requireAllNonNull(target, edited);
        lessons.setLesson(target, edited);
    }

    /**
     * Removes a lesson from the list of lessons.
     * @param lesson The lesson to be added.
     */
    public void removeLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessons.deleteLesson(lesson);
    }

    /**
     * Removes lessons with {@code moduleCode}
     */
    public void removeLessonFromModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        lessons.deleteLessonsFromModule(moduleCode);
    }

    /**
     * Finds the next lesson in Mod Manager that is going to happen.
     */
    public Lesson findNextLesson() {
        return lessons.findNextLesson();
    }

    /**
     * Finds lessons that is happening on a particular day.
     */
    public List<Lesson> findLessonsByDay(DayOfWeek day) {
        return lessons.findLessonsByDay(day);
    }


    /**
     * Gets the LessonList.
     */
    public LessonList getLessons() {
        return lessons;
    }

    /**
     * Gets the lesson list for a module.
     */
    public ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode) {
        return FXCollections
                .observableArrayList(lessons.findLessonByModuleCode(moduleCode));
    }

    /**
     * Checks if the time slot is free.
     */
    public boolean isTimeSlotFree(Lesson lesson, Optional<Lesson> lessonToExclude) {
        return lessons.isTimeSlotFree(lesson, lessonToExclude);
    }

    /**
     * Replaces the given module code {@code target} in the lesson list with {@code editedModuleCode}.
     * {@code target} must exist in Mod Manager.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in Mod Manager.
     */
    public void setModuleCodeInLessonList(ModuleCode target, ModuleCode editedModuleCode) {
        lessons.setModuleCode(target, editedModuleCode);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules \n"
                + facilitators.asUnmodifiableObservableList().size() + " facilitators \n"
                + tasks.asUnmodifiableObservableList().size() + " tasks\n"
                + lessons.getLessonList().size() + " lessons";
    }

    @Override
    public ObservableList<Facilitator> getFacilitatorList() {
        return facilitators.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public List<Lesson> getLessonList() {
        return lessons.getLessonList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModManager // instanceof handles nulls
                && modules.equals(((ModManager) other).modules)
                && facilitators.equals(((ModManager) other).facilitators)
                && tasks.equals(((ModManager) other).tasks))
                && lessons.equals(((ModManager) other).lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modules, facilitators, tasks);
    }
}
