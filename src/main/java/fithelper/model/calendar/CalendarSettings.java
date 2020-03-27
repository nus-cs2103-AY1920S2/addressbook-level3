package fithelper.model.calendar;

import java.time.LocalDateTime;

/**
 * Stores information about user preferences
 */
public class CalendarSettings {
    private LocalDateTime date;
    private String mode;
    public CalendarSettings(LocalDateTime date, String mode) {
        this.date = date;
        this.mode = mode;
    }
    public LocalDateTime getDate() {
        return this.date;
    }
    public String getMode() {
        return this.mode;
    }
    public void setDate(LocalDateTime dateToSet) {
        this.date = dateToSet;
    }
    public void setMode(String modeToSet) {
        this.mode = modeToSet;
    }
}
