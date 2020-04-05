package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;


/**
 * Class for screenshot purposes
 */
public class ScheduleWindow extends UiPart<Stage> {
    private static final String FXML = "ScheduleWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(ScheduleWindow.class);

    @FXML
    private BorderPane borderPane;

    public ScheduleWindow(Stage stage, Region scheduleRegion) {
        super(FXML, stage);
        borderPane.setCenter(scheduleRegion);
    }

    /**
     * Shows the schedule window
     */
    public void show() {
        logger.config("Displaying Schedule");
        getRoot().setFullScreen(true);
        getRoot().centerOnScreen();
        getRoot().show();
    }

    /**
     * Closes the schedule window
     */
    public void close() {
        getRoot().setFullScreen(false);
        getRoot().close();
    }



}
