package seedu.address.ui.taskui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;
    public final int id;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardbox;
    @FXML
    private Label moduleCode;
    @FXML
    private Label taskNum;
    @FXML
    private Label taskDescription;
    @FXML
    private Label taskTime;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.id = displayedIndex;
        this.task = task;

        taskNum.setStyle("-fx-text-fill: #ffee00;");

        moduleCode.setText(String.format("%s", task.getModuleCode()));
        moduleCode.setStyle("-fx-text-fill: #add8e6");
        taskNum.setText(String.format("ID: %d", task.getTaskNum()));
        taskDescription.setText(String.format("%s",
                task.getDescription()));

        if (task.getDescription() != null) {
            taskDescription.setText(task.getDescription().toString());
        } else {
            taskDescription.setText("");
        }

        if (!task.getTimeString().isEmpty()) {
            taskTime.setText(task.getTimeString());
        } else {
            taskTime.setText("");
            cardbox.getChildren().remove(taskTime);
        }
        cardbox.setMinHeight(0);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id == card.id
                && task.equals(card.task);
    }
}
