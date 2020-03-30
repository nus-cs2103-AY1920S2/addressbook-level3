package com.notably.commons.compiler.parser.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

public abstract class Block {
    private List<Block> children;
    private boolean open;

    public Block() {
        this(true);
    }

    public Block(boolean open) {
        this.open = open;
        children = new ArrayList<>();
    }

    public static boolean canCreateStructuralBlock(String line) {
        return HeaderBlock.isHeader(line)
                || ListBlock.isList(line)
                || ParagraphBlock.isParagraph(line);
    }

    public static Block createStructuralBlock(String line) {
        Objects.requireNonNull(line);

        if (!canCreateStructuralBlock(line)) {
            throw new InvalidLineException("No structural block can be created from \"%s\"");
        }

        if (HeaderBlock.isHeader(line)) {
            return HeaderBlock.fromLine(line);
        }
        if (ListBlock.isList(line)) {
            return ListBlock.fromLine(line);
        }
        return ParagraphBlock.fromLine(line);
    }

    public abstract boolean next(String line);

    public abstract String toHtml();

    public List<Block> getChildren() {
        return children;
    }

    public void addChild(Block child) {
        children.add(child);
    }

    public boolean isOpen() {
        return open;
    }

    public void close() {
        open = false;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Block)) {
            return false;
        }

        Block another = (Block) object;
        return Objects.equals(children, another.children);
    }
}

