package tatracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.Prefixes;

public class CommandDetailsTest {

    private CommandDetails details = new CommandDetailsBuilder()
            .withParameters(Prefixes.DATE, Prefixes.START_TIME)
            .withOptionals(Prefixes.END_TIME)
            .withExamples(Prefixes.DATE, Prefixes.START_TIME, Prefixes.END_TIME)
            .build();

    private CommandDetails detailsWithNoArgs = new CommandDetailsBuilder().build();

    private CommandDetails emptyDetails = new CommandDetails(
            "",
            "",
            "",
            List.of(),
            List.of()
    );

    @Test
    public void constructor_validFields_success() {
        // Filled out details
        assertFalse(details.getCommandWord().contains(" "));
        assertFalse(details.getSubWord().contains(" "));
        assertFalse(details.getParameters().isEmpty());
        assertFalse(details.getOptionals().isEmpty());
        assertFalse(details.getPrefixesForExample().isEmpty());
        assertFalse(details.getUsage().isEmpty());
        assertFalse(details.getExample().isEmpty());

        // Empty details
        assertTrue(emptyDetails.getParameters().isEmpty());
        assertTrue(emptyDetails.getOptionals().isEmpty());
        assertTrue(emptyDetails.getPrefixesForExample().isEmpty());
        assertTrue(emptyDetails.getUsage().isEmpty());
        assertTrue(emptyDetails.getExample().isEmpty());
    }

    @Test
    public void constructor_invalidFields_throwsIllegalArgumentException() {
        // Invalid command word
        assertThrows(IllegalArgumentException.class, () ->
                new CommandDetails("one space", "ok", "blank info", List.of(), List.of()));

        // Invalid sub word
        assertThrows(IllegalArgumentException.class, () ->
                new CommandDetails("ok", "one space", "blank info", List.of(), List.of()));

        // Invalid example
        assertThrows(IllegalArgumentException.class, () ->
                new CommandDetails("ok", "ok", "blank info", List.of(), List.of(), Prefixes.NAME));
    }

    @Test
    public void getFullCommandWord() {
        // Check that the sub word is always separated by a one space
        assertEquals(details.getFullCommandWord(), "Hello World");
    }

    @Test
    public void equals() {
        CommandDetails copiedDetails = new CommandDetailsBuilder(details).build();

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

        // Different command word -> false
        CommandDetails editedDetails = new CommandDetailsBuilder(detailsWithNoArgs).withCommandWord("goodbye").build();
        assertNotEquals(detailsWithNoArgs, editedDetails);

        // Different sub word -> false
        editedDetails = new CommandDetailsBuilder(detailsWithNoArgs).withSubWord("yellow").build();
        assertNotEquals(detailsWithNoArgs, editedDetails);

        // Different info -> false
        editedDetails = new CommandDetailsBuilder(detailsWithNoArgs).withInfo("This is the wrong info").build();
        assertNotEquals(detailsWithNoArgs, editedDetails);

        // Different parameters -> false
        editedDetails = new CommandDetailsBuilder(detailsWithNoArgs).withParameters(Prefixes.NAME).build();
        assertNotEquals(detailsWithNoArgs, editedDetails);

        // Different optionals -> false
        editedDetails = new CommandDetailsBuilder(detailsWithNoArgs).withOptionals(Prefixes.NAME).build();
        assertNotEquals(detailsWithNoArgs, editedDetails);

        // Different prefixes for examples -> false
        editedDetails = new CommandDetailsBuilder(detailsWithNoArgs)
                .withParameters(Prefixes.NAME)
                .withExamples(Prefixes.NAME).build();
        assertNotEquals(detailsWithNoArgs, editedDetails);

        editedDetails = new CommandDetailsBuilder(detailsWithNoArgs)
                .withOptionals(Prefixes.NAME)
                .withExamples(Prefixes.NAME).build();
        assertNotEquals(detailsWithNoArgs, editedDetails);
    }

    public static class CommandDetailsBuilder {
        public static final String DEFAULT_COMMAND_WORD = "Hello";
        public static final String DEFAULT_SUBWORD = "World";
        public static final String DEFAULT_INFO = "The first program of an aspiring programmer";

        private String commandWord;
        private String subWord;
        private String info;
        private List<Prefix> parameters;
        private List<Prefix> optionals;
        private List<Prefix> examples;

        public CommandDetailsBuilder() {
            commandWord = DEFAULT_COMMAND_WORD;
            subWord = DEFAULT_SUBWORD;
            info = DEFAULT_INFO;
            parameters = new ArrayList<>();
            optionals = new ArrayList<>();
            examples = new ArrayList<>();
        }

        public CommandDetailsBuilder(CommandDetails toCopy) {
            commandWord = toCopy.getCommandWord();
            subWord = toCopy.getSubWord();
            info = toCopy.getInfo();
            parameters = new ArrayList<>(toCopy.getPrefixDictionary().getParameters());
            optionals = new ArrayList<>(toCopy.getPrefixDictionary().getOptionals());
            examples = new ArrayList<>(toCopy.getPrefixesForExample());
        }

        /**
         * Sets the {@code commandWord} of the {@code CommandDetails} that we are building.
         */
        public CommandDetailsBuilder withCommandWord(String commandWord) {
            this.commandWord = commandWord;
            return this;
        }

        /**
         * Sets the {@code subWord} of the {@code CommandDetails} that we are building.
         */
        public CommandDetailsBuilder withSubWord(String subWord) {
            this.subWord = subWord;
            return this;
        }

        /**
         * Sets the {@code info} of the {@code CommandDetails} that we are building.
         */
        public CommandDetailsBuilder withInfo(String info) {
            this.info = info;
            return this;
        }

        /**
         * Sets the {@code parameters} of the {@code CommandDetails} that we are building.
         */
        public CommandDetailsBuilder withParameters(Prefix ... parameters) {
            this.parameters = List.of(parameters);
            return this;
        }

        /**
         * Sets the {@code optionals} of the {@code CommandDetails} that we are building.
         */
        public CommandDetailsBuilder withOptionals(Prefix ... optionals) {
            this.optionals = List.of(optionals);
            return this;
        }

        /**
         * Sets the {@code examples} of the {@code CommandDetails} that we are building.
         */
        public CommandDetailsBuilder withExamples(Prefix ... examples) {
            this.examples = List.of(examples);
            return this;
        }

        /**
         * Builds a {@code CommandDetails} for finer control of individual parameters in test cases.
         */
        public CommandDetails build() {
            return new CommandDetails(commandWord, subWord, info, parameters, optionals,
                    examples.toArray(Prefix[]::new));
        }
    }
}
