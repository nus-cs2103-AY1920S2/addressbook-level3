package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fithelper.commons.exceptions.IllegalValueException;

import fithelper.model.entry.Calorie;
import fithelper.model.entry.Duration;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Remark;
import fithelper.model.entry.Status;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Entry's %s field is missing!";

    private final String type;
    private final String name;
    private final String time;
    private final String location;
    private final String calorie;
    private final String status;
    private final String remark;
    private final String duration;

    /**
     * Constructs a {@code JsonAdaptedEntry} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedEntry(@JsonProperty("type") String type,
                            @JsonProperty("name") String name,
                            @JsonProperty("time") String time,
                            @JsonProperty("location") String location,
                            @JsonProperty("calorie") String calorie,
                            @JsonProperty("status") String status,
                            @JsonProperty("remark") String remark,
                            @JsonProperty("duration") String duration) {
        this.type = type;
        this.name = name;
        this.time = time;
        this.location = location;
        this.calorie = calorie;
        this.status = status;
        this.remark = remark;
        this.duration = duration;
    }

    /**
     * Converts a given {@code Entry} into this class for Jackson use.
     */
    public JsonAdaptedEntry(Entry source) {
        type = source.getType().getValue();
        name = source.getName().value;
        time = source.getTime().toString();
        location = source.getLocation().value;
        calorie = source.getCalorie().toString();
        status = source.getStatus().value;
        remark = source.getRemark().value;
        duration = source.getDuration().value;
    }

    /**
     * Build {@code Type} based on Json file string.
     *
     * @return Attribute type.
     * @throws IllegalValueException Invalid value for type.
     */
    public Type buildType() throws IllegalValueException {
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(type);
    }

    /**
     * Build {@code Name} based on Json file string.
     *
     * @return Attribute name.
     * @throws IllegalValueException Invalid value for name.
     */
    public Name buildName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Build {@code Time} based on Json file string.
     *
     * @return Attribute time.
     * @throws IllegalValueException Invalid value for time.
     */
    public Time buildTime() throws IllegalValueException {
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(time);
    }

    /**
     * Build {@code Location} based on Json file string.
     *
     * @return Attribute location.
     * @throws IllegalValueException Invalid value for location.
     */
    public Location buildLocation() throws IllegalValueException {
        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(location);
    }

    /**
     * Build {@code Calorie} based on Json file string.
     *
     * @return Attribute calorie.
     * @throws IllegalValueException Invalid value for calorie.
     */
    public Calorie buildCalorie() throws IllegalValueException {
        if (calorie == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Calorie.class.getSimpleName()));
        }
        if (!Calorie.isValidCalorie(calorie)) {
            throw new IllegalValueException(Calorie.MESSAGE_CONSTRAINTS);
        }
        return new Calorie(calorie);
    }

    /**
     * Build {@code Status} based on Json file string.
     *
     * @return Attribute status.
     * @throws IllegalValueException Invalid value for status.
     */
    public Status buildStatus() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(status);
    }

    /**
     * Build {@code Remark} based on Json file string.
     *
     * @return Attribute remark.
     * @throws IllegalValueException Invalid value for remark.
     */
    public Remark buildRemark() throws IllegalValueException {
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        return new Remark(remark);
    }

    /**
     * Build {@code Duration} based on Json file string.
     *
     * @return Attribute duration.
     * @throws IllegalValueException Invalid value for duration.
     */
    public Duration buildDuration() throws IllegalValueException {
        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        return new Duration(duration);
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Entry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public Entry toModelType() throws IllegalValueException {
        final Type modelType = buildType();
        final Name modelName = buildName();
        final Time modelTime = buildTime();
        final Location modelLocation = buildLocation();
        final Calorie modelCalorie = buildCalorie();
        final Status modelStatus = buildStatus();
        final Remark modelRemark = buildRemark();
        final Duration modelDuration = buildDuration();

        return new Entry(modelType, modelName, modelTime, modelLocation, modelCalorie,
                modelStatus, modelRemark, modelDuration);
    }

}
