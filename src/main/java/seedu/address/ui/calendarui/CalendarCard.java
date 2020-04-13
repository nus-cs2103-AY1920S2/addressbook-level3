package seedu.address.ui.calendarui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.task.Task;
import seedu.address.model.util.DailySchedulable;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a class or task.
 */
public class CalendarCard extends UiPart<Region> {

    private static final String FXML = "CalendarCard.fxml";

    @FXML
    private StackPane cardPane;

    @FXML
    private VBox card;

    @FXML
    private Label description;

    @FXML
    private Label time;

    /**
     * Constructs a card for Schedulable item.
     */
    public CalendarCard(DailySchedulable item) {
        super(FXML);

        if (item instanceof Task) {
            initTaskCard((Task) item);
        }

        if (item instanceof Lesson) {
            initLessonCard((Lesson) item);
        }
        description.setStyle("-fx-font-weight: bold;");
        time.setStyle("-fx-font-weight: bold;");
    }

    /**
     * Initializes a Calendar Card for a task
     */
    private void initTaskCard(Task task) {
        String describing = String.format("[%s %d] %s", task.getModuleCode(), task.getTaskNum(), task.getDescription());
        description.setText(describing);

        if (!task.getTimeString().equals("")) {
            time.setText(task.getTimeString());
        }

        if (task.isTaskDone()) {
            card.setStyle("-fx-background-color: #4BB543; -fx-background-radius: 15px; "
                    + "-fx-background-insets: 3px, 0px; -fx-padding: 10px");
        } else {
            card.setStyle("-fx-background-color: #9d6365; -fx-background-radius: 15px; "
                    + "-fx-background-insets: 3px, 0px; -fx-padding: 10px");
        }
    }

    /**
     * Initializes a Calendar Card for a lesson
     */
    private void initLessonCard(Lesson lesson) {
        String code = lesson.getModuleCode().toString();
        switch (lesson.getType()) {
        case LAB:
            description.setText(String.format("%s LAB", code));
            break;
        case REC:
            description.setText(String.format("%s REC", code));
            break;
        case LEC:
            description.setText(String.format("%s LEC", code));
            break;
        case SEC:
            description.setText(String.format("%s SEC", code));
            break;
        case TUT:
            description.setText(String.format("%s TUT", code));
            break;
        default:
            break;
        }
        time.setText(lesson.getDayAndTime().getStartTime().toString() + " - "
                + lesson.getDayAndTime().getEndTime().toString());
    }
}
