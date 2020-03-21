package seedu.zerotoone.ui.exercise;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends UiPart<Region> {
    private static final String FXML = "exercise/ExerciseListPanel.fxml";

    @FXML
    private ListView<Exercise> exerciseListView;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        exerciseListView.setItems(exerciseList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class ExerciseListViewCell extends ListCell<Exercise> {
        @Override
        protected void updateItem(Exercise exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExerciseCard(exercise, getIndex() + 1).getRoot());
            }
        }
    }

}
