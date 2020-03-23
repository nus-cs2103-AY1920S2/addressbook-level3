package com.notably.logic.suggestion.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionItem;
import com.notably.testutil.ModelStubBase;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A Stub class for Model.
 */
public class ModelStub extends ModelStubBase {
    private ObservableList<SuggestionItem> suggestions;
    private Property<Optional<String>> responseTextProperty;
    private StringProperty input;

    public ModelStub() {
        suggestions = FXCollections.observableArrayList();
        responseTextProperty = new SimpleObjectProperty(Optional.empty());
        input = new SimpleStringProperty("");
    }

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
