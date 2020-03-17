package fithelper.model.profile;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the user profile in the FitHelper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Profile {

    private final Name name;
    private final Gender gender;
    private final Age age;
    private final Address address;
    private final Height height;
    private final TargetWeight targetWeight;

    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, Gender gender, Age age, Address address, Height height, TargetWeight targetWeight) {
        requireAllNonNull(name, age, gender, address, height, targetWeight);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.height = height;
        this.targetWeight = targetWeight;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public TargetWeight getTargetWeight() {
        return targetWeight;
    }

    public Height getHeight() {
        return height;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gender, age, address, height, targetWeight);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName())
                .append(" Gender: ")
                .append(getGender())
                .append(" Age: ")
                .append(getAge())
                .append(" Address: ")
                .append(getAddress())
                .append(" Height:")
                .append(getHeight())
                .append(" TargetWeight: ")
                .append(getTargetWeight().toString());
        return builder.toString();
    }

}
