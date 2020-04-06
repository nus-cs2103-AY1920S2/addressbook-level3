package igrad.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String title;
    private final String moduleCode;
    private final String credits;
    private final String memo;
    private final String semester;
    private final String description;
    private final String grade;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("title") String name, @JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("credits") String credits, @JsonProperty("memo") String memo,
                             @JsonProperty("semester") String semester, @JsonProperty("description") String description,
                             @JsonProperty("grade") String grade, @JsonProperty("tagged") List<JsonAdaptedTag> tags) {
        this.title = name;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.memo = memo;
        this.semester = semester;
        this.description = description;
        this.grade = grade;

        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        title = source.getTitle().value;
        moduleCode = source.getModuleCode().value;
        credits = source.getCredits().value;
        memo = source.getMemo().isPresent() ? source.getMemo().get().value : null;
        semester = source.getSemester().isPresent() ? source.getSemester().get().value : null;
        description = source.getDescription().isPresent() ? source.getDescription().get().value : null;
        grade = source.getGrade().isPresent() ? source.getGrade().get().value : null;

        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Tag> moduleTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            moduleTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }

        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        final Title modelTitle = new Title(title);

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ModuleCode.class.getSimpleName()));
        }

        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }

        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (credits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Credits.class.getSimpleName()));
        }

        if (!Credits.isValidCredits(credits)) {
            throw new IllegalValueException(Credits.MESSAGE_CONSTRAINTS);
        }

        if (semester != null && !Semester.isValidSemester(semester)) {
            throw new IllegalValueException(Semester.MESSAGE_CONSTRAINTS);
        }

        if (grade != null && !Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }

        final Credits modelCredits = new Credits(credits);

        final Optional<Semester> modelSemester = semester != null
            ? Optional.of(new Semester(semester)) : Optional.empty();

        final Optional<Memo> modelMemo = memo != null
            ? Optional.of(new Memo(memo)) : Optional.empty();

        final Optional<Description> modelDescription = description != null
            ? Optional.of(new Description(description)) : Optional.empty();

        final Optional<Grade> modelGrade = grade != null
            ? Optional.of(new Grade(grade)) : Optional.empty();

        final Set<Tag> modelTags = new HashSet<>(moduleTags);

        return new Module(modelTitle, modelModuleCode, modelCredits, modelMemo, modelSemester,
            modelDescription, modelGrade, modelTags);
    }

}
