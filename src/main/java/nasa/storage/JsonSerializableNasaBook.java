package nasa.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.Module;

/**
 * An Immutable NASA that is serializable to JSON format.
 */
@JsonRootName(value = "nasabook")
class JsonSerializableNasaBook {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNasaBook} with the given modules.
     */
    @JsonCreator
    public JsonSerializableNasaBook(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyNasaBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNASABook}.
     */
    public JsonSerializableNasaBook(ReadOnlyNasaBook source) {
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this nasa book into the model's {@code NasaBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NasaBook toModelType() throws IllegalValueException {
        NasaBook nasaBook = new NasaBook();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (nasaBook.hasModule(module.getModuleCode())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            nasaBook.addModule(module);
        }
        return nasaBook;
    }

}


