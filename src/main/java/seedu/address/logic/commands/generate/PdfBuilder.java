package seedu.address.logic.commands.generate;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import seedu.address.model.item.Internship;
import seedu.address.model.item.Person;
import seedu.address.model.item.Project;
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.Level;

/**
 * Builder class to format resume file.
 */
public class PdfBuilder {

    private static final Color COLOR_ACCENT = new Color(153, 0, 51);
    private static final Color COLOR_MAIN = new Color(0, 0, 0);

    private static final float MARGIN_X = 64;
    private static final float MARGIN_Y = 100;
    private static final float START_X = PDRectangle.A4.getLowerLeftX();
    private static final float START_Y = PDRectangle.A4.getLowerLeftY();
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static final float LINE_LIMIT = PAGE_WIDTH - 2 * MARGIN_X;
    private static final float SPACING = 20;

    private static final int FONT_SIZE_BODY = 11;
    private static final int FONT_SIZE_HEADING = 14;
    private static final int FONT_SIZE_TITLE = 20;

    private static final PDFont FONT_BOLD = PDType1Font.HELVETICA_BOLD;
    private static final PDFont FONT_REGULAR = PDType1Font.HELVETICA;
    private static final PDFont FONT_OBLIQUE = PDType1Font.HELVETICA_OBLIQUE;

    private final PDDocument resume = new PDDocument();

    private float curX;
    private float curY;
    private int curSize;
    private PDFont curFont;
    private PDPageContentStream contentStream;

    //=========== Public methods =============================================================================

    /**
     * Adds a new page to the .pdf file.
     *
     * @throws IOException
     */
    public void addPage() throws IOException {
        PDPage blank = new PDPage();
        resume.addPage(blank);
        contentStream = new PDPageContentStream(resume, blank);
        setFont(FONT_REGULAR, FONT_SIZE_BODY);
        setColor(COLOR_MAIN);
        contentStream.setLeading(SPACING);
        contentStream.beginText();
        curX = START_X;
        curY = START_Y;
        resetX();
        resetY();
    }

    /**
     * Adds the user's name, bio, contact information and education to the .pdf file.
     * @param user the user of the application.
     * @throws IOException
     */
    public void addPersonalDetails(Person user) throws IOException {
        addResumeTitle(user);
        addBio(user);
        addContact(user);
        addEducation(user);
    }

    /**
     * Adds the user's internship experiences to the .pdf file.
     * @param internshipsToAdd the list of {@code Internship} ids to be added to the .pdf file.
     * @throws IOException
     */
    public void addInternships(List<Internship> internshipsToAdd) throws IOException {
        addSectionTitle("INTERNSHIPS");
        for (Internship internship: internshipsToAdd) {
            addInternship(internship);
        }
    }

    /**
     * Adds the user's projects to the .pdf file.
     * @param projectsToAdd the list of {@code Project} ids to be added to the .pdf file.
     * @throws IOException
     */
    public void addProjects(List<Project> projectsToAdd) throws IOException {
        addSectionTitle("PROJECTS");
        for (Project project: projectsToAdd) {
            addProject(project);
        }
    }

    /**
     * Adds the user's skills to the .pdf file.
     * @param skillsToAdd the list of {@code Skill} ids to be added to the .pdf file.
     * @throws IOException
     */
    public void addSkills(List<Skill> skillsToAdd) throws IOException {
        addSectionTitle("SKILLS");

        List<Skill> basic = new ArrayList<>();
        List<Skill> intermediate = new ArrayList<>();
        List<Skill> advanced = new ArrayList<>();

        for (Skill skill: skillsToAdd) {
            Level level = skill.getLevel();
            switch (level) {
            case BASIC:
                basic.add(skill);
                break;
            case INTERMEDIATE:
                intermediate.add(skill);
                break;
            case ADVANCED:
                advanced.add(skill);
                break;
            default:
                assert false : "Able to reach unreachable statement";
            }
        }

        if (!advanced.isEmpty()) {
            showLeveledSkills("Advanced: ", advanced);
        }
        if (!intermediate.isEmpty()) {
            showLeveledSkills("Intermediate: ", intermediate);
        }
        if (!basic.isEmpty()) {
            showLeveledSkills("Basic: ", basic);
        }
    }

    /**
     * Builds the completed resume file.
     *
     * @return the formatted {@code PDDocument} to be saved.
     * @throws IOException
     */
    public PDDocument build() throws IOException {
        contentStream.endText();
        contentStream.close();
        return this.resume;
    }

    //=========== Page set-up ================================================================================

