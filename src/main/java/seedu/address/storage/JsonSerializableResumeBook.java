package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ResumeBook;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Note;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.util.ItemUtil;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "resumebook")
class JsonSerializableResumeBook {

    public static final String MESSAGE_DUPLICATE_RESUME = "Resumes list contains duplicate resume(s).";

    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "Internships list contains duplicate internship(s).";

    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";

    public static final String MESSAGE_DUPLICATE_SKILL = "Skills list contains duplicate skill(s).";

    public static final String MESSAGE_DUPLICATE_NOTE = "Notes list contains duplicate note(s).";

    private final JsonAdaptedPerson user;
    private final List<JsonAdaptedResume> resumes = new ArrayList<>();
    private final List<JsonAdaptedInternship> internships = new ArrayList<>();
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();
    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableResumeBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableResumeBook(@JsonProperty("user") JsonAdaptedPerson user,
                                      @JsonProperty("resumes") List<JsonAdaptedResume> resumes,
                                      @JsonProperty("internships") List<JsonAdaptedInternship> internships,
                                      @JsonProperty("skills") List<JsonAdaptedSkill> skills,
                                      @JsonProperty("projects") List<JsonAdaptedProject> projects,
                                      @JsonProperty("notes") List<JsonAdaptedNote> notes) {
        this.user = user;
        this.resumes.addAll(resumes);
        this.internships.addAll(internships);
        this.skills.addAll(skills);
        this.projects.addAll(projects);
        this.notes.addAll(notes);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableResumeBook}.
     */
    public JsonSerializableResumeBook(ReadOnlyResumeBook source) {
        user = new JsonAdaptedPerson(source.getUser());
        resumes.addAll(source
                .getResumeList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedResume::new)
                .collect(Collectors.toList()));
        internships.addAll(source
                .getInternshipList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedInternship::new)
                .collect(Collectors.toList()));
        skills.addAll(source
                .getSkillList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
        projects.addAll(source
                .getProjectList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedProject::new)
                .collect(Collectors.toList()));
        notes.addAll(source
               .getNoteList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedNote::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ResumeBook toModelType() throws IllegalValueException {
        ResumeBook resumeBook = new ResumeBook();
        Person person = user.toModelType();
        resumeBook.setUser(person);

        /* To initialize
         */
        int maxIdValue = -1;
        for (JsonAdaptedResume jsonAdaptedResume : resumes) {
            Resume resume = jsonAdaptedResume.toModelType();
            if (resumeBook.hasResume(resume)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESUME);
            }
            resumeBook.addResume(resume);
            maxIdValue = Math.max(maxIdValue, resume.getId());
        }
        ItemUtil.setBaseIdOfItemType("res", maxIdValue + 1);

        maxIdValue = -1;
        for (JsonAdaptedInternship jsonAdaptedInternship : internships) {
            Internship internship = jsonAdaptedInternship.toModelType();
            if (resumeBook.hasInternship(internship)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INTERNSHIP);
            }
            resumeBook.addInternship(internship);
            maxIdValue = Math.max(maxIdValue, internship.getId());
        }
        ItemUtil.setBaseIdOfItemType("int", maxIdValue + 1);

        maxIdValue = -1;
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            Project project = jsonAdaptedProject.toModelType();
            if (resumeBook.hasProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            resumeBook.addProject(project);
            maxIdValue = Math.max(maxIdValue, project.getId());
        }
        ItemUtil.setBaseIdOfItemType("proj", maxIdValue + 1);

        maxIdValue = -1;
        for (JsonAdaptedSkill jsonAdaptedSkill : skills) {
            Skill skill = jsonAdaptedSkill.toModelType();
            if (resumeBook.hasSkill(skill)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SKILL);
            }
            resumeBook.addSkill(skill);
            maxIdValue = Math.max(maxIdValue, skill.getId());
        }
        ItemUtil.setBaseIdOfItemType("ski", maxIdValue + 1);

        maxIdValue = -1;
        for (JsonAdaptedNote jsonAdaptedNote : notes) {
            Note note = jsonAdaptedNote.toModelType();
            if (resumeBook.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_NOTE);
            }
            resumeBook.addNote(note);
            maxIdValue = Math.max(maxIdValue, note.getId());
        }
        ItemUtil.setBaseIdOfItemType("note", maxIdValue + 1);

        return resumeBook;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other)
                || (other instanceof JsonSerializableResumeBook
                && user.equals(((JsonSerializableResumeBook) other).user)
                && resumes.equals(((JsonSerializableResumeBook) other).resumes)
                && internships.equals(((JsonSerializableResumeBook) other).internships)
                && projects.equals(((JsonSerializableResumeBook) other).projects)
                && skills.equals(((JsonSerializableResumeBook) other).skills)
                && notes.equals(((JsonSerializableResumeBook) other).notes));
    }
}
