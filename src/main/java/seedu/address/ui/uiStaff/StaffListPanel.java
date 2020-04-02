package seedu.address.ui.uiStaff;

import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelStaff.Staff;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of teachers.
 */
public class StaffListPanel extends UiPart<Region> {

  private static final String FXML = "TeacherListPanel.fxml";
  private final Logger logger = LogsCenter.getLogger(StaffListPanel.class);

  @FXML
  private ListView<Staff> teacherListView;

  public StaffListPanel(ObservableList<Staff> teacherList) {
    super(FXML);
    teacherListView.setItems(teacherList);
    teacherListView.setCellFactory(listView -> new TeacherListViewCell());
  }

  /**
   * Custom {@code ListCell} that displays the graphics of a {@code Teacher} using a {@code
   * TeacherCard}.
   */
  class TeacherListViewCell extends ListCell<Staff> {

    @Override
    protected void updateItem(Staff staff, boolean empty) {
      super.updateItem(staff, empty);

      if (empty || staff == null) {
        setGraphic(null);
        setText(null);
      } else {
        setGraphic(new StaffCard(staff, getIndex() + 1).getRoot());
      }
    }
  }

}
