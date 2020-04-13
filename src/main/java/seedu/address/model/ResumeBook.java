package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Item;
import seedu.address.model.item.Note;
import seedu.address.model.item.ObservablePerson;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ItemUtil;

/**
 * Wraps all data at the resume-book level
 * Duplicates are not allowed (by .isSame comparison)
 */
public class ResumeBook implements ReadOnlyResumeBook {

    // Should be all caps but check style complain
    private final UniqueItemList<Item> itemsToDisplay;
    private final UniqueItemList<Internship> internships;
    private final UniqueItemList<Project> projects;
    private final UniqueItemList<Skill> skills;
    private final UniqueItemList<Resume> resumes;
    private final UniqueItemList<Note> notesToDisplay;
    private final UniqueItemList<Note> notes;
    private String displayType = "";
    private ObservablePerson observableUser;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        Person defaultUser = new Person(new DisplayPicture("/images/Duke.png"), new Name("Default name"),
                new Description("Default description"), new Phone("000"), new Email("000@gmail.com"), new Github("000"),
                new University("Default university"), new Major("Default major"),
                new Time("12-9999"), new Time("12-9999"), new Cap("0.0 5.0"));
        observableUser = new ObservablePerson(defaultUser);
        itemsToDisplay = new UniqueItemList<>();
        internships = new UniqueItemList<>();
        projects = new UniqueItemList<>();
        skills = new UniqueItemList<>();
        resumes = new UniqueItemList<>();
        notesToDisplay = new UniqueItemList<>();
        notes = new UniqueItemList<>();
    }

    public ResumeBook() {}

    /**
     * Creates an ResumeBook using the Items in the {@code toBeCopied}
     */
    public ResumeBook(ReadOnlyResumeBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //=========== Replace display list =====================================================================

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItemsToDisplay(List<Item> itemsToDisplay) {
        this.itemsToDisplay.setItems(itemsToDisplay);
    }

    /**
     * Sets the currently displayed list based on the given keyword.
     */
    public void setItemsToDisplay(String type) {
        switch (type) {
        case ItemUtil.INTERNSHIP_ALIAS:
            setInternshipToDisplay();
            break;
        case ItemUtil.PROJECT_ALIAS:
            setProjectToDisplay();
            break;
        case ItemUtil.SKILL_ALIAS:
            setSkillToDisplay();
            break;
        case ItemUtil.RESUME_ALIAS:
            setResumeToDisplay();
            break;
        default:
            break;
        }
    }

    /**
     * Returns the item type of the currently displayed list.
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     * Replaces the contents of the item list to the content of the internship list.
     */
    public void setInternshipToDisplay() {
        displayType = ItemUtil.INTERNSHIP_ALIAS;
        setItemsToDisplay(internships.getItemList());
    }

    /**
     * Replaces the contents of the item list to the content of the project list.
     */
    public void setProjectToDisplay() {
        displayType = ItemUtil.PROJECT_ALIAS;
        setItemsToDisplay(projects.getItemList());
    }

    /**
     * Replaces the contents of the item list to the content of the skill list.
     */
    public void setSkillToDisplay() {
        displayType = ItemUtil.SKILL_ALIAS;
        setItemsToDisplay(skills.getItemList());
    }

    /**
     * Replaces the contents of the item list to the content of the resume list.
     */
    public void setResumeToDisplay() {
        displayType = ItemUtil.RESUME_ALIAS;
        setItemsToDisplay(resumes.getItemList());
    }

    //=========== Overwrite data ================================================================================
    /**
     * Replaces the user profile detail with that of {@code person}.
     */
    public void setUser(Person user) {
        // Method's name is setPerson so it is consistent with the Observable class name.
        this.observableUser.setPerson(user);
    }

    /**
     * Replaces the contents of the internship list with {@code internships}.
     * {@code internships} must not contain duplicate items.
     */
    public void setInternships(UniqueItemList<Internship> internships) {
        this.internships.setItems(internships);
    }

    /**
     * Replaces the contents of the project list with {@code projects}.
     * {@code projects} must not contain duplicate items.
     */
    public void setProjects(UniqueItemList<Project> projects) {
        this.projects.setItems(projects);
    }

    /**
     * Replaces the contents of the skill list with {@code skills}.
     * {@code skills} must not contain duplicate items.
     */
    public void setSkills(UniqueItemList<Skill> skills) {
        this.skills.setItems(skills);
    }

    /**
     * Replaces the contents of the resume list with {@code resumes}.
     * {@code resumes} must not contain duplicate items.
     */
    public void setResumes(UniqueItemList<Resume> resumes) {
        this.resumes.setItems(resumes);
    }

    /**
     * Replaces the contents of the note list with {@code notes}.
     * {@code notes} must not contain duplicate items.
     */
    private void setNotes(UniqueItemList<Note> notes) {
        this.notes.setItems(notes);
        setNoteToDisplay();
    }

    /**
     * Resets the existing data of this {@code ResumeBook} with {@code newData}.
     */
    public void resetData(ReadOnlyResumeBook newData) {
        requireNonNull(newData);
        setUser(newData.getUser());
        setInternships(newData.getInternshipList());
        setProjects(newData.getProjectList());
        setSkills(newData.getSkillList());
        setResumes(newData.getResumeList());
        setNotes(newData.getNoteList());
        setItemsToDisplay(((ResumeBook) newData).getDisplayType());
    }

    //=========== Internships ================================================================================

    /**
     * Returns true if an internship with the same identity as {@code internship} exists in the resume book.
     */
    public boolean hasInternship(Internship internship) {
        requireNonNull(internship);
        return internships.contains(internship);
    }

    /**
     * Adds an internship to the resume book.
     * The internship must not already exist in the resume book.
     */
    public void addInternship(Internship internship) {
        requireNonNull(internship);
        internships.add(internship);
    }

    /**
     * Replaces the given internship {@code target} in the list with {@code editedInternship}.
     * {@code target} must exist in the resume book.
     * The identity of {@code editedInternship} must not be the same as another existing internship in the resume book.
     */
    public void setInternship(Internship target, Internship editedInternship) {
        internships.setItem(target, editedInternship);
    }

    /**
     * Removes {@code key} from this {@code ResumeBook}.
     * {@code key} must exist in the resume book.
     */
    public void deleteInternship(Internship key) {
        int id = key.getId();
        internships.remove(key);
        for (Item item : resumes) {
            Resume resume = (Resume) item;
            Resume newResume = new Resume(resume.getName(), resume.getId(), resume.getTags());
            List<Integer> newInternshipList = new ArrayList<>();
            newInternshipList.addAll(resume.getInternshipIds());

            newResume.setInternshipIds(newInternshipList);
            newResume.setProjectIds(resume.getProjectIds());
            newResume.setSkillIds(resume.getSkillIds());
            newResume.getInternshipIds().remove(Integer.valueOf(id));

            setResume(resume, newResume);
        }
    }

    @Override
    public Internship getInternshipByIndex(Index index) {
        return internships.asUnmodifiableObservableList().get(index.getZeroBased());
    }

    @Override
    public List<Internship> getInternshipsByTag(Tag tag) {
        return internships
            .getItemList()
            .stream()
            .distinct()
            .filter(x -> x.hasTag(tag))
            .map(x -> (Internship) x)
            .collect(Collectors.toList());
    }

    public void sortInternships(Comparator<Internship> sortComparator) {
        internships.sort(sortComparator);
    }

    @Override
    public boolean hasInternshipId(int id) {
        for (Internship item : internships) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Internship getInternshipById(int id) {
        return internships.getById(id);
    }

    @Override
    public int getInternshipSize() {
        return internships.getSize();
    }

    //=========== Projects ================================================================================

    /**
     * Returns true if a project with the same identity as {@code project} exists in the resume book.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    /**
     * Adds a project to the resume book.
     * The project must not already exist in the resume book.
     */
    public void addProject(Project project) {
        requireNonNull(project);
        projects.add(project);
    }

    /**
     * Replaces the given project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the resume book.
     * The identity of {@code editedProject} must not be the same as another existing project in the resume book.
     */
    public void setProject(Project target, Project editedProject) {
        projects.setItem(target, editedProject);
    }

    /**
     * Removes {@code key} from this {@code ResumeBook}.
     * {@code key} must exist in the resume book.
     */
    public void deleteProject(Project key) {
        int id = key.getId();
        projects.remove(key);
        for (Item item : resumes) {
            Resume resume = (Resume) item;
            Resume newResume = new Resume(resume.getName(), resume.getId(), resume.getTags());
            List<Integer> newProjectList = new ArrayList<>();
            newProjectList.addAll(resume.getProjectIds());

            newResume.setInternshipIds(resume.getInternshipIds());
            newResume.setProjectIds(newProjectList);
            newResume.setSkillIds(resume.getSkillIds());
            newResume.getProjectIds().remove(Integer.valueOf(id));

            setResume(resume, newResume);
        }
    }

    @Override
    public Project getProjectByIndex(Index index) {
        return projects.asUnmodifiableObservableList().get(index.getZeroBased());
    }

    @Override
    public List<Project> getProjectsByTag(Tag tag) {
        return projects
            .getItemList()
            .stream()
            .distinct()
            .filter(x -> x.hasTag(tag))
            .map(x -> (Project) x)
            .collect(Collectors.toList());
    }

    public void sortProjects(Comparator<Project> sortComparator) {
        projects.sort(sortComparator);
    }

    @Override
    public boolean hasProjectId(int id) {
        for (Project item : projects) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Project getProjectById(int id) {
        return projects.getById(id);
    }

    @Override
    public int getProjectSize() {
        return projects.getSize();
    }

    //=========== Skills ================================================================================

    /**
     * Returns true if a skill with the same identity as {@code skill} exists in the resume book.
     */
    public boolean hasSkill(Skill skill) {
        requireNonNull(skill);
        return skills.contains(skill);
    }

    /**
     * Adds a skill to the resume book.
     * The skill must not already exist in the resume book.
     */
    public void addSkill(Skill skill) {
        requireNonNull(skill);
        skills.add(skill);
    }

    /**
     * Replaces the given skill {@code target} in the list with {@code editedSkill}.
     * {@code target} must exist in the resume book.
     * The identity of {@code editedSkill} must not be the same as another existing skill in the resume book.
     */
    public void setSkill(Skill target, Skill editedSkill) {
        skills.setItem(target, editedSkill);
    }

    /**
     * Removes {@code key} from this {@code ResumeBook}.
     * {@code key} must exist in the resume book.
     */
    public void deleteSkill(Skill key) {
        int id = key.getId();
        skills.remove(key);
        for (Item item : resumes) {
            Resume resume = (Resume) item;
            Resume newResume = new Resume(resume.getName(), resume.getId(), resume.getTags());
            List<Integer> newSkillList = new ArrayList<>();
            newSkillList.addAll(resume.getSkillIds());

            newResume.setInternshipIds(resume.getInternshipIds());
            newResume.setProjectIds(resume.getProjectIds());
            newResume.setSkillIds(newSkillList);
            newResume.getSkillIds().remove(Integer.valueOf(id));

            setResume(resume, newResume);
        }
    }

    @Override
    public Skill getSkillByIndex(Index index) {
        return skills.asUnmodifiableObservableList().get(index.getZeroBased());
    }

    @Override
    public List<Skill> getSkillsByTag(Tag tag) {
        return skills
            .getItemList()
            .stream()
            .distinct()
            .filter(x -> x.hasTag(tag))
            .map(x -> (Skill) x)
            .collect(Collectors.toList());
    }

    public void sortSkills(Comparator<Skill> sortComparator) {
        skills.sort(sortComparator);
    }

    @Override
    public boolean hasSkillId(int id) {
        for (Skill item : skills) {
            if (item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Skill getSkillById(int id) {
        return skills.getById(id);
    }

    @Override
    public int getSkillSize() {
        return skills.getSize();
    }

    //=========== Resumes ================================================================================

    /**
     * Returns true if a resume with the same identity as {@code resume} exists in the resume book.
     */
    public boolean hasResume(Resume resume) {
        requireNonNull(resume);
        return resumes.contains(resume);
    }

    /**
     * Adds a resume to the resume book.
     * The resume must not already exist in the resume book.
     */
    public void addResume(Resume resume) {
        requireNonNull(resume);
        resumes.add(resume);
    }

    /**
     * Replaces the given resume {@code target} in the list with {@code editedResume}.
     * {@code target} must exist in the resume book.
     * The identity of {@code editedResume} must not be the same as another existing resume in the resume book.
     */
    public void setResume(Resume target, Resume editedResume) {
        resumes.setItem(target, editedResume);
    }

    /**
     * Edits the resume in the resume book.
     */
    public void editResume(Resume target, List<Integer> internshipsId, List<Integer> projectsId,
                           List<Integer> skillsId) {
        target.setInternshipIds(internshipsId);
        target.setProjectIds(projectsId);
        target.setSkillIds(skillsId);
    }

    /**
     * Removes {@code key} from this {@code ResumeBook}.
     * {@code key} must exist in the resume book.
     */
    public void deleteResume(Resume key) {
        resumes.remove(key);
    }

    @Override
    public Resume getResumeByIndex(Index index) {
        return resumes.asUnmodifiableObservableList().get(index.getZeroBased());
    }

    public void sortResumes(Comparator<Resume> sortComparator) {
        resumes.sort(sortComparator);
    }

    @Override
    public boolean hasResumeId(int resumeIndex) {
        for (Resume item : resumes) {
            if (item.getId() == resumeIndex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getResumeSize() {
        return resumes.getSize();
    }


    //=========== Notes ================================================================================

    /**
     * Returns true if a skill with the same identity as {@code skill} exists in the resume book.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a skill to the resume book.
     * The skill must not already exist in the resume book.
     */
    public void addNote(Note note) {
        requireNonNull(note);
        notes.add(note);
        setNoteToDisplay();
    }

    /**
     * Replaces the given skill {@code target} in the list with {@code editedSkill}.
     * {@code target} must exist in the resume book.
     * The identity of {@code editedSkill} must not be the same as another existing skill in the resume book.
     */
    public void setNote(Note target, Note editedNote) {
        notes.setItem(target, editedNote);
        setNoteToDisplay();
    }

    /**
     * Removes {@code key} from this {@code notes}.
     * {@code key} must exist in the note list.
     */
    public void deleteNote(Note key) {
        notes.remove(key);
        setNoteToDisplay();
    }

    /**
     * Resets the list of notes in UI to be consistent with the list kept in model {@code notes}.
     * This must be called after any changes made to notes!
     */
    private void setNoteToDisplay() {
        this.notesToDisplay.setItems(notes.getItemList().stream().map(x -> (Note) x).collect(Collectors.toList()));
    }

    @Override
    public Note getNoteByIndex(Index index) {
        return notes.asUnmodifiableObservableList().get(index.getZeroBased());
    }

    @Override
    public int getNoteListSize() {
        return notes.getSize();
    }

    /**
     * Sorts the list of notes in the resume book using the provided {@code sortComparator}.
     */
    public void sortNotes(Comparator<Note> sortComparator) {
        notes.sort(sortComparator);
        setNoteToDisplay();
    }

    @Override
    public Person getUser() {
        return this.observableUser.getInternalPerson();
    }

    //=========== Util methods ================================================================================

    /**
     * Returns the String form the item list currently on display.
     */
    @Override
    public String toString() {
        return itemsToDisplay.asUnmodifiableObservableList().size() + " items";
    }

    /**
     * Returns the current list of items for display as an {@code ObservableList<Item>}
     */
    @Override
    public ObservableList<Item> getItemToDisplayList() {
        return itemsToDisplay.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Note> getNoteToDisplayList() {
        return notesToDisplay.asUnmodifiableObservableList();
    }

    @Override
    public UniqueItemList<Internship> getInternshipList() {
        return internships;
    }

    @Override
    public UniqueItemList<Project> getProjectList() {
        return projects;
    }

    @Override
    public UniqueItemList<Skill> getSkillList() {
        return skills;
    }

    @Override
    public UniqueItemList<Resume> getResumeList() {
        return resumes;
    }

    @Override
    public UniqueItemList<Note> getNoteList() {
        return notes;
    }

    @Override
    public ObservablePerson getObservableUser() {
        return observableUser;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResumeBook // instanceof handles nulls
                && observableUser.equals(((ResumeBook) other).observableUser)
                && itemsToDisplay.equals(((ResumeBook) other).itemsToDisplay)
                && internships.equals(((ResumeBook) other).internships)
                && projects.equals(((ResumeBook) other).projects)
                && skills.equals(((ResumeBook) other).skills)
                && resumes.equals(((ResumeBook) other).resumes)
                && notes.equals(((ResumeBook) other).notes));
    }

    @Override
    public int hashCode() {
        return itemsToDisplay.hashCode();
    }

}
