package seedu.address.ui;

import static seedu.address.model.notes.Notes.PRIORITY_HIGH;
import static seedu.address.model.notes.Notes.PRIORITY_LOW;
import static seedu.address.model.notes.Notes.PRIORITY_MEDIUM;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.notes.Notes;

/**
 * Represents a Controller for NotesCard fxml.
 */
public class NotesCard extends UiPart<Region> {

    private static final String FXML = "NotesCard.fxml";

    public final Notes note;

    @FXML
    private VBox notesPane;
    @FXML
    private Label name;
    @FXML
    private Label priority;
    @FXML
    private Label content;
    @FXML
    private Label dateTime;

    /**
     * Constructor for NotesCard
     * @param note, with student's name, date added and content
     * @param displayedIndex, the Note's position in the ListView
     */
    public NotesCard(Notes note, int displayedIndex) {
        super(FXML);
        this.note = note;
        name.setText("[#" + displayedIndex + "] Student: " + note.getStudent());
        content.setText("\n" + note.getContent());
        priority.setText("Priority: " + note.getPriority());
        dateTime.setText("Added on: " + note.getDateTime());
        switch (note.getPriority()) {
        case PRIORITY_HIGH:
            notesPane.setStyle("-fx-background-color: #FF6347;");
            break;
        case PRIORITY_MEDIUM:
            notesPane.setStyle("-fx-background-color: #FF8C00;");
            break;
        case PRIORITY_LOW:
            notesPane.setStyle("-fx-background-color: #fff556;");
            break;
        default:
            notesPane.setStyle("-fx-background-color: #fff556;");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotesCard)) {
            return false;
        }

        // state check
        NotesCard card = (NotesCard) other;
        //needs to edit
        return true;
    }

}
