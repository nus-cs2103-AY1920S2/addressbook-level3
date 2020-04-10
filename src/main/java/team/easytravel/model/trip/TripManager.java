package team.easytravel.model.trip;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.core.time.Date;
import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.core.time.Time;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.exception.IllegalOperationException;
import team.easytravel.model.util.attributes.Title;

/**
 * Manages the trip of the application.
 */
public class TripManager {
    public static final String MESSAGE_ERROR_SET_TRIP = "You cannot do this as a trip has already been set.";
    public static final String MESSAGE_ERROR_NO_TRIP = "Trip has not been set. You need to first set a trip using "
            + "the \"settrip\" command.";
    private static final String MESSAGE_ERROR_SCHEDULE = "The ending time of the scheduled activity cannot go beyond "
            + "the scheduled day";

    private boolean hasTrip;
    private Trip trip;
    private List<DaySchedule> daySchedules;

    /**
     * Instantiates a new trip manager.
     */
    public TripManager() {
        hasTrip = false;
        trip = null;
        daySchedules = new ArrayList<>();
    }

    /**
     * Creates an TripManager using the Trip in the {@code} toBeCopied}
     */
    public TripManager(TripManager toBeCopied) {
        daySchedules = new ArrayList<>();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code TripManager} with {@code newData}
     */
    public void resetData(TripManager newData) {
        requireNonNull(newData);
        hasTrip = newData.hasTrip;
        trip = newData.trip;
        daySchedules.addAll(newData.daySchedules);
    }

