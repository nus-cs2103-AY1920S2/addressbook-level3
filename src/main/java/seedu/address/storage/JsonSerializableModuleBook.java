package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModuleBook;
import seedu.address.model.nusmodule.NusModule;

/**
 * An Immutable ModuleBook that is serializable to JSON format.
 */
@JsonRootName(value = "modulebook")
public class JsonSerializableModuleBook {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedNusModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableModuleBook(@JsonProperty("modules") List<JsonAdaptedNusModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableModuleBook(ModuleBook source) {
        modules.addAll(source.getModulesTakenList().stream().map(JsonAdaptedNusModule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this module book into the model's {@code ModuleBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModuleBook toModelType() throws IllegalValueException {
        ModuleBook moduleBook = new ModuleBook();
        for (JsonAdaptedNusModule jsonAdaptedNusModule : modules) {
            NusModule module = jsonAdaptedNusModule.toModelType();
            if (moduleBook.hasModule(module.getModuleCode())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            moduleBook.addModule(module);
        }

        return moduleBook;
    }
}
