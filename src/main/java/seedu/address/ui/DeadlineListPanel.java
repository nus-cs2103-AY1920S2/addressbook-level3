package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.profile.Profile;

/**
 * Panel containing the list of persons.
 */
public class DeadlineListPanel extends UiPart<Region> {
    private static final String FXML = "DeadlineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);

    @FXML
    private ListView<Profile> personListView;

    public DeadlineListPanel(ObservableList<Profile> profileList) {
        super(FXML);
        personListView.setItems(profileList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Profile} using a {@code DeadlineCard}.
     */
    class PersonListViewCell extends ListCell<Profile> {
        @Override
        protected void updateItem(Profile profile, boolean empty) {
            super.updateItem(profile, empty);

            if (empty || profile == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeadlineCard(profile, getIndex() + 1).getRoot());
            }
        }
    }

}
