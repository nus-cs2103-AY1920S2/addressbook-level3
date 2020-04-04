package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;

/**
 * Panel containing the list of persons.
 */
public class AssignmentListPanel extends UiPart<Region> {
    private static final String FXML = "AssignmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AssignmentListPanel.class);

    @FXML
    private ListView<Assignment> assignmentListView;

    @FXML
    private Label title;

    public AssignmentListPanel(ObservableList<Assignment> assignmentList) {
        super(FXML);

        title.setText("To-Do List:");

        assignmentListView.setItems(assignmentList);
        assignmentListView.setCellFactory(listView -> new AssignmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class AssignmentListViewCell extends ListCell<Assignment> {
        @Override
        protected void updateItem(Assignment assignment, boolean empty) {
            super.updateItem(assignment, empty);

            if (empty || assignment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssignmentCard(assignment, getIndex() + 1).getRoot());
            }
        }
    }

}
