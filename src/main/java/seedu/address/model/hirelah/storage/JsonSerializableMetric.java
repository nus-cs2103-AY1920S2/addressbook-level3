package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.Metric;
import seedu.address.model.hirelah.MetricList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable JsonAdaptedMetrics that is serializable to JSON format {@link JsonAdaptedMetric}.
 */
@JsonRootName(value = "metrics")
public class JsonSerializableMetric {
    private final List<JsonAdaptedMetric> metrics = new ArrayList<>();
    /**
     * Converts a given {@code MetricList} into this class for Jackson use.
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    @JsonCreator
    public JsonSerializableMetric(@JsonProperty("metrics") List<JsonAdaptedMetric> source) {
        this.metrics.addAll(source);
    }

    public JsonSerializableMetric(MetricList source) throws IllegalValueException {
        List<Metric> convertedList = source.getObservableList();
        metrics.addAll(convertedList.stream().map(JsonAdaptedMetric::new).collect(Collectors.toList()));
    }
    public MetricList toModelType() throws IllegalValueException {
        MetricList newData = new MetricList();
        for (JsonAdaptedMetric jsonAdaptedmetrics : metrics) {
            Metric metric = jsonAdaptedmetrics.toModelType();
            /**
             * implementation to be confirmed
             */
        }
        return newData;
    }
}
