package seedu.zerotoone.ui.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class TimerCard extends UiPart<Region> {
    private static final String FXML = "home/TimerCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExerciseList level 4</a>
     */

    @FXML
    private Label timer;

    public TimerCard(Integer timeInMs) {
        super(FXML);
        int secondsAbs = timeInMs / 1000;
        int minutesAbs = secondsAbs / 60;
        String hours = String.valueOf(minutesAbs / 60 % 24);
        String minutes = String.valueOf(minutesAbs % 60);
        String seconds = String.valueOf(secondsAbs % 60);
        String timerString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timer.setText(timerString);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimerCard)) {
            return false;
        }

        // state check
        TimerCard card = (TimerCard) other;
        return timer.getText().equals(card.timer.getText());
    }
}
