package seedu.address.ui.uiCourse;

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
import seedu.address.ui.uiStudent.StudentVeryDetailedCard;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Panel containing the list of courses.
 */
public class CourseDetailedPanel extends UiPart<Region> {

    private static final String FXML = "CourseDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CourseDetailedPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Course> courseDetailedView;

    @FXML
    private ListView<HashMap> studentListView;

    //    {
//      "details": Course,
//      "students": [
//          {
//            "info": Student,
//            "progress_list": [Progress],
//            "number_of_done_progress": Integer,
//          }
//      ]
//    }

    public CourseDetailedPanel(ObservableMap<String, Object> courseMap, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        courseMap.addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
                ObservableMap<String, Object> newCourseMap = (ObservableMap<String, Object>) change.getMap();
                updateDetailView(newCourseMap);
                updateStudentsView(newCourseMap);
            }
        });
    }

    private void updateDetailView(ObservableMap<String, Object> newCourseMap) {
        if (newCourseMap.containsKey("details")) {
            Course course = (Course) newCourseMap.get("details");
            ObservableList<Course> filteredCourses = FXCollections.observableArrayList();
            filteredCourses.add(course);
            courseDetailedView.setItems(filteredCourses);
            courseDetailedView.setCellFactory(listView -> new CourseListViewCell());
        }
    }

    private void updateStudentsView(ObservableMap<String, Object> newCourseMap) {
        if (newCourseMap.containsKey("students")) {
            studentListView.setItems((ObservableList<HashMap>) newCourseMap.get("students"));
            studentListView.setCellFactory(listView -> new StudentListViewCell());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Course} using a {@code
     * StudentCard}.
     */
    class CourseListViewCell extends ListCell<Course> {

        @Override
        protected void updateItem(Course course, boolean empty) {
            super.updateItem(course, empty);

            if (empty || course == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CourseDetailedCard(course, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code
     * StudentCard}.
     */
    class StudentListViewCell extends ListCell<HashMap> {

        @Override
        protected void updateItem(HashMap studentMap, boolean empty) {
            super.updateItem(studentMap, empty);

            if (empty || studentMap == null) {
                setGraphic(null);
                setText(null);
            } else {
                Student thisStudent = (Student) studentMap.get("info");
                String selectedCourseID = studentMap.get("selected_courseID").toString();
                ObservableList<Progress> progressList = (ObservableList<Progress>) studentMap.get("progress_list");
                int noOfDoneProgress = (int) studentMap.get("number_of_done_progress");
                setGraphic(new StudentVeryDetailedCard(thisStudent, selectedCourseID, progressList, noOfDoneProgress, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
