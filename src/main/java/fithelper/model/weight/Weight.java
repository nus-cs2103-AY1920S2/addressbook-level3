package fithelper.model.weight;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Weight record in the FitHelper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Weight {

    private final Date date;
    private WeightValue weightValue;
    private Bmi bmi;


    /**
     * Every field must be present and not null.
     */
    public Weight(Date date, WeightValue weightValue, Bmi bmi) {
        requireAllNonNull(date, weightValue, bmi);
        this.date = date;
        this.weightValue = weightValue;
        this.bmi = bmi;
    }

    public Date getDate() {
        return date;
    }

    public WeightValue getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(WeightValue weightValue) {
        this.weightValue = weightValue;
    }

    public Bmi getBmi() {
        return bmi;
    }

    public void setBmi(Bmi bmi) {
        this.bmi = bmi;
    }

    /**
     * Returns true if both Weight record have the same date value.
     * This defines a weaker notion of equality between two weight records
     */
    public boolean isSameWeight(Weight anotherWeight) {
        if (anotherWeight == this) {
            return true;
        }

        return anotherWeight != null
                && anotherWeight.getDate().equals(getDate());
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

        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;
        return otherWeight.getWeightValue().equals(getWeightValue())
                && otherWeight.getDate().equals(getDate())
                && otherWeight.getBmi().equals(getBmi());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, weightValue, bmi);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(" Weight Value: ")
                .append(getWeightValue())
                .append(" Bmi: ")
                .append(getBmi());
        return builder.toString();
    }

}
