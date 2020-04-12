package seedu.address.ui.uiStaff;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.modelStaff.Staff;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;

/**
 * Panel containing the list of staffs.
 */
public class StaffListPanel extends UiPart<Region> {

    private static final String FXML = "StaffListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StaffListPanel.class);
    private CommandBox commandBox;

    @FXML
    private ListView<Staff> staffListView;

    public StaffListPanel(ObservableList<Staff> staffList, CommandBox commandBox) {
        super(FXML);
        this.commandBox = commandBox;
        staffListView.setItems(staffList);
        staffListView.setCellFactory(listView -> new StaffListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Staff} using a {@code
     * StaffCard}.
     */
    class StaffListViewCell extends ListCell<Staff> {

        @Override
        protected void updateItem(Staff staff, boolean empty) {
            super.updateItem(staff, empty);

            if (empty || staff == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StaffCard(staff, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

}
