package com.notably.commons.core.path;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * Test for RelativePath.
 */
class RelativePathTest {

    @Test
    public void createRelativePath_validInputString_generateAbsolutePath() {
        try {
            final RelativePath testInput = RelativePath.fromString("CS2103/notes");
            List<String> paths = new ArrayList<>();
            paths.add("CS2103");
            paths.add("notes");
            assertEquals(paths, testInput.getComponents());
        } catch (InvalidPathException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void createAbsolutePath_invalidInputString_exceptionThrown() {
        assertThrows(InvalidPathException.class, () -> RelativePath.fromString("/CS2103/notes"));
    }

    @Test
    public void convertRelativeToAbsolutePath_validInput_correctedPath() throws InvalidPathException {
        final RelativePath inputRelativePath = RelativePath.fromString("CS2103/notes/hello");
        final AbsolutePath inputCurrPath = AbsolutePath.fromString("/CS2103");

        final AbsolutePath expectedOutput = AbsolutePath.fromString("/notes/hello");

        assertEquals(expectedOutput, inputRelativePath.toAbsolutePath(inputCurrPath));
    }

    @Test
    public void convertRelativePath_invalidInput_exceptionThrown() throws InvalidPathException {
            final RelativePath inputRelativePath = RelativePath.fromString("../../notes/hello");
            final AbsolutePath inputCurrPath = AbsolutePath.fromString("/CS2103");

            assertThrows(InvalidPathException.class, () -> inputRelativePath.toAbsolutePath(inputCurrPath));
    }
}
