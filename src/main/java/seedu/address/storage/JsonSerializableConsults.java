package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.consult.ReadOnlyConsult;

/**
 * An Immutable ConsultTAble that is serializable to JSON format.
 */
@JsonRootName(value = "consults")
class JsonSerializableConsults {

    public static final String MESSAGE_DUPLICATE_CONSULT = "Consults list contains duplicate consult(s).";

    private final List<JsonAdaptedConsult> consults = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableConsults} with the given consults.
     */
    @JsonCreator
    public JsonSerializableConsults(@JsonProperty("consults") List<JsonAdaptedConsult> consults) {
        this.consults.addAll(consults);
    }

    /**
     * Converts a given {@code ReadOnlyConsult} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableConsults}.
     */
    public JsonSerializableConsults(ReadOnlyConsult source) {
        consults.addAll(source.getAllConsults().stream().map(JsonAdaptedConsult::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ConsultTAble into the model's {@code ConsultStorage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ConsultTAble toModelType() throws IllegalValueException {
        ConsultTAble consultTAble = new ConsultTAble();
        for (JsonAdaptedConsult jsonAdaptedConsult : consults) {
            Consult consult = jsonAdaptedConsult.toModelType();
            if (consultTAble.hasConsult(consult)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONSULT);
            }
            consultTAble.addConsult(consult);
        }
        return consultTAble;
    }

}
