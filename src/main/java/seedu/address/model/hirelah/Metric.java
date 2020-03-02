package seedu.address.model.hirelah;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * Metric
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 02 Mar 2020
 *
 */

/**
 * <p>Metric class abstracts the comparator of the interviewee
 * based on the respective weight of the attributes.</p>
 * @author AY1920S2-W15-2
 */

public class Metric {
    public String name;
    public HashMap<Attribute, Double> attributeToWeight;

    public Metric(String name) {
        this.name = name;
        this.attributeToWeight = new HashMap<>();
    }

    public String setValueToAttribute(Attribute attribute, double value) {
        this.attributeToWeight.put(attribute, value);
        return String.format("Successfully set the value of attribute %s to %.2f", attribute, value);
    }

    public Comparator<Interviewee> getComparator() {
        return new Comparator<>() {
            @Override
            public int compare(Interviewee interviewee, Interviewee anotherInterviewee) {
                double totalScoreFirst = computeScore(interviewee);
                double totalScoreSecond = computeScore(anotherInterviewee);
                return totalScoreSecond - totalScoreFirst < 0 ? -1 : totalScoreFirst == totalScoreSecond ? 0 : 1;
            }
        }
    }

    public double computeScore(Interviewee interviewee) {
        double totalScore = 0;
        for (Map.Entry mapElement : attributeToWeight.entrySet()) {
            Attribute attribute = (Attribute)mapElement.getKey();
            double weight = (double)mapElement.getValue();

            totalScore += weight * interviewee.getScore(attribute);
        }
        return totalScore;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Metric // instanceof handles nulls
                && name.equals(((Metric) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
