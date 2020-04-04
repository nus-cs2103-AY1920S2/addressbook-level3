package seedu.zerotoone.ui.views.log;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.ui.util.UiPart;
/**
 * Panel containing the log page.
 */
public class LogListPanel extends UiPart<Region> {
    private static final String FXML = "log/LogListPanel.fxml";

    @FXML
    private ListView<CompletedWorkout> logListView;

    public LogListPanel(ObservableList<CompletedWorkout> logList) {
        super(FXML);
        logListView.setItems(logList);
        logListView.setCellFactory(listView -> new LogListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code SessionCard}.
     */
    class LogListViewCell extends ListCell<CompletedWorkout> {
        @Override
        protected void updateItem(CompletedWorkout completedworkout, boolean empty) {
            super.updateItem(completedworkout, empty);

            if (empty || completedworkout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CompletedWorkoutCard(completedworkout, getIndex() + 1).getRoot());
            }
        }
    }
}
