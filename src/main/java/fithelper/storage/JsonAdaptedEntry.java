package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fithelper.commons.exceptions.IllegalValueException;

import fithelper.model.entry.Calorie;
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
                            @JsonProperty("remark") String remark) {
        this.type = type;
        this.name = name;
        this.time = time;
        this.location = location;
        this.calorie = calorie;
        this.status = status;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Entry} into this class for Jackson use.
     */
    public JsonAdaptedEntry(Entry source) {
        type = source.getType().value;
        name = source.getName().value;
        time = source.getTime().toString();
        location = source.getLocation().value;
        calorie = source.getCalorie().toString();
        status = source.getStatus().value;
        remark = source.getRemark().value;
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Entry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public Entry toModelType() throws IllegalValueException {

        // type.
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        // name.
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        // time.
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        // location.
        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        // calorie.
        if (calorie == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Calorie.class.getSimpleName()));
        }
        if (!Calorie.isValidCalorie(calorie)) {
            throw new IllegalValueException(Calorie.MESSAGE_CONSTRAINTS);
        }
        final Calorie modelCalorie = new Calorie(calorie);

        // status.
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        // remark.
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        return new Entry(modelType, modelName, modelTime, modelLocation, modelCalorie, modelStatus, modelRemark);
    }

}
