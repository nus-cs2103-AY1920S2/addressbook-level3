package com.notably.commons.core.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * TODO: Add Javadoc
 */
public class AbsolutePath implements Path {
    public static final String INVALID_ABSOLUTE_PATH = "Invalid absolute path";
    public static final String VALIDATION_REGEX = "(\\/(\\p{Alnum}+))+\\/?";
    private final List<String> paths;

    private AbsolutePath(String absolutePathString) {
        this.paths = new ArrayList<>();
        for (String obj : absolutePathString.split("/")) {
            if (!obj.trim().equals("")) {
                this.paths.add(obj.trim());
            }
        }
    }

    public AbsolutePath(List<String> absolutePathList) {
        this.paths = absolutePathList;
    }

    /**
     * Check if a supplied string is a ValidAbsolutePath
     * @param absolutePathString
     * @return
     */
    public static boolean isValidAbsolutePath(String absolutePathString) {
        return absolutePathString.matches(VALIDATION_REGEX);
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
            throw new InvalidPathException(INVALID_ABSOLUTE_PATH);
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
        for (String obj : temp) {
            if (obj.equals("..")) {
                if (temp.remove(temp2.size() - 1) == null) {
                    throw new InvalidPathException("Empty directory");
                }
            } else if (obj.equals(".")) {
                //Do nothing
            } else {
                temp.add(obj);
            }
        }
        return new AbsolutePath(temp);
    }

    /**
     * Convert Absolute path to Relative path.
     * @param currentWorkingPath of the current working directory.
     * @return the converted RelativePath
     */
    public RelativePath toRelativePath(AbsolutePath currentWorkingPath) throws InvalidPathException {
        int sizeOfCurrent = currentWorkingPath.getComponents().size();
        if (sizeOfCurrent < this.getComponents().size()) {
            throw new InvalidPathException("Current working path bigger than AbsolutePath");
        }
        List<String> temp = this.paths.subList(sizeOfCurrent, this.paths.size());
        String relativePathString = String.join("/", temp);
        return RelativePath.fromString(relativePathString);
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
        return "/" + String.join("/", this.paths);
    }
}

