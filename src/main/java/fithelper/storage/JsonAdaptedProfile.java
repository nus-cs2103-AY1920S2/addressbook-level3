package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fithelper.commons.exceptions.IllegalValueException;

import fithelper.model.profile.Address;
import fithelper.model.profile.Age;
import fithelper.model.profile.Gender;
import fithelper.model.profile.Height;
import fithelper.model.profile.Name;
import fithelper.model.profile.Profile;
import fithelper.model.profile.TargetWeight;
import fithelper.model.weight.Bmi;
import fithelper.model.weight.WeightValue;

/**
 * Jackson-friendly version of {@link Profile}.
 */
class JsonAdaptedProfile {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Profile's %s field is missing!";

    private final String name;
    private final String gender;
    private final String age;
    private final String address;
    private final String height;
    private final String targetWeight;
    private final String currentWeight;
    private final String currentBmi;

    /**
     * Constructs a {@code JsonAdaptedProfile} with the given profile details.
     */
    @JsonCreator
    public JsonAdaptedProfile(@JsonProperty("name") String name,
                              @JsonProperty("gender") String gender,
                              @JsonProperty("age") String age,
                              @JsonProperty("address") String address,
                              @JsonProperty("height") String height,
                              @JsonProperty("targetWeight") String targetWeight,
                              @JsonProperty("currentWeight") String currentWeight,
                              @JsonProperty("currentBmi") String currentBmi) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.height = height;
        this.targetWeight = targetWeight;
        this.currentWeight = currentWeight;
        this.currentBmi = currentBmi;
    }

    /**
     * Converts a given {@code Profile} into this class for Jackson use.
     */
    public JsonAdaptedProfile(Profile source) {
        name = source.getName().value;
        gender = source.getGender().value;
        age = String.valueOf(source.getAge().value);
        address = source.getAddress().value;
        height = String.valueOf(source.getHeight().value);
        targetWeight = String.valueOf(source.getTargetWeight().value);
        currentWeight = (source.getCurrentWeight() == null) ? "" : String.valueOf(source.getCurrentWeight().value);
        currentBmi = (source.getCurrentBmi() == null) ? "" : String.valueOf(source.getCurrentBmi().value);
    }

    /**
     * Build {@code Name} based on Json file string.
     *
     * @return Attribute name.
     * @throws IllegalValueException Invalid value for name.
     */
    public Name buildName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Build {@code Gender} based on Json file string.
     *
     * @return Attribute gender.
     * @throws IllegalValueException Invalid value for gender.
     */
    public Gender buildGender() throws IllegalValueException {
        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(gender);
    }

    /**
     * Build {@code Age} based on Json file string.
     *
     * @return Attribute age.
     * @throws IllegalValueException Invalid value for age.
     */
    public Age buildAge() throws IllegalValueException {
        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(age);
    }

    /**
     * Build {@code Address} based on Json file string.
     *
     * @return Attribute address.
     * @throws IllegalValueException Invalid value for address.
     */
    public Address buildAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(address);
    }

    /**
     * Build {@code Height} based on Json file string.
     *
     * @return Attribute height.
     * @throws IllegalValueException Invalid value for height.
     */
    public Height buildHeight() throws IllegalValueException {
        if (height == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Height.class.getSimpleName()));
        }
        if (!Height.isValidHeight(height)) {
            throw new IllegalValueException(Height.MESSAGE_CONSTRAINTS);
        }
        return new Height(height);
    }

    /**
     * Build {@code TargetWeight} based on Json file string.
     *
     * @return Attribute targetWeight.
     * @throws IllegalValueException Invalid value for targetWeight.
     */
    public TargetWeight buildTargetWeight() throws IllegalValueException {
        if (targetWeight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TargetWeight.class.getSimpleName()));
        }
        return new TargetWeight(targetWeight);
    }

    /**
     * Build {@code WeightValue} based on Json file string.
     *
     * @return Attribute currentWeight.
     * @throws IllegalValueException Invalid value for currentWeight.
     */
    public WeightValue buildWeightValue() throws IllegalValueException {
        if ("".equals(currentWeight)) {
            return null;
        }
        if (!WeightValue.isValidWeightValue(currentWeight)) {
            throw new IllegalValueException(WeightValue.MESSAGE_CONSTRAINTS);
        }
        return new WeightValue(currentWeight);
    }

    /**
     * Build {@code Bmi} based on Json file string.
     *
     * @return Attribute currentBmi.
     * @throws IllegalValueException Invalid value for currentBmi.
     */
    public Bmi buildBmi() throws IllegalValueException {
        if ("".equals(currentBmi)) {
            return null;
        }
        if (!Bmi.isValidBmi(Double.parseDouble(currentBmi))) {
            throw new IllegalValueException(Bmi.MESSAGE_CONSTRAINTS);
        }
        return new Bmi(Double.parseDouble(currentBmi));
    }

    /**
     * Converts this Jackson-friendly adapted profile object into the model's {@code Profile} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted profile.
     */
    public Profile toModelType() throws IllegalValueException {
        final Name modelName = buildName();
        final Gender modelGender = buildGender();
        final Age modelAge = buildAge();
        final Address modelAddress = buildAddress();
        final Height modelHeight = buildHeight();
        final TargetWeight modelTargetWeight = buildTargetWeight();
        final WeightValue modelCurrentWeight = buildWeightValue();
        final Bmi modelCurrentBmi = buildBmi();

        if (modelCurrentWeight == null) {
            return new Profile(modelName, modelGender, modelAge, modelAddress,
                    modelHeight, modelTargetWeight);
        }

        return new Profile(modelName, modelGender, modelAge, modelAddress,
                modelHeight, modelTargetWeight, modelCurrentWeight, modelCurrentBmi);
    }

}
