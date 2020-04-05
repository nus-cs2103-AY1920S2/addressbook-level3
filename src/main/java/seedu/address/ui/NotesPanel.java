package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.notes.Notes;


/**
 * Represents a Controller class for NotesPanel fxml
 */
public class NotesPanel extends UiPart<Region> {
    private static final String FXML = "NotesPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NotesPanel.class);

    @FXML
    private ListView<Notes> notesView;

    /**
     * Constructor of NotesPanel, with an input of list of students.
     * @param notesList
     */
    public NotesPanel(ObservableList<Notes> notesList) {
        super(FXML);
        notesView.setItems(notesList);
        notesView.setCellFactory(view -> new NotesViewCell());
    }


    /**
     * Displays the note graphics
     */
    class NotesViewCell extends ListCell<Notes> {
        @Override
        protected void updateItem(Notes note, boolean empty) {
            super.updateItem(note, empty);

            if (empty || note == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: #FFE8D7;");
            } else {
                setGraphic(new NotesCard(note, getIndex() + 1).getRoot());
            }
        }
    }


}
