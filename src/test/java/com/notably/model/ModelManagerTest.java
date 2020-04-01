package com.notably.model;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.notably.commons.GuiSettings;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.userpref.UserPrefModel;
import com.notably.model.userpref.UserPrefModelImpl;
import com.notably.model.viewstate.ViewStateModelImpl;

public class ModelManagerTest {
    private ModelManager modelManager = new ModelManager(
        new BlockModelImpl(),
        new SuggestionModelImpl(),
        new ViewStateModelImpl(),
        new UserPrefModelImpl());

    @Test
    public void constructor() {
        assertEquals(new UserPrefModelImpl(), modelManager.getUserPrefModel());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new BlockTreeImpl(), modelManager.getBlockTree());
    }

    @Test
    public void setUserPrefModel_nullUserPrefModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefModel(null));
    }

    @Test
    public void setUserPrefModel_validUserPrefModel_copiesUserPrefModel() {
        UserPrefModel userPrefModel = new UserPrefModelImpl();
        userPrefModel.setBlockDataFilePath(Paths.get("block/tree/file/path"));
        userPrefModel.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefModel(userPrefModel);
        assertEquals(userPrefModel, modelManager.getUserPrefModel());

        // Modifying userPrefModel should not modify modelManager's userPrefModel
        UserPrefModel oldUserPrefModel = new UserPrefModelImpl(userPrefModel);
        userPrefModel.setBlockDataFilePath(Paths.get("new/block/tree/file/path"));
        assertEquals(oldUserPrefModel, modelManager.getUserPrefModel());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setBlockTreeFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setBlockDataFilePath(null));
    }

    @Test
    public void setBlockTreeFilePath_validPath_setsBlockTreeFilePath() {
        Path path = Paths.get("block/tree/file/path");
        modelManager.setBlockDataFilePath(path);
        assertEquals(path, modelManager.getBlockDataFilePath());
    }
}
