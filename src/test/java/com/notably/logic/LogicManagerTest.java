package com.notably.logic;

import static com.notably.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.userpref.UserPrefModelImpl;
import com.notably.model.viewstate.ViewStateModelImpl;
import com.notably.storage.JsonBlockStorage;
import com.notably.storage.JsonUserPrefsStorage;
import com.notably.storage.StorageManager;

public class LogicManagerTest {
    @TempDir
    public Path testFolder;

    private StorageManager storageManager;
    private Model model = new ModelManager(
            new BlockModelImpl(),
            new SuggestionModelImpl(),
            new ViewStateModelImpl(),
            new UserPrefModelImpl());
    private Logic logic;

    @BeforeEach
    public void setUp() throws java.io.IOException {
        JsonBlockStorage blockStorage = new JsonBlockStorage(getTempFilePath("blocks"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(blockStorage, userPrefsStorage);
        logic = new LogicManager(model, storageManager);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertThrows(ParseException.class, () -> logic.execute(invalidCommand));
    }

    @Test
    public void execute_invalidCommand_throwsCommandException() {
        String invalidCommand = "delete /";
        assertThrows(CommandException.class, () -> logic.execute(invalidCommand));
    }

    @Test
    public void () {
        String invalidCommand = "delete /";
        assertThrows(CommandException.class, () -> logic.execute(invalidCommand));
    }


}
