package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.module.ModuleCode;

/**
 * Jackson-friendly version of {@link Facilitator}.
 */
class JsonAdaptedFacilitator {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Facilitator's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String office;
    private final List<JsonAdaptedModuleCode> moduleCode = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFacilitator} with the given facilitator details.
     */
    @JsonCreator
    public JsonAdaptedFacilitator(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                  @JsonProperty("email") String email, @JsonProperty("office") String office,
                                  @JsonProperty("moduleCode") List<JsonAdaptedModuleCode> moduleCode) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.office = office;
        if (moduleCode != null) {
            this.moduleCode.addAll(moduleCode);
        }
    }

    /**
     * Converts a given {@code Facilitator} into this class for Jackson use.
     */
    public JsonAdaptedFacilitator(Facilitator source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        office = source.getOffice().value;
        moduleCode.addAll(source.getModuleCodes().stream()
                .map(JsonAdaptedModuleCode::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted facilitator object into the model's {@code Facilitator} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted facilitator.
     */
    public Facilitator toModelType() throws IllegalValueException {
        final List<ModuleCode> facilitatorModuleCodes = new ArrayList<>();
        for (JsonAdaptedModuleCode moduleCode : moduleCode) {
            facilitatorModuleCodes.add(moduleCode.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email != null && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (office != null && !Office.isValidOffice(office)) {
            throw new IllegalValueException(Office.MESSAGE_CONSTRAINTS);
        }
        final Office modelOffice = new Office(office);

        final Set<ModuleCode> modelModuleCodes = new HashSet<>(facilitatorModuleCodes);
        return new Facilitator(modelName, modelPhone, modelEmail, modelOffice, modelModuleCodes);
    }

}
