package fithelper.model.calendar;

import java.time.LocalDate;

/**
 * Stores information about user preferences
 */
public class CalendarSettings {
    private LocalDate date;
    private LocalDate show;
    private String mode;
    public CalendarSettings(LocalDate date, String mode) {
        this.date = date;
        this.mode = mode;
        this.show = null;
    }
    public LocalDate getDate() {
        return this.date;
    }
    public String getMode() {
        return this.mode;
    }
    public LocalDate getShow() {
        return this.show;
    }
    public void setDate(LocalDate dateToSet) {
        this.date = dateToSet;
    }
    public void setMode(String modeToSet) {
        this.mode = modeToSet;
    }
    public void setShow(LocalDate show) {
        this.show = show;
    }
}
