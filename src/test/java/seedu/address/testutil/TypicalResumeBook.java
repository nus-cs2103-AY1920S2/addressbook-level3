package seedu.address.testutil;

import seedu.address.model.ResumeBook;

/**
 * A utility class containing a list of {@code ResumeBook} objects to be used in tests.
 */
public class TypicalResumeBook {
    public static final ResumeBook TYPICAL = new ResumeBookBuilder()
            .withInternship(new InternshipBuilder(TypicalInternship.GOOGLE).build())
            .withInternship(new InternshipBuilder(TypicalInternship.NINJA_VAN).build())
            .withInternship(new InternshipBuilder(TypicalInternship.PAYPAL).build())
            .withNote((new NoteBuilder(TypicalNote.FINISH_CS_2103).build()))
            .withPerson(new PersonBuilder(TypicalPerson.ALICE).build())
            .withProject(new ProjectBuilder(TypicalProject.ORBITAL).build())
            .withProject(new ProjectBuilder(TypicalProject.DUKE).build())
            .withSkill(new SkillBuilder(TypicalSkill.REACT).build())
            .withSkill(new SkillBuilder(TypicalSkill.GIT).build())
            .withResume(new ResumeBuilder(TypicalResume.ME_RESUME).build())
            .withResume(new ResumeBuilder(TypicalResume.SE_RESUME).build())
            .build();

    /*
        DO NOT CHANGE THIS unless JsonSerializableResumeBookTest and JsonResumeBookStorageTest contents are updated
        accordingly. The json files are based against this.
     */
    public static final ResumeBook TYPICAL_WITHOUT_GOGGLE = new ResumeBookBuilder()
            .withInternship(new InternshipBuilder(TypicalInternship.NINJA_VAN).build())
            .withInternship(new InternshipBuilder(TypicalInternship.PAYPAL).build())
            .withNote(new NoteBuilder(TypicalNote.FINISH_CS_2103).build())
            .withPerson(new PersonBuilder(TypicalPerson.ALICE).build())
            .withProject(new ProjectBuilder(TypicalProject.ORBITAL).build())
            .withSkill(new SkillBuilder(TypicalSkill.REACT).build())
            .withResume(new ResumeBuilder(TypicalResume.ME_RESUME).build())
            .build();

    public static final ResumeBook TYPICAL_WITHOUT_GOOGLE = new ResumeBookBuilder()
            .withInternship(new InternshipBuilder(TypicalInternship.NINJA_VAN).build())
            .withInternship(new InternshipBuilder(TypicalInternship.PAYPAL).build())
            .withNote(new NoteBuilder(TypicalNote.FINISH_CS_2103).build())
            .withNote((new NoteBuilder(TypicalNote.FINISH_HOMEWORK).build()))
            .withPerson(new PersonBuilder(TypicalPerson.ALICE).build())
            .withProject(new ProjectBuilder(TypicalProject.ORBITAL).build())
            .withProject(new ProjectBuilder(TypicalProject.DUKE).build())
            .withSkill(new SkillBuilder(TypicalSkill.REACT).build())
            .withSkill(new SkillBuilder(TypicalSkill.GIT).build())
            .withResume(new ResumeBuilder(TypicalResume.ME_RESUME).build())
            .withResume(new ResumeBuilder(TypicalResume.SE_RESUME).build())
            .build();

    // This TypicalResumeBook is used mainly for the ResumeEdit and TagPull Command tests
    // It will be best not to add more things, nor add Tags to the Items that are added
    public static final ResumeBook TYPICAL_WITH_FILLED_RESUME = new ResumeBookBuilder()
            .withInternship(TypicalInternship.GOOGLE)
            .withInternship(TypicalInternship.NINJA_VAN)
            .withPerson(TypicalPerson.ALICE)
            .withProject(TypicalProject.ORBITAL)
            .withProject(TypicalProject.DUKE)
            .withSkill(TypicalSkill.REACT)
            .withSkill(TypicalSkill.GIT)
            .withResume(TypicalResume.ME_RESUME)
            .withResume(TypicalResume.SE_RESUME)
            .withResume(TypicalResume.FILLED_RESUME)
            .build();
}
