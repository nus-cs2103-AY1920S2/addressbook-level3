package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.TagPullCommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.tag.Tag;

/**
 * Modifies the content of the resume by adding items with the specified tags.
 */
public class TagPullCommand extends Command {
    public static final String COMMAND_WORD = "tagpull";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pulls all the items with the desired tag into the "
            + "resume in the application with the specified index. "
            + "Existing values will be added on top the new items to be added.\n"
            + "Format: " + COMMAND_WORD
            + " RESUME_INDEX "
            + "[#/ TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + " tech "
            + PREFIX_TAG + " frontend";

    public static final String MESSAGE_SUCCESS = "Items pulled:\n%1$d internship(s), %2$d project(s), %3$d skill(s).";

    protected final Index index;
    protected final Set<Tag> tagList;

    public TagPullCommand(Index index, Set<Tag> tagList) {
        this.index = index;
        this.tagList = tagList;
    }

    /**
     * Modifies the content of the resume by adding items with the specified tags.
     *
     * @param model {@code Model} which the command should operate on.
     * @return      {@code CommandResult} that describes changes made when command execute runs successfully.
     * @throws      CommandException if the index specified is greater than the number of resumes.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= model.getResumeSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Resume toEdit = model.getResumeByIndex(index);

        // The item IDs of specified resume
        List<Integer> currInternshipIds = toEdit.getInternshipIds();
        List<Integer> currProjectIds = toEdit.getProjectIds();
        List<Integer> currSkillIds = toEdit.getSkillIds();

        int internshipCountBefore = currInternshipIds.size();
        int projectCountBefore = currProjectIds.size();
        int skillCountBefore = currSkillIds.size();

        // The item IDs with the desired tags
        List<Integer> pulledInternshipIds = pullInternshipIdsByTags(tagList, model);
        List<Integer> pulledProjectIds = pullProjectIdsByTags(tagList, model);
        List<Integer> pulledSkillIds = pullSkillIdsByTags(tagList, model);

        // Concatenate both sets of items as we are adding on top, distinctness is preserved
        List<Integer> newInternshipIds = concatList(currInternshipIds, pulledInternshipIds);
        List<Integer> newProjectIds = concatList(currProjectIds, pulledProjectIds);
        List<Integer> newSkillIds = concatList(currSkillIds, pulledSkillIds);

        int internshipCountAfter = newInternshipIds.size();
        int projectCountAfter = newProjectIds.size();
        int skillCountAfter = newSkillIds.size();

        Resume editedResume = new Resume(toEdit.getName(), toEdit.getId(), toEdit.getTags());
        model.editResume(editedResume, newInternshipIds, newProjectIds, newSkillIds);
        model.setResume(toEdit, editedResume);
        model.setResumeToDisplay();
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitResumeBook();

        int internshipCountChange = internshipCountAfter - internshipCountBefore;
        int projectCountChange = projectCountAfter - projectCountBefore;
        int skillCountChange = skillCountAfter - skillCountBefore;
        String feedbackToUser = String.format(MESSAGE_SUCCESS, internshipCountChange, projectCountChange,
                skillCountChange);

        return new TagPullCommandResult(editedResume.toString(), feedbackToUser,
                model.getDisplayType());
    }

    /**
     * Pulls the Ids of Internship items which matches the Tags in {@code tagList}.
     */
    private List<Integer> pullInternshipIdsByTags(Set<Tag> tagList, Model model) {
        assert tagList != null;
        assert model != null;

        return tagList.stream()
                .map(model::getInternshipsByTag)
                .flatMap(Collection::stream)
                .map(Internship::getId)
                .collect(Collectors.toList());
    }

    /**
     * Pulls the Ids of Project items which matches the Tags in {@code tagList}.
     */
    private List<Integer> pullProjectIdsByTags(Set<Tag> tagList, Model model) {
        assert tagList != null;
        assert model != null;

        return tagList.stream()
                .map(model::getProjectsByTag)
                .flatMap(Collection::stream)
                .map(Project::getId)
                .collect(Collectors.toList());
    }

    /**
     * Pulls the Ids of Skill items which matches the Tags in {@code tagList}.
     */
    private List<Integer> pullSkillIdsByTags(Set<Tag> tagList, Model model) {
        assert tagList != null;
        assert model != null;

        return tagList.stream()
                .map(model::getSkillsByTag)
                .flatMap(Collection::stream)
                .map(Skill::getId)
                .collect(Collectors.toList());
    }

    /**
     * Concatenates two List of Integers, ensuring that they are distinct.
     */
    private List<Integer> concatList(List<Integer> firstList, List<Integer> secondList) {
        assert firstList != null;
        assert secondList != null;

        return Stream.concat(firstList.stream(), secondList.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagPullCommand // instanceof handles nulls
                && index.equals(((TagPullCommand) other).index)
                && tagList.equals(((TagPullCommand) other).tagList));
    }
}
