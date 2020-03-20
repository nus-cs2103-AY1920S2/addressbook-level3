package fithelper.model.diary;

import java.util.Objects;

import static fithelper.commons.util.AppUtil.checkArgument;

/**
 * Represents a Diary's content.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {
    public static final String MESSAGE_CONSTRAINTS =
            "Content should be no more than 300 characters";
    public String value;

    /**
     * Creates a {@code Content}.
     *
     * @param value a valid content.
     */
    public Content(String value) {
        checkArgument(isValidContent(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Creates a {@code Content}.
     */
    public Content() {
        this.value = "";
    }

    public boolean isContentEmpty() {
        if (this.value == null || this.value.equals("")) {
            return true;
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        checkArgument(isValidContent(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public void add(String appendingStr) {
        StringBuilder sb = new StringBuilder(this.value);
        checkArgument(isValidContent(sb.append(appendingStr).toString()),
                MESSAGE_CONSTRAINTS);
        if (!isContentEmpty()) {
            sb.append(" ");
        }
        this.value = sb.append(appendingStr).toString();
    }

    public static boolean isValidContent(String value) {
        return value.length() <= 300;
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
        Content content  = (Content) o;
        return this.value.equals(content.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

