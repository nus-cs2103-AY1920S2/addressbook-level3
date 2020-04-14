package fithelper.model.diary;

import static fithelper.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Diary's content.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {

    public static final String MESSAGE_CONSTRAINTS = "Content should be no more than 200 characters";
    public final String value;
    private String content;

    /**
     * Creates a {@code Content}.
     *
     * @param content a valid content.
     */
    public Content(String content) {
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.value = content;
    }

    /**
     * Creates a {@code Content}.
     */
    public Content() {
        this.value = "";
    }

    /**
     * Checks whether the content is empty.
     * @return a boolean value
     */
    public boolean isContentEmpty() {
        return this.value == null || this.value.equals("");
    }

    public String getValue() {
        return value;
    }

   /* *//**
     * Sets the value of the content.
     * @param value the content to be set
     *//*
    public void setValue(String value) {
        checkArgument(isValidContent(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }*/

    /**
     * Appends a new content to the previous one.
     * @param appendedContent
     */
    public Content appendContent(Content appendedContent) {
        StringBuilder sb = new StringBuilder(this.value);
        checkArgument(isValidContent(new StringBuilder(this.value).append(appendedContent.value).toString()),
                MESSAGE_CONSTRAINTS);
        if (!isContentEmpty()) {
            sb.append(" ");
        }
        return new Content(sb.append(appendedContent.value).toString());
    }

    public static boolean isValidContent(String value) {
        return value.length() <= 200;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        return this.value.equalsIgnoreCase(content.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

