package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FROM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_LEARN_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_NAME_PAYPAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERNSHIP_ROLE_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO;
import static seedu.address.storage.JsonAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.TypicalInternship;

public class JsonAdaptedInternshipTest {

    @Test
    public void toModelType_validInternship_success() throws IllegalValueException {
        // Without tags
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(new InternshipBuilder(TypicalInternship.PAYPAL).withTags().build());
        assertEquals(
                new InternshipBuilder(TypicalInternship.PAYPAL).withTags().build(),
                jsonAdaptedInternship.toModelType());

        List<JsonAdaptedTag> tags = new ArrayList<>();
        jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        tags
                );
        assertEquals(
                new InternshipBuilder(TypicalInternship.PAYPAL).withTags().build(),
                jsonAdaptedInternship.toModelType());

        // With tags
        jsonAdaptedInternship =
                new JsonAdaptedInternship(new InternshipBuilder(TypicalInternship.PAYPAL).build());
        assertEquals(new InternshipBuilder(TypicalInternship.PAYPAL).build(), jsonAdaptedInternship.toModelType());

        tags.add(new JsonAdaptedTag(VALID_TAG_TECH));
        tags.add(new JsonAdaptedTag(VALID_TAG_BACKEND));
        tags.add(new JsonAdaptedTag(VALID_TAG_SE));
        jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        tags
                        );
        assertEquals(new InternshipBuilder(TypicalInternship.PAYPAL).build(), jsonAdaptedInternship.toModelType());
    }

    @Test
    public void toModelType_invalidName_failure() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(VALID_TAG_TECH));
        tags.add(new JsonAdaptedTag(VALID_TAG_BACKEND));
        tags.add(new JsonAdaptedTag(VALID_TAG_SE));
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(INVALID_NAME_DESC,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        tags
                );
        assertThrows(IllegalValueException.class,
                Name.MESSAGE_CONSTRAINTS,
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_missingName_failure() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(VALID_TAG_TECH));
        tags.add(new JsonAdaptedTag(VALID_TAG_BACKEND));
        tags.add(new JsonAdaptedTag(VALID_TAG_SE));
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(null,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        tags
                );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()),
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidId_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "abc",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                "The id field can only be an integer.",
                jsonAdaptedInternship::toModelType);

        jsonAdaptedInternship = new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                "-3",
                VALID_FROM,
                VALID_TO,
                VALID_INTERNSHIP_ROLE_BACKEND,
                VALID_INTERNSHIP_LEARN_DESCRIPTION,
                new ArrayList<>()
        );
        assertThrows(IllegalValueException.class,
                "The id field must not be negative.",
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_missingId_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        null,
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"),
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidRole_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        "",
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                Role.MESSAGE_CONSTRAINTS,
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_missingRole_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        null,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()),
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidFrom_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        INVALID_FROM_DESC,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                Time.MESSAGE_CONSTRAINTS,
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_missingFrom_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        null,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "From"),
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidTo_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        INVALID_TO_DESC,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                Time.MESSAGE_CONSTRAINTS,
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_missingTo_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        null,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "To"),
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidFromTo_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_TO,
                        VALID_FROM,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                "The \"to\" field must not precede the \"from\" field.",
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        "",
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                Description.MESSAGE_CONSTRAINTS,
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_missingDescription_failure() {
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        null,
                        new ArrayList<>()
                );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()),
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void toModelType_invalidTags_failure() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(INVALID_TAG));
        tags.add(new JsonAdaptedTag(VALID_TAG_BACKEND));
        tags.add(new JsonAdaptedTag(VALID_TAG_SE));
        JsonAdaptedInternship jsonAdaptedInternship =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        null,
                        tags
                );
        assertThrows(IllegalValueException.class,
                Tag.MESSAGE_CONSTRAINTS,
                jsonAdaptedInternship::toModelType);
    }

    @Test
    public void equals() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(VALID_TAG_TECH));
        tags.add(new JsonAdaptedTag(VALID_TAG_BACKEND));
        tags.add(new JsonAdaptedTag(VALID_TAG_SE));

        JsonAdaptedInternship jsonAdaptedPayPal =
                new JsonAdaptedInternship(new InternshipBuilder(TypicalInternship.PAYPAL).build());
        JsonAdaptedInternship jsonAdaptedGoogle =
                new JsonAdaptedInternship(new InternshipBuilder(TypicalInternship.GOOGLE).build());
        JsonAdaptedInternship jsonAdaptedPayPalCopy =
                new JsonAdaptedInternship(VALID_INTERNSHIP_NAME_PAYPAL,
                        "3",
                        VALID_FROM,
                        VALID_TO,
                        VALID_INTERNSHIP_ROLE_BACKEND,
                        VALID_INTERNSHIP_LEARN_DESCRIPTION,
                        tags
                );

        // Two constructors give same copy
        assertEquals(jsonAdaptedPayPal, jsonAdaptedPayPalCopy);

        // Different json adapted internships are not equal
        assertNotEquals(jsonAdaptedGoogle, jsonAdaptedPayPal);
    }
}
