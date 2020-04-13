//@@author fatin99

package tatracker.ui.studenttab;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.student.Student;
import tatracker.ui.Focusable;
import tatracker.ui.UiPart;

/**
 * Panel containing the list of students.
 */
public class StudentListPanel extends UiPart<Region> implements Focusable {
    private static final String FXML = "StudentListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);

    @FXML
    private ListView<Student> studentListView;

    public StudentListPanel(ObservableList<Student> studentList) {
        super(FXML);
        logger.fine("Showing Student List");

        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        studentListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                studentListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                studentListView.setStyle("");
            }
        });
    }

    @Override
    public void requestFocus() {
        studentListView.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return studentListView.isFocused();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code StudentCard}.
     */
    class StudentListViewCell extends ListCell<Student> {
        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);
            getStyleClass().removeAll("filtered", "list-cell");

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
                getStyleClass().add("list-cell");
                setStyle("");
            } else {
                setGraphic(new StudentCard(student, getIndex() + 1).getRoot());
                getStyleClass().add("filtered");
            }
        }
    }

}
