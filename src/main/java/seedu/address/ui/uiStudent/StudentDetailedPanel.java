package seedu.address.ui.uiStudent;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;
import seedu.address.ui.uiCourse.CourseVeryDetailedCard;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Panel containing the list of students.
 */
public class StudentDetailedPanel extends UiPart<Region> {

    private static final String FXML = "StudentDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentDetailedPanel.class);
    private CommandBox commandBox;

    @FXML
    public ListView<Student> studentDetailedView;

    @FXML
    public ListView<HashMap> courseListView;


    //    {
//      "details": Student,
//      "courses": [
//          {
//            "info": Course,
//            "progress_list": [Progress],
//            "number_of_done_progress": Integer,
//          }
//      ]
//    }

    public StudentDetailedPanel(ObservableMap<String, Object> studentMap, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        studentMap.addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
                ObservableMap<String, Object> newStudentMap = (ObservableMap<String, Object>) change.getMap();
                updateDetailView(newStudentMap);
                updateCoursesView(newStudentMap);
            }
        });
    }

    private void updateDetailView(ObservableMap<String, Object> newStudentMap) {
        if (newStudentMap.containsKey("details")) {
            Student student = (Student) newStudentMap.get("details");
            ObservableList<Student> filteredStudents = FXCollections.observableArrayList();
            filteredStudents.add(student);
            studentDetailedView.setItems(filteredStudents);
            studentDetailedView.setCellFactory(listView -> new StudentListViewCell());
        }
    }

    private void updateCoursesView(ObservableMap<String, Object> newStudentMap) {
        if (newStudentMap.containsKey("courses")) {
            courseListView.setItems((ObservableList<HashMap>) newStudentMap.get("courses"));
            courseListView.setCellFactory(listView -> new CourseListViewCell());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code
     * CourseCard}.
     */
    class StudentListViewCell extends ListCell<Student> {

        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentDetailedCard(student, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Course} using a {@code
     * CourseCard}.
     */
    class CourseListViewCell extends ListCell<HashMap> {

        @Override
        protected void updateItem(HashMap courseMap, boolean empty) {
            super.updateItem(courseMap, empty);

            if (empty || courseMap == null) {
                setGraphic(null);
                setText(null);
            } else {
                Course thisCourse = (Course) courseMap.get("info");
                String selectedStudentID = courseMap.get("selected_studentID").toString();
                ObservableList<Progress> progressList = (ObservableList<Progress>) courseMap.get("progress_list");
                int noOfDoneProgress = (int) courseMap.get("number_of_done_progress");
                setGraphic(new CourseVeryDetailedCard(thisCourse, selectedStudentID, progressList, noOfDoneProgress, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
