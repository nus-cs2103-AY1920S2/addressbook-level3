package seedu.address.ui.uiStudent;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;

/**
 * Panel containing the list of students.
 */
public class StudentListPanel extends UiPart<Region> {

    private static final String FXML = "StudentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Student> studentListView;

    public StudentListPanel(ObservableList<Student> studentList, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code
     * StudentCard}.
     */
    class StudentListViewCell extends ListCell<Student> {

        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentCard(student, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
