package seedu.foodiebot.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.foodiebot.model.budget.Budget;

/** A ui for the status bar that is displayed at the header of the application. */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML private TextArea resultDisplay;

    @FXML private Label budgetLabel;

    @FXML private ProgressBar budgetTrackerHigh;

    @FXML private ProgressBar budgetTrackerLow;

    public ResultDisplay() {
        super(FXML);
        budgetLabel.setVisible(false);
        budgetTrackerHigh.setVisible(false);
        budgetTrackerLow.setVisible(false);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        budgetLabel.setVisible(false);
        budgetTrackerHigh.setVisible(false);
        budgetTrackerLow.setVisible(false);
        resultDisplay.setText(feedbackToUser);
    }

    public void setFeedbackToUser(String feedbackToUser, Budget budget) {
        requireNonNull(feedbackToUser);
        budgetLabel.setVisible(true);
        float progress = budget.getRemainingBudget() / budget.getTotalBudget();
        if (progress < 0.25) {
            budgetTrackerLow.setProgress(progress);
            budgetTrackerHigh.setProgress(progress);
            budgetTrackerHigh.setVisible(false);
            budgetTrackerLow.setVisible(true);
        } else {
            budgetTrackerLow.setProgress(progress);
            budgetTrackerHigh.setProgress(progress);
            budgetTrackerHigh.setVisible(true);
            budgetTrackerLow.setVisible(false);
        }
        resultDisplay.setText(feedbackToUser);
    }
}
