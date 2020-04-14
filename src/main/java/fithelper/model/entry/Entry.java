package fithelper.model.entry;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Represents a Entry in the FitHelper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {

    private final Type type;
    private final Name name;
    private final Time time;
    private final Location location;
    private final Calorie calorie;
    private Status status;
    private Remark remark;
    private Duration duration;

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = new Status("Undone");
        this.remark = new Remark("");
        this.duration = new Duration("1");
    }

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Remark remark,
                 Duration duration) {

        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = new Status("Undone");
        this.remark = remark;
        this.duration = duration;
    }

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Remark remark) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = new Status("Undone");
        this.remark = remark;
        this.duration = new Duration("1");
    }

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Status status, Remark remark) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = status;
        this.remark = remark;
        this.duration = new Duration("1");
    }

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Status status) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = status;
        this.remark = new Remark("");
        this.duration = new Duration("1");
    }


    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie,
                 Duration duration) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = new Status("Undone");
        this.remark = new Remark("");
        this.duration = duration;
    }


    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Status status,
                 Remark remark, Duration duration) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = status;
        this.remark = remark;
        this.duration = duration;
    }

    /**
     * Every field must be present and not null.
     */
    public Entry(Type type, Name name, Time time, Location location, Calorie calorie, Status status,
                 Duration duration) {
        requireAllNonNull(type, name, location, time, calorie);
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
        this.calorie = calorie;
        this.status = status;
        this.remark = new Remark("");
        this.duration = duration;
    }

    public Type getType() {
        return type;
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Time getTime() {
        return time;
    }

    public LocalDate getDate() {
        return time.getDate();
    }

    public LocalDateTime getDateTime() {
        return time.getDateTime();
    }

    public LocalDateTime getEndTime() {
        return time.getDateTime().plusMinutes(getHours() * 60 + getMinutes());
    }

    public Calorie getCalorie() {
        return calorie;
    }

    public Double getCalorieValue() {
        return calorie.getValue();
    }

    public long getMinutes() {
        return duration.getMinutes();
    }

    public long getHours() {
        return duration.getHours();
    }

    public Duration getDuration() {
        return duration;
    }

    public void addRemark(Remark remark) {
        this.remark = remark;
    }

    public Remark getRemark() {
        return remark;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isFood() {
        return this.type.getValue().equals("food");
    }

    public boolean isSports() {
        return this.type.getValue().equals("sports");
    }

    public boolean isDone() {
        return this.getStatus().value.equalsIgnoreCase("Done");
    }

    public boolean isUndone() {
        return !this.isDone();
    }

    public boolean isToday(String dateStr) {
        return getTime().getDateStr().equalsIgnoreCase(dateStr);
    }

    /**
     * Returns true if both Entry of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two entries.
     */
    public boolean isSameEntry(Entry anotherEntry) {
        if (anotherEntry == this) {
            return true;
        }

        return anotherEntry != null
                && anotherEntry.getName().equals(getName())
                && (anotherEntry.getLocation().equals(getLocation()))
                && (anotherEntry.getTime().equals(getTime()))
                && (anotherEntry.getType().equals(getType()))
                && (anotherEntry.getStatus().equals(getStatus()))
                && (anotherEntry.getRemark().equals(getRemark()))
                && (anotherEntry.getCalorie().equals(getCalorie()))
                && (anotherEntry.getDuration().equals(getDuration()));
    }

    /**
     * Returns true if both Entry has time clashes.
     * This defines a weaker notion of equality between two entries.
     */
    public boolean hasTimeClashes(Entry anotherEntry) {
        if (anotherEntry == this) {
            return true;
        }
        java.time.Duration.between(anotherEntry.getDateTime(), getDateTime()).toMinutes();
        boolean hasClash;
        if (anotherEntry.getDateTime().isBefore(getDateTime())) {
            hasClash = anotherEntry != null && hasTimeClashes(anotherEntry, this);
        } else {
            hasClash = anotherEntry != null && hasTimeClashes(this, anotherEntry);
        }
        return hasClash;
    }

    /**
     * Checks whether two entries have any time clashes, returns true if have
     */
    public boolean hasTimeClashes(Entry entry, Entry toBeCompared) {
        long duration = entry.getDuration().getHours() * 60 + entry.getDuration().getMinutes();
        LocalDateTime temp = entry.getDateTime().plusMinutes(duration);
        return (temp.isAfter(toBeCompared.getDateTime()));
    }


    /**
     * Returns true if both entries have the same identity and data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherEntry = (Entry) other;
        return otherEntry.getName().equals(getName())
                && otherEntry.getType().equals(getType())
                && otherEntry.getLocation().equals(getLocation())
                && otherEntry.getTime().equals(getTime())
                && otherEntry.getCalorie().equals(getCalorie());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, name, time, location, calorie);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName() + "\n")
                .append("Type: ")
                .append(getType() + "\n")
                .append("Time: ")
                .append(getTime() + "\n")
                .append("Location: ")
                .append(getLocation() + "\n")
                .append("Calorie: ")
                .append(getCalorie() + "\n")
                .append("Status: ")
                .append(getStatus() + "\n")
                .append("Remark: ")
                .append(getRemark().toString() + "\n")
                .append("Duration: ")
                .append(getDuration());
        return builder.toString();
    }

}
