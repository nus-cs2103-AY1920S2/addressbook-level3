package seedu.zerotoone.ui.views.schedule;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the schedule page.
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "schedule/ScheduleListPanel.fxml";

    @FXML
    private Text scheduleView;

    public ScheduleListPanel() {
        super(FXML);
    }
}
