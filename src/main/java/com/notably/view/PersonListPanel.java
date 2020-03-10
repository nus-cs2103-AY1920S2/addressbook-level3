package com.notably.view;

import com.notably.commons.core.LogsCenter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends ViewPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Object> personListView;

    public PersonListPanel(ObservableList<Object> personList) {
        super(FXML);
        personListView.setItems(personList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Object> {
        @Override
        protected void updateItem(Object person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
            }
        }
    }

}
