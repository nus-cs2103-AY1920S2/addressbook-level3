package fithelper.model.calendar;

import java.time.LocalDateTime;

/**
 * Stores information about user preferences
 */
public class CalendarSettings {
    private LocalDateTime date;
    public CalendarSettings(LocalDateTime date) {
        this.date = date;
    }
    public LocalDateTime getDate() {
        return this.date;
    }
}
