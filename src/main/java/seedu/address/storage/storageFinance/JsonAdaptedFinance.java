package seedu.address.storage.storageFinance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedFinance {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Finance's %s field is missing!";

    private final String name;
    private final String financeID;
    private final String financeType;
    private final String date;
    private final String amount;
    private final String courseid;
    private final String studentid;
    private final String staffid;
    private final List<JsonFinanceAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedFinance(@JsonProperty("name") String name,
                              @JsonProperty("financeID") String financeID,
                              @JsonProperty("financeType") String financeType,
                              @JsonProperty("date") String date,
                              @JsonProperty("amount") String amount,
                              @JsonProperty("courseid") String courseid,
                              @JsonProperty("studentid") String studentid,
                              @JsonProperty("staffid") String staffid,
                              @JsonProperty("tagged") List<JsonFinanceAdaptedTag> tagged) {
        this.name = name;
        this.financeID = financeID;
        this.financeType = financeType;
        this.date = date;
        this.amount = amount;
        this.courseid = courseid;
        this.studentid = studentid;
        this.staffid = staffid;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Finance} into this class for Jackson use.
     */
    public JsonAdaptedFinance(Finance source) {
        name = source.getName().fullName;
        financeID = source.getId().toString();
        financeType = source.getFinanceType().toString();
        date = source.getDate().toString();
        amount = source.getAmount().value;
        courseid = source.getCourseID().value;
        studentid = source.getStudentID().value;
        staffid = source.getStaffID().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonFinanceAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted finance object into the model's {@code Finance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               finance.
     */
    public Finance toModelType() throws IllegalValueException {
        final List<Tag> financeTags = new ArrayList<>();
        for (JsonFinanceAdaptedTag tag : tagged) {
            financeTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (financeID == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(financeID)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelId = new ID(financeID);

        if (financeType == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, FinanceType.class.getSimpleName()));
        }
        if (!FinanceType.isValidFinanceType(financeType)) {
            throw new IllegalValueException(FinanceType.MESSAGE_CONSTRAINTS);
        }
        final FinanceType modelFinanceType = new FinanceType(financeType);

        if (date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (amount == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Finance.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (courseid == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(courseid)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelCourseID = new ID(courseid);

        if (studentid == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(studentid)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelStudentID = new ID(studentid);

        if (staffid == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
        }
        if (!ID.isValidId(staffid)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelStaffID = new ID(staffid);

        final Set<Tag> modelTags = new HashSet<>(financeTags);
        return new Finance(modelName, modelId, modelFinanceType, modelDate, modelAmount, modelCourseID, modelStudentID, modelStaffID, modelTags);
    }

}
