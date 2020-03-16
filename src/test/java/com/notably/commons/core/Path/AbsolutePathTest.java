package com.notably.commons.core.path;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.exceptions.InvalidPathException;


class AbsolutePathTest {

    @Test
    public void createAbsolutePath_validInputString_generateAbsolutePath() {
        try {
            final AbsolutePath testInput = AbsolutePath.fromString("/CS2103/notes");

            List<String> paths = new ArrayList<>();
            paths.add("CS2103");
            paths.add("notes");
            final AbsolutePath expectedOutput = new AbsolutePath(paths);

            assertEquals(expectedOutput, testInput);
        } catch (InvalidPathException ex) {
            ex.getMessage();
        }

    }

    @Test
    public void createAbsolutePath_invalidInputString1_exceptionThrown() {
        assertThrows(InvalidPathException.class, () -> AbsolutePath.fromString("CS2103/notes"));
    }

    @Test
    public void createAbsolutePath_invalidInputString2_exceptionThrown() {
        assertThrows(InvalidPathException.class, () -> AbsolutePath.fromString("/../CS2103/notes"));
    }

    @Test
    public void convertAbsoluteToRelativePath_validInput_correctedPath() {
        try {
            final AbsolutePath inputAbsolutePath = AbsolutePath.fromString("/CS2103/notes/hello");
            final AbsolutePath inputCurrPath = AbsolutePath.fromString("/CS2103");

            final RelativePath expectedOutput = RelativePath.fromString("notes/hello");

            assertEquals(expectedOutput, inputAbsolutePath.toRelativePath(inputCurrPath));
        } catch (InvalidPathException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void createAbsolutePathFromRelativePath_validInput_convertedAbsolutePath() {
        try {
            final RelativePath inputRelativePath = RelativePath.fromString("/../../hello");
            final AbsolutePath inputCurrPath = AbsolutePath.fromString("/CS2103/filler");

            List<String> expectedPaths = new ArrayList<>();
            expectedPaths.add("hello");

            assertEquals(expectedPaths, AbsolutePath.fromRelativePath(inputRelativePath,inputCurrPath).getComponents());
        } catch (InvalidPathException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void createAbsolutePathFromRelativePath_invalidInput_exceptionThrown() {
        try {
            final RelativePath inputRelativePath = RelativePath.fromString("/../../hello");
            final AbsolutePath inputCurrPath = AbsolutePath.fromString("/CS2103");
            assertThrows(InvalidPathException.class, () -> AbsolutePath.fromRelativePath(inputRelativePath,inputCurrPath));
        } catch (InvalidPathException ex) {
            ex.getMessage();
        }
    }
}
