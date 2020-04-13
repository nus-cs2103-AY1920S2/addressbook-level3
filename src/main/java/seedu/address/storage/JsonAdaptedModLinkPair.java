package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mod.ModLink;
import seedu.address.model.mod.ModLinkPair;

/**
 * Jackson-friendly version of {@link ModLinkPair}.
 */
public class JsonAdaptedModLinkPair {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ModLinkPair's %s field is missing!";

    private final String modLinkName;
    private final String modLink;

    /**
     * Constructs a {@code JsonAdaptedModLinkPair} with the given module link details.
     */
    @JsonCreator
    public JsonAdaptedModLinkPair(@JsonProperty("modLinkName") String modLinkName,
                                  @JsonProperty("modLink") String modLink) {
        this.modLink = modLink;
        this.modLinkName = modLinkName;
    }

    /**
     * Constructs a given {@code ModLinkPair} into this class for Jackson use
     */
    public JsonAdaptedModLinkPair(ModLinkPair modLinkPair) {
        this.modLinkName = modLinkPair.getKey();
        this.modLink = modLinkPair.getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted ModLinkPair object into the model's {@code ModLinkPair} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted modLinkPair.
     */
    public ModLinkPair toModelType() throws IllegalValueException {
        if (modLink == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "MODLINK"));
        }
        if (modLinkName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "MODLINK_NAME"));
        }

        if (!ModLink.isValidModLink(modLink)) {
            throw new IllegalValueException(ModLink.MESSAGE_CONSTRAINTS);
        }
        return new ModLinkPair(modLinkName, new ModLink(modLink));
    }


}
