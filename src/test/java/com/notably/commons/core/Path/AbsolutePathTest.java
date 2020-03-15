package com.notably.commons.core.path;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.exceptions.InvalidPathException;

class AbsolutePathTest {

    @Test
    public void createAbsolutePath() {
        assertThrows(InvalidPathException.class, () -> AbsolutePath.fromString("CS2103/notes"));
        try {
            assertEquals(new AbsolutePath("/CS2103/notes"), AbsolutePath.fromString("/CS2103/notes"));
        } catch (InvalidPathException ex) {
            System.out.println("Error");
        }

    }


    @Test
    public void convertAbsolutePath() {
        AbsolutePath absolutePath = new AbsolutePath("/CS2103/notes/hello");
        AbsolutePath currPath = new AbsolutePath("/CS2103");
        RelativePath relativePath = new RelativePath("notes/hello");
        try {
            AbsolutePath expectedAbsolutePath = new AbsolutePath("/CS2103/notes/hello");
            assertEquals(expectedAbsolutePath, AbsolutePath.fromRelativePath(relativePath, currPath));
            assertEquals(new RelativePath("notes/hello"), absolutePath.toRelativePath(currPath));
        } catch (InvalidPathException ex) {
            System.out.println("Error");
        }

    }
}