    /**
     * Adds and moves to a new page if end-of-page is reached.
     */
    private void nextPageIfEndOfPage() throws IOException {
        if (curY + SPACING <= MARGIN_Y) {
            contentStream.endText();
            contentStream.close();
            addPage();
        }
    }

    /**
     * Changes the current color to the specified color.
     *
     * @param color the {@code Color} to be set.
     * @throws IOException
     */
    private void setColor(Color color) throws IOException {
        contentStream.setNonStrokingColor(color);
    }

    /**
     * Changes the current font to the specified font.
     *
     * @param font the {@code PDType1Font} to be set.
     * @param size the {@code int} font size to be set.
     * @throws IOException
     */
    private void setFont(PDFont font, int size) throws IOException {
        contentStream.setFont(font, size);
        curFont = font;
        curSize = size;
    }

    //=========== Page navigation ============================================================================

    /**
     * Moves the current pointer to the next line.
     *
     * @throws IOException
     */
    private void nextLine() throws IOException {
        contentStream.newLine();
        curY -= SPACING;
    }

    /**
     * Resets the x-coordinate of the current pointer to the left of the page.
     *
     * @throws IOException
     */
    private void resetX() throws IOException {
        float xOffSet = -curX + MARGIN_X;
        contentStream.newLineAtOffset(xOffSet, 0);
        curX += xOffSet;
    }

    /**
     * Resets the y-coordinate of the current pointer to the bottom of the page.
     *
     * @throws IOException
     */
    private void resetY() throws IOException {
        float yOffSet = -curY + PAGE_HEIGHT - MARGIN_Y - SPACING;
        contentStream.newLineAtOffset(0, yOffSet);
        curY += yOffSet;
    }

    //=========== Text formatting ============================================================================

    /**
     * Returns the width of a text in the current font and font size.
     *
     * @param content {@code String} content to be formatted.
     * @return the width of the {@code String} content.
     * @throws IOException
     */
    private float getStringWidth(String content) throws IOException {
        return curFont.getStringWidth(content) * curSize / 1000f;
    }

    /**
     * Center-aligns and shows content in the .pdf file.
     *
     * @param content {@code String} content to be center-aligned.
     * @throws IOException
     */
    private void showTextCenter(String content) throws IOException {
        float stringWidth = getStringWidth(content);
        float xOffSet = -curX + (PAGE_WIDTH - stringWidth) / 2f;
        contentStream.newLineAtOffset(xOffSet, 0);
        curX += xOffSet;
        contentStream.showText(content);
        resetX();
        nextLine();
    }

    /**
     * Pads the item title with whitespaces such that the item name is left-aligned while its time is right-aligned.
     * @param name {@code String} title of the item.
     * @param time {@code String} time of the item.
     * @return the padded {@code String} title line.
     * @throws IOException
     */
    private String padTimeRightAlign(String name, String time) throws IOException {
        String line = name;
        String pad = " ";
        float lineWidth = getStringWidth(name + time);
        float padWidth = getStringWidth(pad);
        while (true) {
            if (lineWidth + padWidth > LINE_LIMIT) {
                break;
            }
            line += pad;
            lineWidth += padWidth;
        }
        return line + time;
    }

    /**
     * Shows the next portion of that fit within a single line. Lines following the first line will be
     * automatically indented.
     *
     * @param words the array of {@code String} words in the given text.
     * @param position the starting position for the line in the given text.
     * @param isFirstLine {@code boolean} value of whether the line is the first line.
     * @return the starting position for the next line to be read.
     * @throws IOException
     */
    private int showNextLine(String[] words, int position, boolean isFirstLine) throws IOException {
        String line = "";
        float lineWidth = 0;
        if (!isFirstLine) {
            lineWidth += getStringWidth(line += "  ");
        }

        while (position < words.length) {
            String add = words[position] + " ";
            float addWidth = getStringWidth(add);
            if (lineWidth + addWidth > LINE_LIMIT) {
                break;
            }
            line += add;
            lineWidth += addWidth;
            position += 1;
        }
        contentStream.showText(line);
        nextLine();
        return position;
    }

    /**
     * Formats and shows content that spans over multiple lines in the .pdf file.
     *
     * @param content {@code String} content to be shown.
     * @throws IOException
     */
    private void showTextMultiLine(String content) throws IOException {
        nextPageIfEndOfPage();
        String[] words = content.split(" ");
        int position = 0;
        position = showNextLine(words, position, true);
        while (position < words.length) {
            position = showNextLine(words, position, false);
        }
    }

    //=========== Content formatting ==========================================================================

    /**
     * Adds title to the resume.
     *
     * @param user the user of the application.
     * @throws IOException
     */
    private void addResumeTitle(Person user) throws IOException {
        setFont(FONT_BOLD, FONT_SIZE_TITLE);
        setColor(COLOR_ACCENT);
        String name = user.getName().toString().toUpperCase();
        showTextCenter(name);
    }

