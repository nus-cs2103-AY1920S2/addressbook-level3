package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bluetooth.CommandList;

import java.util.logging.Logger;

public class HelpPanel extends UiPart<Region> {
    private static final String FXML = "HelpPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HelpPanel.class);

    @FXML
    private ListView<CommandList> commandListListView;

    public HelpPanel(ObservableList<CommandList> commandList) {
        super(FXML);
        commandListListView.setItems(commandList);
        commandListListView.setCellFactory(listView -> new HelpPanel.CommandListViewCell());
    }

    class CommandListViewCell extends ListCell<CommandList> {
        @Override
        protected void updateItem(CommandList commandList, boolean empty) {
            super.updateItem(commandList, empty);

            if (empty || commandList == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HelpCard(commandList, getIndex() +1).getRoot());
            }
        }
    }
}
