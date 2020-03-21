package fithelper.ui.diary;

import fithelper.model.diary.Diary;

import fithelper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * An UI component that displays information of a {@code Diary}.
 */
public class DiaryCard extends UiPart<AnchorPane> {

    private static final String FXML = "DiaryCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Diary diary;

    @FXML
    private AnchorPane innerPane;

    @FXML
    private Label diaryDate;
    @FXML
    private Label content;

    public DiaryCard(Diary diary) {
        super(FXML);
        this.diary = diary;
        diaryDate.setText(diary.getDiaryDate().toString());
        content.setText(diary.getContent().value);
    }

    /**
     * Creates a card displaying the {@code diary diary}.
     *
     * @param diary the list of diary entries display
     * @param displayedIndex the index of the order to show on the card
     */
    public DiaryCard(Diary diary, int displayedIndex) {
        super(FXML);
        this.diary = diary;
        fillInDetails(displayedIndex);
    }

    /**
     * Fills in details in GUI, in list view.
     * @param displayedIndex the index of the order to show on the card
     */
    private void fillInDetails(int displayedIndex) {
        diaryDate.setText(diary.getDiaryDate().toString());
        content.setText(diary.getContent().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof fithelper.ui.diary.DiaryCard)) {
            return false;
        }

        // state check
        fithelper.ui.diary.DiaryCard card = (fithelper.ui.diary.DiaryCard) other;
        return diary.equals(card.diary);
    }
}

