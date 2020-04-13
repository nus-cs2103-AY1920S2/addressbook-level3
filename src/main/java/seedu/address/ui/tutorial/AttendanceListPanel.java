package seedu.address.ui.tutorial;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.student.Student;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of attendance for a particular week and Tutorial.
 */
public class AttendanceListPanel extends UiPart<Region> {

    private static final String FXML = "AttendanceListPanel.fxml";
    private static final String PRESENT_CELL_BACKGROUND = "#2e8140";
    private static final String ABSENT_CELL_BACKGROUND = "#812e2e";
    private final Logger logger = LogsCenter.getLogger(AttendanceListPanel.class);

    @javafx.fxml.FXML
    private ListView<Student> studentListView;
    private ArrayList<Boolean> attendanceList;

    @javafx.fxml.FXML
    private Label attendanceHeader;

    private Tutorial tutorial;
    private String tutorialCode;
    private String tutorialName;
    private int weekOneBased;

    /**
     * Constructor to load an empty attendance list
     */
    public AttendanceListPanel() {
        super(FXML);
        attendanceHeader.setText("Use listAttendance to display!");
    }

    public AttendanceListPanel(Tutorial tutorialToShow, int weekZeroBased) {
        super(FXML);

        ArrayList<Boolean> attendanceToShow = tutorialToShow.getAttendanceWeek(weekZeroBased);
        ArrayList<Student> studentsToShow = tutorialToShow.getEnrolledStudents();
        ObservableList<Student> studentsList = FXCollections.observableArrayList(studentsToShow);

        this.tutorial = tutorialToShow;
        this.attendanceList = attendanceToShow;
        this.tutorialCode = tutorialToShow.getModCode().toString();
        this.tutorialName = tutorialToShow.getTutorialName().toString();
        this.weekOneBased = weekZeroBased + 1;

        String headerStr = tutorialCode + " " + tutorialName + ": Week " + (weekOneBased + 2);
        attendanceHeader.setText(headerStr);
        studentListView.setItems(studentsList);
        studentListView.setCellFactory(listView -> new AttendanceListPanel.AttendanceListViewCell());

    }
    /**
     * Custom {@code ListCell} that displays the graphics of a student's attendance in a {@code Tutorial} using an
     * {@code AttendanceCard}.
     */
    class AttendanceListViewCell extends ListCell<Student> {
        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            ArrayList<Boolean> attendanceToShow = tutorial.getAttendanceWeek(weekOneBased - 1);
            ArrayList<Student> studentsToShow = tutorial.getEnrolledStudents();

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                int index = studentsToShow.indexOf(student);
                boolean isPresent = attendanceToShow.get(index);

                if (isPresent) {
                    setStyle("-fx-background-color:  " + PRESENT_CELL_BACKGROUND + ";");
                } else {
                    setStyle("-fx-background-color: " + ABSENT_CELL_BACKGROUND + ";");
                }
                setGraphic(new AttendanceCard(student, getIndex() + 1, attendanceList).getRoot());
            }
        }
    }
}
