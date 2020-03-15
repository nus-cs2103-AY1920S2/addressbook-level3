package com.notably.model.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Block in the Notably data structure.
 */
public class BlockImpl implements Block {
    private Block parent;
    private Title title;
    private Body body;
    private List<Block> children;


    /**
     * Initializes a root block i.e. the block has no parent.
     * @param title
     */
    public BlockImpl(Title title) {
        this(null, title, new Body(""));
    }

    /**
     * Initializes a block without the body.
     * Used when creating a block without the optional body argument.
     */
    public BlockImpl(Block parent, Title title) {
        this(parent, title, new Body(""));
    }

    /**
     * Initializes a block with body content.
     * Used when creating a block with additional body argument.
     */
    public BlockImpl(Block parent, Title title, Body body) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(body);
        this.title = title;
        this.parent = parent;
        this.body = body;
        this.children = new ArrayList<Block>();
    }

    @Override
    public Title getTitle() {
        return this.title;
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    @Override
    public Optional<Block> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public List<Block> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<Block> newChildren) {
        Objects.requireNonNull(newChildren);
        this.children = newChildren;
    }

    @Override
    public Optional<Block> getChild(Title title) {
        Objects.requireNonNull(title);
        Optional<Block> child = this.children.stream()
            .filter(block -> title.equals(block.getTitle()))
            .findAny();
        return child;
    }

    @Override
    public void setChild(Title title, Block newBlock) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(newBlock);
        Optional<Block> child = this.children.stream()
            .filter(block -> title.equals(block.getTitle()))
            .findAny();
        if (child.isPresent()) {
            int childIndex = this.children.indexOf(child.get());
            this.children.set(childIndex, newBlock);
        }
    }

    @Override
    public void addChild(Block block) {
        this.children.add(block);
    }

    @Override
    public void removeChild(Block toRemove) {
        this.children.remove(toRemove);
    }

    @Override
    public boolean isRoot() {
        return this.title.getText().equals("root")
            && this.parent == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Block)) {
            return false;
        }

        Block otherBlock = (Block) o;
        return otherBlock.getTitle().equals(this.getTitle())
            && otherBlock.getParent().equals(this.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getParent(), this.getTitle());
    }
}
