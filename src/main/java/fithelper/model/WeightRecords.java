package fithelper.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import fithelper.model.weight.Bmi;
import fithelper.model.weight.Date;
import fithelper.model.weight.UniqueWeightList;
import fithelper.model.weight.Weight;
import fithelper.model.weight.WeightValue;
import javafx.collections.ObservableList;

/**
 * Wraps all weight-related data at the WeightRecords level
 * Duplicates are not allowed (by .isSameWeight comparison)
 */
public class WeightRecords implements ReadOnlyWeightRecords {

    private final UniqueWeightList weightRecords = new UniqueWeightList();

    public WeightRecords() {
    }

    /**
     * Creates a WeightRecords using the Weights in the {@code toBeCopied}
     */
    public WeightRecords(ReadOnlyWeightRecords toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the weight list with {@code weightRecords}.
     * {@code weightRecords} must not contain duplicate weightRecords.
     */
    public void setWeights(List<Weight> weightRecords) {
        List<Weight> weightList = new ArrayList<>(weightRecords);
        this.weightRecords.setWeights(weightList);
    }

    /**
     * Replaces the given weight {@code target} in the list with {@code editedWeight}.
     * {@code target} must exist in the Weight Records.
     * The date of {@code editedWeight} must not be the same as another existing weight in the Weight Records.
     */
    public void setWeight(Weight target, Weight editedWeight) {
        requireNonNull(editedWeight);
        weightRecords.setWeight(target, editedWeight);
    }

    /**
     * Resets the existing data of this {@code WeightRecords} with {@code newData}.
     */
    public void resetData(ReadOnlyWeightRecords newData) {
        requireNonNull(newData);
        setWeights(newData.getWeightList());
    }


    //// weight-level operations

    /**
     * Returns true if a weight with the same date as {@code weight} exists in WeightRecords.
     */
    public boolean hasWeight(Weight weight) {
        requireNonNull(weight);
        return weightRecords.contains(weight);
    }

    /**
     * Adds a weight to WeightRecords.
     * The weight must not already exist in WeightRecords.
     */
    public void addWeight(Weight weight) {
        weightRecords.add(weight);
    }

    /**
     * Removes {@code key} from this {@code WeightRecords}.
     * {@code key} must exist in WeightRecords.
     */
    public void removeWeight(Weight key) {
        weightRecords.remove(key);
    }

    public Weight getWeightByDate(Date date) {
        for (Weight weight: weightRecords) {
            if (weight.getDate().equals(date)) {
                return weight;
            }
        }
        return null;
    }

    /**
     * Edit weight value and BMI value of a weight record.
     * @param newWeight the weight record to be edited.
     * @param weightValue new weight value.
     * @param bmi new BMI value.
     */
    public void editWeight(Weight newWeight, WeightValue weightValue, Bmi bmi) {
        for (Weight weight: weightRecords) {
            if (weight.equals(newWeight)) {
                weight.setWeightValue(weightValue);
                weight.setBmi(bmi);
            }
        }
    }

    //// util methods

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Weight Records: ")
                .append(weightRecords.asUnmodifiableObservableList().size())
                .append("\n");
        return builder.toString();
    }

    /**
     * Returns an unmodifiable view of the weight list.
     * This list will not contain any duplicate weight records.
     */
    @Override
    public ObservableList<Weight> getWeightList() {
        return weightRecords.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightRecords // instanceof handles nulls
                && weightRecords.equals(((WeightRecords) other).weightRecords));
    }

    @Override
    public int hashCode() {
        return weightRecords.hashCode();
    }
}
