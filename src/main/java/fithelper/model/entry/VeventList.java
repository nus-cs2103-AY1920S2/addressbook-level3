package fithelper.model.entry;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

/**
 *  A list of VEvents.
 */
public class VeventList {
    private final ObservableList<VEvent> vEvents = FXCollections.observableArrayList();
    private final ObservableList<VEvent> vEventUnmodifiableList =
        FXCollections.unmodifiableObservableList(vEvents);

    public VeventList() {
    }
    /**
     * Generates a new list of events based on two lists
     */
    public VeventList(FilteredList<Entry> filteredFoodEntries, FilteredList<Entry> filteredSportsEntries) {
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
        ArrayList<VEvent> veventList = new ArrayList();
        for (Entry entry: foodEntries) {
            veventList.add(entryToVEvent(entry));
        }
        for (Entry entry: sportsEntries) {
            veventList.add(entryToVEvent(entry));
        }
        return veventList;
    }

    /**
     * Maps event to VEvent
     */
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
