package hirelah.logic.commands;

import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.Remark;
import hirelah.model.hirelah.exceptions.IllegalActionException;
import hirelah.storage.Storage;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;

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
    private static final String ATTRIBUTE_SCORE_TITLE = "Attribute Score";
    private static final String REMARKS_TITLE = "Remarks";

    private final String identifier;

    static class TableRowEntry {
        private final String message;
        private final String duration;
        private final int valueYOffset;

        TableRowEntry(String message, String duration, int valueYOffset) {
            this.message = message;
            this.duration = duration;
            this.valueYOffset = valueYOffset;
        }

        public String getMessage() {
            return message;
        }

        public String getDuration() {
            return duration;
        }

        public int getValueYOffset() {
            return valueYOffset;
        }

        public boolean hasRealDuration() {
            return !duration.equals("");
        }
    }

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
            ObservableList<Remark> remarks = interviewee
                    .getTranscript()
                    .orElseThrow(() ->
                            new CommandException("The transcript for this interviewee could not be found"))
                    .getRemarkListView();

            ObservableList<Pair<Attribute, Double>> attributeToScoreData = interviewee
                    .getTranscript()
                    .orElseThrow(() ->
                            new CommandException("The transcript for this interviewee could not be found"))
                    .getAttributeToScoreData();


            PDDocument document = new PDDocument();
            Pair<Integer, PDPage> positionAfterNameAndAttributePrinting = printNameAndAttributePart(document, interviewee.getFullName().toUpperCase(), attributeToScoreData);
            Pair<Integer, PDPage> startingPositionOfRemarksPart = positionAfterNameAndAttributePrinting;
            if (positionAfterNameAndAttributePrinting.getKey() > 780) {
                startingPositionOfRemarksPart = new Pair<>(0, new PDPage(PDRectangle.A4));
            }
            printRemarksPart(document, remarks, startingPositionOfRemarksPart);
            document.save("data/Jo.pdf");
            document.close();
        } catch (IOException e) {
            throw new CommandException("There is an error in generating the report!");
        }
    }

    private static Pair<Integer, PDPage> printNameAndAttributePart(PDDocument document, String fullName, ObservableList<Pair<Attribute, Double>> attributeScores) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<Attribute, Double> attributeScorePair : attributeScores) {
            stringBuilder.append(attributeScorePair.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(attributeScorePair.getValue());
            if (attributeScorePair.equals(attributeScores.get(attributeScores.size() - 1))) {
                break;
            }
            stringBuilder.append(", ");
        }
        ArrayList<String> splitAttributeScoresSentence = splitSentence(stringBuilder.toString(), 80);
        ArrayList<String> splitName = splitSentence(fullName, 70);
        splitAttributeScoresSentence.add(0, ATTRIBUTE_SCORE_TITLE);
        splitAttributeScoresSentence.addAll(0, splitName);
        return generateNameAndAttributeAllPages(document, splitName.size(), splitAttributeScoresSentence);
    }

    private static Pair<Integer, PDPage> generateNameAndAttributeAllPages(PDDocument document, int nameListSize, ArrayList<String> nameAndAttributeScoreList) throws IOException {
        boolean firstPage = true;
        while (nameAndAttributeScoreList.size() > 57) {
            ArrayList<String> attributeScoreLisOnePage = new ArrayList<>();
            for (int i = 0; i < 57; i++) {
                attributeScoreLisOnePage.add(nameAndAttributeScoreList.get(0));
                nameAndAttributeScoreList.remove(0);
            }
            generateNameAndAttributeOnePage(document, firstPage ? nameListSize : 0, attributeScoreLisOnePage);
            firstPage = false;
        }
        int verticalSpacing = firstPage ? nameAndAttributeScoreList.size() * 13 + 40 : nameAndAttributeScoreList.size() * 13 + 20;
        return new Pair<>(verticalSpacing, generateNameAndAttributeOnePage(document, firstPage ? nameListSize : 0, nameAndAttributeScoreList));
    }

    private static PDPage generateNameAndAttributeOnePage(PDDocument document, int nameListSize, ArrayList<String> sentenceList) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        printName(page, pageContentStream, nameListSize, sentenceList);
        printAttributeTitle(pageContentStream, sentenceList);
        printAttributeScores(pageContentStream, sentenceList);
        return page;
    }

    private static void printName(PDPage page, PDPageContentStream pageContentStream, int nameListSize, ArrayList<String> sentenceList) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD_OBLIQUE;
        int fontSize = 15;
        pageContentStream.setFont(font, fontSize);
        pageContentStream.beginText();
        float startX = 0;
        pageContentStream.newLineAtOffset(0, 800);
        for (int i = 0; i < nameListSize; i++) {
            startX = (page.getMediaBox().getWidth() - (font.getStringWidth(sentenceList.get(0)) / 1000 * fontSize)) / 2;
            pageContentStream.newLineAtOffset(startX, 0);
            pageContentStream.showText(sentenceList.get(0));
            sentenceList.remove(0);
            pageContentStream.newLineAtOffset(-startX, -15);
        }
    }

    private static void printAttributeTitle(PDPageContentStream pageContentStream, ArrayList<String> sentenceList) throws IOException {
        if (sentenceList.get(0).equals(ATTRIBUTE_SCORE_TITLE)) {
            pageContentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
            pageContentStream.newLineAtOffset(50, -15);
            pageContentStream.showText(sentenceList.get(0));
            sentenceList.remove(0);
            pageContentStream.newLineAtOffset(0, -15);
        } else {
            pageContentStream.newLineAtOffset(50, -15);
        }
    }

    private static void printAttributeScores(PDPageContentStream pageContentStream, ArrayList<String> sentenceList) throws IOException {
        pageContentStream.setFont(PDType1Font.HELVETICA, 12);
        if (sentenceList.isEmpty()) {
            pageContentStream.newLineAtOffset(0, -20);
            pageContentStream.showText("-");
        }
        for (String currentString : sentenceList) {
            pageContentStream.showText(currentString);
            pageContentStream.newLineAtOffset(0, -13);
        }
        pageContentStream.endText();
        pageContentStream.close();
    }

    private static void printRemarksPart(PDDocument document, ObservableList<Remark> remarks, Pair<Integer, PDPage> startingPosition) throws IOException {
        ArrayList<Remark> remarkList = new ArrayList<>();
        remarkList.addAll(remarks);
        int currentYOffset = 800 - startingPosition.getKey();
        boolean firstPage = true;
        ArrayList<TableRowEntry> rowsInAPage = new ArrayList<>();
        for (Remark currentRemark : remarkList) {
            ArrayList<String> splitRemarks = splitSentence(currentRemark.getMessage(), 73);
            boolean firstSentence = true;
            for (String currentLine : splitRemarks) {
                TableRowEntry currentRow = new TableRowEntry(currentLine,
                        firstSentence ? currentRemark.getTimeString() : "",
                        firstSentence ? -20 : -13);
                rowsInAPage.add(currentRow);
                currentYOffset -= (firstSentence ? 20 : 13);
                firstSentence = false;
                if (currentYOffset < 50) {
                    if (firstPage) {
                        generatePartialRemarkPage(document, startingPosition, rowsInAPage);
                        firstPage = false;
                    } else {
                        generateFullRemarkPage(document, rowsInAPage);
                    }
                    rowsInAPage = new ArrayList<>();
                    currentYOffset = 800;
                }
            }
            currentYOffset -= 20;
            if (currentYOffset < 50) {
                if (firstPage) {
                    generatePartialRemarkPage(document, startingPosition, rowsInAPage);
                    firstPage = false;
                } else {
                    generateFullRemarkPage(document, rowsInAPage);
                }
                rowsInAPage = new ArrayList<>();
                currentYOffset = 800;
            }
        }
        if (firstPage) {
            generatePartialRemarkPage(document, startingPosition, rowsInAPage);
        } else {
            generateFullRemarkPage(document, rowsInAPage);
        }
    }


    private static void generatePartialRemarkPage(PDDocument document, Pair<Integer, PDPage> startingPosition, ArrayList<TableRowEntry> rowList) throws IOException {
        PDPageContentStream.AppendMode append = PDPageContentStream.AppendMode.APPEND;
        PDPageContentStream pageContentStream = new PDPageContentStream(document, startingPosition.getValue(), append, false);
        pageContentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(50, 800 - startingPosition.getKey());
        pageContentStream.showText(REMARKS_TITLE);
        pageContentStream.newLineAtOffset(-50, 0);
        pageContentStream.setFont( PDType1Font.HELVETICA , 12 );
        if (rowList.isEmpty()) {
            pageContentStream.newLineAtOffset(50, -20);
            pageContentStream.showText("-");
        }
        printRemarkPerLine(pageContentStream, rowList);

    }

    private static void generateFullRemarkPage(PDDocument document, ArrayList<TableRowEntry> rowList) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        pageContentStream.setFont( PDType1Font.HELVETICA , 12 );
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(0, 800);
        printRemarkPerLine(pageContentStream, rowList);
    }

    private static void printRemarkPerLine(PDPageContentStream pageContentStream, ArrayList<TableRowEntry> rowList) throws IOException {
        for (TableRowEntry currentRow : rowList) {
            pageContentStream.newLineAtOffset(50, currentRow.getValueYOffset());
            pageContentStream.showText(currentRow.getDuration());
            pageContentStream.newLineAtOffset(50, 0);
            pageContentStream.showText(currentRow.getMessage());
            pageContentStream.newLineAtOffset(-100, 0);
        }
        pageContentStream.endText();
        pageContentStream.close();
    }


    /**
     * Splits the sentence to a maximum of certain characters per line.
     *
     * @param sentence the sentence to be splitted.
     * @param limit the maximum number of characters per line
     */
    private static ArrayList<String> splitSentence(String sentence, int limit) {
        ArrayList<String> splitRemarks = new ArrayList<>();
        String[] words = sentence.split(" ");

        int i = 0;
        while (true) {
            StringBuilder currentBuilder = new StringBuilder();
            if (i >= words.length) {
                break;
            }
            while (i < words.length) {
                String currentWord = words[i];
                if (currentWord.length() > limit) {
                    while (currentWord.length() > limit) {
                        int remainingCapacity = Math.min(limit-1, limit - 1 - currentBuilder.length());
                        currentBuilder.append(currentWord.substring(0, remainingCapacity).toString() + "-");
                        splitRemarks.add(currentBuilder.toString());
                        currentWord = currentWord.substring(remainingCapacity);
                        currentBuilder = new StringBuilder();
                    }
                    currentBuilder = new StringBuilder();
                    currentBuilder.append(currentWord + " ");
                    currentWord = words[++i];
                }
                if (currentBuilder.length() + currentWord.length() > limit) {
                    break;
                }
                currentBuilder.append(currentWord + " ");
                i++;
            }
            splitRemarks.add(currentBuilder.toString());
        }
        return splitRemarks;

    }
}