    /**
     * Adds user's bio to the resume file.
     *
     * @param user user of the application.
     * @throws IOException
     */
    private void addBio(Person user) throws IOException {
        setFont(FONT_OBLIQUE, FONT_SIZE_HEADING);
        setColor(COLOR_ACCENT);
        String bio = user.getDescription().toString();
        showTextCenter(bio);
    }

    /**
     * Adds user's contact to the resume file.
     *
     * @param user user of the application.
     * @throws IOException
     */
    private void addContact(Person user) throws IOException {
        setFont(FONT_REGULAR, FONT_SIZE_BODY);
        setColor(COLOR_MAIN);
        String phone = user.getPhone().toString();
        String email = user.getEmail().toString();
        String git = user.getGithub().toString();
        String contact = phone + "  |  " + email + "  |  " + git;
        showTextCenter(contact);
        nextLine();
    }

    /**
     * Adds user's educational details to the resume file.
     *
     * @param user user of the application.
     * @throws IOException
     */
    private void addEducation(Person user) throws IOException {
        addSectionTitle("EDUCATION");

        String name = user.getUniversity().toString();
        String time = user.getFrom().format() + " - " + user.getTo().format();
        addItemTitle(name, time);

        setFont(FONT_REGULAR, FONT_SIZE_BODY);
        setColor(COLOR_MAIN);
        String major = "- " + user.getMajor();
        contentStream.showText(major);
        nextLine();
        String cap = "- Cumulative Average Point: " + user.getCap().toString();
        contentStream.showText(cap);
        nextLine();
        nextLine();
    }

    /**
     * Adds an {@code Internship} to the resume file.
     *
     * @param internship {@code Internship} item to be added.
     * @throws IOException
     */
    private void addInternship(Internship internship) throws IOException {
        String name = internship.getName().toString() + " | " + internship.getRole().toString();
        String time = internship.getFrom().format() + " - " + internship.getTo().format();
        addItemTitle(name, time);

        String description = internship.getDescription().toString();
        addItemDescription(description);
    }

    /**
     * Adds a {@code Project} to the resume file.
     *
     * @param project {@code Project} item to be added.
     * @throws IOException
     */
    private void addProject(Project project) throws IOException {
        String name = project.getName().toString() + " | " + project.getWebsite().toString();
        String time = project.getTime().format();
        addItemTitle(name, time);

        String description = project.getDescription().toString();
        addItemDescription(description);
    }

    /**
     * Formats and shows skills of a specific level.
     *
     * @param level the level title to be shown.
     * @param skills list of skills with the specified level.
     * @throws IOException
     */
    private void showLeveledSkills(String level, List<Skill> skills) throws IOException {
        nextPageIfEndOfPage();
        setColor(COLOR_MAIN);
        setFont(FONT_BOLD, FONT_SIZE_BODY);
        contentStream.showText(level);
        String line = skills.get(0).getName().toString();
        for (int i = 1; i < skills.size(); i++) {
            line += ", " + skills.get(i).getName().toString();
        }
        setFont(FONT_REGULAR, FONT_SIZE_BODY);
        showTextMultiLine(line);
    }

    /**
     * Adds a new section title to the resume file.
     *
     * @param section {@code String} title of the new section.
     * @throws IOException
     */
    private void addSectionTitle(String section) throws IOException {
        nextPageIfEndOfPage();
        setFont(FONT_BOLD, FONT_SIZE_HEADING);
        setColor(COLOR_ACCENT);
        contentStream.showText(section);
        nextLine();
    }

    /**
     * Adds title to a new item in the resume file.
     *
     * @param name {@code String} name of the item to be added.
     * @param time {@code String} time of the item to be added.
     * @throws IOException
     */
    private void addItemTitle(String name, String time) throws IOException {
        nextPageIfEndOfPage();
        setColor(COLOR_MAIN);
        setFont(FONT_BOLD, FONT_SIZE_BODY);
        String title = padTimeRightAlign(name, time);
        contentStream.showText(title);
        nextLine();
    }

    /**
     * Adds description to a new item in the resume file.
     *
     * @param itemDescription {@code String} description of the item to be added.
     * @throws IOException
     */
    private void addItemDescription(String itemDescription) throws IOException {
        setFont(FONT_REGULAR, FONT_SIZE_BODY);
        itemDescription = itemDescription.trim() + " ";
        String[] content = itemDescription.split("\\. ");
        for (String line: content) {
            String point = "- " + line.trim() + ". ";
            showTextMultiLine(point);
        }
        nextLine();
    }
}
