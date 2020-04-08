package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.diary.DiaryEntry;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class DiaryEntryMainPage extends UiPart<Region> {

    private static final String FXML = "DiaryEntryMainPage.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final DiaryEntry diaryEntry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label weather;
    @FXML
    private Label mood;
    @FXML
    private TextArea text;

    public DiaryEntryMainPage(DiaryEntry diaryEntry) {
        super(FXML);
        this.diaryEntry = diaryEntry;
        date.setText(diaryEntry.getDate().toString());
        weather.setText(diaryEntry.getWeather().get().toString());
        mood.setText(diaryEntry.getMood().get().toString());
        text.setText(diaryEntry.getEntryContent());
        text.setWrapText(true);

        text.setStyle("-fx-text-inner-color: white;");


    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DiaryEntryMainPage)) {
            return false;
        }
        return false;
    }
}
