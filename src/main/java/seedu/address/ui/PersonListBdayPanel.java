package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of Persons whose birthday is upcoming (in the next 5 days).
 */
public class PersonListBdayPanel extends UiPart<Region> {
    private static final String FXML = "personListBdayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListBdayPanel.class);

    @FXML
    private ListView<Person> personListView;

    public PersonListBdayPanel(ObservableList<Person> bdayList) {
        super(FXML);
        personListView.setItems(bdayList);
        personListView.setCellFactory(listView -> new PersonListBdayPanel.PersonBdayViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonBdayViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
