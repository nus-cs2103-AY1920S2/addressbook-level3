package hirelah.logic.commands;

import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.Remark;
import hirelah.model.hirelah.exceptions.IllegalActionException;
import hirelah.storage.Storage;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static hirelah.logic.commands.OpenReportCommand.MESSAGE_NOT_INTERVIEWED;
import static java.util.Objects.requireNonNull;


public class GenerateReportCommand extends Command {

    public static final String COMMAND_WORD = "report";
    public static final String MESSAGE_SUCCESS = "Report of %s generated.";
    public static final String MESSAGE_FORMAT = COMMAND_WORD + " <interviewee>";
    public static final String MESSAGE_FUNCTION = ": Generates the interview report of a particular interviewee in PDF.\n";
    public static final String MESSAGE_USAGE = MESSAGE_FORMAT
            + MESSAGE_FUNCTION
            + "Example: " + COMMAND_WORD
            + " Jane Doe ";

    private final String identifier;

    public GenerateReportCommand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        IntervieweeList interviewees = model.getIntervieweeList();
        Interviewee interviewee;
        try {
            interviewee = interviewees.getInterviewee(identifier);
            if (!interviewee.isInterviewed()) {
                throw new CommandException(String.format(MESSAGE_NOT_INTERVIEWED, interviewee));
            }
            model.setCurrentInterviewee(interviewee);
            generateReport(interviewee);
        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, interviewee));
    }

    /**
     * Generate interview report in the form of PDF for a particular interviewee.
     *
     * @param interviewee the interviewee.
     */
    private void generateReport(Interviewee interviewee) throws CommandException {
        try {
            PDDocument document = new PDDocument();
            document.save("C:/PdfBox_Examples/my_doc.pdf");
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.beginText();
            pageContentStream.setFont( PDType1Font.TIMES_ROMAN , 12 );
            pageContentStream.newLineAtOffset(100,  700);
            ObservableList<Remark> remarks = interviewee
                    .getTranscript()
                    .orElseThrow(() ->
                            new CommandException("The transcript for this interviewee could not be found"))
                    .getRemarkListView();
            for (Remark currentRemark:remarks) {
                pageContentStream.showText(currentRemark.getMessage());
            }
            pageContentStream.endText();
            pageContentStream.close();
        } catch (IOException e) {
            throw new CommandException("There is an error in generating the report!");
        }
    }
}
