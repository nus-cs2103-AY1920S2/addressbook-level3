package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.diary.DiaryEntry;

/**
 * Panel containing the list of Notes.
 */
public class DiaryListPanel extends UiPart<Region> {
    private static final String FXML = "DiaryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryListPanel.class);

    @FXML
    private ListView<DiaryEntry> diaryListView;

    public DiaryListPanel(ObservableList<DiaryEntry> diaryList) {
        super(FXML);
        diaryListView.setItems(diaryList);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Notes} using a {@code NotesCard}.
     */
    class DiaryListViewCell extends ListCell<DiaryEntry> {
        @Override
        protected void updateItem(DiaryEntry diaryEntries, boolean empty) {
            super.updateItem(diaryEntries, empty);

            if (empty || diaryEntries.toString() == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DiaryEntriesCard(diaryEntries, getIndex() + 1).getRoot());
            }
        }
    }

}
