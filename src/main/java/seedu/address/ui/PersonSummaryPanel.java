package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bluetooth.Person;

import java.util.logging.Logger;

public class PersonSummaryPanel extends UiPart<Region> {
    private static final String FXML = "BluetoothPingPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BluetoothPingSummaryPanel.class);

    @FXML
    private ListView<Person> bluetoothPingsListView;

    public PersonSummaryPanel(ObservableList<Person> personSummaryList) {
        super(FXML);
        bluetoothPingsListView.setItems(personSummaryList);
        bluetoothPingsListView.setCellFactory(listView -> new PersonSummaryPanel.personListViewCell());
    }

    class personListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonSummaryCard(person, getIndex() +1).getRoot());
            }
        }
    }
}
