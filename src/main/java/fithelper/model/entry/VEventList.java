package fithelper.model.entry;
import fithelper.commons.core.LogsCenter;
import fithelper.ui.calendar.CalendarPage;
import java.util.ArrayList;
import static java.util.Objects.requireNonNull;
import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

public class VEventList {
    private final ObservableList<VEvent> vEvents = FXCollections.observableArrayList();
    private final ObservableList<VEvent> vEventUnmodifiableList =
    FXCollections.unmodifiableObservableList(vEvents);

    public VEventList() {
    }
    /**
     * Generates a new list of events based on two lists
     */
    public VEventList(FilteredList<Entry> filteredFoodEntries, FilteredList<Entry> filteredSportsEntries) {
        this();
        refreshedList(filteredFoodEntries, filteredSportsEntries);
    }

    /**
     * Refreshes the existing list with new lists.
     */
    public void refreshedList(FilteredList<Entry> foodEntries, FilteredList<Entry> sportsEntries) {
        requireNonNull(foodEntries);
        requireNonNull(sportsEntries);
        ArrayList<VEvent> vEvents = entriesToVEvents(foodEntries, sportsEntries);
        requireAllNonNull(vEvents);
        this.vEvents.setAll(vEvents);
    }

    /**
     * Maps events to VEvents
     */
    private ArrayList<VEvent> entriesToVEvents(FilteredList<Entry> foodEntries, FilteredList<Entry> sportsEntries) {
        ArrayList<VEvent> VEventList = new ArrayList();
        for (Entry entry: foodEntries) {
            VEventList.add(entryToVEvent(entry));
        }
        for (Entry entry: sportsEntries) {
            VEventList.add(entryToVEvent(entry));
        }
        return VEventList;
    }

    private VEvent entryToVEvent(Entry entry) {
        VEvent vEvent = new VEvent();
        vEvent.setDateTimeStart(entry.getDateTime());
        vEvent.setDateTimeEnd(entry.getDateTime().plusHours(1));
        vEvent.setSummary(entry.getName().toString());
        vEvent.setUniqueIdentifier(getUniqueIdentifier(entry));
        if (entry.getStatus().toString().equals("Done")) {
            vEvent.withCategories("25");
        } else {
            vEvent.withCategories("29");
        }
        return vEvent;
    }

    /**
     * Returns an unmodifiable ObservableList.
     */
    public ObservableList<VEvent> getVEvents() {
        return this.vEventUnmodifiableList;
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
