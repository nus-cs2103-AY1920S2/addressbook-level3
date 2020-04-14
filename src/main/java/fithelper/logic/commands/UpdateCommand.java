package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.FLAG_FORCE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_ATTRIBUTE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.util.CollectionUtil;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
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
 * Edits the details of an existing value in the age book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update the value of the profile attribute identified "
            + "by given the profile attribute name. \n"
            + "Parameters: "
            + "[" + FLAG_FORCE + "(if force to update)] "
            + "[" + PREFIX_ATTRIBUTE + "ATTRIBUTE_NAME] "
            + "[" + PREFIX_VALUE + "NEW_VALUE]\n";

    public static final String MESSAGE_UPDATE_VALUE_SUCCESS = "Your profile has been successfully updated!";
    public static final String MESSAGE_NOT_UPDATED = "You are not updating any attribute in your profile.";
    public static final String MESSAGE_DUPLICATE_VALUE = "Are you sure to overwrite your original value? "
            + "If so, use flag " + FLAG_FORCE + " to force updating.";

    private static final String MESSAGE_COMMIT = "update the user profile";

    private static final Logger logger = LogsCenter.getLogger(UpdateCommand.class);

    private final UpdateValueDescriptor updateProfileDescriptor;

    /**
     * @param updateProfileDescriptor details to update the value with
     */
    public UpdateCommand(UpdateValueDescriptor updateProfileDescriptor) {
        requireNonNull(updateProfileDescriptor);

        this.updateProfileDescriptor = new UpdateValueDescriptor(updateProfileDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean isForced = updateProfileDescriptor.getBoolean();

        Profile originalProfile = model.getUserProfile().getUserProfile();
        Profile updatedProfile = createUpdatedProfile(originalProfile, updateProfileDescriptor);

        if (originalProfile.equals(updatedProfile)) {
            throw new CommandException(MESSAGE_NOT_UPDATED);
        }

        // case where updates required but exists original value.
        if (!isForced) {
            throw new CommandException(MESSAGE_DUPLICATE_VALUE);
        }

        model.setUserProfile(updatedProfile);

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("Updated user profile [%s]", updateProfileDescriptor.toString()));

        return new CommandResult(String.format(MESSAGE_UPDATE_VALUE_SUCCESS, updatedProfile),
                CommandResult.DisplayedPage.PROFILE);
    }

    /**
     * Creates and returns a {@code Profile} with the details of {@code originalProfile}
     * updated with {@code updateProfileDescriptor}.
     */
    private static Profile createUpdatedProfile(Profile originalProfile,
                                                UpdateValueDescriptor updateProfileDescriptor) {
        assert originalProfile != null;

        Name updatedName = updateProfileDescriptor.getName().orElse(originalProfile.getName());
        Gender updatedGender = updateProfileDescriptor.getGender().orElse(originalProfile.getGender());
        Age updatedAge = updateProfileDescriptor.getAge().orElse(originalProfile.getAge());
        Address updatedAddress = updateProfileDescriptor.getAddress().orElse(originalProfile.getAddress());
        Height updatedHeight = updateProfileDescriptor.getHeight().orElse(originalProfile.getHeight());
        TargetWeight updatedTargetWeight = updateProfileDescriptor.getTargetWeight()
                .orElse(originalProfile.getTargetWeight());
        WeightValue updatedWeightValue = originalProfile.getCurrentWeight();
        Bmi updatedBmi = originalProfile.getCurrentBmi();

        return new Profile(updatedName, updatedGender, updatedAge, updatedAddress, updatedHeight,
                updatedTargetWeight, updatedWeightValue, updatedBmi);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        // state check
        UpdateCommand e = (UpdateCommand) other;
        return updateProfileDescriptor.equals(e.updateProfileDescriptor);
    }

    /**
     * Stores the details to update the value with. Each non-empty field value will replace the
     * corresponding field value of the value only if it is a forced updating.
     */
    public static class UpdateValueDescriptor {
        private boolean isForced;
        private Name name;
        private Gender gender;
        private Age age;
        private Address address;
        private Height height;
        private TargetWeight targetWeight;

        public UpdateValueDescriptor() {}

        /**
         * Copy constructor.
         */
        public UpdateValueDescriptor(UpdateValueDescriptor toCopy) {
            setBoolean(toCopy.isForced);
            setName(toCopy.name);
            setGender(toCopy.gender);
            setAge(toCopy.age);
            setAddress(toCopy.address);
            setHeight(toCopy.height);
            setTargetWeight(toCopy.targetWeight);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(name, gender, age, address, height, targetWeight);
        }

        public void setBoolean(boolean isForced) {
            this.isForced = isForced;
        }

        public boolean getBoolean() {
            return this.isForced;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setHeight(Height height) {
            this.height = height;
        }

        public Optional<Height> getHeight() {
            return Optional.ofNullable(height);
        }

        public void setTargetWeight(TargetWeight targetWeight) {
            this.targetWeight = targetWeight;
        }

        public Optional<TargetWeight> getTargetWeight() {
            return Optional.ofNullable(targetWeight);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateValueDescriptor)) {
                return false;
            }

            // state check
            UpdateValueDescriptor e = (UpdateValueDescriptor) other;

            return getName().equals(e.getName())
                    && getBoolean() == (e.getBoolean())
                    && getGender().equals(e.getGender())
                    && getAge().equals(e.getAge())
                    && getHeight().equals(e.getHeight())
                    && getAddress().equals(e.getAddress())
                    && getTargetWeight().equals(e.getTargetWeight());
        }
    }
}
