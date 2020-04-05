package seedu.zerotoone.ui.views.home;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.OngoingSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the home page.
 */
public class HomePanel extends UiPart<Region> {
    private static final String FXML = "home/HomePanel.fxml";

    @FXML
    private ListView<OngoingSet> ongoingSessionView;
    @FXML
    private Label lastExerciseName;
    @FXML
    private Label lastExerciseReps;

    public HomePanel(ObservableList<OngoingSet> ongoingSetList, Optional<CompletedSet> lastSet) {
        super(FXML);
        if (lastSet.isPresent()) {
            CompletedSet completedSet = lastSet.get();
            String name = completedSet.getExerciseName().fullName;
            String weight = completedSet.getWeight().value;
            String reps = completedSet.getNumReps().value;
            lastExerciseName.setText(name);
            lastExerciseReps.setText(reps + " reps, " + weight + "kg");
        }
        ongoingSessionView.setItems(ongoingSetList);
        ongoingSessionView.setCellFactory(listView -> new OngoingSetViewCell());
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class OngoingSetViewCell extends ListCell<OngoingSet> {
        @Override
        protected void updateItem(OngoingSet ongoingSet, boolean empty) {
            super.updateItem(ongoingSet, empty);

            if (empty || ongoingSet == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OngoingSessionCard(ongoingSet).getRoot());
            }
        }
    }
}
