package com.notably.logic.suggestion.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.model.Model;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;
import com.notably.testutil.ModelStubBase;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OpenSuggestionCommandTest {
    private static class ModelStub extends ModelStubBase {
        private ObservableList<SuggestionItem> suggestions;
        private Property<Optional<String>> responseTextProperty;
        private StringProperty input;

        public ModelStub() {
            suggestions = FXCollections.observableArrayList();
            responseTextProperty = new SimpleObjectProperty(Optional.empty());
            input = new SimpleStringProperty("");
        }

        @Override
        public Property<Optional<String>> responseTextProperty() {
            return responseTextProperty;
        }

        public void setResponseText(String responseText) {
            Objects.requireNonNull(responseText);
            responseTextProperty.setValue(Optional.of(responseText));
        }

        public ObservableList<SuggestionItem> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<SuggestionItem> suggestions) {
            Objects.requireNonNull(suggestions);
            this.suggestions.setAll(suggestions);
        }

        public String getInput() {
            return this.input.getValue();
        }

        public void setInput(String input) {
            this.input.setValue(input);
        }

        @Override
        public BlockTree getBlockTree() {
            BlockTree blockTree = new BlockTreeImpl();

            try {
                AbsolutePath toRoot = AbsolutePath.fromString("/");
                AbsolutePath toCs2103 = AbsolutePath.fromString("/CS2103");
                AbsolutePath toCs3230 = AbsolutePath.fromString("/CS3230");
                AbsolutePath toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
                AbsolutePath toCs2103Week2 = AbsolutePath.fromString("/CS2103/Week2");
                AbsolutePath toCs2103Week3 = AbsolutePath.fromString("/CS2103/Week3");
                AbsolutePath toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");

                Block cs2103 = new BlockImpl(new Title("CS2103"));
                Block cs3230 = new BlockImpl(new Title("CS3230"));
                Block week1 = new BlockImpl(new Title("Week1"));
                Block week2 = new BlockImpl(new Title("Week2"));
                Block week3 = new BlockImpl(new Title("Week3"));
                Block lecture = new BlockImpl(new Title("Lecture"));
                blockTree.add(toRoot, cs2103);
                blockTree.add(toRoot, cs3230);
                blockTree.add(toCs2103, week1);
                blockTree.add(toCs2103, week2);
                blockTree.add(toCs2103, week3);
                blockTree.add(toCs2103Week1, lecture);
            } catch (InvalidPathException e) {
                throw new AssertionError(e);
            }

            return blockTree;
        }
    }

    private static BlockTree blockTree;
    private static AbsolutePath toRoot;
    private static AbsolutePath toCs2103;
    private static AbsolutePath toCs3230;
    private static AbsolutePath toCs2103Week1;
    private static AbsolutePath toCs2103Week2;
    private static AbsolutePath toCs2103Week3;
    private static AbsolutePath toCs2103Week1Lecture;

    private Model model;

    @BeforeAll
    public static void setUp() throws InvalidPathException {
        toRoot = AbsolutePath.fromString("/");
        toCs2103 = AbsolutePath.fromString("/CS2103");
        toCs3230 = AbsolutePath.fromString("/CS3230");
        toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
        toCs2103Week2 = AbsolutePath.fromString("/CS2103/Week2");
        toCs2103Week3 = AbsolutePath.fromString("/CS2103/Week3");
        toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");
    }

    @BeforeEach
    public void initialize() {
        model = new ModelStub();
        blockTree = model.getBlockTree();
    }

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenSuggestionCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toRoot);
        assertThrows(NullPointerException.class, () -> openSuggestionCommand.execute(null));
    }

    @Test
    public void execute_displayText_test() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103);
        openSuggestionCommand.execute(model);

        assertEquals(Optional.of("Open a note"), model.responseTextProperty().getValue());

        // Expected result
        SuggestionItem cs2103 = new SuggestionItemImpl(toCs2103.getStringRepresentation(), null);
        SuggestionItem cs2103Week1Lecture = new SuggestionItemImpl(toCs2103Week1Lecture.getStringRepresentation(),
            null);
        SuggestionItem cs2103Week2 = new SuggestionItemImpl(toCs2103Week2.getStringRepresentation(), null);
        SuggestionItem cs2103Week3 = new SuggestionItemImpl(toCs2103Week3.getStringRepresentation(), null);

        List<SuggestionItem> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add(cs2103);
        expectedSuggestions.add(cs2103Week1Lecture);
        expectedSuggestions.add(cs2103Week2);
        expectedSuggestions.add(cs2103Week3);

        for (int i = 0; i < expectedSuggestions.size(); i++) {
            SuggestionItem suggestion = model.getSuggestions().get(i);
            SuggestionItem expectedSuggestion = expectedSuggestions.get(i);
            assertEquals(suggestion.getDisplayText(), expectedSuggestion.getDisplayText());
        }
    }

    @Test
    public void execute_action_test() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        openSuggestionCommand.execute(model);
        List<SuggestionItem> suggestions = model.getSuggestions();

        List<String> expectedInputs = new ArrayList<>();
        expectedInputs.add(toCs2103Week1.getStringRepresentation());
        expectedInputs.add(toCs2103Week1Lecture.getStringRepresentation());

        for (int i = 0; i < expectedInputs.size(); i++) {
            SuggestionItem suggestionItem = suggestions.get(i);
            String expectedInput = expectedInputs.get(i);
            suggestionItem.getAction().run();
            String input = model.getInput();
            assertEquals(expectedInput, input);
        }
    }

}
