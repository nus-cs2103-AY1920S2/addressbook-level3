package seedu.address.ui.reminder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.Reminder;
import seedu.address.storage.StorageManager;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private static final String FXML = "ReminderListCard.fxml";
    private static final String ONGOING_REMINDER_BACKGROUND = "#FFED87";
    private static final String DONE_REMINDER_BACKGROUND = "#9FFFB1";
    private static final String OVERDUE_REMINDER_BACKGROUND = "#FFA5AA";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on StudentTAble level 4</a>
     */

    public final Reminder reminder;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label dateTime;
    @FXML
    private Label dueIn;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        description.setText(reminder.getDescription().toString());
        setStyle(reminder);
        logger.fine(reminder.getDescription().toString());
    }

    @FXML
    private void setStyle(Reminder reminder) {
        LocalDateTime dueDateTime = LocalDateTime.of(reminder.getDate(), reminder.getTime());
        dateTime.setText("DEADLINE: " + dueDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-YY HH:mm")));
        LocalDateTime nowDateTime = LocalDateTime.now();

        if (reminder.getDone()) {
            dueIn.setText("DUE IN: DONE");
            cardPane.setStyle("-fx-background-color:  " + DONE_REMINDER_BACKGROUND + ";");
        } else {
            if (!dueDateTime.isAfter(nowDateTime)) {
                dueIn.setText("DUE IN: OVERDUE");
                cardPane.setStyle("-fx-background-color:  " + OVERDUE_REMINDER_BACKGROUND + ";");
            } else {
                cardPane.setStyle("-fx-background-color:  " + ONGOING_REMINDER_BACKGROUND + ";");
                long dayDifferences = ChronoUnit.DAYS.between(nowDateTime, dueDateTime.plusMinutes(1));
                if (dayDifferences > 0) {
                    dueIn.setText("DUE IN: " + dayDifferences + " day(s)");
                } else {
                    Timeline due = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                        LocalDateTime frameDateTime = LocalDateTime.now();
                        long hourDifference = ChronoUnit.HOURS.between(frameDateTime, dueDateTime);
                        LocalDateTime tempDateTime = frameDateTime.plusHours(hourDifference);
                        long minuteDifference = ChronoUnit.MINUTES.between(tempDateTime, dueDateTime);
                        tempDateTime = tempDateTime.plusMinutes(minuteDifference);
                        long secondDifference = ChronoUnit.SECONDS.between(tempDateTime, dueDateTime);
                        if (secondDifference != 0) {
                            minuteDifference++;
                            if (minuteDifference == 60) {
                                hourDifference++;
                                minuteDifference = 0;
                            }
                        }
                        dueIn.setText("DUE IN: " + hourDifference + " hour(s) "
                                + minuteDifference + " minute(s) ");
                    }), new KeyFrame(Duration.seconds(1)));
                    due.setCycleCount((int) ChronoUnit.SECONDS.between(nowDateTime, dueDateTime) + 1);
                    due.play();
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
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
                && reminder.equals(card.reminder);
    }
}
