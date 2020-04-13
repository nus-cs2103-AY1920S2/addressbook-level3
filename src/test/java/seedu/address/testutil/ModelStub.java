package seedu.address.testutil;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.action.DoableAction;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getModManagerFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModManagerFilePath(Path modManagerFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModManager(ReadOnlyModManager newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyModManager getModManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModuleCode(String moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModule(Module target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Module> getModule() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Module> findModule(ModuleCode moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateModule(Optional<Module> module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasFacilitator(Facilitator facilitator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFacilitator(Facilitator target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFacilitator(Facilitator facilitator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFacilitator(Facilitator target, Facilitator editedFacilitator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModuleCodeFromFacilitatorList(ModuleCode target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModuleCodeInFacilitatorList(ModuleCode target, ModuleCode editedModuleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Facilitator> getFilteredFacilitatorList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredFacilitatorList(Predicate<Facilitator> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Facilitator> getFacilitatorListForModule() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFacilitatorListForModule(Predicate<Facilitator> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Returns true if a module with the same identity as {@code module} exists in Mod Manager.
     *
     * @param module
     */
    @Override
    public boolean hasTask(Task module) {
        throw new AssertionError("This method should not be called");
    }

    /**
     * Deletes the given module.
     * The module must exist in Mod Manager.
     *
     * @param target
     */
    @Override
    public void deleteTask(Task target) {
        throw new AssertionError("This method should not be called");
    }

    /**
     * Adds the given module.
     * {@code module} must not already exist in Mod Manager.
     *
     * @param module
     */
    @Override
    public void addTask(Task module) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void deleteTasksWithModuleCode(ModuleCode target) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void setModuleCodeInTaskList(ModuleCode target, ModuleCode editedModuleCode) {
        throw new AssertionError("This method should not be called");
    }

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in Mod Manager.
     * The task identity of {@code editedTask} must not be the same as another existing task
     * in Mod Manager.
     *
     * @param target
     * @param editedTask
     */
    @Override
    public void setTask(Task target, Task editedTask) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getTaskListForModule() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateTaskListForModule(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLesson(Lesson target, Lesson edited) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeLessonFromModule(ModuleCode moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Lesson> getLessons() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Lesson findNextLesson() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Lesson> findLessonByDay(DayOfWeek day) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModuleCodeInLessonList(ModuleCode target, ModuleCode editedModuleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isTimeSlotFree(Lesson lesson, Optional<Lesson> lessonToExclude) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateCalendar(Calendar calendar) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Calendar getCalendar() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAction(DoableAction<?> action) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public DoableAction<?> undo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public DoableAction<?> redo() {
        throw new AssertionError("This method should not be called.");
    }


}
