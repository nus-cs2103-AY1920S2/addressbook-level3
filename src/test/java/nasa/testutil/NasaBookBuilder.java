package nasa.testutil;

import nasa.model.NasaBook;
import nasa.model.module.Module;

/**
 * A utility class to help with building NASA objects.
 * Example usage: <br>
 *     {@code NasaBook nb = new NasaBookBuilder().withModule("CS2103T", "Software Engineering").build();}
 */
public class NasaBookBuilder {

    private NasaBook nasaBook;

    public NasaBookBuilder() {
        nasaBook = new NasaBook();
    }

    public NasaBookBuilder(NasaBook nasaBook) {
        this.nasaBook = nasaBook;
    }

    /**
     * Add module to Nasabook.
     * @param module module
     * @return NasaBookBuilder
     */
    public NasaBookBuilder withModule(Module module) {
        nasaBook.addModule(module);
        return this;
    }

    /**
     * Build Nasabook.
     * @return NasaBook
     */
    public NasaBook build() {
        return nasaBook;
    }

}
