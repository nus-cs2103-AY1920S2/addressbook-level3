//@@author yapdianhao
package seedu.jelphabot.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.logic.Logic;
import seedu.jelphabot.model.task.Task;

/**
 * Reminders shown in app startup. Will show when tasks are about to expire,
 * as specified by the user.
 */
public class ReminderPopup extends UiPart<Stage> {

    public static final String FXML = "ReminderPopup.fxml";

    public static final String REMINDERS_STRING = "Here are your tasks that are due soon!";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final ObservableList<Task> taskList;
    //private final List<Popup> popupList;
    //private final List<Task> toBeReminded;

    private Stage reminderStage;
    private Logic logic;
    private ResultDisplay resultDisplay;
    private TaskListPanel taskListPanel;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;


    public ReminderPopup (Stage reminderStage, Logic logic) {
        super(FXML, reminderStage);
        logger.info("Initialising reminders");
        this.reminderStage = reminderStage;
        this.logic = logic;
        this.taskList = FXCollections.observableArrayList();
        setWindowDefaultSize(logic.getPopUpWindowGuiSettings());
    }

    /**
     * Fills the placeholders of this window
     */
    void fillWindow() {
        // get the list of Incomplete tasks
        for (Task task : logic.getFilteredByReminder()) {
            taskList.add(task);
        }
        taskListPanel = new TaskListPanel(taskList);
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        // initialise resultDisplay
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser(REMINDERS_STRING);
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    /**
     * Shows the reminder popup. If no reminders are pending,
     * a popup is not required.
     */
    void show() {
        logger.info("Showing reminderStage");
        if (taskList.size() == 0) {
            return;
        } else {
            reminderStage.show();
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        reminderStage.setHeight(guiSettings.getWindowHeight());
        reminderStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            reminderStage.setX(guiSettings.getWindowCoordinates().getX());
            reminderStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    @FXML
    private void closeButtonAction() {
        logger.info("Close Button pressed. Closing morningCallWindow");
        reminderStage.close();
    }
}
