package seedu.address.ui.admin;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.admin.Date;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of dates.
 */
public class DateListPanel extends UiPart<Region> {
    private static final String FXML = "DateListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DateListPanel.class);

    @FXML
    private ListView<Date> dateListView;

    public DateListPanel(ObservableList<Date> dateList) {
        super(FXML);
        dateListView.setItems(dateList);
        dateListView.setCellFactory(listView -> new DateListPanel.DateListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Date} using a {@code DateCard}.
     */
    class DateListViewCell extends ListCell<Date> {
        @Override
        protected void updateItem(Date date, boolean empty) {
            super.updateItem(date, empty);

            if (empty || date == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DateCard(date, getIndex() + 1).getRoot());
            }
        }
    }
}
