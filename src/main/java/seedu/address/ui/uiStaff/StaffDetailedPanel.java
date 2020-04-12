package seedu.address.ui.uiStaff;

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
import seedu.address.model.modelStaff.Staff;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;
import seedu.address.ui.uiCourse.CourseCard;

import java.util.logging.Logger;

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

    public StaffDetailedPanel(ObservableMap<String, Object> staffMap, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        staffMap.addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
                ObservableMap<String, Object> newStaffMap = (ObservableMap<String, Object>) change.getMap();
                updateDetailView(newStaffMap);
                updateCoursesView(newStaffMap);
            }
        });
    }

    private void updateDetailView(ObservableMap<String, Object> newStaffMap) {
        if (newStaffMap.containsKey("details")) {
            Staff staff = (Staff) newStaffMap.get("details");
            ObservableList<Staff> filteredStaffs = FXCollections.observableArrayList();
            filteredStaffs.add(staff);
            staffDetailedView.setItems(filteredStaffs);
            staffDetailedView.setCellFactory(listView -> new StaffListViewCell());
        }
    }

    private void updateCoursesView(ObservableMap<String, Object> newStaffMap) {
        if (newStaffMap.containsKey("courses")) {
            courseListView.setItems((ObservableList<Course>) newStaffMap.get("courses"));
            courseListView.setCellFactory(listView -> new CourseListViewCell());
        }
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
                setGraphic(new StaffDetailedCard(staff, commandBox, getIndex() + 1).getRoot());
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
                setGraphic(new CourseCard(course, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
