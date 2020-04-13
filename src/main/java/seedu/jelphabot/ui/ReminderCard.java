//@@author yapdianhao
package seedu.jelphabot.ui;

import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jelphabot.model.reminder.ReminderShowsTask;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderCard.fxml";

    public final ReminderShowsTask reminderShowsTask;

    @FXML
    private Label id;

    @FXML
    private HBox cardPane;

    @FXML
    private Label moduleCode;

    @FXML
    private Label description;

    @FXML
    private Label dateTime;

    @FXML
    private Label reminderDay;

    @FXML
    private Label reminderHour;

    public ReminderCard(ReminderShowsTask reminderShowsTask, NumberBinding displayedIndex) {
        super(FXML);
        this.reminderShowsTask = reminderShowsTask;
        //setId(displayedIndex);
        populateReminderElements(reminderShowsTask);
    }

    public ReminderCard(ReminderShowsTask reminderShowsTask) {
        super(FXML);
        this.reminderShowsTask = reminderShowsTask;
        //setId(displayedIndex);
        populateReminderElements(reminderShowsTask);
    }

    private void setId(NumberBinding displayedIndex) {
        id.textProperty().bind(displayedIndex.asString("%d. "));
    }

    private void setId(int displayedIndex) {
        id.setText(String.format("%d. ", displayedIndex));
    }

    /**
     * Set the value of the labels.
     */
    private void populateReminderElements(ReminderShowsTask reminderShowsTask) {
        moduleCode.setText(reminderShowsTask.getModuleCode().value);
        description.setText(reminderShowsTask.getDescription().fullDescription);
        dateTime.setText(reminderShowsTask.getDateTime().getDisplayValue());
        reminderDay.setText(reminderShowsTask.getReminderDay().getReminderDay() + " day(s)");
        reminderHour.setText("" + reminderShowsTask.getReminderHour().getReminderHour() + " hour(s)");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                   && reminderShowsTask.equals(card.reminderShowsTask);
    }
}
