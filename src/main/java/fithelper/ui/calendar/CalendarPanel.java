package fithelper.ui.calendar;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import jfxtras.icalendarfx.components.VEvent;

/**
 * Display two calendars.
 */
public class CalendarPanel extends UiPart<AnchorPane> {
    private static final String FXML = "CalendarPanel.fxml";
    private ObservableList<Entry> foodList;
    private ObservableList<Entry> sportList;
    private final CalendarPage calendarPage;
    private MonthView monthView;
    private UpcomingList upcomingList;
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    @FXML
    private StackPane calendarPagePlaceholder;

    @FXML
    private AnchorPane monthViewPlaceholder;

    @FXML
    private StackPane upcomingListPlaceholder;

    /**
     * Creates a calendar page displaying two components from {@code }.
     */
    public CalendarPanel(ObservableList<Entry> foodList, ObservableList<Entry> sportList,
                         ObservableList<VEvent> events) {
        super(FXML);
        this.foodList = foodList;
        this.sportList = sportList;
        logger.info("Initializing Calendar Page");
        calendarPage = new CalendarPage(events);
        monthView = new MonthView(LocalDateTime.now());
        upcomingList = new UpcomingList(foodList, sportList, LocalDateTime.now());
        calendarPage.updateScheduler();
        calendarPagePlaceholder.getChildren().add(calendarPage.getRoot());
        monthViewPlaceholder.getChildren().add(monthView.getView());
        upcomingListPlaceholder.getChildren().add(upcomingList.getRoot());
    }

    public void updateScheduler() {
        calendarPage.updateScheduler();
    }

    // set date reference based on parameter date
    public void setDate(LocalDateTime date) {
        calendarPage.setDate(date);
        monthView = new MonthView(date);
        upcomingList = new UpcomingList(foodList, sportList, date);
        upcomingListPlaceholder.getChildren().clear();
        upcomingListPlaceholder.getChildren().add(upcomingList.getRoot());
        monthViewPlaceholder.getChildren().clear();
        monthViewPlaceholder.getChildren().add(monthView.getView());

    }
}
