package seedu.address.model;

import java.util.List;

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
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of a resume book
 */
public interface ReadOnlyResumeBook {

    //=========== Get Detail Operations ======================================================================

    /**
     * Returns the user of the resume book as a Person object.
     *
     * @return {@code Person} item representing the user.
     */
    Person getUser();

    /**
     * Returns an unmodifiable view of the current items list as an {@code ObservableList}
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemToDisplayList();

    /**
     * Returns the display type in String form of the current display list in the list box
     */
    String getDisplayType();

    /**
     * Returns an unmodifiable view of the current notes list as an {@code ObservableList}
     * This list will not contain any duplicate items.
     */
    ObservableList<Note> getNoteToDisplayList();

    /**
     * Returns an unmodifiable view of the current internship list.
     * This list will not contain any duplicate internship.
     */
    UniqueItemList<Internship> getInternshipList();

    /**
     * Returns an unmodifiable view of the current project list.
     * This list will not contain any duplicate project.
     */
    UniqueItemList<Project> getProjectList();

    /**
     * Returns an unmodifiable view of the current skill list.
     * This list will not contain any duplicate skill.
     */
    UniqueItemList<Skill> getSkillList();

    /**
     * Returns an unmodifiable view of the current resume list.
     * This list will not contain any duplicate resume.
     */
    UniqueItemList<Resume> getResumeList();

    /**
     * Returns an unmodifiable view of the current note list.
     * This list will not contain any duplicate resume.
     */
    UniqueItemList<Note> getNoteList();

    /**
     * Returns the user in the Resume Book as an ObservablePerson object.
     */
    ObservablePerson getObservableUser();

    //=========== Query List Operations ================================================================================

    /**
     * Return an Internship item at the specified index from the internship list.
     *
     * @param index index of internship
     * @return Internship item at {@code index}
     */
    Internship getInternshipByIndex(Index index);

    /**
     * Returns a list of Internship which is tagged with {@code tag}.
     * @param tag the {@code tag} which is expected in the Internship item.
     * @return List of Internship tagged with {@code tag}.
     */
    List<Internship> getInternshipsByTag(Tag tag);

    boolean hasInternshipId(int id);

    /**
     * Return a Internship item with the specified ID from the internship list.
     *
     * @param id
     * @return Internship item with {@code id}
     */
    Internship getInternshipById(int id);

    /**
     * Return the size of the internship list.
     */
    int getInternshipSize();

    /**
     * Return a Project item at the specified index from the project list.
     *
     * @param index index of project
     * @return Project item at {@code index}
     */
    Project getProjectByIndex(Index index);

    /**
     * Returns a list of Project which is tagged with {@code tag}.
     * @param tag the {@code tag} which is expected in the Project item.
     * @return List of Project tagged with {@code tag}.
     */
    List<Project> getProjectsByTag(Tag tag);

    boolean hasProjectId(int id);

    /**
     * Return a Project item with the specified ID from the project list.
     *
     * @param id
     * @return Project item with {@code id}
     */
    Project getProjectById(int id);

    /**
     * Return the size of the project list.
     */
    int getProjectSize();

    /**
     * Return a Skill item at the specified index from the skill list.
     *
     * @param index index of skill
     * @return Skill item at {@code index}
     */
    Skill getSkillByIndex(Index index);

    /**
     * Returns a list of Skill which is tagged with {@code tag}.
     * @param tag the {@code tag} which is expected in the Skill item.
     * @return List of Skill tagged with {@code tag}.
     */
    List<Skill> getSkillsByTag(Tag tag);

    boolean hasSkillId(int id);

    /**
     * Return a Skill item with the specified ID from the skill list.
     *
     * @param id
     * @return Skill item with {@code id}
     */
    Skill getSkillById(int id);

    /**
     * Return the size of the skill list.
     */
    int getSkillSize();

    /**
     * Return a Resume item at the specified index from the resume list.
     *
     * @param index index of resume
     * @return Resume item at {@code index}
     */
    Resume getResumeByIndex(Index index);

    boolean hasResumeId(int id);

    /**
     * Return the size of the resume list.
     */
    int getResumeSize();

    /**
     * Return a Note item at the specified index from the note list.
     *
     * @param index index of skill
     * @return Skill item at {@code index}
     */
    Note getNoteByIndex(Index index);

    /**
     * Return the size of the note list.
     */
    public int getNoteListSize();

}
