package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import static seedu.address.testutil.TypicalInternship.GOOGLE;
import static seedu.address.testutil.TypicalInternship.NINJA_VAN;
import static seedu.address.testutil.TypicalInternship.PAYPAL;
import static seedu.address.testutil.TypicalNote.FINISH_CS_2103;
import static seedu.address.testutil.TypicalNote.FINISH_HOMEWORK;
import static seedu.address.testutil.TypicalPerson.ALICE;
import static seedu.address.testutil.TypicalProject.DUKE;
import static seedu.address.testutil.TypicalProject.ORBITAL;
import static seedu.address.testutil.TypicalResume.FILLED_RESUME;
import static seedu.address.testutil.TypicalResume.ME_RESUME;
import static seedu.address.testutil.TypicalResume.SE_RESUME;
import static seedu.address.testutil.TypicalResumeBook.TYPICAL;
import static seedu.address.testutil.TypicalResumeBook.TYPICAL_WITHOUT_GOGGLE;
import static seedu.address.testutil.TypicalResumeBook.TYPICAL_WITHOUT_GOOGLE;
import static seedu.address.testutil.TypicalResumeBook.TYPICAL_WITH_FILLED_RESUME;
import static seedu.address.testutil.TypicalSkill.GIT;
import static seedu.address.testutil.TypicalSkill.REACT;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.item.Internship;
import seedu.address.model.item.Note;
import seedu.address.model.item.ObservablePerson;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.model.item.exceptions.ItemNotFoundException;
import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.NoteBuilder;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.ResumeBuilder;
import seedu.address.testutil.SkillBuilder;

public class ResumeBookTest {

    private ResumeBook resumeBook;

    private final Person defaultUser = new Person(new DisplayPicture("/images/Duke.png"), new Name("Default name"),
            new Description("Default description"), new Phone("000"), new Email("000@gmail.com"), new Github("000"),
            new University("Default university"), new Major("Default major"),
            new Time("12-9999"), new Time("12-9999"), new Cap("0.0 5.0"));

    @BeforeEach
    public void setUp() {
        resumeBook = new ResumeBook();
    }

