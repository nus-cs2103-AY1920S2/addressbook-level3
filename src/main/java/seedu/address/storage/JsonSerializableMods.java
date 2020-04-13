package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.mod.ReadOnlyMod;

/**
 * An Immutable ModTAble that is serializable to JSON format.
 */
@JsonRootName(value = "mods")

public class JsonSerializableMods {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedMod> mods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMods} with the given modules.
     */
    @JsonCreator
    public JsonSerializableMods(@JsonProperty("mods") List<JsonAdaptedMod> mods) {
        this.mods.addAll(mods);
    }

    /**
     * Converts a given {@code ReadOnlyMod} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMods}.
     */
    public JsonSerializableMods(ReadOnlyMod source) {
        mods.addAll(source.getAllMods().stream().map(JsonAdaptedMod::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ModTAble into the model's {@code ModStorage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModTAble toModelType() throws IllegalValueException {
        ModTAble modTAble = new ModTAble();
        for (JsonAdaptedMod jsonAdaptedMod : mods) {
            Mod mod = jsonAdaptedMod.toModelType();
            if (modTAble.hasMod(mod)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            modTAble.addMod(mod);
        }
        return modTAble;
    }
}

