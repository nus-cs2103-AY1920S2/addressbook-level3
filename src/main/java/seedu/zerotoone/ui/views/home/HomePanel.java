package seedu.zerotoone.ui.views.home;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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
    private ListView<CompletedSet> lastSetView;
    @FXML
    private ListView<Integer> timerListView;

    public HomePanel(ObservableList<OngoingSet> ongoingSetList, ObservableList<CompletedSet> lastSet,
                     ObservableList<Integer> timerList) {
        super(FXML);

        timerListView.setItems(timerList);
        timerListView.setCellFactory(listView -> new TimerListViewCell());

        lastSetView.setItems(lastSet);
        lastSetView.setCellFactory(listView -> new LastSetViewCell());
        if (!lastSet.isEmpty()) {
            Color color = lastSet.get(0).isFinished() ? Color.GREEN : Color.RED;
            lastSetView.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class LastSetViewCell extends ListCell<CompletedSet> {
        @Override
        protected void updateItem(CompletedSet completedSet, boolean empty) {
            super.updateItem(completedSet, empty);
            if (empty || completedSet == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (completedSet.isFinished()) {
                    Region lc = new LastSessionCard(completedSet).getRoot();
                    setGraphic(lc);
                } else {
                    Region lc = new FailSessionCard(completedSet).getRoot();
                    setGraphic(lc);
                }
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class TimerListViewCell extends ListCell<Integer> {
        @Override
        protected void updateItem(Integer timeInMs, boolean empty) {
            super.updateItem(timeInMs, empty);
            if (empty || timeInMs == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TimerCard(timeInMs).getRoot());
            }
        }
    }
}
