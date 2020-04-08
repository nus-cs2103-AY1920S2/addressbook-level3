package fithelper.ui.calendar;

import java.time.LocalDateTime;
import java.util.Locale;

import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

/**
 * Displays all food/sports items on calendar.
 */

public class CalendarPage extends UiPart<AnchorPane> {
    private static final Locale UK_LOCALE = Locale.UK;
    private static final String FXML = "CalendarPanel.fxml";
    private ICalendarAgenda agenda;

    @FXML
    private BorderPane calendarBorderPane;

    public CalendarPage(ObservableList<VEvent> events) {
        super(FXML);
        VCalendar vCalendar = new VCalendar();
        vCalendar.setVEvents(events);
        this.agenda = new ICalendarAgenda(vCalendar);
        initCalendar(this.agenda);
        calendarBorderPane.setCenter(agenda);
        calendarBorderPane.setMaxWidth(500);
        calendarBorderPane.setMaxHeight(500);
    }

    private void initCalendar(ICalendarAgenda agenda) {
        setLocale(agenda, UK_LOCALE);
        setWeeklySkin();
        disableMouseClick(this.agenda);
    }

    /**
     * Change the EventSchedulePanel to show the time interval including this targetDateTime. If in daily skin,
     * simply show the date. If in weekly skin, show the week, with start day defined as in locale, including
     * @param targetDateTime
     * @param targetDateTime the desired dateTime to be viewed.
     */
    public void setDisplayedDateTime(LocalDateTime targetDateTime) {
        this.agenda.setDisplayedLocalDateTime(targetDateTime);
    }

    /**
     * Method to set locale of ICalendarAgenda. Note that the locale decides the starting day of a week.
     * @param agenda ICalendarAgenda to be set
     * @param locale desired locale to be set
     */
    private void setLocale(ICalendarAgenda agenda, Locale locale) {
        agenda.setLocale(locale);
    }

    /**
     * Disables all mouse click related actions. Scrolling is still allowed
     * @param agenda ICalendarAgenda to be disabled
     */
    private void disableMouseClick(ICalendarAgenda agenda) {
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
        agenda.setActionCallback(null);
        agenda.setNewAppointmentCallback(null);
        agenda.setSelectedOneAppointmentCallback(null);
        agenda.setNewAppointmentDrawnCallback(null);
        agenda.setAppointmentChangedCallback(null);
        agenda.setOnMouseClicked(null);
        agenda.setOnMousePressed(null);
        agenda.setAllowDragging(false);
        agenda.setOnTouchPressed(null);
        agenda.setOnMouseEntered(null);
        agenda.setOnMouseExited(null);
    }

    /**
     * Sets the calendar to week format.
     */
    public void setWeeklySkin() {
        AgendaWeekSkin weekSkin = new AgendaWeekSkin(this.agenda);
        agenda.setSkin(weekSkin);
    }

    /**
     * Sets the calendar to daily format.
     */
    public void setDailySkin() {
        AgendaDaySkin dailySkin = new AgendaDaySkin(this.agenda);
        agenda.setSkin(dailySkin);
    }

    /**
     * Updates the scheduler to be reflected on UI
     */
    public void updateScheduler() {
        this.agenda.updateAppointments();
    }

    /**
     * Update the calendar interface based on a date
     * @param dateToSet the date to be referenced by
     */
    public void setDate(LocalDateTime dateToSet) {
        this.agenda.setDisplayedLocalDateTime(dateToSet);
    }
}
