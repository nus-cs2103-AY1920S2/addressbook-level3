package seedu.address.testutil;

import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ResumeBook;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Note;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;

/**
 * A utility class to help with building ResumeBook objects.
 * Example usage: <br>
 *     {@code ResumeBook ab = new ResumeBookBuilder().withPerson("John", "Doe").build();}
 */
public class ResumeBookBuilder {

    private ResumeBook resumeBook;

    public ResumeBookBuilder() {
        resumeBook = new ResumeBook();
    }

    public ResumeBookBuilder(ReadOnlyResumeBook resumeBook) {
        this.resumeBook = new ResumeBook();
        this.resumeBook.setUser(new PersonBuilder(resumeBook.getUser()).build());
        resumeBook.getInternshipList().getItemList().stream()
                .map(x -> new InternshipBuilder((Internship) x).build())
                .forEach(x -> this.resumeBook.addInternship(x));
        resumeBook.getSkillList().getItemList().stream()
                .map(x -> new SkillBuilder((Skill) x).build())
                .forEach(x -> this.resumeBook.addSkill(x));
        resumeBook.getProjectList().getItemList().stream()
                .map(x -> new ProjectBuilder((Project) x).build())
                .forEach(x -> this.resumeBook.addProject(x));
        resumeBook.getResumeList().getItemList().stream()
                .map(x -> new ResumeBuilder((Resume) x).build())
                .forEach(x -> this.resumeBook.addResume(x));
        resumeBook.getNoteList().getItemList().stream()
                .map(x -> new NoteBuilder((Note) x).build())
                .forEach(x -> this.resumeBook.addNote(x));
    }

    /**
     * Sets a new {@code Person} to the {@code ResumeBook} that we are building.
     */
    public ResumeBookBuilder withPerson(Person person) {
        resumeBook.setUser(person);
        return this;
    }

    /**
     * Adds an internship to the resume book.
     * @param internship internship to add.
     * @return builder object with added internship.
     */
    public ResumeBookBuilder withInternship(Internship internship) {
        resumeBook.addInternship(internship);
        return this;
    }

    /**
     * Adds a project to the resume book.
     * @param project project to add.
     * @return builder object with added project.
     */
    public ResumeBookBuilder withProject(Project project) {
        resumeBook.addProject(project);
        return this;
    }

    /**
     * Adds a note to the resume book.
     * @param note note to add.
     * @return builder object with added note.
     */
    public ResumeBookBuilder withNote(Note note) {
        resumeBook.addNote(note);
        return this;
    }

    /**
     * Adds a resume to the resume book.
     * @param resume resume to add.
     * @return builder object with added resume.
     */
    public ResumeBookBuilder withResume(Resume resume) {
        resumeBook.addResume(resume);
        return this;
    }

    /**
     * Adds a skill to the resume book.
     * @param skill skill to add.
     * @return builder object with added skill.
     */
    public ResumeBookBuilder withSkill(Skill skill) {
        resumeBook.addSkill(skill);
        return this;
    }

    public ResumeBook build() {
        return resumeBook;
    }
}
