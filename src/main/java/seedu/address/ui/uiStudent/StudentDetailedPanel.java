package seedu.address.ui.uiStudent;

import java.util.HashMap;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;
import seedu.address.ui.uiCourse.CourseCard;

/**
 * Panel containing the list of students.
 */
public class StudentDetailedPanel extends UiPart<Region> {

  private static final String FXML = "StudentDetailedPanel.fxml";
  private final Logger logger = LogsCenter.getLogger(StudentDetailedPanel.class);
  private CommandBox commandBox;

  @FXML
  private StackPane studentDetailedView;

  @FXML
  private ListView<Course> courseListView;

//  Map: key -> value
//  {
//    "details": Student
//    "course": [CourseDetail]
//  }

  public StudentDetailedPanel(HashMap<String, Object> studentMap, CommandBox commandBox) {
    super(FXML);
    this.commandBox = commandBox;
    Student student = (Student) studentMap.get("details");
    StudentDetailedCard studentCard = new StudentDetailedCard(student, commandBox, 1);
    studentDetailedView.getChildren().add(studentCard.getRoot());
    courseListView.setItems((ObservableList<Course>) studentMap.get("courses"));
    courseListView.setCellFactory(listView -> new CourseListViewCell());
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Assignment} using a {@code
   * CourseCard}.
   */
  class CourseListViewCell extends ListCell<Course> {

    @Override
    protected void updateItem(Course course, boolean empty) {
      super.updateItem(course, empty);

      if (empty || course == null) {
        setGraphic(null);
        setText(null);
      } else {
        setGraphic(new CourseCard(course, commandBox,getIndex() + 1).getRoot());
      }
    }
  }

}
