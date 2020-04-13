//@@author Clouddoggo

package seedu.jelphabot.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jelphabot.model.productivity.Productivity;

/**
 * An UI component that displays information of a {@code Productivity}.
 */
public class ProductivityCard extends UiPart<Region> {

    private static final String FXML = "ProductivityCard.fxml";

    private final Productivity productivity;

    @FXML
    private HBox cardPane;
    @FXML
    private Label tasksCompleted;
    @FXML
    private Label remark;
    @FXML
    private Label overdueStatus;
    @FXML
    private Label runningTimers;
    @FXML
    private Label timeSpentToday;
    @FXML
    private ProgressBar tasksCompletionProgress;

    public ProductivityCard(Productivity productivity) {
        super(FXML);
        this.productivity = productivity;

        String[] completionProductivity = productivity.getTasksCompleted().toStringArray();
        tasksCompleted.setText("   " + completionProductivity[0]);
        remark.setText(completionProductivity[1]);
        overdueStatus.setText(completionProductivity[2]);
        runningTimers.setText(productivity.getRunningTimers().toString());
        timeSpentToday.setText(productivity.getTimeSpentToday().toString());
        tasksCompletionProgress.setProgress(productivity.getTasksCompleted().getPercentage());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductivityCard)) {
            return false;
        }

        // state check
        ProductivityCard card = (ProductivityCard) other;
        return tasksCompleted.getText().equals(card.tasksCompleted.getText())
                   && runningTimers.getText().equals(card.runningTimers.getText())
                   && remark.getText().equals(card.remark.getText())
                   && overdueStatus.getText().equals(card.overdueStatus.getText())
                   && timeSpentToday.getText().equals(card.timeSpentToday.getText())
                   && productivity.equals(card.productivity);
    }
}
