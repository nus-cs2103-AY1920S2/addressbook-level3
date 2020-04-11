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
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            Pair<Integer, PDPage> positionAfterAttributeScorePrinting = printAttributeScoresPart(document, attributeToScoreData);
            printRemarksPart(document, remarks, positionAfterAttributeScorePrinting);
            //currentPage.getPageContentStream().endText();
            //currentPage.getPageContentStream().close();
            document.save("data/Jo.pdf");
            document.close();
        } catch (IOException e) {
            throw new CommandException("There is an error in generating the report!");
        }
    }

    private static Pair<Integer, PDPage> printAttributeScoresPart(PDDocument document, ObservableList<Pair<Attribute, Double>> attributeScores) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<Attribute, Double> attributeScorePair : attributeScores) {
            stringBuilder.append(attributeScorePair.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(attributeScorePair.getValue());
            if (attributeScorePair.equals(attributeScores.get(attributeScores.size()-1))) {
                break;
            }
            stringBuilder.append(", ");
        }
        ArrayList<String> splitAttributeScoresSentence = splitSentence(stringBuilder.toString());
        return new Pair(splitAttributeScoresSentence.size() * 13, generateAttributeScores(document, splitAttributeScoresSentence));
    }

    private static PDPage generateAttributeScores(PDDocument document, ArrayList<String> splitAttributeScoresList) throws IOException {
        while (splitAttributeScoresList.size() > 60) {
            ArrayList<String> firstSixtyAttributeScoresList = new ArrayList<>();
            for (int i = 0; i < 60; i++) {
                firstSixtyAttributeScoresList.add(splitAttributeScoresList.get(i));
                splitAttributeScoresList.remove(i);
            }
            generateAttributeScoresPage(document, firstSixtyAttributeScoresList);
        }
        return generateAttributeScoresPage(document, splitAttributeScoresList);
    }

    private static PDPage generateAttributeScoresPage(PDDocument document, ArrayList<String> sentenceList) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        pageContentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(50, 800);
        for (String currentString : sentenceList) {
            pageContentStream.showText(currentString);
            pageContentStream.newLineAtOffset(0, -13);
        }
        pageContentStream.endText();
        pageContentStream.close();
        return page;
    }


    private static void printRemarksPart(PDDocument document, ObservableList<Remark> remarks, Pair<Integer, PDPage> startingPosition) throws IOException {
        ArrayList<Remark> remarkList = new ArrayList<>();
        remarkList.addAll(remarks);
        int currentYOffset = 800 - startingPosition.getKey();
        boolean firstPage = true;
        ArrayList<TableRowEntry> rowsInAPage = new ArrayList<>();
        for (Remark currentRemark : remarkList) {
            ArrayList<String> splitRemarks = splitSentence(currentRemark.getMessage());
            for (String currentLine : splitRemarks) {
                TableRowEntry currentRow = new TableRowEntry(currentLine,
                        currentLine.equals(splitRemarks.get(0))? currentRemark.getTimeString() : "",
                        currentLine.equals(splitRemarks.get(0))? -20 : -13);
                rowsInAPage.add(currentRow);
                currentYOffset -= 13;
                if (currentYOffset < 20) {
                    if (firstPage) {
                        generatePartialRemarkPage(document, startingPosition.getValue(), rowsInAPage);
                    } else {
                        firstPage = false;
                        generateFullRemarkPage(document, rowsInAPage);
                        rowsInAPage = new ArrayList<>();
                        currentYOffset = 800;
                    }
                }
            }
            currentYOffset -= 20;
            if (currentYOffset < 20) {
                if (firstPage) {
                    generatePartialRemarkPage(document, startingPosition.getValue(), rowsInAPage);
                } else {
                    firstPage = false;
                    generateFullRemarkPage(document, rowsInAPage);
                    rowsInAPage = new ArrayList<>();
                    currentYOffset = 800;
                }
            }
        }
        generateFullRemarkPage(document, rowsInAPage);
    }

    private static void generatePartialRemarkPage(PDDocument document, PDPage currentPage, ArrayList<TableRowEntry> rowList) throws IOException {
        PDPageContentStream pageContentStream = new PDPageContentStream(document, currentPage);
        pageContentStream.setFont( PDType1Font.HELVETICA , 12 );
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(0, 800);
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

    private static void generateFullRemarkPage(PDDocument document, ArrayList<TableRowEntry> rowList) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        pageContentStream.setFont( PDType1Font.HELVETICA , 12 );
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(0, 800);
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
         * Generate new pag
        private static PDPage printReport(PDDocument document, ArrayList<Remark> remarkList) throws IOException {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.setFont( PDType1Font.HELVETICA , 12 );
            pageContentStream.beginText();
            pageContentStream.newLineAtOffset(100, 800);
            int currentYOffset = 0;
            while (!remarkList.isEmpty()) {
                Remark currentRemark = remarkList.get(0);
                ArrayList<String> splitRemark = splitRemark(currentRemark.getMessage());
                if ((currentYOffset + (splitRemark.size() * 10) < 780)) {
                    System.out.println("UNTIL WHEN");
                    printTime(currentRemark.getTimeString(), pageContentStream);
                    printRemark(splitRemark, splitRemark.size(), pageContentStream);
                    remarkList.remove(0);
                    currentYOffset += (20 + splitRemark.size() * 10);
                }
                else {
                    int numberOfLinesLeft = (780 - currentYOffset) / 10;

                    printRemark(splitRemark, numberOfLinesLeft, pageContentStream);
                    remarkList.remove(0);
                    List<String> remainingRemarkList = splitRemark.subList(numberOfLinesLeft, splitRemark.size());
                    ArrayList<String> remainingRemarkArrayList = new ArrayList<>();
                    remainingRemarkArrayList.addAll(remainingRemarkList);
                    currentYOffset = 0;
                    PDPage currentAdditionalPage = new PDPage();
                    int remainingLines = 0;
                    while (remainingRemarkArrayList.size() > 0) {
                        remainingLines = remainingRemarkArrayList.size();
                        currentAdditionalPage = clearCurrentRemainingRemark(document, remainingRemarkArrayList);
                    }
                    pageContentStream = new PDPageContentStream(document, currentAdditionalPage);
                    currentYOffset += (10 * remainingLines);
                    pageContentStream.beginText();
                    pageContentStream.setFont(PDType1Font.HELVETICA, 12);
                    pageContentStream.newLineAtOffset(100, (800 - currentYOffset));
                }
            }
            pageContentStream.endText();
            pageContentStream.close();
            return page;
        }


        private static PDPage clearCurrentRemainingRemark(PDDocument document, ArrayList<String> remarkList) throws IOException {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.setFont( PDType1Font.HELVETICA , 12 );
            pageContentStream.beginText();
            pageContentStream.newLineAtOffset(50, 800);
            int currentYOffset = 0;
            int numberOfLinesLeft = (780 - currentYOffset)/10;
            if (numberOfLinesLeft > remarkList.size()) {
                printRemark(remarkList, remarkList.size(), pageContentStream);
                remarkList.clear();
            } else {
                printRemark(remarkList, numberOfLinesLeft, pageContentStream);
                List<String> remainingRemarkList = remarkList.subList(numberOfLinesLeft, remarkList.size());
                remarkList.clear();
                remarkList.addAll(remainingRemarkList);
            }
            pageContentStream.endText();
            pageContentStream.close();
            return page;
        }*/

    /**
     * Prints the remark in a wrap text manner and starts at an indentation level.
     *
     * @param time the time to be printed.
     */
    private static void printTime(String time, PDPageContentStream pageContentStream) throws IOException {
        pageContentStream.newLineAtOffset(-50, 0);
        pageContentStream.showText(time);
    }

    /**
     * Prints the remark in a wrap text manner and starts at an indentation level.
     *
     * @param remarks the list of remarks to be printed.
     */
    private static void printRemark(ArrayList<String> remarks, int size, PDPageContentStream pageContentStream) throws IOException {
        pageContentStream.newLineAtOffset(50, 0);
        for (int i = 0; i < size; i++) {
            try {
                pageContentStream.showText(remarks.get(i));
                pageContentStream.newLineAtOffset(0, -10);

            } catch (IndexOutOfBoundsException e) {
                System.out.println("THIS IS SUPER BEIR");
                System.out.println(size);
                for (String s : remarks) {
                    System.out.println(s);
                }
            }
        }
    }

    /**
     * Splits the sentence to a maximum of 80 characters per line.
     *
     * @param sentence the sentence to be splitted.
     */
    private static ArrayList<String> splitSentence(String sentence) {
        int numberOfLines = (sentence.length()/80) + 1;
        ArrayList<String> splitRemarks = new ArrayList<>();
        String[] words = sentence.split(" ");

        int i = 0;
        while (splitRemarks.size() < numberOfLines) {
            StringBuilder currentBuilder = new StringBuilder();
            while (i < words.length) {
                String currentWord = words[i];
                if (currentWord.length() > 80) {
                    while (currentWord.length() > 80) {
                        int remainingCapacity = Math.min(79, 79 - currentBuilder.length());
                        currentBuilder.append(currentWord.substring(0, remainingCapacity).toString() + "-");
                        splitRemarks.add(currentBuilder.toString());
                        currentWord = currentWord.substring(remainingCapacity);
                        currentBuilder = new StringBuilder();
                    }
                    currentBuilder = new StringBuilder();
                    currentBuilder.append(currentWord + " ");
                    currentWord = words[++i];
                }
                if (currentBuilder.length() + currentWord.length() > 80) {
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
