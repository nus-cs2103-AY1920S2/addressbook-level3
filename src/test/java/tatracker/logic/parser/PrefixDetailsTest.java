package tatracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class PrefixDetailsTest {

    private PrefixDetails details = new PrefixDetailsBuilder()
            .withValidator(s -> !s.contains("!"))
            .withExamples("two words")
            .build();

    private PrefixDetails detailsWithMultipleExamples = new PrefixDetailsBuilder(details)
            .withExamples("", "oneWord", "two words")
            .build();

    private PrefixDetails detailsWithNoExamples = new PrefixDetailsBuilder().build();

    private PrefixDetails emptyDetails = new PrefixDetails(new Prefix(""));

    @Test
    public void getPrefixWithInfo() {
        // Check that the prefix is before the prefix info
        assertEquals(details.getPrefixWithInfo(), "a/ARGUMENT");

        // Check ellipsis added for multiple examples
        assertEquals(detailsWithMultipleExamples.getPrefixWithInfo(), "a/ARGUMENT...");
    }

    @Test
    public void getPrefixWithOptionalInfo() {
        // Check that the prefix is before the prefix info, and that it is surrounded in square brackets
        assertEquals(details.getPrefixWithOptionalInfo(), "[a/ARGUMENT]");

        // Check ellipsis added for multiple examples
        assertEquals(detailsWithMultipleExamples.getPrefixWithOptionalInfo(), "[a/ARGUMENT]...");
    }

    @Test
    public void getPrefixWithExamples() {
        // Check that the prefix is before the example
        assertEquals(details.getPrefixWithExamples(), "a/two words");

        // Check that the prefix is before each example when there are multiple examples
        assertEquals(detailsWithMultipleExamples.getPrefixWithExamples(), "a/ a/oneWord a/two words");
    }

    @Test
    public void equals() {
        PrefixDetails copiedDetails = new PrefixDetailsBuilder(details).build();

        // Same object -> true
        assertEquals(details, details);

        // Same values -> false
        assertNotEquals(details, copiedDetails);

        // null -> false
        assertNotEquals(details, null);

        // Different type -> false
        assertNotEquals(details, 1);

        // Different object -> false
        assertNotEquals(details, emptyDetails);

        // Different prefix -> false
        PrefixDetails editedDetails = new PrefixDetailsBuilder(detailsWithNoExamples)
                .withPrefix(new Prefix("u/")).build();
        assertNotEquals(detailsWithNoExamples, editedDetails);

        // Different constraints -> false
        editedDetails = new PrefixDetailsBuilder(detailsWithNoExamples)
                .withConstraint("No constraints").build();
        assertNotEquals(detailsWithNoExamples, editedDetails);

        // Different validator -> false
        editedDetails = new PrefixDetailsBuilder(detailsWithNoExamples)
                .withValidator(s -> !s.contains("?")).build();
        assertNotEquals(detailsWithNoExamples, editedDetails);

        // Different examples -> false
        editedDetails = new PrefixDetailsBuilder(detailsWithNoExamples)
                .withExamples("", "different", "example strings").build();
        assertNotEquals(detailsWithNoExamples, editedDetails);
    }

    public static class PrefixDetailsBuilder {
        public static final Prefix DEFAULT_PREFIX = new Prefix("a/", "ARGUMENT");
        public static final String DEFAULT_CONSTRAINT = "Arguments can be anything";
        public static final Function<String, Boolean> DEFAULT_VALIDATOR = value -> true;

        private Prefix prefix;
        private String constraint;
        private Function<String, Boolean> validator;
        private List<String> examples;

        public PrefixDetailsBuilder() {
            prefix = DEFAULT_PREFIX;
            constraint = DEFAULT_CONSTRAINT;
            validator = DEFAULT_VALIDATOR;
            examples = new ArrayList<>();
        }

        public PrefixDetailsBuilder(PrefixDetails toCopy) {
            prefix = toCopy.getPrefix();
            constraint = toCopy.getConstraint();
            validator = toCopy.getValidator();
            examples = new ArrayList<>(toCopy.getExamples());
        }

        /**
         * Sets the {@code prefix} of the {@code PrefixDetails} that we are building.
         */
        public PrefixDetailsBuilder withPrefix(Prefix prefix) {
            this.prefix = prefix;
            return this;
        }

        /**
         * Sets the {@code constraint} of the {@code PrefixDetails} that we are building.
         */
        public PrefixDetailsBuilder withConstraint(String constraint) {
            this.constraint = constraint;
            return this;
        }

        /**
         * Sets the {@code validator} of the {@code PrefixDetails} that we are building.
         */
        public PrefixDetailsBuilder withValidator(Function<String, Boolean> validator) {
            this.validator = validator;
            return this;
        }

        /**
         * Sets the {@code examples} of the {@code PrefixDetails} that we are building.
         */
        public PrefixDetailsBuilder withExamples(String ... examples) {
            this.examples = List.of(examples);
            return this;
        }

        /**
         * Builds a {@code CommandDetails} for finer control of individual parameters in test cases.
         */
        public PrefixDetails build() {
            return new PrefixDetails(prefix, constraint, validator, examples.toArray(String[]::new));
        }
    }
}
