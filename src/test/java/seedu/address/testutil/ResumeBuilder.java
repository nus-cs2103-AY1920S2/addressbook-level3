package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.item.Internship;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Resume objects.
 */
public class ResumeBuilder {
    public static final String DEFAULT_NAME = "Company A";
    public static final String[] DEFAULT_TAGS = {"backend", "naps"};

    private Name name;
    private Set<Tag> tags = new HashSet<>();

    // The actual Resume uses ids, but during building I will use the objects and map it to its ids
    private List<Internship> internships = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Skill> skills = new ArrayList<>();

    public ResumeBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    public ResumeBuilder(Resume resumeToCopy) {
        name = resumeToCopy.getName();
        tags = new HashSet<>(resumeToCopy.getTags());
    }

    /**
     * Creates a ResumeBuilder with a certain name.
     * @param name String name
     * @return ResumeBuilder with new name
     */
    public ResumeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Resume} that we are building.
     */
    public ResumeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Creates a ResumeBuilder with a newly added Internship.
     * @param internship the Internship to be added.
     * @return ResumeBuilder with a newly added Internship.
     */
    public ResumeBuilder withInternship(Internship internship) {
        internships.add(internship);
        return this;
    }

    /**
     * Creates a ResumeBuilder with a newly added Project.
     * @param project the Project to be added.
     * @return ResumeBuilder with a newly added Project.
     */
    public ResumeBuilder withProject(Project project) {
        projects.add(project);
        return this;
    }

    /**
     * Creates a ResumeBuilder with a newly added Skill.
     * @param skill the Skill to be added.
     * @return ResumeBuilder with a newly added Skill.
     */
    public ResumeBuilder withSkill(Skill skill) {
        skills.add(skill);
        return this;
    }

    /**
     * Builds the Resume based on data that has been added to the ResumeBuilder.
     * @return the Resume that has been built.
     */
    public Resume build() {
        Resume resume = new Resume(name, tags);
        List<Integer> internshipIds = internships
                .stream()
                .map(Internship::getId)
                .collect(Collectors.toList());

        List<Integer> projectIds = projects
                .stream()
                .map(Project::getId)
                .collect(Collectors.toList());

        List<Integer> skillIds = skills
                .stream()
                .map(Skill::getId)
                .collect(Collectors.toList());

        resume.setInternshipIds(internshipIds);
        resume.setProjectIds(projectIds);
        resume.setSkillIds(skillIds);
        return resume;
    }
}
