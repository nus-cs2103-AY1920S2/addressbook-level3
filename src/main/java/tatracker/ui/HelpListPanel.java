//@@author potatocombat

package tatracker.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.logic.commands.CommandDetails;

/**
 * Panel containing the list of groups.
 */
public class HelpListPanel extends UiPart<Region> implements Focusable {
    private static final String FXML = "HelpListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(HelpListPanel.class);

    @FXML
    private ListView<CommandDetails> helpListView;

    public HelpListPanel(ObservableList<CommandDetails> helpList) {
        super(FXML);
        logger.fine("Showing Help List");
        helpListView.setItems(helpList);
        helpListView.setCellFactory(listView -> new HelpListViewCell());
    }

    @Override
    public void requestFocus() {
        helpListView.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return helpListView.isFocused();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Group} using a {@code GroupCard}.
     */
    class HelpListViewCell extends ListCell<CommandDetails> {
        @Override
        protected void updateItem(CommandDetails details, boolean empty) {
            super.updateItem(details, empty);

            if (empty || details == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HelpCard(details).getRoot());
            }
        }
    }
}
