package seedu.address.logic;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;

/**
 * API of the Logic component.
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ModManager.
     *
     * @see seedu.address.model.Model#getModManager()
     */
    ReadOnlyModManager getModManager();

    /** Returns an unmodifiable view of the filtered list of modules. */
    ObservableList<Module> getFilteredModuleList();

    /** Returns an unmodifiable view of the filtered list of facilitators. */
    ObservableList<Facilitator> getFilteredFacilitatorList();

    /** Returns an unmodifiable view of the filtered list of tasks. */
    ObservableList<Task> getFilteredTaskList();

    /** Returns an unmodifiable view of the filtered list of lessons. */
    List<Lesson> getLessons();

    /** Returns the module to be viewed. */
    Optional<Module> getModule();

    /** Returns an unmodifiable view of the filtered list of facilitators. */
    ObservableList<Facilitator> getFacilitatorListForModule();

    /** Returns an unmodifiable view of the filtered list of tasks. */
    ObservableList<Task> getTaskListForModule();

    /** Returns an unmodifiable view of the filtered list of lessons. */
    ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode);

    /** Returns the calendar to be viewed. */
    Calendar getCalendar();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getModManagerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