    @Test
    public void constructor() {
        assertEquals(new UniqueItemList<>(), resumeBook.getInternshipList());
        assertEquals(new UniqueItemList<>(), resumeBook.getNoteList());
        assertEquals(new UniqueItemList<>(), resumeBook.getProjectList());
        assertEquals(new UniqueItemList<>(), resumeBook.getResumeList());
        assertEquals(new UniqueItemList<>(), resumeBook.getNoteList());
        assertEquals("", resumeBook.getDisplayType());
        assertEquals(new ObservablePerson(defaultUser), resumeBook.getObservableUser());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyResumeBook_replacesData() {
        ResumeBook newData = TYPICAL_WITHOUT_GOGGLE;
        resumeBook.resetData(newData);
        assertEquals(newData, resumeBook);
    }

    @Test
    public void setUser_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.setUser(null));
    }

    @Test
    public void setUser_withValidPerson_replacesUser() {
        Person newUser = ALICE;
        resumeBook.setUser(newUser);
        assertEquals(ALICE, resumeBook.getUser());
    }

    @Test
    public void hasInternship_nullInternship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.hasInternship(null));
    }

    @Test
    public void hasInternship_internshipNotInResumeBook_returnsFalse() {
        assertFalse(resumeBook.hasInternship(GOOGLE));
    }

    @Test
    public void hasInternship_internshipInResumeBook_returnsTrue() {
        resumeBook.addInternship(GOOGLE);
        assertTrue(resumeBook.hasInternship(GOOGLE));
    }

    @Test
    public void hasInternship_internshipWithSameIdentityFieldsInResumeBook_returnsTrue() {
        resumeBook.addInternship(GOOGLE);
        Internship editedGoogle = new InternshipBuilder(GOOGLE).withDescription("Justice").withTags("wut").build();
        assertTrue(resumeBook.hasInternship(editedGoogle));
    }

    @Test
    public void deleteInternship_internshipIsPresent_success() {
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOGGLE);

        assertTrue(resumeBook.hasInternship(PAYPAL));
        resumeBook.deleteInternship(PAYPAL);
        assertFalse(resumeBook.hasInternship(PAYPAL));
    }

    @Test
    public void deleteInternship_internshipIsNotPresent_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> resumeBook.deleteInternship(GOOGLE));
    }

    @Test
    public void setInternship_internshipIsPresentAndEditIsValidButInternshipIsNotSameAnymoreAfterEdit_success() {
        // Starts with a typical ResumeBook that contains GOOGLE
        resumeBook = new ResumeBook(TYPICAL);

        Internship editedGoogle = new InternshipBuilder(GOOGLE).withRole("Guardian").withTags("wut").build();
        assertFalse(GOOGLE.isSame(editedGoogle));
        resumeBook.setInternship(GOOGLE, editedGoogle);
        assertFalse(resumeBook.hasInternship(GOOGLE));
        assertTrue(resumeBook.hasInternship(editedGoogle));
    }

    @Test
    public void setInternship_internshipIsPresentAndEditIsValidAndInternshipIsSameAfterEdit_success() {
        // Starts with a typical ResumeBook that contains GOOGLE
        resumeBook = new ResumeBook(TYPICAL);

        // Since only tags are edited this Internship is considered the same
        Internship editedGoogle = new InternshipBuilder(GOOGLE).withTags("wut").build();
        assertTrue(GOOGLE.isSame(editedGoogle));
        resumeBook.setInternship(GOOGLE, editedGoogle);
        // hasInternship use isSame() check to determine if it contains an Internship
        assertTrue(resumeBook.hasInternship(GOOGLE));
        assertTrue(resumeBook.hasInternship(editedGoogle));
    }

    @Test
    public void setInternship_internshipIsNotPresent_throwsItemNotFoundException() {
        // Starts with a typical ResumeBook that does not contain GOOGLE
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOOGLE);

        Internship editedGoogle = new InternshipBuilder(GOOGLE).withTags("wut").build();
        assertThrows(ItemNotFoundException.class, () -> resumeBook.setInternship(GOOGLE, editedGoogle));
    }

    @Test
    public void setInternship_internshipIsPresentButTheEditClashesWithAnExistingItem_throwsDuplicateItemException() {
        // Starts with a typical ResumeBook that contains both PAYPAL and GOOGLE
        resumeBook = new ResumeBook(TYPICAL);

        // This edit does not change the identity fields of PAYPAL
        Internship editedPaypal = new InternshipBuilder(PAYPAL).withDescription("wut").withTags("wut").build();
        assertThrows(DuplicateItemException.class, () -> resumeBook.setInternship(GOOGLE, editedPaypal));
    }

    @Test
    public void sortInternships_byNameInDescendingOrder_success() {
        // Starts with a typical ResumeBook that contains both GOOGLE, NINJA_VAN, and PAYPAL in that order.
        resumeBook = new ResumeBook(TYPICAL);

        UniqueItemList<Internship> sortedInternshipList = new UniqueItemList<>();
        sortedInternshipList.add(PAYPAL);
        sortedInternshipList.add(NINJA_VAN);
        sortedInternshipList.add(GOOGLE);

        assertNotEquals(sortedInternshipList, resumeBook.getInternshipList());
        // After the sort the ordering should be PAYPAL, NINJA_VAN, GOOGLE
        resumeBook.sortInternships(Comparator.comparing(Internship::getName).reversed());
        assertEquals(sortedInternshipList, resumeBook.getInternshipList());
    }

    @Test
    public void getInternshipByTags_noneMatch_returnsEmptyList() {
        // Starts with a typical ResumeBook that contains Internships.
        resumeBook = new ResumeBook(TYPICAL);
        assertTrue(resumeBook.getInternshipList().getSize() > 0);

        Tag tag = new Tag("NoItemWillContainThis");
        assertEquals(0, resumeBook.getInternshipsByTag(tag).size());
    }

    @Test
    public void getInternshipByTags_thereIsMatch_returnsNonEmptyList() {
        // Starts with a typical ResumeBook that contains 3 Internships that all contain the tag "tech".
        resumeBook = new ResumeBook(TYPICAL);
        assertTrue(resumeBook.getInternshipList().getSize() > 0);

        Tag tag = new Tag("tech");
        assertEquals(3, resumeBook.getInternshipsByTag(tag).size());
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.hasProject(null));
    }

    @Test
    public void hasProject_projectNotInResumeBook_returnsFalse() {
        assertFalse(resumeBook.hasProject(DUKE));
    }

    @Test
    public void hasProject_projectInResumeBook_returnsTrue() {
        resumeBook.addProject(DUKE);
        assertTrue(resumeBook.hasProject(DUKE));
    }

    @Test
    public void hasProject_projectWithSameIdentityFieldsInResumeBook_returnsTrue() {
        resumeBook.addProject(DUKE);
        Project editedDuke = new ProjectBuilder(DUKE).withDescription("Justice").withTags("wut").build();
        assertTrue(resumeBook.hasProject(editedDuke));
    }

    @Test
    public void deleteProject_projectIsPresent_success() {
        // Start with a ResumeBook that has DUKE
        resumeBook = new ResumeBook(TYPICAL);

        assertTrue(resumeBook.hasProject(DUKE));
        resumeBook.deleteProject(DUKE);
        assertFalse(resumeBook.hasProject(DUKE));
    }

    @Test
    public void deleteProject_projectIsNotPresent_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> resumeBook.deleteProject(DUKE));
    }

    @Test
    public void setProject_projectIsPresentAndEditIsValidButProjectIsNotSameAnymoreAfterEdit_success() {
        // Starts with a typical ResumeBook that contains DUKE
        resumeBook = new ResumeBook(TYPICAL);

        Project editedDuke = new ProjectBuilder(DUKE).withTime("12-1970").withTags("wut").build();
        assertFalse(DUKE.isSame(editedDuke));
        resumeBook.setProject(DUKE, editedDuke);
        assertFalse(resumeBook.hasProject(DUKE));
        assertTrue(resumeBook.hasProject(editedDuke));
    }

    @Test
    public void setProject_projectIsPresentAndEditIsValidAndProjectIsSameAfterEdit_success() {
        // Starts with a typical ResumeBook that contains DUKE
        resumeBook = new ResumeBook(TYPICAL);

        // Since only tags are edited this project is considered the same
        Project editedDuke = new ProjectBuilder(DUKE).withTags("wut").build();
        assertTrue(DUKE.isSame(editedDuke));
        resumeBook.setProject(DUKE, editedDuke);
        assertTrue(resumeBook.hasProject(DUKE));
        assertTrue(resumeBook.hasProject(editedDuke));
    }

    @Test
    public void setProject_projectIsNotPresent_throwsItemNotFoundException() {
        // Starts with a typical ResumeBook that does not contain DUKE
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOGGLE);

        Project editedDuke = new ProjectBuilder(DUKE).withTags("wut").build();
        assertThrows(ItemNotFoundException.class, () -> resumeBook.setProject(DUKE, editedDuke));
    }

    @Test
    public void setProject_projectIsPresentButTheEditClashesWithAnExistingItem_throwsDuplicateItemException() {
        // Starts with a typical ResumeBook that contains both ORBITAL and DUKE
        resumeBook = new ResumeBook(TYPICAL);

        // This edit does not change the identity fields of ORBITAL
        Project editedOrbital = new ProjectBuilder(ORBITAL).withDescription("wut").withTags("wut").build();
        assertThrows(DuplicateItemException.class, () -> resumeBook.setProject(DUKE, editedOrbital));
    }

    @Test
    public void sortProjects_byNameInAscendingOrder_success() {
        // Starts with a typical ResumeBook that contains ORBITAL and DUKE in that order.
        resumeBook = new ResumeBook(TYPICAL);

        UniqueItemList<Project> sortedProjectList = new UniqueItemList<>();
        sortedProjectList.add(DUKE);
        sortedProjectList.add(ORBITAL);

        assertNotEquals(sortedProjectList, resumeBook.getProjectList());
        // After the sort the ordering should be DUKE, ORBITAL
        resumeBook.sortProjects(Comparator.comparing(Project::getName));
        assertEquals(sortedProjectList, resumeBook.getProjectList());
    }

    @Test
    public void getProjectByTags_noneMatch_returnsEmptyList() {
        // Starts with a typical ResumeBook that contains some projects.
        resumeBook = new ResumeBook(TYPICAL);
        assertTrue(resumeBook.getProjectList().getSize() > 0);

        Tag tag = new Tag("NoItemWillContainThis");
        assertEquals(0, resumeBook.getProjectsByTag(tag).size());
    }

    @Test
    public void getProjectsByTags_thereIsMatch_returnsNonEmptyList() {
        // Starts with a typical ResumeBook that with DUKE and ORBITAL. Only DUKE contains tag "Java"
        resumeBook = new ResumeBook(TYPICAL);
        assertTrue(resumeBook.getProjectList().getSize() > 0);

        Tag tag = new Tag("Java");
        assertEquals(1, resumeBook.getProjectsByTag(tag).size());
        assertTrue(resumeBook.getProjectsByTag(tag).contains(DUKE));
    }

    @Test
    public void hasResume_nullResume_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.hasResume(null));
    }

    @Test
    public void hasResume_resumeNotInResumeBook_returnsFalse() {
        assertFalse(resumeBook.hasResume(SE_RESUME));
    }

    @Test
    public void hasResume_resumeInResumeBook_returnsTrue() {
        resumeBook.addResume(SE_RESUME);
        assertTrue(resumeBook.hasResume(SE_RESUME));
    }

    @Test
    public void hasResume_resumeWithSameIdentityFieldsInResumeBook_returnsTrue() {
        resumeBook.addResume(SE_RESUME);
        Resume editedSe = new ResumeBuilder(SE_RESUME).withTags("wut").build();
        assertTrue(resumeBook.hasResume(editedSe));
    }

    @Test
    public void deleteResume_resumeIsPresent_success() {
        // Start with a ResumeBook that has SE_RESUME
        resumeBook = new ResumeBook(TYPICAL);

        assertTrue(resumeBook.hasResume(SE_RESUME));
        resumeBook.deleteResume(SE_RESUME);
        assertFalse(resumeBook.hasResume(SE_RESUME));
    }

    @Test
    public void deleteResume_resumeIsNotPresent_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> resumeBook.deleteResume(SE_RESUME));
    }

    @Test
    public void setResume_resumeIsPresentAndEditIsValidButResumeIsNotSameAnymoreAfterEdit_success() {
        // Starts with a typical ResumeBook that contains SE_RESUME
        resumeBook = new ResumeBook(TYPICAL);

        Resume editedSe = new ResumeBuilder(SE_RESUME).withName("Meme").withTags("wut").build();
        assertFalse(SE_RESUME.isSame(editedSe));
        resumeBook.setResume(SE_RESUME, editedSe);
        assertFalse(resumeBook.hasResume(SE_RESUME));
        assertTrue(resumeBook.hasResume(editedSe));
    }

    @Test
    public void setResume_resumeIsPresentAndEditIsValidAndResumeIsSameAfterEdit_success() {
        // Starts with a typical ResumeBook that contains SE_RESUME
        resumeBook = new ResumeBook(TYPICAL);

        // Since only tags are edited this resume is considered the same
        Resume editedSe = new ResumeBuilder(SE_RESUME).withTags("wut").build();
        assertTrue(SE_RESUME.isSame(editedSe));
        resumeBook.setResume(SE_RESUME, editedSe);
        assertTrue(resumeBook.hasResume(SE_RESUME));
        assertTrue(resumeBook.hasResume(editedSe));
    }

    @Test
    public void setResume_resumeIsNotPresent_throwsItemNotFoundException() {
        // Starts with a typical ResumeBook that does not contain SE_RESUME
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOGGLE);

        Resume editedSe = new ResumeBuilder(SE_RESUME).withTags("wut").build();
        assertThrows(ItemNotFoundException.class, () -> resumeBook.setResume(SE_RESUME, editedSe));
    }

    @Test
    public void setResume_resumeIsPresentButTheEditClashesWithAnExistingItem_throwsDuplicateItemException() {
        // Starts with a typical ResumeBook that contains both ME_RESUME and SE_RESUME
        resumeBook = new ResumeBook(TYPICAL);

        // This edit does not change the identity fields of ME_RESUME
        Resume editedMe = new ResumeBuilder(ME_RESUME).withTags("wut").build();
        assertThrows(DuplicateItemException.class, () -> resumeBook.setResume(SE_RESUME, editedMe));
    }

    @Test
    public void sortResumes_byNameInAscendingOrder_success() {
        // Starts with a typical ResumeBook that contains ME_RESUME, SE_RESUME, and FILLED_RESUME in that order.
        resumeBook = new ResumeBook(TYPICAL_WITH_FILLED_RESUME);

        UniqueItemList<Resume> sortedResumeList = new UniqueItemList<>();
        sortedResumeList.add(FILLED_RESUME);
        sortedResumeList.add(ME_RESUME);
        sortedResumeList.add(SE_RESUME);

        assertNotEquals(sortedResumeList, resumeBook.getResumeList());

        resumeBook.sortResumes(Comparator.comparing(Resume::getName));
        assertEquals(sortedResumeList, resumeBook.getResumeList());
    }

    @Test
    public void hasSkill_nullSkill_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.hasSkill(null));
    }

    @Test
    public void hasSkill_skillNotInResumeBook_returnsFalse() {
        assertFalse(resumeBook.hasSkill(GIT));
    }

    @Test
    public void hasSkill_skillInResumeBook_returnsTrue() {
        resumeBook.addSkill(GIT);
        assertTrue(resumeBook.hasSkill(GIT));
    }

    @Test
    public void hasSkill_skillWithSameIdentityFieldsInResumeBook_returnsTrue() {
        resumeBook.addSkill(GIT);
        Skill editedGit = new SkillBuilder(GIT).withTags("wut").build();
        assertTrue(resumeBook.hasSkill(editedGit));
    }

    @Test
    public void deleteSkill_skillIsPresent_success() {
        // Start with a ResumeBook that has GIT
        resumeBook = new ResumeBook(TYPICAL);

        assertTrue(resumeBook.hasSkill(GIT));
        resumeBook.deleteSkill(GIT);
        assertFalse(resumeBook.hasSkill(GIT));
    }

    @Test
    public void deleteSkill_skillIsNotPresent_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> resumeBook.deleteSkill(GIT));
    }

    @Test
    public void setSkill_skillIsPresentAndEditIsValidButSkillIsNotSameAnymoreAfterEdit_success() {
        // Starts with a typical ResumeBook that contains GIT
        resumeBook = new ResumeBook(TYPICAL);

        // Identity field for a Skill object is name
        Skill editedGit = new SkillBuilder(GIT).withName("Gut").withLevel(Level.ADVANCED).withTags("wut").build();
        assertFalse(DUKE.isSame(editedGit));
        resumeBook.setSkill(GIT, editedGit);
        assertFalse(resumeBook.hasSkill(GIT));
        assertTrue(resumeBook.hasSkill(editedGit));
    }

    @Test
    public void setSkill_skillIsPresentAndEditIsValidAndSkillIsSameAfterEdit_success() {
        // Starts with a typical ResumeBook that contains GIT
        resumeBook = new ResumeBook(TYPICAL);

        // Since only tags are edited this skill is considered the same
        Skill editedGit = new SkillBuilder(GIT).withTags("wut").build();
        assertTrue(GIT.isSame(editedGit));
        resumeBook.setSkill(GIT, editedGit);
        assertTrue(resumeBook.hasSkill(GIT));
        assertTrue(resumeBook.hasSkill(editedGit));
    }

    @Test
    public void setSkill_skillIsNotPresent_throwsItemNotFoundException() {
        // Starts with a typical ResumeBook that does not contain GIT
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOGGLE);

        Skill editedGit = new SkillBuilder(GIT).withTags("wut").build();
        assertThrows(ItemNotFoundException.class, () -> resumeBook.setSkill(GIT, editedGit));
    }

    @Test
    public void setSkill_skillIsPresentButTheEditClashesWithAnExistingItem_throwsDuplicateItemException() {
        // Starts with a typical ResumeBook that contains both GIT and REACT
        resumeBook = new ResumeBook(TYPICAL);

        // This edit does not change the identity fields of REACT
        Skill editedReact = new SkillBuilder(REACT).withTags("wut").build();
        assertThrows(DuplicateItemException.class, () -> resumeBook.setSkill(GIT, editedReact));
    }

    @Test
    public void sortSkills_byNameInAscendingOrder_success() {
        // Starts with a typical ResumeBook that contains REACT and GIT in that order.
        resumeBook = new ResumeBook(TYPICAL);
        UniqueItemList<Skill> sortedSkillList = new UniqueItemList<>();
        sortedSkillList.add(GIT);
        sortedSkillList.add(REACT);

        assertNotEquals(sortedSkillList, resumeBook.getSkillList());
        // After the sort the ordering should be GIT, REACT
        resumeBook.sortSkills(Comparator.comparing(Skill::getName));
        assertEquals(sortedSkillList, resumeBook.getSkillList());
    }

    @Test
    public void getSkillsByTags_noneMatch_returnsEmptyList() {
        // Starts with a typical ResumeBook that contains some projects.
        resumeBook = new ResumeBook(TYPICAL);
        assertTrue(resumeBook.getSkillList().getSize() > 0);

        Tag tag = new Tag("NoItemWillContainThis");
        assertEquals(0, resumeBook.getSkillsByTag(tag).size());
    }

    @Test
    public void getSkillsByTags_thereIsMatch_returnsNonEmptyList() {
        // Starts with a typical ResumeBook that with GIT and REACT that both contain the tag "tech"
        resumeBook = new ResumeBook(TYPICAL);
        assertTrue(resumeBook.getSkillList().getSize() > 0);

        Tag tag = new Tag("tech");
        assertEquals(2, resumeBook.getSkillsByTag(tag).size());
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> resumeBook.hasNote(null));
    }

    @Test
    public void hasNote_noteNotInResumeBook_returnsFalse() {
        assertFalse(resumeBook.hasNote(FINISH_CS_2103));
    }

    @Test
    public void hasNote_noteInResumeBook_returnsTrue() {
        resumeBook.addNote(FINISH_CS_2103);
        assertTrue(resumeBook.hasNote(FINISH_CS_2103));
    }

    @Test
    public void hasNote_noteWithSameIdentityFieldsInResumeBook_returnsTrue() {
        resumeBook.addNote(FINISH_CS_2103);
        Note editedNote = new NoteBuilder(FINISH_CS_2103).withTags("wut").build();
        assertTrue(resumeBook.hasNote(editedNote));
    }

    @Test
    public void deleteNote_noteIsPresent_success() {
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOOGLE);

        assertTrue(resumeBook.hasNote(FINISH_HOMEWORK));
        resumeBook.deleteNote(FINISH_HOMEWORK);
        assertFalse(resumeBook.hasNote(FINISH_HOMEWORK));
    }

    @Test
    public void deleteNote_noteIsNotPresent_throwsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> resumeBook.deleteNote(FINISH_HOMEWORK));
    }

    @Test
    public void setNote_noteIsPresentAndEditIsValidButNoteIsNotSameAnymoreAfterEdit_success() {
        // Starts with a typical ResumeBook that contains FINISH_HOMEWORK
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOOGLE);

        Note editedNote = new NoteBuilder(FINISH_HOMEWORK).withName("Finish This").withTags("wut").build();
        assertFalse(FINISH_HOMEWORK.isSame(editedNote));
        resumeBook.setNote(FINISH_HOMEWORK, editedNote);
        assertFalse(resumeBook.hasNote(FINISH_HOMEWORK));
        assertTrue(resumeBook.hasNote(editedNote));
    }

    @Test
    public void setNote_noteIsPresentAndEditIsValidAndNoteIsSameAfterEdit_success() {
        // Starts with a typical ResumeBook that contains FINISH_HOMEWORK
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOOGLE);

        // Since only tags are edited this note is considered the same
        Note editedNote = new NoteBuilder(FINISH_HOMEWORK).withTags("wut").build();
        assertTrue(FINISH_HOMEWORK.isSame(editedNote));
        resumeBook.setNote(FINISH_HOMEWORK, editedNote);

        assertTrue(resumeBook.hasNote(FINISH_HOMEWORK));
        assertTrue(resumeBook.hasNote(editedNote));
    }

    @Test
    public void setNote_noteIsNotPresent_throwsItemNotFoundException() {
        // Starts with a typical ResumeBook that does not contain FINISH_HOMEWORK
        resumeBook = new ResumeBook(TYPICAL);

        Note editedNote = new NoteBuilder(FINISH_HOMEWORK).withTags("wut").build();
        assertThrows(ItemNotFoundException.class, () -> resumeBook.setNote(FINISH_HOMEWORK, editedNote));
    }

    @Test
    public void setNote_noteIsPresentButTheEditClashesWithAnExistingItem_throwsDuplicateItemException() {
        // Starts with a typical ResumeBook that contains both FINISH_CS_2103 and FINISH_HOMEWORK
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOOGLE);

        // This edit does not change the identity fields of FINISH_CS_2103
        Note editedNote = new NoteBuilder(FINISH_CS_2103).withTags("wut").build();
        assertThrows(DuplicateItemException.class, () -> resumeBook.setNote(FINISH_HOMEWORK, editedNote));
    }

    @Test
    public void sortNotes_byTimeInAscendingOrder_success() {
        // Starts with a typical ResumeBook that contains FINISH_CS_2103 and FINISH_HOMEWORK in that order.
        // Deadline for FINISH_CS_2103 is 12-2020 and for the other is 03-2020
        resumeBook = new ResumeBook(TYPICAL_WITHOUT_GOOGLE);

        UniqueItemList<Note> sortedNoteList = new UniqueItemList<>();
        sortedNoteList.add(FINISH_HOMEWORK);
        sortedNoteList.add(FINISH_CS_2103);

        assertNotEquals(sortedNoteList, resumeBook.getNoteList());
        // After the sort the ordering should be FINISH_HOMEWORK, FINISH_CS_2103
        resumeBook.sortNotes(Comparator.comparing(Note::getTime));
        assertEquals(sortedNoteList, resumeBook.getNoteList());
    }
}
