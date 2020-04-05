package seedu.address.ui.academics;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.academics.Assessment;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of students.
 */
public class AcademicsPanel extends UiPart<Region> {
    private static final String FXML = "AcademicsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AcademicsPanel.class);

    @FXML
    private ListView<Assessment> assessmentListView;

    public AcademicsPanel(ObservableList<Assessment> assessmentList) {
        super(FXML);
        assessmentListView.setItems(assessmentList);
        assessmentListView.setCellFactory(listView -> new AssessmentListViewCell());
    }

    public AcademicsPanel(ObservableList<Assessment> assessmentList, String type) {
        super(FXML);
        assessmentListView.setItems(assessmentList);
        switch (type) {
        case "all":
            assessmentListView.setCellFactory(listView -> new AssessmentListViewCell());
            break;
        case "homework":
            assessmentListView.setCellFactory(listView -> new AssessmentListViewCellHomework());
            break;
        case "exam":
            assessmentListView.setCellFactory(listView -> new AssessmentListViewCellExam());
            break;
        case "report":
            assessmentListView.setCellFactory(listView -> new AssessmentListViewCellReport());
            break;
        default:
            assert type.equals("all") || type.equals("completed")
                    || type.equals("detailed") : "The string type is invalid.";
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assessment} using an {@code AssessmentCard}.
     */
    class AssessmentListViewCell extends ListCell<Assessment> {
        @Override
        protected void updateItem(Assessment assessment, boolean empty) {
            super.updateItem(assessment, empty);

            if (empty || assessment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssessmentCard(assessment, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assessment} using an {@code AssessmentCard}.
     */
    class AssessmentListViewCellHomework extends ListCell<Assessment> {
        @Override
        protected void updateItem(Assessment assessment, boolean empty) {
            super.updateItem(assessment, empty);

            if (empty || assessment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssessmentCardHomework(assessment, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assessment} using an {@code AssessmentCard}.
     */
    class AssessmentListViewCellExam extends ListCell<Assessment> {
        @Override
        protected void updateItem(Assessment assessment, boolean empty) {
            super.updateItem(assessment, empty);

            if (empty || assessment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssessmentCardExam(assessment, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assessment} using an {@code AssessmentCard}.
     */
    class AssessmentListViewCellReport extends ListCell<Assessment> {
        @Override
        protected void updateItem(Assessment assessment, boolean empty) {
            super.updateItem(assessment, empty);

            if (empty || assessment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssessmentCardReport(assessment, getIndex() + 1).getRoot());
            }
        }
    }
}
