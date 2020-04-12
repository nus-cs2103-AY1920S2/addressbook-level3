package seedu.address.storage.storageStaff;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffAddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "staffaddressbook")
class JsonStaffSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_STAFF = "Staffs list contains duplicate staff(s).";

    private final List<JsonAdaptedStaff> staffs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given teachers.
     */
    @JsonCreator
    public JsonStaffSerializableAddressBook(
            @JsonProperty("staffs") List<JsonAdaptedStaff> staffs) {
        this.staffs.addAll(staffs);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBookGeneric<Teacher>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *               JsonTeacherSerializableAddressBook}.
     */
    public JsonStaffSerializableAddressBook(ReadOnlyAddressBookGeneric<Staff> source) {
        staffs.addAll(
                source.getList().stream().map(JsonAdaptedStaff::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StaffAddressBook toModelType() throws IllegalValueException {
        StaffAddressBook staffAddressBook = new StaffAddressBook();
        for (JsonAdaptedStaff jsonAdaptedStaff : staffs) {
            Staff staff = jsonAdaptedStaff.toModelType();
            if (staffAddressBook.has(staff)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            staffAddressBook.add(staff);
        }
        return staffAddressBook;
    }

}
