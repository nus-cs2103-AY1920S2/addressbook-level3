package seedu.address.model.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.collections.ObservableList;

import seedu.address.model.ingredient.Ingredient;

/**
 * Exports the given arguments to a pdf file.
 */
public class PdfExporter {

    private static final PDFont PDF_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 16;
    private static final String MULTIPLE_LINE_PREFIX = "O    ";
    private static final String PDF_HEADER_TEXT = "My Shopping List";
    private static final String MULTIPLE_LINE_SUB_LINES_PREFIX = "       ";
    private static final int START_INDEX = 0;
    private static final int LINE_CHARACTER_LIMIT = 50;
    private static final int MULTIPLE_LINE_CHARACTER_LIMIT = 43;

    /**
     * Exports the given list of {@code ingredients} from the cart to a pdf file.
     *
     * @param ingredients The list of ingredients in the cart.
     * @throws IOException if cart.pdf is open and therefore unmodifiable.
     */
    public static void exportCart(ObservableList<Ingredient> ingredients) throws IOException {

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            float leading = 1.5f * FONT_SIZE;

            PDRectangle mediabox = page.getMediaBox();
            float margin = 72;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;
            contentStream.beginText();
            contentStream.setFont(PDF_FONT, FONT_SIZE);
            contentStream.newLineAtOffset(startX, startY);

            List<String> lines = getTextFromCart(ingredients);
            int index = 0;

            while (index < lines.size()) {
                if (index % 28 == 0 && index != 0) {
                    contentStream.endText();
                    contentStream.close();
                    page = new PDPage();
                    doc.addPage(page);
                    contentStream = new PDPageContentStream(doc, page);
                    contentStream.beginText();
                    contentStream.setFont(PDF_FONT, FONT_SIZE);
                    contentStream.newLineAtOffset(startX, startY);
                }

                contentStream.showText(lines.get(index));
                contentStream.newLineAtOffset(0, -leading);
                index++;
            }

            contentStream.endText();
            contentStream.close();

            doc.save(new File("cart.pdf"));
        }
    }

    private static List<String> getTextFromCart(ObservableList<Ingredient> ingredients) {
        List<String> lines = new ArrayList<>();
        lines.add(PDF_HEADER_TEXT);
        lines.add(" ");

        for (Ingredient ingredient : ingredients) {
            String text = MULTIPLE_LINE_PREFIX + ingredient.toString();
            boolean isFirstLine = true;

            while (text.length() > 0) {
                if (text.length() < LINE_CHARACTER_LIMIT) {
                    if (isFirstLine) {
                        lines.add(text);
                    } else {
                        lines.add(MULTIPLE_LINE_SUB_LINES_PREFIX + text);
                    }

                    break;
                } else {
                    if (isFirstLine) {
                        lines.add(text.substring(START_INDEX, LINE_CHARACTER_LIMIT));
                        text = text.substring(LINE_CHARACTER_LIMIT);
                    } else {
                        lines.add("       " + text.substring(START_INDEX, MULTIPLE_LINE_CHARACTER_LIMIT));
                        text = text.substring(MULTIPLE_LINE_CHARACTER_LIMIT);
                    }

                    isFirstLine = false;
                }
            }
        }

        return lines;
    }
}
