package com.notably.logic.suggestion.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.suggestion.block.BlockTreeStub;
import com.notably.logic.suggestion.model.ModelStub;
import com.notably.model.Model;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;

public class OpenSuggestionCommandTest {
    private BlockTree blockTree;
    private AbsolutePath toRoot;
    private AbsolutePath toCs2103;
    private AbsolutePath toCs3230;
    private AbsolutePath toCs2103Week1;
    private AbsolutePath toCs2103Week2;
    private AbsolutePath toCs2103Week3;
    private AbsolutePath toCs2103Week1Lecture;
    private Model model;

    @BeforeAll
    public void setUp() throws InvalidPathException {
        toRoot = AbsolutePath.fromString("/");
        toCs2103 = AbsolutePath.fromString("/CS2103");
        toCs3230 = AbsolutePath.fromString("/CS3230");
        toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
        toCs2103Week2 = AbsolutePath.fromString("/CS2103/Week2");
        toCs2103Week3 = AbsolutePath.fromString("/CS2103/Week3");
        toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");
    }

    @BeforeEach
    /**
     * Creates a tree with dummy values.
     */
    public void createTree() {
        blockTree = new BlockTreeStub();
        Block cs2103 = new BlockImpl(new Title("CS2103"));
        Block cs3230 = new BlockImpl(new Title("CS3230"));
        Block week1 = new BlockImpl(new Title("Week1"));
        Block week2 = new BlockImpl(new Title("Week2"));
        Block week3 = new BlockImpl(new Title("Week3"));
        Block lecture = new BlockImpl(new Title("lecture"));
        blockTree.add(toRoot, cs2103);
        blockTree.add(toRoot, cs3230);
        blockTree.add(toCs2103, week1);
        blockTree.add(toCs2103, week2);
        blockTree.add(toCs2103, week3);
        blockTree.add(toCs2103Week1, lecture);
    }

    @BeforeEach
    public void initializeModel() {
        model = new ModelStub();
    }

    @AfterEach
    public void clearTree() {
        blockTree = new BlockTreeStub();
    }

    @AfterEach
    public void clearModel() {
        model = new ModelStub();
    }

    @Test
    public void setResponseTextToModelTest() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        openSuggestionCommand.execute(model);
        assertEquals("Open a note", model.responseTextProperty().getValue());
    }

    @Test
    public void setSuggestionsToModelTest() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        List<AbsolutePath> possiblePaths = new ArrayList<>();
        possiblePaths.add(toCs2103Week1);
        possiblePaths.add(toCs2103Week1Lecture);
        List<SuggestionItem> suggestions = new ArrayList<>();
        suggestions.add(new SuggestionItemImpl("/CS2103/Week1", null));
        suggestions.add(new SuggestionItemImpl("/CS2103/Week1/Lecture", null));
        assertEquals("/CS2103/Week1", model.getSuggestions().get(0).getDisplayText());
        assertEquals("/CS2103/Week1/Lecture", model.getSuggestions().get(0).getDisplayText());
    }

    @Test
    public void getPossiblePaths_fromRoot_test() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toRoot);
        List<AbsolutePath> possiblePathsFromCommand = openSuggestionCommand.getPossiblePaths(toRoot, model);
        List<AbsolutePath> possiblePaths = new ArrayList<>();
        possiblePaths.add(toCs2103);
        possiblePaths.add(toCs3230);
        possiblePaths.add(toCs2103Week1);
        possiblePaths.add(toCs2103Week2);
        possiblePaths.add(toCs2103Week3);
        possiblePaths.add(toCs2103Week1Lecture);

        for (int i = 0; i < possiblePaths.size(); i++) {
            assertTrue(possiblePathsFromCommand.get(i).equals(possiblePaths.get(i)));
        }
    }

    @Test
    public void getSuggestionsTest() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        List<AbsolutePath> possiblePaths = new ArrayList<>();
        possiblePaths.add(toCs2103Week1);
        possiblePaths.add(toCs2103Week1Lecture);
        List<SuggestionItem> suggestions = openSuggestionCommand.getSuggestions(possiblePaths, model);
        assertEquals("/CS2103/Week1", suggestions.get(0).getDisplayText());
        assertEquals("/CS2103/Week1/Lecture", suggestions.get(1).getDisplayText());
    }
}
