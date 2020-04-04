package seedu.address.commons.trie;

import java.util.ArrayList;

/**
 * This is a wrapper class to hold the information returned by
 * {@code Trie#listAllSimilarWords}.
 */
public class SimilarWordsResult {
    public final String longestPrefixString;
    public final ArrayList<String> similarWords;

    public SimilarWordsResult(String longestPrefixString, ArrayList<String> similarWords) {
        this.longestPrefixString = longestPrefixString;
        this.similarWords = similarWords;
    }
}
