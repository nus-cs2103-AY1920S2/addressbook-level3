package seedu.address.commons.trie;

import java.util.ArrayList;

/**
 * This is a wrapper class to hold the information returned by
 * {@code Trie#listAllSimilarWords}. The member {@code longestPrefixString} will
 * contain the longest common prefix of the words in {@code similarWords}.
 */
public class SimilarWordsResult {
    public final String longestPrefixString;
    public final ArrayList<String> similarWords;

    public SimilarWordsResult(String longestPrefixString, ArrayList<String> similarWords) {
        this.longestPrefixString = longestPrefixString;
        this.similarWords = similarWords;
    }
}
