package hirelah.storage;

import static hirelah.model.hirelah.Metric.isValidMetricName;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.Metric;

/**
 * Jackson-friendly version of {@link Metric}.
 */
public class JsonAdaptedMetric {
    public static final String MESSAGE_CONSTRAINTS = "Invalid %s stored!";
    private String name;
    private String attributeToWeight;

    /**
     * Constructs a {@code JsonAdaptedMetric} with the the relevant details.
     */
    @JsonCreator
    public JsonAdaptedMetric(@JsonProperty("name") String source,
                             @JsonProperty("attributeToWeight") String attributeToWeight) {
        this.name = source;
        this.attributeToWeight = attributeToWeight;
    }


    public JsonAdaptedMetric(Metric source) {
        this.name = source.toString();
        this.attributeToWeight = source.hashMapToString();
    }
    /**
     * Converts this Jackson-friendly adapted metric object into the model's {@code Metric} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted metric.
     */
    public Metric toModelType() throws IllegalValueException {
        if (!isValidMetricName(name)) {
            throw new IllegalValueException(String.format(MESSAGE_CONSTRAINTS, "NAME"));
        }
        if (attributeToWeight == null || attributeToWeight.length() == 0) {
            throw new IllegalValueException(String.format(MESSAGE_CONSTRAINTS, "pair of Attribute to weight"));
        }
        String[] source = attributeToWeight.split("-a");
        HashMap<Attribute, Double> destination = new HashMap<>();
        for (int i = 0; i < source.length; i++) {
            Attribute key = new Attribute(source[i++]);
            Double value = Double.parseDouble(source[i]);
            destination.put(key, value);
        }
        return new Metric(name, destination);
    }
}
