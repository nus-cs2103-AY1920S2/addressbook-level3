package fithelper.ui.diary;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.diary.Diary;
import fithelper.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for diary page.
 * An diary page contains diary pages.
 */
public class DiaryPage extends UiPart<AnchorPane> {
    private static final String FXML = "DiaryPage.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryPage.class);

    @FXML
    private ListView<Diary> diaryListView;

    /**
     * Creates a diary page displaying diaries from {@code diaryList}.
     */
    public DiaryPage(ObservableList<Diary> diaryList) {
        super(FXML);

        logger.info("Initializing Diary Page");

        initializeDiaryListView(diaryList);
    }

    /**
     * Initializes the list view.
     * @param diaryList an observable list of diaries
     */
    private void initializeDiaryListView(ObservableList<Diary> diaryList) {
        diaryListView.setItems(diaryList);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());
    }

    /**
     * Constructs sportListView Cell class.
     */
    static class DiaryListViewCell extends ListCell<Diary> {
        /**
         * Update diary cell.
         * @param diary
         * @param empty
         */
        protected void updateItem(Diary diary, boolean empty) {
            super.updateItem(diary, empty);
            updateSelected(false);
            if (empty || diary == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DiaryCard(diary).getRoot());
            }
        }
    }
}


