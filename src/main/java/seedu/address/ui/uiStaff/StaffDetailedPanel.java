package seedu.address.ui.uiStaff;

import java.util.HashMap;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStaff.Staff;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;
import seedu.address.ui.uiCourse.CourseCard;

/**
 * Panel containing the list of staffs.
 */
public class StaffDetailedPanel extends UiPart<Region> {

  private static final String FXML = "StaffDetailedPanel.fxml";
  private final Logger logger = LogsCenter.getLogger(StaffDetailedPanel.class);
  private CommandBox commandBox;

  @FXML
  private ListView<Staff> staffDetailedView;

  @FXML
  private ListView<Course> courseListView;

//  Map: key -> value
//  {
//    "details": Staff
//    "course": [CourseDetail]
//  }

  public StaffDetailedPanel(HashMap<String, Object> staffMap, CommandBox commandBox) {
    super(FXML);
    this.commandBox = commandBox;
    Staff staff = (Staff) staffMap.get("details");
    ObservableList<Staff> filteredStaffs = FXCollections.observableArrayList();
    filteredStaffs.add(staff);
    staffDetailedView.setItems(filteredStaffs);
    staffDetailedView.setCellFactory(listView -> new StaffListViewCell());

    courseListView.setItems((ObservableList<Course>) staffMap.get("courses"));
    courseListView.setCellFactory(listView -> new CourseListViewCell());
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Staff} using a {@code
   * CourseCard}.
   */
  class StaffListViewCell extends ListCell<Staff> {

    @Override
    protected void updateItem(Staff staff, boolean empty) {
      super.updateItem(staff, empty);

      if (empty || staff == null) {
        setGraphic(null);
        setText(null);
      } else {
        setGraphic(new StaffDetailedCard(staff, commandBox,getIndex() + 1).getRoot());
      }
    }
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Course} using a {@code
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
