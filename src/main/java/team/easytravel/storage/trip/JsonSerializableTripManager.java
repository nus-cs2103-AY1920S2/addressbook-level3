package team.easytravel.storage.trip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.trip.TripManager;

/**
 * An Immutable TripManager that is serializable to JSON format.
 */
@JsonRootName(value = "tripManager")
public class JsonSerializableTripManager {
    private final boolean hasTrip;
    private final JsonAdaptedTrip jsonAdaptedTrip;

    /**
     * Constructs a {@code JsonSerializableTripManager} with the given trip.
     */
    @JsonCreator
    public JsonSerializableTripManager(
            @JsonProperty("trip") JsonAdaptedTrip jsonAdaptedTrip) {
        if (jsonAdaptedTrip != null) {
            hasTrip = true;
            this.jsonAdaptedTrip = jsonAdaptedTrip;
        } else {
            hasTrip = false;
            this.jsonAdaptedTrip = null;
        }
    }

    /**
     * Converts a given {@code TripManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTripManager}.
     */
    public JsonSerializableTripManager(TripManager source) {
        if (source.hasTrip()) {
            hasTrip = true;
            jsonAdaptedTrip = new JsonAdaptedTrip(source.getTrip());
        } else {
            hasTrip = false;
            jsonAdaptedTrip = null;
        }
    }

    /**
     * Converts this JsonSerializableTripManager into the model's {@code TripManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TripManager toModelType() throws IllegalValueException {
        TripManager tripManager = new TripManager();
        if (hasTrip) {
            tripManager.setTrip(jsonAdaptedTrip.toModelType());
        }
        return tripManager;
    }
}
