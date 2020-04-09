package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;

/**
 * Jackson-friendly version of {@link NusModule}.
 */
public class JsonAdaptedNusModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "NusModule's %s field is missing!";

    private final String moduleCode;
    private final String modularCredit;
    private final String grade;
    private final List<JsonAdaptedModuleTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedNusModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedNusModule(@JsonProperty("moduleCode") String moduleCode,
                                @JsonProperty("modularCredit") String modularCredit,
                                @JsonProperty("grade") String grade,
                                @JsonProperty("tasks") List<JsonAdaptedModuleTask> tasks) {
        this.moduleCode = moduleCode;
        this.modularCredit = modularCredit;
        this.grade = grade;
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedNusModule(NusModule source) {
        moduleCode = source.getModuleCode().toString();
        modularCredit = Integer.toString(source.getModularCredit());
        if (source.getGrade().isEmpty()) {
            grade = null;
        } else {
            grade = source.getGrade().get().getText();
        }
        tasks.addAll(source.getTasks().stream()
                .map(JsonAdaptedModuleTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted nus module object into the model's {@code NusModule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted nus module.
     */
    public NusModule toModelType() throws IllegalValueException {
        final List<ModuleTask> moduleTasks = new ArrayList<>();
        for (JsonAdaptedModuleTask task : tasks) {
            moduleTasks.add(task.toModelType());
        }

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (modularCredit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "modular credit"));
        }

        final int modelMc = Integer.parseInt(modularCredit);

        Grade modelGrade = null;
        if (grade != null) {
            modelGrade = Grade.getGrade(grade);
        }

        return new NusModule(modelModuleCode, modelMc, Optional.ofNullable(modelGrade), moduleTasks);
    }
}
