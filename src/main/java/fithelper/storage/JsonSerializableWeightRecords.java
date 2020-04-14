package fithelper.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.ReadOnlyWeightRecords;
import fithelper.model.WeightRecords;
import fithelper.model.weight.Weight;

/**
 * An Immutable WeightRecords that is serializable to JSON format.
 */
@JsonRootName(value = "weightrecords")
class JsonSerializableWeightRecords {

    public static final String MESSAGE_DUPLICATE_WEIGHT = "Weight list contains duplicate weight(s).";

    private final List<JsonAdaptedWeight> weights = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWeightRecords} with the given weights.
     */
    @JsonCreator
    public JsonSerializableWeightRecords(@JsonProperty("weights") List<JsonAdaptedWeight> weights) {
        this.weights.addAll(weights);
    }

    /**
     * Converts a given {@code ReadOnlyWeightRecords} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWeightRecords}.
     */
    public JsonSerializableWeightRecords(ReadOnlyWeightRecords source) {
        weights.addAll(source.getWeightList().stream().map(JsonAdaptedWeight::new).collect(Collectors.toList()));
    }

    /**
     * Converts this weightRecords into the model's {@code WeightRecords} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WeightRecords toModelType() throws IllegalValueException {
        WeightRecords weightRecords = new WeightRecords();
        for (JsonAdaptedWeight jsonAdaptedWeight : weights) {
            Weight weight = jsonAdaptedWeight.toModelType();
            if (weightRecords.hasWeight(weight)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WEIGHT);
            }
            weightRecords.addWeight(weight);
        }
        return weightRecords;
    }

}