    /**
     * Set a new trip with the given {@code trip}.
     */
    public void setTrip(Trip trip) {
        if (hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_SET_TRIP);
        }
        hasTrip = true;
        this.trip = trip;
        daySchedules = IntStream.range(0, trip.getNumDays())
                .mapToObj(x -> new DaySchedule())
                .collect(Collectors.toList());
    }

    public Trip getTrip() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip;
    }

    public Title getTripTitle() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip.getTitle();
    }

    public void setTripTitle(Title title) {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        this.trip.setTitle(title);
    }

    public int getTripNumDays() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip.getNumDays();
    }

    public Budget getTripBudget() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip.getBudget();
    }


    public void setBudget(Budget editedBudget) {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }

        this.trip.setBudget(editedBudget);
    }

    public ExchangeRate getExchangeRate() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip.getExchangeRate();

    }

    public Date getTripStartDate() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip.getStartDate();
    }

    public Date getTripEndDate() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return trip.getEndDate();
    }

    public ObservableList<DayScheduleEntry> getDayScheduleEntryList(int dayIndex) {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return daySchedules.get(dayIndex).getDayScheduleList();
    }

    public List<ObservableList<DayScheduleEntry>> getDayScheduleEntryLists() {
        if (!hasTrip) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        return daySchedules.stream().map(DaySchedule::getDayScheduleList).collect(Collectors.toList());
    }

    /**
     * Returns true if there exist a trip
     */
    public boolean hasTrip() {
        return hasTrip;
    }

    /**
     * Returns the duration of the trip
     */
    public int getDuration() {
        return this.trip.getNumDays();
    }

    /**
     * Returns the status of this trip's schedule.
     */
    public String getScheduleStatus() {
        List<Index> daysWithOverlaps = new ArrayList<>();
        int size = getTripNumDays();
        for (int i = 0; i < size; i++) {
            boolean isOverlapping = false;
            List<DayScheduleEntry> dayScheduleEntryList = getDayScheduleEntryList(i);
            for (DayScheduleEntry scheduleEntry : dayScheduleEntryList) {
                if (dayScheduleEntryList.stream()
                        .anyMatch(entry -> !scheduleEntry.equals(entry) && scheduleEntry.isOverlapping(entry))) {
                    isOverlapping = true;
                }
            }
            if (isOverlapping) {
                daysWithOverlaps.add(Index.fromZeroBased(i));
            }
        }
        if (daysWithOverlaps.isEmpty()) {
            return "[✔] There are no overlapping programs in your schedule.";
        }
        return "[❌] Day "
                + daysWithOverlaps.stream()
                .map(Index::getOneBased)
                .map(Object::toString)
                .collect(Collectors.joining(" "))
                + " has/have overlapping program(s)";
    }

    /**
     * Schedule all activities and transport booking into the shcedule.
     */
    public void scheduleAll(List<Activity> activities, List<TransportBooking> transportBookings) {
        if (!hasTrip()) {
            throw new IllegalOperationException(MESSAGE_ERROR_NO_TRIP);
        }
        activities.stream().filter(activity -> activity.getScheduledDateTime().isPresent())
                .forEach(this::scheduleActivity);
        transportBookings.forEach(this::scheduleTransportBooking);
    }

    /**
     * Schedule an activity into a into the schedule.
     */
    public void scheduleActivity(Activity activityToSchedule) {
        DateTime startDateTime = activityToSchedule.getScheduledDateTime()
                .orElseThrow(() -> new IllegalOperationException(DayScheduleEntry.MESSAGE_ERROR_ACTIVITY_SCHEDULING));
        if (startDateTime.getDate().compareTo(getTripStartDate()) < 0) {
            throw new IllegalOperationException("The activity to be scheduled starts earlier than the trip.");
        }
        int dayIndex = getTripStartDate().daysUntilInclusive(startDateTime.getDate()) - 1;
        DaySchedule daySchedule = daySchedules.get(dayIndex);
        DayScheduleEntry entry = DayScheduleEntry.fromActivity(activityToSchedule);
        DateTime endDateTime = entry.getEndDateTime();

        int numDays = startDateTime.getDate().daysUntilInclusive(endDateTime.getDate());
        if (!(numDays == 2 && endDateTime.getTime().equals(Time.fromString("00:00"))) && numDays != 1) {
            throw new IllegalOperationException(MESSAGE_ERROR_SCHEDULE);
        }
        daySchedule.addScheduleEntry(entry);
    }

    /**
     * Unschedule an activity from the schedule.
     */
    public void unscheduleActivity(DayScheduleEntry toDelete) {
        DateTime startDateTime = toDelete.getStartDateTime();
        int dayIndex = getTripStartDate().daysUntilInclusive(startDateTime.getDate()) - 1;
        DaySchedule daySchedule = daySchedules.get(dayIndex);
        daySchedule.removeScheduleEntry(toDelete);
    }

    /**
     * Schedule a transport booking into the schedule.
     */
    public void scheduleTransportBooking(TransportBooking transportBookingToSchedule) {
        DateTime startDateTime = transportBookingToSchedule.getStartDateTime();
        if (startDateTime.getDate().compareTo(getTripStartDate()) < 0) {
            throw new IllegalOperationException("The transport booking is earlier than the trip.");
        }
        DateTime endDateTime = transportBookingToSchedule.getEndDateTime();
        if (endDateTime.getDate().compareTo(getTripEndDate()) > 0) {
            throw new IllegalOperationException("The transport booking ends later than the trip.");
        }
        int dayIndex = getTripStartDate().daysUntilInclusive(startDateTime.getDate()) - 1;
        DaySchedule daySchedule = daySchedules.get(dayIndex);
        DayScheduleEntry entry = DayScheduleEntry.fromTransportBooking(transportBookingToSchedule);
        daySchedule.addScheduleEntry(entry);
    }

    /**
     * Unschedule a specified transport booking from the schedule.
     */
    public void unscheduleTransportBooking(DayScheduleEntry toDelete) {
        DateTime startDateTime = toDelete.getStartDateTime();
        int dayIndex = getTripStartDate().daysUntilInclusive(startDateTime.getDate()) - 1;
        DaySchedule daySchedule = daySchedules.get(dayIndex);
        daySchedule.removeScheduleEntry(toDelete);
    }

    @Override
    public String toString() {
        return "TripManager - hasTrip: " + hasTrip + " trip: {" + trip + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasTrip, trip, daySchedules);
    }
}

