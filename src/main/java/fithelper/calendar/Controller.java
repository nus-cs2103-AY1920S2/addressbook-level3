package fithelper.calendar;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * Get the pane to put calendar on.
 */
public class Controller {
    @FXML
    private Pane calendarPane;

    public Pane getCalendarPane() {
        return calendarPane;
    }
}
