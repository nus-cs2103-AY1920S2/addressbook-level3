package com.notably.commons.core.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * TODO: Add Javadoc
 */
public class AbsolutePath implements Path {
    public static final String INVALIDABSOLUTEPATH = "Invalid absolute path";
    private final List<String> paths;


    public AbsolutePath(String absolutePathString) {
        this.paths = new ArrayList<>();
        for (String obj : absolutePathString.split("/")) {
            if (!obj.trim().equals("")) {
                this.paths.add(obj.trim());
            }
        }
    }

    /**
     * Check if string is a ValidAbsolutePath
     * @param absolutePathString
     * @return
     */
    public static boolean isValidAbsolutePath(String absolutePathString) {
        if (absolutePathString.charAt(0) == '/') {
            if (absolutePathString.contains("../")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates an absolute path from a string.
     * @param absolutePathString used to create an absolutePath.
     * @return Absolute Path.
     * @throws InvalidPathException if String provided is not a valid absolutePath.
     */
    public static AbsolutePath fromString(String absolutePathString) throws InvalidPathException {
        if (isValidAbsolutePath(absolutePathString)) {
            return new AbsolutePath(absolutePathString);
        } else {
            throw new InvalidPathException(INVALIDABSOLUTEPATH);
        }
    }

    /**
     * Convert from relative path to absolute path.
     * @param relativePath used to convert to absolute path.
     * @param currentWorkingPath of the current working directory.
     * @return the converted AbsolutePath.
     * @throws InvalidPathException
     */
    public static AbsolutePath fromRelativePath(RelativePath relativePath,
            AbsolutePath currentWorkingPath) throws InvalidPathException {
        List<String> temp = new ArrayList<>(relativePath.getComponents());
        List<String> temp2 = new ArrayList<>(currentWorkingPath.getComponents());
        temp2.addAll((temp));
        return new AbsolutePath(String.join("/", temp2));
    }

    /**
     * Convert Absolute path to Relative path.
     * @param currentWorkingPath of the current working directory.
     * @return the converted RelativePath
     */
    public RelativePath toRelativePath(AbsolutePath currentWorkingPath) {
        // TODO: Add implementation
        int sizeOfCurrent = currentWorkingPath.getComponents().size();
        List<String> temp = this.paths.subList(sizeOfCurrent, this.paths.size());
        String relativePathString = String.join("/", temp);
        return new RelativePath(relativePathString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getComponents() {
        return this.paths;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Path)) {
            return false;
        }

        Path another = (Path) object;
        List<String> temp = another.getComponents();
        return this.paths.equals(temp);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.paths);
    }

    @Override
    public String toString() {
        return (Arrays.toString(this.paths.toArray()));
    }
}

