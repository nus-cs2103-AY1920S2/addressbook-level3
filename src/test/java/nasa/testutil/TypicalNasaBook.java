package nasa.testutil;

import nasa.model.NasaBook;

public class TypicalNasaBook {
    public static final NasaBook NASABOOK_TYPE_1 = new NasaBookBuilder().build();

    private TypicalNasaBook() {} // prevents instantiation
}
