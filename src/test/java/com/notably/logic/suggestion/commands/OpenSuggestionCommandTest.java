package com.notably.logic.suggestion.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.logic.suggestion.SuggestionCommand;
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
    private BlockTree blockTree = new BlockTreeStub();
    private AbsolutePath toRoot;
    private AbsolutePath toCs2103;
    private AbsolutePath toCs2103Week1;
    private AbsolutePath toCs2103Week1Lecture;
    private OpenSuggestionCommand openSuggestionCommand;

    @BeforeAll
    public void setUp() throws InvalidPathException {
        toRoot = AbsolutePath.fromString("/");
        toCs2103 = AbsolutePath.fromString("/CS2103");
        toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
        toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");
        openSuggestionCommand = new OpenSuggestionCommand(toRoot);
    }


    /**
     * Creates a tree with dummy values.
     */
    public void createTree() {
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

    @Test
    public void executeTest() {
        openSuggestionCommand = new OpenSuggestionCommand(toCs2103Week1);
        Model model = new ModelStub();
        openSuggestionCommand.execute(model);
        assertEquals("Open a note", model.responseTextProperty().getValue());

        List<AbsolutePath> possiblePaths = new ArrayList<>();
        possiblePaths.add(toCs2103Week1);
        possiblePaths.add(toCs2103Week1Lecture);
        List<SuggestionItem> suggestions = new ArrayList<>();
        suggestions.add(new SuggestionItemImpl("/CS2103/Week1", null));
        suggestions.add(new SuggestionItemImpl("/CS2103/Week1/Lecture", null));

    }
}
