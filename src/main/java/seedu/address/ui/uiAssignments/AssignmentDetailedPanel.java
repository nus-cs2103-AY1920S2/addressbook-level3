package seedu.address.ui.uiAssignments;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;

/**
 * Panel containing the list of assignments.
 */
public class AssignmentDetailedPanel extends UiPart<Region> {

    private static final String FXML = "AssignmentDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignmentDetailedPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Assignment> assignmentDetailedView;

//  Map: key -> value
//  {
//    "details": Assignment
//    "course": [CourseDetail]
//  }

    public AssignmentDetailedPanel(ObservableMap<String, Object> assignmentMap, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        assignmentMap.addListener(new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
                ObservableMap<String, Object> newAssignmentMap = (ObservableMap<String, Object>) change.getMap();
                updateDetailView(newAssignmentMap);
            }
        });
    }

    private void updateDetailView(ObservableMap<String, Object> newAssignmentMap) {
        if (newAssignmentMap.containsKey("details")) {
            Assignment assignment = (Assignment) newAssignmentMap.get("details");
            ObservableList<Assignment> filteredAssignments = FXCollections.observableArrayList();
            filteredAssignments.add(assignment);
            assignmentDetailedView.setItems(filteredAssignments);
            assignmentDetailedView.setCellFactory(listView -> new AssignmentListViewCell());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assignment} using a {@code
     * CourseCard}.
     */
    class AssignmentListViewCell extends ListCell<Assignment> {

        @Override
        protected void updateItem(Assignment assignment, boolean empty) {
            super.updateItem(assignment, empty);

            if (empty || assignment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssignmentDetailedCard(assignment, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
