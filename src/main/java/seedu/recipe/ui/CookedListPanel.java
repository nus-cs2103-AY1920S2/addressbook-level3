package seedu.recipe.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.model.cooked.Record;

/**
 * Panel containing the list of recipes.
 */
public class CookedListPanel extends UiPart<Region> {
    private static final String FXML = "CookedListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CookedListPanel.class);

    @FXML
    private ListView<Record> cookedListView;

    public CookedListPanel(ObservableList<Record> recordList) {
        super(FXML);
        cookedListView.setItems(recordList);
        cookedListView.setCellFactory(listView -> new RecordListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    class RecordListViewCell extends ListCell<Record> {
        @Override
        protected void updateItem(Record record, boolean empty) {
            super.updateItem(record, empty);
            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new CookedCard(record, getIndex() + 1).getRoot());
                } catch (IOException e) {
                    logger.warning("Failed to load : " + StringUtil.getDetails(e));
                }
            }
        }
    }

}
