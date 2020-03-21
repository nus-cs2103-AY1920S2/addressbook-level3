package seedu.zerotoone.ui.views.workout;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the workout page.
 */
public class WorkoutListPanel extends UiPart<Region> {
    private static final String FXML = "workout/WorkoutListPanel.fxml";

    @FXML
    private Text workoutView;

    public WorkoutListPanel() {
        super(FXML);
    }
}
