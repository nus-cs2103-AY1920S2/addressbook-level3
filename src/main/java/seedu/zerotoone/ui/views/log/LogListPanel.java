package seedu.zerotoone.ui.views.log;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.ui.util.UiPart;
/**
 * Panel containing the log page.
 */
public class LogListPanel extends UiPart<Region> {
    private static final String FXML = "log/LogListPanel.fxml";

    @FXML
    private ListView<CompletedExercise> sessionListView;

    public LogListPanel(ObservableList<CompletedExercise> completedExerciseList) {
        super(FXML);
        sessionListView.setItems(completedExerciseList);
        sessionListView.setCellFactory(listView -> new LogListPanel.SessionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code SessionCard}.
     */
    class SessionListViewCell extends ListCell<CompletedExercise> {
        @Override
        protected void updateItem(CompletedExercise completedExercise, boolean empty) {
            super.updateItem(completedExercise, empty);

            if (empty || completedExercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SessionCard(completedExercise, getIndex() + 1).getRoot());
            }
        }
    }
}
