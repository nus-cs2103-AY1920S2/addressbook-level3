package cookbuddy.model.recipe.attribute;

import static cookbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import cookbuddy.commons.util.FileUtil;
import cookbuddy.model.recipe.Recipe;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Wrapper class for Path in {@code Recipe}.
 * Acts as the identity reference to a {@code Recipe}.
 */
public class Image {

    public static final String MESSAGE_CONSTRAINTS = "Image not found or invalid image path given";

    public final Path imageFilePath;

    /**
     * Constructs an {@code Image}.
     *
     * @param imageFilePath A valid file path.
     */
    public Image(String imageFilePath) {
        requireNonNull(imageFilePath);
        checkArgument(isValidImageFilePath(imageFilePath), MESSAGE_CONSTRAINTS);
        this.imageFilePath = Paths.get(imageFilePath);
    }

    /**
     * Returns true if the given string is a valid Path.
     */
    public static boolean isValidImageFilePath(String test) {
        // Paths.get() throws InvalidPathException when the path is invalid.
        // It is caught and returned as false.
        try {
            return FileUtil.isValidPath(test)
                && FileUtil.isFileExists(Paths.get(test));
        } catch (InvalidPathException e) {
            return false;
        }
    }

    /**
     * Returns the absolute path of an image from its given relative path.
     *
     * @param relativePath Relative path of an image.
     * @return the absolute path of an image.
     */
    public static String toAbsolutePath(String relativePath) {
        Path path = Paths.get(relativePath);
        Path absolutePath = path;
        if (!path.isAbsolute()) {
            Path base = Paths.get("");
            absolutePath = base.resolve(path).toAbsolutePath();
        }
        return absolutePath.normalize().toString();
    }

    /**
     * Converts an image from a BufferedImage to a WritableImage to be loaded in JavaFX.
     *
     * @param recipe A recipe.
     * @return A Writable Image.
     */
    public static WritableImage getWritableImage(Recipe recipe) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(String.valueOf(recipe.getImageFilePath().imageFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WritableImage wi = null;
        if (bi != null) {
            wi = new WritableImage(bi.getWidth(), bi.getHeight());
            PixelWriter pw = wi.getPixelWriter();
            for (int i = 0; i < bi.getWidth(); i = i + 1) {
                for (int j = 0; j < bi.getHeight(); j = j + 1) {
                    pw.setArgb(i, j, bi.getRGB(i, j));
                }
            }
        }
        return wi; //since Writeable Image extends Image, it can be loaded in JavaFX
    }


    @Override
    public String toString() {
        return imageFilePath.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Image // instanceof handles nulls
            && toString().equals(((Image) other).toString())); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
