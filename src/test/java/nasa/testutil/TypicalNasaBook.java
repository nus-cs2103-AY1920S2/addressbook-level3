package nasa.testutil;

import nasa.model.NasaBook;

/**
 * Class for a NasaBook.
 */
public class TypicalNasaBook {
    public static final NasaBook NASABOOK_TYPE_1 = new NasaBookBuilder().build().deepCopyNasaBook();

    private TypicalNasaBook() {} // prevents instantiation
}
