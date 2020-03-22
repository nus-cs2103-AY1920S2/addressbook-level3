package seedu.address.model.hirelah;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * MetricList
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>MetricList class manages the list of metrics that have been
 * added by the interviewer. MetricList can retrieve the attributes using
 * the prefix.</p>
 * @author AY1920S2-W15-2
 */

public class MetricList {
    private static final String ALREADY_EXISTS_MESSAGE = "This metric is already exists!";
    private static final String DUPLICATE_MESSAGE = "There are multiple metrics with the same prefix.";
    private static final String INCOMPLETE_MESSAGE = "The argument given is incomplete.";
    private static final String NOT_FOUND_MESSAGE = "No metrics with the entered prefix.";

    private ObservableList<Metric> metrics;

    /**
     * Constructs a MetricList instance.
     */
    public MetricList() {
        this.metrics = FXCollections.observableArrayList();
    }

    public ObservableList<Metric> getObservableList() {
        return metrics;
    }

    /**
     * Adds the metric to the list.
     * @param metricName The metric name.
     * @throws IllegalValueException If the name of the metric already exists.
     */

    public void add(String metricName, AttributeList attributes,
                    ArrayList<String> attributePrefixes, ArrayList<Double> weightages) throws IllegalValueException {
        Metric metric = Metric.of(metricName);
        checkCompleteArgument(attributePrefixes, weightages, attributes);
        boolean isDuplicate = isDuplicate(metric);

        if (isDuplicate) {
            throw new IllegalValueException(ALREADY_EXISTS_MESSAGE);
        }

        metrics.add(metric);
    }

    /**
     * Find the metric based on its prefix.
     * @param metricPrefix The prefix of the metric.
     * @return The corresponding Attribute instance.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Metric found.
     */

    public Metric find(String metricPrefix) throws IllegalValueException {
        Optional<Metric> exactMetric = metrics.stream().filter(metric -> metric.toString()
                                                                               .equals(metricPrefix))
                                                       .findFirst();
        if (exactMetric.isEmpty()) {
            checkPrefix(metricPrefix);
            return metrics.stream().filter(metric -> metric.toString().startsWith(metricPrefix))
                    .findFirst()
                    .get();
        } else {
            return exactMetric.get();
        }

    }

    /**
     * Deletes the metric by its prefix.
     * @param metricPrefix The prefix of the attribute.
     * @return The outcome message.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Attribute found.
     */

    public String delete(String metricPrefix) throws IllegalValueException {
        Metric metric = find(metricPrefix);
        metrics.remove(metric);
        return String.format("Successfully removed metric: %s", metric);
    }

    /**
     * Checks the number of metrics that starts with the prefix.
     * @param metricPrefix The prefix of the attribute.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Metric found.
     */

    private void checkPrefix(String metricPrefix) throws IllegalValueException {
        long startWithPrefix = metrics.stream()
                .filter(metric -> metric.toString().startsWith(metricPrefix))
                .count();

        if (startWithPrefix > 1) {
            throw new IllegalValueException(DUPLICATE_MESSAGE);
        } else if (startWithPrefix == 0) {
            throw new IllegalValueException(NOT_FOUND_MESSAGE);
        }
    }

    private void checkCompleteArgument(ArrayList<String> attributePrefixes, ArrayList<Double> weightages,
                                       AttributeList attributes) throws IllegalValueException {
        HashMap<Attribute, Boolean> checklist = initiateChecklist(attributes);

        for (String prefix : attributePrefixes) {
            Attribute attribute = attributes.find(prefix);
            checklist.put(attribute, true);
        }

        if (isNotCompleteChecklist(checklist) && weightages.size() != attributePrefixes.size()) {
            throw new IllegalValueException(INCOMPLETE_MESSAGE);
        }
    }

    private boolean isNotCompleteChecklist(HashMap<Attribute, Boolean> checklist) {
        boolean result = true;

        for (Map.Entry<Attribute, Boolean> entry : checklist.entrySet()) {
            result = result && entry.getValue();
        }
        return !result;
    }

    private HashMap<Attribute, Boolean> initiateChecklist(AttributeList attributes) {
        ObservableList<Attribute> attributeList = attributes.getObservableList();
        HashMap<Attribute, Boolean> hashMap = new HashMap<>();

        for (Attribute attribute: attributeList) {
            hashMap.put(attribute, false);
        }

        return hashMap;
    }

    private boolean isDuplicate(Metric metric) {
        return metrics.stream().anyMatch(metric::equals);
    }
}
