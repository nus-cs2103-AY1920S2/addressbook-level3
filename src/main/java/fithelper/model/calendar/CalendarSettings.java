package fithelper.model.calendar;

import java.time.LocalDateTime;

/**
 * Stores information about user preferences
 */
public class CalendarSettings {
    private LocalDateTime date;
    private LocalDateTime show;
    private String mode;
    public CalendarSettings(LocalDateTime date, String mode) {
        this.date = date;
        this.mode = mode;
        this.show = null;
    }
    public LocalDateTime getDate() {
        return this.date;
    }
    public String getMode() {
        return this.mode;
    }
    public LocalDateTime getShow() {
        return this.show;
    }
    public void setDate(LocalDateTime dateToSet) {
        this.date = dateToSet;
    }
    public void setMode(String modeToSet) {
        this.mode = modeToSet;
    }
    public void setShow(LocalDateTime show) {
        this.show = show;
    }
}
