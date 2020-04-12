package seedu.address.ui.uiAssignments;

import javafx.collections.ObservableList;
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
 * Panel containing the list of teachers.
 */
public class AssignmentListPanel extends UiPart<Region> {

    private static final String FXML = "AssignmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignmentListPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Assignment> assignmentListView;

    public AssignmentListPanel(ObservableList<Assignment> assignmentList, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        assignmentListView.setItems(assignmentList);
        assignmentListView.setCellFactory(listView -> new AssignmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assignment} using a {@code
     * AssignmentCard}.
     */
    class AssignmentListViewCell extends ListCell<Assignment> {

        @Override
        protected void updateItem(Assignment assignment, boolean empty) {
            super.updateItem(assignment, empty);

            if (empty || assignment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssignmentCard(assignment, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
