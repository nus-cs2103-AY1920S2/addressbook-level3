package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.commons.compiler.parser.exceptions.InvalidLineException;

public class HeaderBlock extends Block {
    private static final Pattern PATTERN = Pattern.compile("^ *(?<hash>#{1,6}) *(?<text>(?<= +).*)?$");

    private final int level;
    private final String text;

    public HeaderBlock(int level, String text) {
        super(false);

        Objects.requireNonNull(text);

        this.level = level;
        this.text = text;
    }

    public static boolean isHeader(String line) {
        Objects.requireNonNull(line);

        return PATTERN.matcher(line).lookingAt();
    }

    public static HeaderBlock fromLine(String line) {
        Objects.requireNonNull(line);

        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.find()) {
            throw new InvalidLineException(String.format("\"%s\" is not a valid header", line));
        }

        int level = matcher.group("hash").length();
        String text = matcher.group("text").trim();
        if (text == null) {
            text = "";
        }

        return new HeaderBlock(level, text);
    }

    @Override
    public boolean next(String line) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toHtml() {
        return String.format("<h%1$d>%2$s</h%1$d>", level, text);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HeaderBlock)) {
            return false;
        }

        HeaderBlock another = (HeaderBlock) object;
        return super.equals(another)
                && level == another.level
                && Objects.equals(text, another.text);
    }
}

