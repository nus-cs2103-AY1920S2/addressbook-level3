package seedu.address.storage.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;

/**
 * An Immutable Admin that is serializable to JSON format.
 */
@JsonRootName(value = "admin")
public class JsonSerializableAdmin {
    public static final String MESSAGE_DUPLICATE_DATE = "Dates list contains duplicate date(s).";

    private final List<JsonAdaptedDate> dates = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAdmin} with the given dates.
     */
    @JsonCreator
    public JsonSerializableAdmin(@JsonProperty("dates") List<JsonAdaptedDate> dates) {
        this.dates.addAll(dates);
    }

    /**
     * Converts a given {@code ReadOnlyAdmin} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAdmin}.
     */
    public JsonSerializableAdmin(ReadOnlyAdmin source) {
        dates.addAll(source.getDateList().stream().map(JsonAdaptedDate::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Admin} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Admin toModelType() throws IllegalValueException {
        Admin admin = new Admin();
        for (JsonAdaptedDate jsonAdaptedDate : dates) {
            Date date = jsonAdaptedDate.toModelType();
            if (admin.hasDate(date)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DATE);
            }
            admin.addDate(date);
        }
        return admin;
    }
}
