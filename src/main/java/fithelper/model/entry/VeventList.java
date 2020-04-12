package fithelper.model.entry;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static fithelper.commons.util.EventUtil.entryToVEvent;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import fithelper.commons.exceptions.IllegalValueException;

import fithelper.model.entry.exceptions.DuplicateEntryException;
import fithelper.model.entry.exceptions.EntryNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;



/**
 *  A list of VEvents.
 */
public class VeventList {
    private ObservableList<VEvent> vEvents = FXCollections.observableArrayList();
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
    public void refreshedList(ObservableList<Entry> foodEntries, ObservableList<Entry> sportsEntries) {
        requireNonNull(foodEntries);
        requireNonNull(sportsEntries);
        ArrayList<VEvent> vEvents = entriesToVEvents(foodEntries, sportsEntries);
        requireAllNonNull(vEvents);
        this.vEvents.setAll(vEvents);
    }

    /**
     * Maps events to VEvents
     */
    private ArrayList<VEvent> entriesToVEvents(ObservableList<Entry> foodEntries, ObservableList<Entry> sportsEntries) {
        ArrayList<VEvent> veventList = new ArrayList<>();
        for (Entry entry: foodEntries) {
            veventList.add(entryToVEvent(entry));
        }
        for (Entry entry: sportsEntries) {
            veventList.add(entryToVEvent(entry));
        }
        return veventList;
    }

    /**
     * Returns an unmodifiable ObservableList.
     */
    public ObservableList<VEvent> getVEvents() {
        return this.vEventUnmodifiableList;
    }

    /**
     * Add a new vEvent to the vEvents list.
     *
     * @param entry to add to the list after converted to VEvent.
     */
    public void addVEvent(Entry entry) throws IllegalValueException {
        VEvent vEvent = entryToVEvent(entry);
        requireNonNull(vEvent);
        if (contains(vEvent)) {
            throw new IllegalValueException("Duplicated VEvents cannot be added");
        }
        vEvents.add(vEvent);
    }

    /**
     * Returns true if the list contains an equivalent VEvent as the given argument.
     */
    public boolean contains(VEvent event) {
        requireNonNull(event);
        return vEvents.stream().anyMatch(vEvent -> equals(vEvent, event));
    }

    /**
     * Returns true if the two parameters are equal.
     */
    private boolean equals(VEvent vEvent, VEvent event) {
        boolean isEqual = true;
        if (!vEvent.getSummary().equals(event.getSummary())
            || !vEvent.getDateTimeEnd().equals(event.getDateTimeEnd())
            || !vEvent.getDateTimeStart().equals(event.getDateTimeStart())
            || !vEvent.getCategories().equals(event.getCategories())) {
            isEqual = false;
        }
        return isEqual;
    }

    /**
     * Replace the old entry with the updated one.
     * @param entry the old entry
     * @param newEntry updated entry
     */
    public void setVEvent(Entry entry, Entry newEntry) {
        VEvent newEvent = entryToVEvent(newEntry);
        VEvent event = entryToVEvent(entry);
        requireAllNonNull(newEvent, event);
        int index = vEvents.indexOf(event);
        if (index == -1) {
            throw new EntryNotFoundException();
        }

        if (!event.equals(newEvent) && contains(newEvent)) {
            throw new DuplicateEntryException();
        }
        vEvents.set(index, newEvent);
    }

    /**
     * Removes the equivalent entry from the list.
     * The entry must exist in the list.
     */
    public void deleteVEvent(Entry toRemove) {
        requireNonNull(toRemove);
        VEvent temp = entryToVEvent(toRemove);
        if (!vEvents.remove(temp)) {
            throw new EntryNotFoundException();
        }
    }

    public void clearList() {
        this.vEvents.clear();
    }
}
