package fithelper.testutil;

import fithelper.model.entry.Calorie;
import fithelper.model.entry.Duration;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;

/**
 * A utility class to help with building Entry objects.
 */
public class EntryBuilder {

    public static final String DEFAULT_NAME = "Ice-cream";
    public static final String DEFAULT_TIME = "2020-05-01-10:00";
    public static final String DEFAULT_LOCATION = "711";
    public static final String DEFAULT_CALORIE = "100";
    public static final String DEFAULT_TYPE = "food";
    public static final String DEFAULT_DURATION = "1";

    private Name name;
    private Location location;
    private Time time;
    private Calorie calorie;
    private Type type;
    private Duration duration;

    public EntryBuilder() {
        type = new Type(DEFAULT_TYPE);
        name = new Name(DEFAULT_NAME);
        location = new Location(DEFAULT_LOCATION);
        calorie = new Calorie(DEFAULT_CALORIE);
        time = new Time(DEFAULT_TIME);
        duration = new Duration(DEFAULT_DURATION);
    }

    /**
     * Initializes the EntryBuilder with the data of {@code entryToCopy}.
     */
    public EntryBuilder(Entry entryToCopy) {
        name = entryToCopy.getName();
        time = entryToCopy.getTime();
        calorie = entryToCopy.getCalorie();
        location = entryToCopy.getLocation();
        type = entryToCopy.getType();
        duration = entryToCopy.getDuration();
    }

    /**
     * Sets the {@code Name} of the {@code Entry} that we are building.
     */
    public EntryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code Entry} that we are building.
     */
    public EntryBuilder withDuration(String duration) {
        this.duration = new Duration(duration);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Entry} that we are building.
     */
    public EntryBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Entry} that we are building.
     */
    public EntryBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code Entry} that we are building.
     */
    public EntryBuilder withCalorie(String calorie) {
        this.calorie = new Calorie(calorie);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Entry} that we are building.
     */
    public EntryBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    public Entry build() {
        return new Entry(type, name, time, location, calorie, duration);
    }

}
