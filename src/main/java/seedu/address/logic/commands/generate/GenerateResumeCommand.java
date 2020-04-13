package seedu.address.logic.commands.generate;

import static java.lang.System.err;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.GenerateResumeCommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Resume;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Name;

/**
 * Generates .pdf file from a resume.
 */
public class GenerateResumeCommand extends Command {

    public static final String COMMAND_WORD = "rgen";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a .pdf file from a resume in the ResuMe application with the specified index.\n"
            + "If no name is provided for the output .pdf file, the default name is the same as the resume name.\n"
            + "Format: rgen RESUME_INDEX [" + PREFIX_NAME + " FILE_NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_NAME + " MyResume";
    public static final String MESSAGE_GENERATE_SUCCESS = "Generated %s from %s";
    private static final String rootPath = "";

    private String fileName;
    private final Index targetIndex;
    private final PdfBuilder builder = new PdfBuilder();

    public GenerateResumeCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        fileName = null;
    }

    public GenerateResumeCommand(Index targetIndex, Name resumeName) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.fileName = resumeName.toString();
    }

    /**
     * Generates .pdf file from the resume.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes changes made when command execute runs successfully.
     * @throws CommandException if the index specified is greater than the number of resumes.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Get resume item
        requireNonNull(model);
        if (targetIndex.getZeroBased() >= model.getResumeSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }
        Person user = model.getUser();
        Resume resumeToGenerate = model.getResumeByIndex(targetIndex);

        // Set file name
        if (this.fileName == null) {
            fileName = resumeToGenerate.getName().toString();
        }

        // Get lists of items
        List<Internship> internshipsToAdd = resumeToGenerate.getInternshipIds().stream()
                .map(x -> model.hasInternshipId(x) ? model.getInternshipById(x) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());;

        List<Project> projectsToAdd = resumeToGenerate.getProjectIds().stream()
                .map(x -> model.hasProjectId(x) ? model.getProjectById(x) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Skill> skillsToAdd = resumeToGenerate.getSkillIds().stream()
                .map(x -> model.hasSkillId(x) ? model.getSkillById(x) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        try {
            builder.addPage();
            builder.addPersonalDetails(user);

            if (!internshipsToAdd.isEmpty()) {
                builder.addInternships(internshipsToAdd);
            }

            if (!projectsToAdd.isEmpty()) {
                builder.addProjects(projectsToAdd);
            }

            if (!skillsToAdd.isEmpty()) {
                builder.addSkills(skillsToAdd);
            }

            PDDocument resume = builder.build();
            resume.save(rootPath + fileName + ".pdf");
            openPopUp();

        } catch (IOException e) {
            err.println("Error while trying to create .pdf file - " + e);
        }

        return new GenerateResumeCommandResult(resumeToGenerate.toString(),
                String.format(MESSAGE_GENERATE_SUCCESS, fileName, resumeToGenerate.getName().toString()),
                model.getDisplayType());
    }

    /**
     * Opens the generated .pdf file in a pop-up window.
     * @throws IOException
     */
    private void openPopUp() throws IOException {
        if (Desktop.isDesktopSupported()) {
            File file = new File(rootPath + fileName + ".pdf");
            Desktop.getDesktop().open(file);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateResumeCommand // instanceof handles nulls
                && targetIndex.equals(((GenerateResumeCommand) other).targetIndex));
    }
}
