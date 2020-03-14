package com.notably.model.block;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a path to a @link Block.
 */
public class Path {
    private List<String> paths;
    private boolean isAbsolute;

    public Path(String args) {
        this.paths = new ArrayList<>();
        if (args.charAt(0) == '/') {
            this.isAbsolute = true;
        } else {
            this.isAbsolute = false;
        }
        for (String obj : args.split("/")) {
            paths.add(obj.trim());
        }
    }

    public List<String> getPaths() {
        return this.paths;
    }

    public boolean isAbsolute() {
        return this.isAbsolute;
    }

}
