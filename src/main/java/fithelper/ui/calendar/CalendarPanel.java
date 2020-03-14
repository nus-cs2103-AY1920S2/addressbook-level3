package fithelper.ui.calendar;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Display two calendars.
 */
public class CalendarPanel extends UiPart<AnchorPane> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private CalendarPage calendarPage;
    private FullCalendar fullCalendar;

    @FXML
    private StackPane calendarPagePlaceholder;

    @FXML
    private AnchorPane fullCalendarPlaceholder;

    /**
     * Creates a calendar page displaying two components from {@code }.
     */
    public CalendarPanel(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        super(FXML);
        logger.info("Initializing Calendar Page");
        this.calendarPage = new CalendarPage(foodList, sportList);
        this.fullCalendar = new FullCalendar(foodList, sportList);
        calendarPagePlaceholder.getChildren().add(this.calendarPage.getRoot());
        fullCalendarPlaceholder.getChildren().add(this.fullCalendar.getView());
    }


}
