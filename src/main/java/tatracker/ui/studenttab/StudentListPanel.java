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
    private static final String BACKGROUND_COLOUR = "#5f4d42";
    private static final String BORDER_COLOUR = "#917b3e";
    private static final String BORDER_WIDTH = "1";

    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);

    @FXML
    private ListView<Student> studentListView;

    public StudentListPanel(ObservableList<Student> studentList) {
        super(FXML);
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
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

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
                setStyle("");
            } else {
                setGraphic(new StudentCard(student, getIndex() + 1).getRoot());
                setStyle("-fx-background-color: " + BACKGROUND_COLOUR + "; "
                        + "-fx-border-color: " + BORDER_COLOUR + "; "
                        + "-fx-border-width: " + BORDER_WIDTH + ";");
            }
        }
    }

}
