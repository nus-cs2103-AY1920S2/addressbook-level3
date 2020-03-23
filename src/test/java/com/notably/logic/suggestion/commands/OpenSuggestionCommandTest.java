package com.notably.logic.suggestion.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.suggestion.model.ModelStub;
import com.notably.model.Model;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.model.suggestion.SuggestionItemImpl;

public class OpenSuggestionCommandTest {
    private static BlockTree blockTree;
    private static AbsolutePath toRoot;
    private static AbsolutePath toCs2103;
    private static AbsolutePath toCs3230;
    private static AbsolutePath toCs2103Week1;
    private static AbsolutePath toCs2103Week2;
    private static AbsolutePath toCs2103Week3;
    private static AbsolutePath toCs2103Week1Lecture;

    private Model model;
    private OpenSuggestionCommand openSuggestionCommand;

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
    public void setUpTestTree() {
        blockTree = new BlockTreeImpl();
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
    }

    @BeforeEach
    public void initializeModel() {
        model = new ModelStub();
    }

    @Test
    public void setSuggestionsToModelTest() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        List<AbsolutePath> possiblePaths = new ArrayList<>();
        possiblePaths.add(toCs2103Week1);
        possiblePaths.add(toCs2103Week1Lecture);
        List<SuggestionItem> suggestions = new ArrayList<>();
        suggestions.add(new SuggestionItemImpl("/CS2103/Week1", () -> model.setInput("/CS2103/Week1")));
        suggestions.add(new SuggestionItemImpl("/CS2103/Week1/Lecture", () ->
            model.setInput("/CS2103/Week1/Lecture")));
        openSuggestionCommand.setSuggestionsToModel(model, suggestions);
        assertEquals(suggestions.get(0), model.getSuggestions().get(0));
        assertEquals(suggestions.get(1), model.getSuggestions().get(1));
    }

    /* TODO: add after BlockModelImpl is done.
    @Test
    public void updatePossiblePaths_fromRoot_test() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toRoot);
        openSuggestionCommand.updatePossiblePaths(toRoot, model);
        List<AbsolutePath> possiblePathsFromCommand = openSuggestionCommand.getPossiblePaths();
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
    }*/

    /*
    @Test
    public void getChildRecursiveTest() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103);
        BlockTreeItem cs2103BlockTreeItem = blockTree.get(toCs2103);
        //openSuggestionCommand.getChildRecursive(cs2103BlockTreeItem, toCs2103.getComponents());
        List<AbsolutePath> possiblePaths = openSuggestionCommand.getPossiblePaths();

        List<AbsolutePath> expectedPossiblePaths = new ArrayList<>();
        expectedPossiblePaths.add(toCs2103);
        expectedPossiblePaths.add(toCs2103Week1);
        expectedPossiblePaths.add(toCs2103Week2);
        expectedPossiblePaths.add(toCs2103Week3);
        expectedPossiblePaths.add(toCs2103Week1Lecture);
        //assertEquals(expectedPossiblePaths, possiblePaths);
    }*/

    @Test
    public void getSuggestions_displayText_test() {
        OpenSuggestionCommand openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        List<AbsolutePath> possiblePaths = new ArrayList<>();
        possiblePaths.add(toCs2103Week1);
        possiblePaths.add(toCs2103Week1Lecture);
        List<SuggestionItem> suggestions = openSuggestionCommand.getSuggestions(possiblePaths, model);
        assertEquals("/CS2103/Week1", suggestions.get(0).getDisplayText());
        assertEquals("/CS2103/Week1/Lecture", suggestions.get(1).getDisplayText());
    }
}
