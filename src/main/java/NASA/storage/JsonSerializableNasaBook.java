package NASA.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import NASA.commons.exceptions.IllegalValueException;
import NASA.model.NasaBook;
import NASA.model.ReadOnlyNasaBook;
import NASA.model.module.Module;

/**
 * An Immutable NASABook that is serializable to JSON format.
 */
@JsonRootName(value = "nasabook")
class JsonSerializableNasaBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Module list contains duplicate module(s).";

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
        NasaBook NasaBook = new NasaBook();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (NasaBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            NasaBook.addModule(module);
        }
        return NasaBook;
    }

}


