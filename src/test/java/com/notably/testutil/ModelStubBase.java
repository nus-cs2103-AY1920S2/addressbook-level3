package com.notably.testutil;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.notably.commons.core.GuiSettings;
import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.ReadOnlyAddressBook;
import com.notably.model.ReadOnlyUserPrefs;
import com.notably.model.block.Block;
import com.notably.model.block.BlockTree;
import com.notably.model.block.Body;
import com.notably.model.suggestion.SuggestionItem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Base {@link Model} stub that overrides all methods from {@link Model}
 * to throw {@link UnsupportedOperationException}.
 */
public class ModelStubBase implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void hasPerson(Object person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deletePerson(Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPerson(Object person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPerson(Object target, Object editedPerson) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ObservableList<Object> getFilteredPersonList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Object> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ObservableList<SuggestionItem> getSuggestions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSuggestions(List<SuggestionItem> suggestions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Property<Optional<String>> responseTextProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setResponseText(String responseText) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearResponseText() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearSuggestions() {
        throw new UnsupportedOperationException();
    }

    //=========== ViewStateModel ==================================================================
    @Override
    public StringProperty inputProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInput(String input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BooleanProperty boolProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getBool() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBool(Boolean bool) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockTree getBlockTree() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbsolutePath getCurrentlyOpenPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Property<AbsolutePath> getCurrentlyOpenPathProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPath(AbsolutePath p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCurrentlyOpenBlock(AbsolutePath p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addBlockToCurrentPath(Block b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeBlock(AbsolutePath p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCurrentlyOpenBlockBody(Body newBody) {
        throw new UnsupportedOperationException();
    }
}

