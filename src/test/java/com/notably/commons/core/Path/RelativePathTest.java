package com.notably.commons.core.path;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * Test for RelativePath.
 */
class RelativePathTest {

    @Test
    public void createRelativePath() {
        assertThrows(InvalidPathException.class, () -> RelativePath.fromString("/CS2103/notes"));
        try {
            assertEquals(new RelativePath("CS2103/notes"), RelativePath.fromString("CS2103/notes"));
        } catch (InvalidPathException ex) {
            System.out.println("Error");
        }

    }


    @Test
    public void convertRelativePath() {
        AbsolutePath absolutePath = new AbsolutePath("/CS2103/notes/hello");
        AbsolutePath currPath = new AbsolutePath("/CS2103");
        RelativePath relativePath = new RelativePath("notes/hello");
        try {
            RelativePath expectedRelative = new RelativePath("notes/hello");
            assertEquals(expectedRelative, relativePath.fromAbsolutePath(absolutePath, currPath));
            assertEquals(new AbsolutePath("/CS2103/notes/hello"), relativePath.toAbsolutePath(currPath));
        } catch (InvalidPathException ex) {
            System.out.println("Error");
        }

    }
}
