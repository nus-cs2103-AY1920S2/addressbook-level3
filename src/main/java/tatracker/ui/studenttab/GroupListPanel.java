//@@author fatin99

package tatracker.ui.studenttab;

import static tatracker.model.TaTracker.getCurrentlyShownGroup;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.group.Group;
import tatracker.ui.Focusable;
import tatracker.ui.UiPart;

/**
 * Panel containing the list of groups.
 */
public class GroupListPanel extends UiPart<Region> implements Focusable {
    private static final String FXML = "GroupListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<Group> groupListView;

    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
        groupListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                groupListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                groupListView.setStyle("");
            }
        });
    }

    @Override
    public void requestFocus() {
        groupListView.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return groupListView.isFocused();
    }

    /**
     * Update ListCells in order to facilitate highlighting when a filter command is entered
     * @param groupList the updated groupList
     */
    public void updateCells(ObservableList<Group> groupList) {
        logger.fine("reached updateCells");
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Group} using a {@code GroupCard}.
     */
    class GroupListViewCell extends ListCell<Group> {
        @Override
        protected void updateItem(Group group, boolean empty) {
            super.updateItem(group, empty);
            getStyleClass().removeAll("filtered", "list-cell");

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
                getStyleClass().add("list-cell");
                setStyle("");
            } else {
                setGraphic(new GroupCard(group, getIndex() + 1).getRoot());
                if (group.equals(getCurrentlyShownGroup())) {
                    getStyleClass().add("filtered");
                } else {
                    getStyleClass().add("list-cell");
                    setStyle("");
                }
            }
        }
    }
}
