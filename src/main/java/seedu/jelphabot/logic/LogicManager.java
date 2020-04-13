package seedu.jelphabot.logic;

import static seedu.jelphabot.commons.util.DateUtil.getDueTodayPredicate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.logic.commands.Command;
import seedu.jelphabot.logic.commands.CommandResult;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.logic.parser.JelphaBotParser;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.productivity.ProductivityList;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.summary.SummaryList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.ReminderPredicate;
import seedu.jelphabot.model.task.predicates.TaskCompletedWithinDayPredicate;
import seedu.jelphabot.model.task.predicates.TaskIsCompletedPredicate;
import seedu.jelphabot.model.task.predicates.TaskIsIncompletePredicate;
import seedu.jelphabot.model.task.tasklist.GroupedTaskList;
import seedu.jelphabot.model.task.tasklist.PinnedTaskList;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;
import seedu.jelphabot.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final JelphaBotParser jelphaBotParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        jelphaBotParser = new JelphaBotParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = jelphaBotParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveJelphaBot(model.getJelphaBot());
            //storage.saveJelphaBot(model.getJelphaBot(), true);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return model.getFilteredReminderList();
    }

    @Override
    public ObservableList<ReminderShowsTask> getReminderShowsTaskList() {
        model.updateReminderShowsTask();
        return model.getReminderShowsTaskList();
    }

    @Override
    public ReadOnlyJelphaBot getJelphaBot() {
        return model.getJelphaBot();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredCalendarTaskList() {
        return model.getFilteredCalendarTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredByCompleteTaskList() {
        ObservableList<Task> filteredTasks = model.getFilteredTaskList();
        TaskIsCompletedPredicate predicate = new TaskIsCompletedPredicate();
        return new FilteredList<>(filteredTasks, predicate);
    }

    @Override
    public ObservableList<Task> getFilteredByCompletedTodayTaskList() {
        ObservableList<Task> filteredTasks = model.getFilteredTaskList();
        TaskCompletedWithinDayPredicate pred = new TaskCompletedWithinDayPredicate();
        return new FilteredList<>(filteredTasks, pred);
    }

    public ObservableList<Task> getFilteredByReminder() {
        ViewTaskList taskList = model.getLastShownList();
        List<Reminder> reminderList = model.getReminderListFromJelphaBot();
        ReminderPredicate reminderPredicate = new ReminderPredicate(taskList, reminderList);
        return model.getFilteredTaskList().filtered(reminderPredicate);
    }

    @Override
    public ObservableList<Task> getFilteredByIncompleteTaskList() {
        ObservableList<Task> filteredTasks = model.getFilteredTaskList();
        TaskIsIncompletePredicate taskIncompletePredicate = new TaskIsIncompletePredicate();
        return new FilteredList<>(filteredTasks, taskIncompletePredicate);
    }

    //@@author eedenong
    public ObservableList<Task> getFilteredByIncompleteDueTodayTaskList() {
        ObservableList<Task> filteredTasks = model.getFilteredTaskList();
        TaskIsIncompletePredicate taskIncompletePredicate = new TaskIsIncompletePredicate();
        FilteredList<Task> filteredIncompleteList = new FilteredList<>(filteredTasks, taskIncompletePredicate);
        return new FilteredList<>(filteredIncompleteList, getDueTodayPredicate());
    }

    // @@author

    @Override
    public GroupedTaskList getGroupedTaskList(GroupedTaskList.Category category) {
        return model.getGroupedTaskList(category);
    }

    @Override
    public PinnedTaskList getPinnedTaskList() {
        return model.getPinnedTaskList();
    }

    @Override
    public ProductivityList getProductivityList() {
        return model.getProductivityList();
    }

    @Override
    public SummaryList getSummaryList() {
        return model.getSummaryList();
    }

    @Override
    public Path getJelphaBotFilePath() {
        return model.getJelphaBotFilePath();
    }

    @Override
    public Path getRemindersFilePath() {
        return model.getRemindersFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public GuiSettings getPopUpWindowGuiSettings() {
        return model.getPopUpWindowGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    /**
     * Updates the filter of the filtered calendar task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredCalendarTaskList(Predicate<Task> predicate) {
        model.updateFilteredCalendarTaskList(predicate);
    }
}
