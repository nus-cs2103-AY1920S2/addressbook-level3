package team.easytravel.ui;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.ui.accommodationtab.AccommodationBookingTabPanel;
import team.easytravel.ui.activitiestab.ActivityTabPanel;
import team.easytravel.ui.expensestab.FixedExpenseTabPanel;
import team.easytravel.ui.help.HelpTabPanel;
import team.easytravel.ui.packinglisttab.PackingListTabPanel;
import team.easytravel.ui.scheduletab.ScheduleTabPanel;
import team.easytravel.ui.transportationtab.TransportBookingTabPanel;

/**
 * Panel containing the list of persons.
 */
public class SideTabsBar extends UiPart<Region> {
    public static final String STYLE_BUTTON_SELECTED = "button-selected";
    private static final String FXML = "SideTabsBar.fxml";

    private final Logger logger = LogsCenter.getLogger(SideTabsBar.class);

    private Function<String, Function<Runnable, Consumer<Button>>> consumer;

    @FXML
    private Button schedule;

    @FXML
    private Button activities;

    @FXML
    private Button accommodation;

    @FXML
    private Button transportation;

    @FXML
    private Button packingList;

    @FXML
    private Button fixedExpenses;

    @FXML
    private Button help;

    public SideTabsBar(Function<String, Function<Runnable, Consumer<Button>>> consumer) {
        super(FXML);
        this.consumer = consumer;
    }

    @FXML
    public void handleSwitchToScheduleTab() {
        consumer.apply(ScheduleTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(schedule);
    }

    @FXML
    public void handleSwitchToActivitiesTab() {
        consumer.apply(ActivityTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(activities);
    }

    @FXML
    public void handleSwitchToAccommodationTab() {
        consumer.apply(AccommodationBookingTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(accommodation);
    }

    @FXML
    public void handleSwitchToTransportationTab() {
        consumer.apply(TransportBookingTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(transportation);
    }

    @FXML
    public void handleSwitchToPackingListTab() {
        consumer.apply(PackingListTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(packingList);
    }

    @FXML
    public void handleSwitchToFixedExpensesTab() {
        consumer.apply(FixedExpenseTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(fixedExpenses);
    }

    @FXML
    public void handleSwitchToHelpTab() {
        consumer.apply(HelpTabPanel.TAB_NAME).apply(this::clearAllSelected).accept(help);
    }

    /**
     * Clears the selected styling of all the buttons in this SideTabBar.
     */
    private void clearAllSelected() {
        schedule.getStyleClass().remove(STYLE_BUTTON_SELECTED);
        activities.getStyleClass().remove(STYLE_BUTTON_SELECTED);
        accommodation.getStyleClass().remove(STYLE_BUTTON_SELECTED);
        transportation.getStyleClass().remove(STYLE_BUTTON_SELECTED);
        packingList.getStyleClass().remove(STYLE_BUTTON_SELECTED);
        fixedExpenses.getStyleClass().remove(STYLE_BUTTON_SELECTED);
        help.getStyleClass().remove(STYLE_BUTTON_SELECTED);
    }
}
