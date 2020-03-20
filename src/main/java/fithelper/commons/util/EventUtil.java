package fithelper.commons.util;

import java.util.List;

import fithelper.model.entry.Entry;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;

/**
 * A class for accessing and modifying VEvent.
 */
public class EventUtil {
    public static final String PINK = "group21";
    public static final String GREY = "group18";
    /**
     * Maps event to VEvent
     */
    public static VEvent entryToVEvent(Entry entry) {
        VEvent vEvent = new VEvent();
        vEvent.setDateTimeStart(entry.getDateTime());
        vEvent.setDateTimeEnd(entry.getDateTime().plusHours(entry.getDuration()));
        StringBuilder summary = new StringBuilder();
        summary.append(entry.getName());
        summary.append(" ");
        summary.append(entry.getLocation());
        summary.append(" ");
        summary.append(entry.getCalorie());
        vEvent.setSummary(summary.toString());
        vEvent.setUniqueIdentifier(getUniqueIdentifier(entry));
        if (entry.getStatus().toString().equals("Done")) {
            Categories categories = new Categories();
            // pink color in iCalendarAgenda
            categories.setValue(List.of(GREY));
            vEvent.setCategories(List.of(categories));
        } else {
            Categories categories = new Categories();
            // pink color in iCalendarAgenda
            categories.setValue(List.of(PINK));
            vEvent.setCategories(List.of(categories));
        }
        return vEvent;
    }


    public static String getUniqueIdentifier(Entry entry) {
        StringBuilder tmp = new StringBuilder();
        tmp.append(entry.getName().toString());
        tmp.append(entry.getDateTime());
        tmp.append("-");
        tmp.append(entry.getDateTime().plusMinutes(30));
        return tmp.toString();
    }
}
