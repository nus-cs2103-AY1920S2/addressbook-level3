package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedFacilitator.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.BENSON;
import static seedu.address.testutil.TypicalFacilitators.BENSON_NULL_EMAIL;
import static seedu.address.testutil.TypicalFacilitators.BENSON_NULL_OFFICE;
import static seedu.address.testutil.TypicalFacilitators.BENSON_NULL_PHONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;

public class JsonAdaptedFacilitatorTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_OFFICE = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MODULE_CODE = "#cs2103t";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_OFFICE = BENSON.getOffice().toString();
    private static final List<JsonAdaptedModuleCode> VALID_MODULE_CODES = BENSON.getModuleCodes().stream()
            .map(JsonAdaptedModuleCode::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFacilitatorDetails_returnsFacilitator() throws Exception {
        JsonAdaptedFacilitator facilitator = new JsonAdaptedFacilitator(BENSON);
        assertEquals(BENSON, facilitator.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFacilitator facilitator =
                new JsonAdaptedFacilitator(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_OFFICE, VALID_MODULE_CODES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, facilitator::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFacilitator facilitator = new JsonAdaptedFacilitator(
                null, VALID_PHONE, VALID_EMAIL, VALID_OFFICE, VALID_MODULE_CODES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, facilitator::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedFacilitator facilitator =
                new JsonAdaptedFacilitator(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_OFFICE, VALID_MODULE_CODES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, facilitator::toModelType);
    }

    @Test
    public void toModelType_nullPhone_success() throws Exception {
        JsonAdaptedFacilitator facilitator = new JsonAdaptedFacilitator(
                VALID_NAME, null, VALID_EMAIL, VALID_OFFICE, VALID_MODULE_CODES);
        assertEquals(BENSON_NULL_PHONE, facilitator.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedFacilitator facilitator =
                new JsonAdaptedFacilitator(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_OFFICE, VALID_MODULE_CODES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, facilitator::toModelType);
    }

    @Test
    public void toModelType_nullEmail_success() throws Exception {
        JsonAdaptedFacilitator facilitator = new JsonAdaptedFacilitator(
                VALID_NAME, VALID_PHONE, null, VALID_OFFICE, VALID_MODULE_CODES);
        assertEquals(BENSON_NULL_EMAIL, facilitator.toModelType());
    }

    @Test
    public void toModelType_invalidOffice_throwsIllegalValueException() {
        JsonAdaptedFacilitator facilitator =
                new JsonAdaptedFacilitator(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_OFFICE, VALID_MODULE_CODES);
        String expectedMessage = Office.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, facilitator::toModelType);
    }

    @Test
    public void toModelType_nullOffice_success() throws Exception {
        JsonAdaptedFacilitator facilitator = new JsonAdaptedFacilitator(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_MODULE_CODES);
        assertEquals(BENSON_NULL_OFFICE, facilitator.toModelType());
    }

    @Test
    public void toModelType_invalidModuleCodes_throwsIllegalValueException() {
        List<JsonAdaptedModuleCode> invalidModuleCodes = new ArrayList<>(VALID_MODULE_CODES);
        invalidModuleCodes.add(new JsonAdaptedModuleCode(INVALID_MODULE_CODE));
        JsonAdaptedFacilitator facilitator =
                new JsonAdaptedFacilitator(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_OFFICE, invalidModuleCodes);
        assertThrows(IllegalValueException.class, facilitator::toModelType);
    }

}
