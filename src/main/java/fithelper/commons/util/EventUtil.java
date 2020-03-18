package fithelper.commons.util;

import fithelper.model.entry.Entry;
import jfxtras.icalendarfx.components.VEvent;

public class EventUtil {
    /**
     * Maps event to VEvent
     */
    public static VEvent entryToVEvent(Entry entry) {
        VEvent vEvent = new VEvent();
        vEvent.setDateTimeStart(entry.getDateTime());
        vEvent.setDateTimeEnd(entry.getDateTime().plusHours(1));
        StringBuilder summary = new StringBuilder();
        summary.append(entry.getName());
        String temp = "";
        if (entry.getStatus().toString().equals("Done")) {
            temp = " D";
        } else {
            temp = " U";
        }
        summary.append(temp);
        vEvent.setSummary(summary.toString());
        vEvent.setUniqueIdentifier(getUniqueIdentifier(entry));
        if (entry.getStatus().toString().equals("Done")) {
            vEvent.withCategories("29");
        } else {
            vEvent.withCategories("24");
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
