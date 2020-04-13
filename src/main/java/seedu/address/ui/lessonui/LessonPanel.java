package seedu.address.ui.lessonui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;
import seedu.address.ui.UiPart;


/**
 * Panel containing the list of lessons.
 */
public class LessonPanel extends UiPart<Region> {
    private static final String FXML = "LessonPanel.fxml";

    @FXML
    private Label title;

    @FXML
    private ListView<Lesson> lessons;

    public LessonPanel(ObservableList<Lesson> lessonList) {
        super(FXML);
        lessons.setItems(lessonList);
        lessons.setCellFactory(listView -> new LessonPanel.LessonListViewCell() {});
        lessons.setPadding(new Insets(0));
    }

    public LessonPanel() {
        super(FXML);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}.
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: transparent;");
            } else {
                setGraphic(new LessonModCard(lesson, getIndex() + 1).getRoot());
                setStyle("-fx-background-color: #215360; -fx-background-radius: 15px; "
                        + "-fx-background-insets: 3px, 0px; -fx-padding: 10px");
            }
        }
    }
}
