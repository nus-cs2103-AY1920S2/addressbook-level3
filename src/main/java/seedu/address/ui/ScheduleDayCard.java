package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.day.Day;

/**
 * A UI Component that displays the information of a {@code Day}.
 */
public class ScheduleDayCard extends UiPart<Region> {
    private static final String FXML = "ScheduleDayCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Day day;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label hours;
    @FXML
    private Label assignments;

    public ScheduleDayCard(Day day, int numDays) {
        super(FXML);
        this.day = day;
        date.setStyle("-fx-text-fill: #000");
        date.setText(" " + LocalDate.now().plusDays(numDays).format(DateTimeFormatter.ofPattern("dd MMM")) + " ");
        hours.setText("Estimated workload for today: " + day.getHours().hours);
        assignments.setText("Assignments due today:\n" + day.getDueAssignmentsToString());

        if ((numDays == 0 && day.getDueAssignments().size() != 0) || day.getHours().hours > 10) {
            date.setBackground(new Background(new BackgroundFill(Color.rgb(255, 87, 51),
                CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (day.getHours().hours > 5 && day.getHours().hours <= 10) {
            date.setBackground(new Background(new BackgroundFill(Color.rgb(255, 195, 0),
                CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            date.setBackground(new Background(new BackgroundFill(Color.rgb(218, 247, 166),
                CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        ScheduleDayCard card = (ScheduleDayCard) other;
        return day.equals(card.day);
    }
}
