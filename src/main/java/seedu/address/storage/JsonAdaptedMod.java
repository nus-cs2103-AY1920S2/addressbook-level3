package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLinkPair;

/**
 * Jackson-friendly version of {@link Mod}.
 */

public class JsonAdaptedMod {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String modCode;
    private final String modName;
    private final String note;
    private final List<JsonAdaptedModLinkPair> links = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMod} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedMod(@JsonProperty("modCode") String modCode,
                          @JsonProperty("modName") String modName,
                          @JsonProperty("note") String note,
                          @JsonProperty("links") List<JsonAdaptedModLinkPair> links) {
        this.modCode = modCode;
        this.modName = modName;
        this.note = note;
        if (links != null) {
            this.links.addAll(links);
        }
    }

    /**
     * Converts a given {@code Mod} into this class for Jackson use.
     */
    public JsonAdaptedMod(Mod source) {
        modCode = source.getModCode().toString();
        modName = source.getModName();
        note = source.getNote();
        links.addAll(source.getLinks().stream()
            .map(JsonAdaptedModLinkPair::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Mod} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Mod toModelType() throws IllegalValueException {

        if (modCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "MOD_CODE"));
        }
        if (modName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "MOD_NAME"));
        }

        ModCode modelModCode;
        String modelModName;

        if (!ModCode.isValidModCode(modCode)) {
            throw new IllegalValueException(ModCode.MESSAGE_CONSTRAINTS);
        }
        modelModCode = new ModCode(modCode);
        modelModName = modName;

        Mod modelMod = new Mod(modelModCode, modelModName);

        if (note != null) {
            modelMod = new Mod(modelMod, note);
        }

        List<ModLinkPair> modelLinks = new ArrayList<>();
        for (JsonAdaptedModLinkPair jsonAdaptedModLinkPair : links) {
            ModLinkPair modLinkPair = jsonAdaptedModLinkPair.toModelType();
            modelMod = new Mod(modelMod, modLinkPair.getKey(), modLinkPair.getValue());
        }

        return modelMod;
    }
}

