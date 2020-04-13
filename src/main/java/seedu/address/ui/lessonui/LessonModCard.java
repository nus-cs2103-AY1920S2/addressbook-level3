package seedu.address.ui.lessonui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.Lesson;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Facilitator} for a module.
 */
public class LessonModCard extends UiPart<Region> {
    private static final String FXML = "LessonListCard.fxml";

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label type;
    @FXML
    private VBox cardbox;
    @FXML
    private Label id;
    @FXML
    private Label day;
    @FXML
    private Label time;
    @FXML
    private Label venue;

    public LessonModCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText(displayedIndex + ". ");
        type.setText(lesson.getType().toString());

        day.setText(lesson.getDayAndTime().getDay().toString());
        time.setText(lesson.getDayAndTime().getStartTime().toString() + "-"
                + lesson.getDayAndTime().getEndTime().toString());

        if (lesson.getVenue() != null) {
            venue.setText(lesson.getVenue());
        } else {
            cardbox.getChildren().remove(venue);
        }

        cardbox.setMinHeight(0);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonModCard)) {
            return false;
        }

        // state check
        LessonModCard card = (LessonModCard) other;
        return id.getText().equals(card.id.getText())
                && lesson.equals(card.lesson);
    }
}
