package seedu.zerotoone.ui.views.schedule;

import static seedu.zerotoone.commons.util.DateUtil.getPrettyDateTimeString;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.ui.util.UiPart;

/**
 *
 */
public class ScheduledWorkoutCard extends UiPart<Region> {
    private static final String FXML = "schedule/ScheduledWorkoutCard.fxml";

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label scheduledWorkoutId;
    @FXML
    private Label scheduledWorkoutName;
    @FXML
    private Label dateTime;

    public ScheduledWorkoutCard(ScheduledWorkout scheduledWorkout, int displayedIndex) {
        super(FXML);
        scheduledWorkoutId.setText(String.format("%d. ", displayedIndex));
        scheduledWorkoutName.setText(scheduledWorkout.getScheduledWorkoutName());
        dateTime.setText(getPrettyDateTimeString(scheduledWorkout.getDateTime().getLocalDateTime()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduledWorkoutCard)) {
            return false;
        }

        // state check
        ScheduledWorkoutCard card = (ScheduledWorkoutCard) other;
        return scheduledWorkoutId.getText().equals(card.scheduledWorkoutId.getText())
                && scheduledWorkoutName.getText().equals(card.scheduledWorkoutName.getText());
    }
}
