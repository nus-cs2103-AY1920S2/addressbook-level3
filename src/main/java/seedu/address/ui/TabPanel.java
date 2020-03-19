package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
<<<<<<< HEAD
import seedu.address.model.listmanagers.accommodationbooking.AccommodationBooking;
=======
>>>>>>> Add Addactivity command
import seedu.address.model.listmanagers.activity.Activity;
import seedu.address.model.listmanagers.fixedexpense.FixedExpense;
import seedu.address.model.listmanagers.packinglistitem.PackingListItem;
import seedu.address.model.listmanagers.transportbooking.TransportBooking;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TabPanel.class);

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane transportBookingPanelPlaceholder;

    @FXML
    private StackPane fixedExpensePanelPlaceholder;

    @FXML
<<<<<<< HEAD
    private StackPane packingListPanelPlaceholder;

    @FXML
    private StackPane accommodationListPanelPlaceholder;

    @FXML
    private StackPane activitiesListPanelPlaceholder;
=======
    private StackPane activityPanelPlaceholder;

>>>>>>> Add Addactivity command

    public TabPanel(ObservableList<Person> personList,
                    ObservableList<TransportBooking> transportList,
                    ObservableList<FixedExpense>fixedExpenseList,
<<<<<<< HEAD
                    ObservableList<PackingListItem> packingList,
                    ObservableList<AccommodationBooking> accommodationList,
                    ObservableList<Activity> activityList
                    ) {
=======
                    ObservableList<Activity> activityList) {
>>>>>>> Add Addactivity command
        super(FXML);
        personListPanelPlaceholder.getChildren().add(new PersonListPanel(personList).getRoot());
        transportBookingPanelPlaceholder.getChildren().add(new TransportBookingPanel(transportList).getRoot());
        fixedExpensePanelPlaceholder.getChildren().add(new FixedExpensePanel(fixedExpenseList).getRoot());
<<<<<<< HEAD
        packingListPanelPlaceholder.getChildren().add(new PackingListPanel(packingList).getRoot());
        accommodationListPanelPlaceholder.getChildren().add(new AccommodationBookingPanel(accommodationList).getRoot());
        activitiesListPanelPlaceholder.getChildren().add(new ActivityPanel(activityList).getRoot());

=======
        activityPanelPlaceholder.getChildren().add(new ActivityPanel(activityList).getRoot());
>>>>>>> Add Addactivity command

    }
}
