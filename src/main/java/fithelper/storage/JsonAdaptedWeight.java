package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.weight.Bmi;
import fithelper.model.weight.Date;
import fithelper.model.weight.Weight;
import fithelper.model.weight.WeightValue;

/**
 * Jackson-friendly version of {@link Weight}.
 */
class JsonAdaptedWeight {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Weight's %s field is missing!";

    private final String date;
    private final String weightValue;
    private final String bmi;

    /**
     * Constructs a {@code JsonAdaptedWeight} with the given weight details.
     */
    @JsonCreator
    public JsonAdaptedWeight(@JsonProperty("date") String date,
                             @JsonProperty("weightValue") String weightValue,
                             @JsonProperty("bmi") String bmi) {

        this.date = date;
        this.weightValue = weightValue;
        this.bmi = bmi;
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     */
    public JsonAdaptedWeight(Weight source) {
        bmi = source.getBmi().toString();
        date = source.getDate().toString();
        weightValue = source.getWeightValue().toString();
    }

    /**
     * Build {@code Bmi} based on Json file string.
     *
     * @return Attribute bmi.
     * @throws IllegalValueException Invalid value for bmi.
     */
    public Bmi buildBmi() throws IllegalValueException {
        if (bmi == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Bmi.class.getSimpleName()));
        }
        if (!Bmi.isValidBmi(Double.parseDouble(bmi))) {
            throw new IllegalValueException(Bmi.MESSAGE_CONSTRAINTS);
        }
        return new Bmi(Double.parseDouble(bmi));
    }

    /**
     * Build {@code Date} based on Json file string.
     *
     * @return Attribute date.
     * @throws IllegalValueException Invalid value for date.
     */
    public Date buildDate() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(date);
    }

    /**
     * Build {@code WeightValue} based on Json file string.
     *
     * @return Attribute weightValue.
     * @throws IllegalValueException Invalid value for weightValue.
     */
    public WeightValue buildWeightValue() throws IllegalValueException {
        if (weightValue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WeightValue.class.getSimpleName()));
        }
        if (!WeightValue.isValidWeightValue(weightValue)) {
            throw new IllegalValueException(WeightValue.MESSAGE_CONSTRAINTS);
        }
        return new WeightValue(weightValue);
    }

    /**
     * Converts this Jackson-friendly adapted weight object into the model's {@code Weight} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted weight.
     */
    public Weight toModelType() throws IllegalValueException {
        final Bmi modelBmi = buildBmi();
        final Date modelDate = buildDate();
        final WeightValue modelWeightValue = buildWeightValue();

        return new Weight(modelDate, modelWeightValue, modelBmi);
    }

}
